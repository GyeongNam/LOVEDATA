## 소개 및 사이트 링크

   데이트 장소 & 코스 사이트

- [LOVEDATA 사이트](https://lovedata.duckdns.org/)

## 개발 인원

- [dorumam(최진웅)](https://github.com/dorumamu/)
- [mon0mon(이민기)](https://github.com/mon0mon/)
- [GyeongNam(조경남)](https://github.com/GyeongNam/)

## 개발 환웅
- 운영채제:   Linux (CentOS 7)<br>
- WAS:        Tomcat 9.0<br>
- framework:  Spring boot 2.44<br>
- DB:         MySQL 5.4<br>

## 구성도

![image](https://user-images.githubusercontent.com/63902992/143848772-c58e3f6e-cc04-4de9-ac54-977920ffc9b4.png)

## mon0mon(이민기)

JPA\
QurDSL\
GSON\

1. 로그인

    1. 사이트 내 회원가입한 유저 로그인
        - Spring Security를 활용하여 ID, PW로 로그인

    2. SNS 회원가입(카카오, 네이버)을 한 유저 로그인

        - 카카오 로그인 페이지로 이동
        
        [OAuthController](com.project.love_data.controller.OAuthController)
        ```java
        @GetMapping(value = "/login_kakao")
        public String kakaoLogin(
                HttpServletRequest request,
                HttpSessionCsrfTokenRepository csrfTokenRepository
        ) {
            // acessCodeRequest는 카카오 로그인 페이지로 이동하는 
            decodedURL = acessCodeRequest.excute(request, csrfTokenRepository);

            return "redirect:" + decodedURL;
        }
        ```

        - 토큰 정보 확인
        
        [OAuthController](https://github.com/mon0mon/LOVEDATA/blob/main/src/main/java/com/project/love_data/controller/OAuthController.java)

        ```java
        @RequestMapping(value = "/login_kakao/process")
        public String kakaoLogin_Process(
                HttpServletRequest request,
                HttpServletResponse response,
                RedirectAttributes redirectAttributes,
                HttpSession session,
                Model model
        ) throws IOException {}
        ```

        전달 받은 코드를 바탕으로 토큰을 요청
        이후에 해당 토큰으로 유저 정보를 가져옴
        ```java
        token = tokenRequest.excute(request);

        kakaoUserInfo = infoKakao.excute(request, token);
        ```

        유저의 이메일과 ID를 이용해서 로그인 시도
        ```java
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            kakaoUserInfo.getEmail(), kakaoUserInfo.getId() + "!@#$");
        ```

        등록된 정보가 없다면 회원가입 페이지로 이동
        ```java
        redirectAttributes.addFlashAttribute("social_info", "kakao");
            redirectAttributes.addFlashAttribute("social_id", kakaoUserInfo.getId());

            return "redirect:/signup";
        ```

2. 장소

    1. 추천 장소

    ![추천장소]()

    - 등록된 장소들 가운데 사람들이 가장 많이 보고 추천한 순으로 정렬

    - 이름과 위치, 태그를 통한 검색

    [LocationService](https://github.com/mon0mon/LOVEDATA/blob/main/src/main/java/com/project/love_data/businessLogic/service/LocationService.java)

    ```java
    public PageResultDTO<LocationDTO, Location> getList(PageRequestDTO requestDTO) {
        boolean flagASC = false;

        Pageable pageable택
        // 오름차순, 내림차순 선택
        switch (requestDTO.getSortingOrder()) {
            case ASC:
                flagASC = true;
                break;
            default:
                flagASC = false;
                break;
        }

        // 정렬 기준으로 정렬 (중복 X)
        switch (requestDTO.getSortCriterion()) {
            case DATE:
                // 생략
            case LIKE:
                // 생략
            case VIEW:
            default:
                if (flagASC) {
                    pageable = requestDTO.getPageable(Sort.by("viewCount").ascending());
                } else {
                    pageable = requestDTO.getPageable(Sort.by("viewCount").descending());
                }
                break;
        }

        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<Location> result = repository.findAll(booleanBuilder, pageable);

        Function<Location, LocationDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }
    ```

    2. 장소 등록

    ![장소등록]()

    - 로그인 후 장소 등록 가능

    [LocationController](https://github.com/mon0mon/LOVEDATA/blob/main/src/main/java/com/project/love_data/controller/service/LocationController.java)
    
    등록페이지에서 들어온 정보를 바탕으로 장소 등록 진행
    ```java
    @PostMapping("/service/loc_registration/regData")
    public String locRegistrationData(@RequestParam("files") List<MultipartFile> fileList,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        // 장소 이미지를 저장하고, 해당 경로를 가져옴
        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE, UploadFileCount.MULTIPLE
                MIN_UPLOAD_COUNT, MAX_UPLOAD_COUNT, PathType.LOC, request);

        // 장소 객체를 생성 후 DB에 저장
        Location entity = locService.register(reqParam, tagList, filePath);

        // 장소 등록을 완료한 후 추천 장소 페이지로 이동
        return "redirect:/service/loc_recommend";
    }
    ```

    3. 장소 편집

    ![장소 수정]()

    - 본인이 등록한 장소이거나 관리자일 경우 수정가능

    [LocationController](https://github.com/mon0mon/LOVEDATA/blob/main/src/main/java/com/project/love_data/controller/service/LocationController.java)

    ```java
        @PostMapping("/service/loc_edit/regData")
        public String locEditData(@RequestParam("files") List<MultipartFile> fileList,
                HttpServletRequest request,
                RedirectAttributes redirectAttributes) {
        // 장소 수정 정보에 입력된 장소가 맞는지 확인하는 과정
        if (loc_no_Test == null || loc_uuid_Test == null || !loc_uuid_Test.equals(loc_no_Test)) {
            return "redirect:/service/loc_recommend";
        }

        // 수정된 이미지 파일을 업로드
        // 기존과 동일하다면 넘어감
        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE, UploadFileCount.MULTIPLE,
                MIN_UPLOAD_COUNT, MAX_UPLOAD_COUNT, PathType.LOC, request);

        LocationDTO dto = locService.entityToDto(locService.edit(reqParam, tagList, filePath));

        return "redirect:/service/loc_recommend";
    }
    ```

    4. 지역별 장소

    ![지역별 장소]()

    - 등록된 장소 중에 지역별 장소만을 골라서 보는 메뉴

    

3. 코스

    1. 추천 코스

    ![추천 코스]()

    - 추천 장소와 동일한 기능

    2. 코스 등록

    ![코스 등록]()

    - 등록된 장소를 기반으로 코스를 등록

    - 최소 2개 이상의 장소를 포함하고, 로그인 해야 등록 가능

    ```java
        @PostMapping("/service/cor_registration/regData")
        public String corRegistrationData(@RequestParam("files") List<MultipartFile> fileList,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        // 코스에 포함된 장소의 id를 모두 가져옴
        for (int i = 1; i <= Integer.parseInt(reqParam.get("location_length")); i++) {
            reqParam.put("loc_no_" + i, request.getParameter("loc_no_" + i));
            reqParam.put("loc_id_" + i, request.getParameter("loc_id_" + i));
        }

        // 코스와 관련된 이미지 등록
        filePath = fileUploadService.execute(fileList, UploadFileType.IMAGE, UploadFileCount.MULTIPLE,
                MIN_UPLOAD_COUNT, MAX_UPLOAD_COUNT, PathType.COR, request);

        Course entity = corService.register(reqParam, tagList, filePath);

        return "redirect:/service/cor_recommend";
    }
    ```

    3. 코스 편집

    - 장소 편집과 동일한 기능

    