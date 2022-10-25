## 소개 및 사이트 링크

   데이트 장소 & 코스 사이트

- [LOVEDATA 사이트](https://lovedata.duckdns.org/)

## 개발 인원

- [dorumamu](https://github.com/dorumamu/)
- [mon0mon](https://github.com/mon0mon/)
- [GyeongNam](https://github.com/GyeongNam/)

## 개발 환경
- 운영채제:   Linux (CentOS 7)<br>
- WAS:        Tomcat 9.0<br>
- framework:  Spring boot 2.44<br>
- DB:         MySQL 5.4<br>

## 구성도

![image](https://user-images.githubusercontent.com/63902992/143848772-c58e3f6e-cc04-4de9-ac54-977920ffc9b4.png)

## dorumamu

## mon0mon

## GyeongNam

해당 목록은 '경남' 이 만든 것만 설명되어있습니다.

참고 사이트:<br>
[FullCalendar](https://fullcalendar.io/)

[DataTables](https://datatables.net/)

[SmartEditor2](http://naver.github.io/smarteditor2/user_guide/)



   1. 일반 회원가입
  
      - form을 이용해 post 형식으로 UserController에서 user에 저장
  
      ```java
      @RequestMapping(value = "/signup_add", method = RequestMethod.POST)
      public String signup(
            @RequestParam(value = "str_email01") String email1,
            @RequestParam(value = "str_email02") String email2,
            @RequestParam(value = "userPwd") String pwd,
            @RequestParam(value = "nickname") String nickname,
            @RequestParam(value = "userName") String userName,
            @RequestParam(value = "str_phone01") String phone01,
            @RequestParam(value = "str_phone02") String phone02,
            @RequestParam(value = "str_phone03") String phone03,
            @RequestParam(value = "birthday") String birthday,
            @RequestParam(value = "gender") boolean gender,
            @RequestParam(value = "recv_email") boolean recv_email,
            @RequestParam(value = "social") boolean social,
            @RequestParam(value = "social_info") String social_info,
            @RequestParam(value = "profile_pic") String profile_pic,
            HttpServletRequest request
      ) {

         String tempStr = request.getParameter("social_id");
         int social_id = 0;
         if (tempStr == null || !tempStr.equals("")) {
            Integer.parseInt(tempStr);
         }
         User user = User.builder()
               .user_email(email1 + "@" + email2)
               .user_pw(passwordEncoder.encode(pwd))
               .user_nic(nickname)
               .user_name(userName)
               .user_phone(phone01 + phone02 + phone03)
               .user_birth(birthday)
               .user_sex(gender)
               .user_email_re(recv_email)
               .user_social(social)
               .social_info(social_info.equals("") ? "웹페이지" : social_info)
               .social_id(social_id)
               .build();
         user.addUserRole(UserRole.USER);
         log.info("profile_pic : " + profile_pic.length());
         if (profile_pic != null) {
            user.setProfile_pic(profile_pic);
         }
         if(profile_pic.length()==0){
            user.setProfile_pic("/image/icon/user/user.png");
         }

         userRepository.save(user);

         return "redirect:/";
      }
      ```
  
   2. 캘린더
      
      jsp에서 캘린더를 연결한다.
      
      ```html
      <link rel="stylesheet" href="/fullcalendar/bootstrap.min.css">
      <link rel="stylesheet" href='/fullcalendar/select.min.css' />
      <link rel="stylesheet" href='/fullcalendar/bootstrap-datetimepicker.min.css' />
      <link rel="stylesheet" href="/fullcalendar/fullcalendar.min.css" />
      <link rel="stylesheet" href="/fullcalendar/main.css">
         ...
      <div id="calendar"></div>
         ...
      <script src="/fullcalendar/jquery.min.js"></script>
      <script src="/fullcalendar/bootstrap.min.js"></script>
      <script src="/fullcalendar/moment.min.js"></script>
      <script src="/fullcalendar/select.min.js"></script>
      <script src="/fullcalendar/bootstrap-datetimepicker.min.js"></script>
      <script src="/fullcalendar/fullcalendar.min.js"></script>
      <script src="/fullcalendar/ko.js"></script>
      <script src="/fullcalendar/main.js"></script>
      ```
      fullcalendar/main.js에서 이벤트 등록한다. 서버와 통신은 비동기식인 ajax를 이용한다.
      ```js
      // 일정받아오기
      events: function (start, end, timezone, callback) {
         var token = $("meta[name='_csrf']").attr("content");
         var header = $("meta[name='_csrf_header']").attr("content");
         $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });

          $.ajax({
            type: "post",
            url: "/user/cal_all",
            data: {
            },
            success: function (response) {
                console.log(response);
                var arr = response;
                var events = [];
                $.each(arr, function(index, item){
                    events.push({
                        _id: item.cal_no,
                        title: item.title,
                        start: item.start,
                        end: item.end,
                        text: item.text,
                        username: item.user_mail, // 로그인 정보
                        road: item.road,
                        road2: item.road2,
                        color: item.color,
                        allDay: item.all_day
                    });
                });
                callback(events);
            }
          });
        },
      ```
      일정 등록
      ```js
      var newEvent = function (start, end, eventType) {

          $("#contextMenu").hide();

          modalTitle.html('일정 추가');

          editTitle.val('');
          editStart.val(start);
          editEnd.val(end);
          editDesc.val('');
          editadr.val('');
          editadr2.val('');

          addBtnContainer.show();
          modifyBtnContainer.hide();
          eventModal.modal('show');

          var eventId = 1 + Math.floor(Math.random() * 1000);

          //새로운 일정 저장버튼 클릭
          $('#save-event').unbind();
          $('#save-event').on('click', function () {

              var eventData = {
                  title: editTitle.val(),
                  start: editStart.val(),
                  end: editEnd.val(),
                  text: editDesc.val(),
                  road: editadr.val(),
                  road2: editadr2.val(),
                  color: editColor.val(),
                  allDay: editAllDay.val()
              };

              if (eventData.start > eventData.end) {
                  alert('끝나는 날짜가 앞설 수 없습니다.');
                  return false;
              }

              if (eventData.title === '') {
                  alert('일정명은 필수입니다.');
                  return false;
              }

              var realEndDay;

              if (editAllDay.is(':checked')) {
                  eventData.start = moment(eventData.start).format('YYYY-MM-DD');
                  eventData.end = moment(eventData.end).add(1, 'days').format('YYYY-MM-DD');
                  realEndDay = moment(eventData.end).format('YYYY-MM-DD');
                  eventData.allDay = true;
                  console.log(eventData);
              }

             
              calendar.fullCalendar('renderEvent', eventData, true);


              eventModal.find('input, textarea').val('');
              editAllDay.prop('checked', false);
              eventModal.modal('hide');

              var token = $("meta[name='_csrf']").attr("content");
              var header = $("meta[name='_csrf_header']").attr("content");
              $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });

              //새로운 일정 저장
              $.ajax({
                  url: "/user/cal_add",
                  dataType: 'json',
                  contentType: "application/json; charset=UTF-8",
                  data: JSON.stringify(eventData),
                  type: "POST",
                  success: function (response) {
                      location.reload();
                  }
              });
          });
      };
      ```
      일정 삭제
      ```js
      $('#deleteEvent').on('click', function () {

          $('#deleteEvent').unbind();
          // $("#calendar").fullCalendar('removeEvents', $(this).data('id'));
          calendar.fullCalendar('removeEvents', $(this).data('id'));
          eventModal.modal('hide');
          var eventdata = {
              _id: $(this).data('id')
          };
          var token = $("meta[name='_csrf']").attr("content");
          var header = $("meta[name='_csrf_header']").attr("content");
          $(document).ajaxSend(function(e, xhr, options) { xhr.setRequestHeader(header, token); });
          //삭제시
          $.ajax({
              url: "/user/cal_delete",
              dataType: 'json',
              contentType: "application/json; charset=UTF-8",
              data: JSON.stringify(eventdata),
              type: "POST",
              success: function (response) {
                  alert('삭제되었습니다.');
                  console.log(response);
              }
          });

      });
      ```
      
   3. 고객센터
      -공지사항
      
         페이지네이션을 직접 해보았다.
         페이지 하나당 15개 항목을 보여준다.
         ```java
         // 페이지네이션
           long j=0;

           if(notice.size()<15){
               model.addAttribute("noti",notice);
           }else {
               for (int i = 0; i < no_size; i++) {
                   notice_page = notice.subList(0,15);

                   if (i % 15 == 0) {
                       j = j + 1;
                       if (j == Long.parseLong(page)) {
                           model.addAttribute("noti",notice_page);
                           break;
                       } else {
                           notice.subList(0,15).clear();

                           if(notice.size()<15){
                               model.addAttribute("noti",notice);
                               break;
                           }
                       }
                   }
               }
           }
         ```
         공지사항 저장 시 기획에서 스마트 에디터 사용을 원해 네이버 스마트에디터2를 사용했다. jsp에 스마트 에디터를 연결한다.
         ```html 
         <script type="text/javascript" src="/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
               ...
            <textarea name="notice_content" id="smartEditor" style="width: auto; max-height: 10%"></textarea>
               ...
         <script src="/js/SmartEditor2.js"></script>
         ```
         SmartEditor2.js에서 스마티 에디터를 설정하고
         ```js
         var oEditors = [];
         nhn.husky.EZCreator.createInIFrame({
             oAppRef : oEditors,
             elPlaceHolder : "smartEditor",
             sSkinURI : "/se2/SmartEditor2Skin.html",
             fCreator : "createSEditor2",
             htParams : {
                 // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                 bUseToolbar : true,

                 // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
                 bUseVerticalResizer : false,

                 // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
                 bUseModeChanger : false
             }
         });
         ```
         저장 함수를 만들어준다.
         ```js
         function save(){
             oEditors.getById["smartEditor"].exec("UPDATE_CONTENTS_FIELD", []);
             //스마트 에디터 값을 텍스트컨텐츠로 전달
             var content = document.getElementById("smartEditor").value;
             $("#form_name").val(content);
             var title = $("#title").val();
             if (title.replace(/\s|　/gi, "").length == 0) {
                 alert("제목을 입력해주세요.");
                 $("#title").focus();
             }else {
                 $("Form").submit();
             }

             return;
         }
         ```
      -문의사항
      
         문의 사항도 form을 이용한 데이터 처리를 하였지만 관리자일 경우 답변이 필요하기 때문에 spring security와 jstl를 이용해서 관리자가 접근할 수 있게 했다.
         ```html
         <sec:authorize access="isAuthenticated()">
            <sec:authorize access="hasAnyRole('ADMIN')">
                관리자일 경우
            </sec:authorize>
            <sec:authorize access="!hasRole('ADMIN')">
                관리자가 아닐경우
            </sec:authorize>
         </sec:authorize>
         ```
      -정책
      
         해당 페이지는 js로 만든 탭 기능을 이용해 총직, 개인정보처리방침 등을 보여준다.
         
      -회원탈퇴
      
         비밀번호를 한번 확인하고 회원 탈퇴할 수 있게 하였다. 회원탈퇴일 경우 모든 회원의 데이터를 비활성화 시키고 사진파일은 임시휴지통으로 보낸다.
         ```java
         @PostMapping(value = "/lovedata_delete")
         public String lovedata_delete(HttpServletRequest request){
            String user_no = request.getParameter("user_no");
            User user = userService.select(Long.parseLong(user_no));
            List<Comment> comment = cmtService.findAllByUserNo(Long.parseLong(user_no));
            List<Review> reviews = reviewService.findAllByUser_no(Long.parseLong(user_no));
            List<ReviewImage> reviewImages = reviewImageService.getImage_UserNo(Long.parseLong(user_no));
            List<Questions> questions = serviceCenterService.qu_findAllByUser_no(user_no);
            List<QuestionsImage> questionsImages = questionsImageService.user_no_imgselect(user_no);

            List<Calender> calenders = calenderService.Cal_select(user.getUser_email());
            user.set_deleted(true);
            user.setUser_Activation(false);
            userService.update(user);
            for(int i = 0; i<comment.size(); i++){
               comment.get(i).set_deleted(true);
               cmtService.update(comment.get(i));
            }
            for(int i = 0; i<reviews.size(); i++){
               reviews.get(i).set_deleted(true);
               reviewService.update(reviews.get(i));
            }
            for(int i = 0; i<reviewImages.size(); i++){
               reviewImages.get(i).set_deleted(true);
               reviewImageService.update(reviewImages.get(i));

               try {
                  // 기존 이미지 review -> upload
                  String imgpath;
                  if ("Windows_NT".equals(System.getenv().get("OS"))) {
                     String r = request.getSession().getServletContext().getRealPath("/");
                     int idx = r.indexOf("main");
                     imgpath = r.substring(0, idx) + "main/resources/static" + reviewImages.get(i).getImg_url();

                     File file = FileUtils.getFile(imgpath);
                     File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/REV^" + reviewImages.get(i).getImg_uuid());
                     FileUtils.moveFile(file, fileToMove);
                  } else {
                     imgpath = Linux_Image_Upload_Path + "review/" + reviewImages.get(i).getImg_uuid();

                     File file = FileUtils.getFile(imgpath);
                     File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path + "upload/REV^" + reviewImages.get(i).getImg_uuid());
                     FileUtils.moveFile(file, fileToMove);
                  }

               } catch(IOException e){
                  e.printStackTrace();
               }
            }
            for(int i = 0; i<questions.size(); i++){
               questions.get(i).setQu_activation(false);
               serviceCenterService.qu_update(questions.get(i));
            }
            for(int i = 0; i<questionsImages.size(); i++){
               questionsImages.get(i).setQu_img_Activation(false);
               questionsImageService.update(questionsImages.get(i));

               try {
                  // 기존 이미지 qna -> upload
                  String imgpath;
                  if ("Windows_NT".equals(System.getenv().get("OS"))) {
                     String r = request.getSession().getServletContext().getRealPath("/");
                     int idx = r.indexOf("main");
                     imgpath = r.substring(0, idx) + "main/resources/static/image/qna/" + questionsImages.get(i).getQu_img_url();

                     File file = FileUtils.getFile(imgpath);
                     File fileToMove = FileUtils.getFile(r.substring(0, idx) + "main/resources/static/image/upload/QNA^" + questionsImages.get(i).getQu_img_url());
                     FileUtils.moveFile(file, fileToMove);
                  } else {
                     imgpath = Linux_Image_Upload_Path + "qna/" + questionsImages.get(i).getQu_img_url();

                     File file = FileUtils.getFile(imgpath);
                     File fileToMove = FileUtils.getFile(Linux_Image_Upload_Path + "upload/QNA^" + questionsImages.get(i).getQu_img_url());
                     FileUtils.moveFile(file, fileToMove);
                  }

               } catch(IOException e){
                  e.printStackTrace();
               }
            }
            for(int i = 0; i<calenders.size(); i++){
               calenders.get(i).setCal_Activation(false);
               calenderService.update(calenders.get(i));
            }
            return "redirect:/logout";
         }
         ```
   4. 관리자 -유저관리 
      
      유저의 모든 장소, 코스, 댓글, 리뷰, 정지상황등을 보여주며 유저를 정지시킬 수 있다.
      
      한 페이지 내에 테이블이 많아야하며 모두 페이지네이션이 되어야 했다. 
      
      해당문제를 해결하기 위해 데이터테이블을 이용했다.
      ```html
      <link rel="stylesheet" href=" https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css">
      <link rel="stylesheet" href=" https://cdn.datatables.net/1.11.3/css/dataTables.bootstrap5.min.css"/>
      <link rel="stylesheet" href=" https://cdn.datatables.net/rowgroup/1.1.4/css/rowGroup.bootstrap5.min.css"/>
        <table class="table-bordered table text-center tables" >
      <script defer src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
      <script defer src="https://cdn.datatables.net/1.11.3/js/dataTables.jqueryui.min.js"></script>
      <script defer src="https://cdn.datatables.net/1.11.3/js/dataTables.bootstrap5.min.js"></script>
      <script defer src="https://cdn.datatables.net/rowgroup/1.1.4/js/dataTables.rowGroup.min.js"></script>
      <script defer src="/js/datatable.js"></script>
      ```
      datatable.js에서 한국어로 변경해줬다.
      ```js
      $(document).ready(function() {
          $('.tables').DataTable( {
              language: {
                  "decimal":        "",
                  "emptyTable":     "데이터가 없습니다.",
                  "info":           "총 _TOTAL_중   _START_에서 _END_까지 표시",
                  "infoEmpty":      "0 개 항목 중 0 ~ 0 개 표시",
                  "infoFiltered":   "(_MAX_ 총 항목에서 필터링 됨)",
                  "infoPostFix":    "",
                  "thousands":      ",",
                  "lengthMenu":     "_MENU_",
                  "loadingRecords": "로드 중 ...",
                  "processing":     "처리 중 ...",
                  "search":         "검색:",
                  "zeroRecords":    "일치하는 레코드가 없습니다.",
                  "paginate": {
                      "first":      "처음",
                      "last":       "마지막",
                      "next":       "다음",
                      "previous":   "이전"
                  },
                  "aria": {
                      "sortAscending":  ": 오름차순으로 정렬",
                      "sortDescending": ": 내림차순으로 정렬"
                  }
              },
          } );
      } );
      ```
      
      정지기능또한 정지테이블에 추가시키며, 정지해제일이 다가왔을경우
      
      ```java
      @Component
      @EnableScheduling
      public class Scheculer implements ApplicationRunner {

          @Autowired
          UserService userService;

          @Override
          public void run(ApplicationArguments args) throws Exception {
              user_release();
          }

            //  초(0-59)   분(0-59)　　시간(0-23)　　일(1-31)　　월(1-12)　　요일(0-7)
          @Scheduled(cron = "* * 0 * * *", zone = "Asia/Seoul")
          public void user_release() throws ParseException {
              Date date = new Date();
              SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");


              List<UserSuspension> userSuspension = userService.findAllByprogress("1");
              for(int i = 0; i<userSuspension.size(); i++){
                  Date endDay = format1.parse(userSuspension.get(i).getEnd_day());
                  if(date.after(endDay)){
                      // 시간이 지났을 때
                      List<UserSuspension> end_userSuspension = userService.findStopByUser_no(userSuspension.get(i).getUser_no(),"1");
                      List<UserSuspension> re_userSuspension = userService.findStopByUser_no(userSuspension.get(i).getUser_no(),"2");

                      if(re_userSuspension.size() > 0){
                          end_userSuspension.get(0).setProgress("0");
                          userService.su_update(end_userSuspension.get(0));

                          java.util.Calendar cal_end = Calendar.getInstance();
                          cal_end.setTime(date);
                          cal_end.add(Calendar.DATE,Integer.parseInt(re_userSuspension.get(0).getStop_day()));

                          re_userSuspension.get(0).setProgress("1");
                          re_userSuspension.get(0).setStart_day(format1.format(date));
                          re_userSuspension.get(0).setEnd_day(format1.format(cal_end.getTime()));
                          userService.su_update(re_userSuspension.get(0));
                      }else {
                          end_userSuspension.get(0).setProgress("0");
                          userService.su_update(end_userSuspension.get(0));

                          User user = userService.select(end_userSuspension.get(0).getUser_no());
                          user.setUser_Activation(true);
                          userService.update(user);
                      }
                  }
              }
          }
      }
      ```
      스케줄러를 통해 자동으로 유저를 해제해준다.
      
      다만, 해제해줘야 할 유저의 정지기록이 더 있을경우 갱신시켜준다.
      
   5. 관리자 -공지사항 문의사항
      
      고객센터의 공지사항과 문의사항 기능과 거의 동일하다.