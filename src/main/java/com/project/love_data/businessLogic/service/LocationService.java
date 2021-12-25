package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.*;
import com.project.love_data.model.resource.CourseImage;
import com.project.love_data.model.resource.LocationImage;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.QLocation;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.CommentRepository;
import com.project.love_data.repository.LocationImageRepository;
import com.project.love_data.repository.LocationRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repository;
    private final LocationImageRepository imgRepository;
    private final LocationImageService imgService;
    private final CommentService cmtService;
    private final CommentRepository cmtRepository;
    private final DeletedImageInfoService deletedImageInfoService;

    public Location register(Map<String, String> reqParam, List<String> tagList, List<String> filePath) {
        LocationDTO dto = getLocationDto(reqParam, tagList);
        List<LocationImage> imgList = new ArrayList<>();
        Location entity = dtoToEntity(dto);

        repository.save(entity);

        for (int i = 0; i < filePath.size(); i += 2) {
            // filePath.get(i)  ==  Parent Folder (URI)
            // filePath.get(i+1)  ==  fileNames
            LocationImage locImage = imgService.getImageEntity(reqParam.get("user_no"), filePath.get(i), filePath.get(i + 1), entity, i / 2);
            LocationImage imgEntity = imgService.update(locImage);
            imgList.add(imgEntity);
        }

        entity.setImgSet(new HashSet<>(imgList));
        entity.setThumbnail(imgList.get(0).getImg_url());

        repository.save(entity);

        for (LocationImage locationImage : imgList) {
            imgRepository.save(locationImage);
        }

        log.info("entity : " + entity);

        return entity;
    }

    public Location dtoToEntity(LocationDTO dto) {
        Location entity = Location.builder()
                .loc_no(dto.getLoc_no())
                .loc_name(dto.getLoc_name())
                .loc_uuid(dto.getLoc_uuid())
                .user_no(dto.getUser_no())
                .fullRoadAddr(dto.getFullRoadAddr())
                .roadAddr(dto.getRoadAddr())
                .addrDetail(dto.getAddrDetail())
                .siDo(dto.getSiDo())
                .siGunGu(dto.getSiGunGu())
                .info(dto.getInfo())
                .tel(dto.getTel())
                .zipNo(dto.getZipNo())
                .tagSet(dto.getTagSet())
                .imgSet(new HashSet<>(dto.getImgList()))
                .cmtSet(new HashSet<>(dto.getCmtList()))
                .likeCount(dto.getLikeCount())
                .thumbnail(dto.getThumbnail())
                .viewCount(dto.getViewCount())
                .is_deleted(dto.is_deleted())
                .is_reported(dto.is_reported())
                .build();

        return entity;
    }

    public LocationDTO entityToDto(Location entity) {
        LocationDTO dto = LocationDTO.builder()
                .loc_no(entity.getLoc_no())
                .loc_name(entity.getLoc_name())
                .loc_uuid(entity.getLoc_uuid())
                .user_no(entity.getUser_no())
                .fullRoadAddr(entity.getFullRoadAddr())
                .roadAddr(entity.getRoadAddr())
                .addrDetail(entity.getAddrDetail())
                .siDo(entity.getSiDo())
                .siGunGu(entity.getSiGunGu())
                .info(entity.getInfo())
                .tel(entity.getTel())
                .zipNo(entity.getZipNo())
                .tagSet(entity.getTagSet())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .likeCount(entity.getLikeCount())
                .thumbnail(entity.getThumbnail())
                .viewCount(entity.getViewCount())
                .is_deleted(entity.is_deleted())
                .is_reported(entity.is_reported())
                .build();

        // Image List 변환 및 정렬
        // idx 기준 정렬
        List<LocationImage> tempList = new ArrayList<>();
        List<LocationImage> imgList = new ArrayList<>();
        boolean sortedFlag = false;
        int count = 0;

        for (LocationImage img :
                entity.getImgSet()) {
            tempList.add(img);
        }

//        for (int i = 0; i < tempList.size(); i++) {
//            for (int j = 0; j < tempList.size(); j++) {
//                if (tempList.get(j).getIdx() == i) {
//                    imgList.add(tempList.get(j));
//                    break;
//                }
//            }
//        }

        // idx가 전부 0일 경우 정리가 되지 않은 것이므로
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i).getIdx() == 0) {
                count++;
            }
        }

        // 플래그 설정
        if (count > 1) {
            sortedFlag = false;
        } else {
            sortedFlag = true;
        }

        if (!sortedFlag) {
            for (int i = 0; i < tempList.size(); i++) {
                tempList.get(i).setIdx((long) i);
                imgList.add(tempList.get(i));
            }
        } else {
            for (int i = 0; i < tempList.size(); i++) {
                for (int j = 0; j < tempList.size(); j++) {
                    if (tempList.get(j).getIdx() == i) {
                        imgList.add(tempList.get(j));
                        break;
                    }
                }
            }
        }


        // Comment List 변환 및 정렬
        // cmd_idx 기준 정렬
        List<Comment> tempCmtList = new ArrayList<>(entity.getCmtSet());
        List<Comment> cmtList = new ArrayList<>();
        Long cmtMaxCounter = cmtRepository.findMaxIdxByLoc_no(entity.getLoc_no());

        if (cmtMaxCounter == null) {
            cmtMaxCounter = 0L;
        }
        int maxCmtIndex = Math.toIntExact(cmtMaxCounter);
        int liveCmtCount = 0;

        for (int i = 0; i <= maxCmtIndex; i++) {
            for (int j = 0; j < tempCmtList.size(); j++) {
                if (tempCmtList.get(j).getCmtIdx() == i) {
                    cmtList.add(tempCmtList.get(j));
                    break;
                }
            }
        }

        for (Comment comment : cmtList) {
            if (!comment.is_deleted()) {
                liveCmtCount++;
            }
        }

        dto.setImgList(imgList);
        dto.setCmtList(cmtList);
        dto.setLiveCmtCount(liveCmtCount);

        return dto;
    }

    private LocationDTO getLocationDto(Map<String, String> reqParam, List<String> tagList) {
        LocationDTO loc = LocationDTO.builder()
                .loc_name(reqParam.get("name"))
                .user_no(Long.valueOf(reqParam.get("user_no")))
                .fullRoadAddr(reqParam.get("fullAddr"))
                .roadAddr(reqParam.get("roadAddr"))
                .addrDetail(reqParam.get("addrDetail"))
                .siDo(reqParam.get("siDoName"))
                .siGunGu(reqParam.get("siGunGuName"))
                .info(reqParam.get("info"))
                .tel(reqParam.get("tel"))
                .zipNo(reqParam.get("zipNo"))
                .build();

        for (String item : tagList) {
            loc.addLocTag(item);
        }

        return loc;
    }

//    public PageResultDTO<LocationDTO, Location> getList(PageRequestDTO requestDTO) {
//        return getList(requestDTO, SearchType.NONE,
//                SortCriterion.VIEW, SortingOrder.DES);
//    }

    public PageResultDTO<LocationDTO, Location> getList(PageRequestDTO requestDTO) {
//        Pageable pageable = requestDTO.getPageable(Sort.by("viewCount").descending());
        boolean flagASC = false;

        Pageable pageable;
        switch (requestDTO.getSortingOrder()) {
            case ASC:
                flagASC = true;
                break;
            default:
                flagASC = false;
                break;
        }

        switch (requestDTO.getSortCriterion()) {
            case DATE:
                if (flagASC) {
                    pageable = requestDTO.getPageable(Sort.by("regDate").ascending());
                } else {
                    pageable = requestDTO.getPageable(Sort.by("regDate").descending());
                }
                break;
            case LIKE:
                if (flagASC) {
                    pageable = requestDTO.getPageable(Sort.by("likeCount").ascending());
                } else {
                    pageable = requestDTO.getPageable(Sort.by("likeCount").descending());
                }
                break;
            case VIEW:
//                if (flagASC) {
//                    pageable = requestDTO.getPageable(Sort.by("viewCount").ascending());
//                } else {
//                    pageable = requestDTO.getPageable(Sort.by("viewCount").descending());
//                }
//                break;
            default:
                if (flagASC) {
                    pageable = requestDTO.getPageable(Sort.by("viewCount").ascending());
                } else {
                    pageable = requestDTO.getPageable(Sort.by("viewCount").descending());
                }
                break;
        }

        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<com.project.love_data.model.service.Location> result = repository.findAll(booleanBuilder, pageable);

//        switch (requestDTO.getSearchType()){
//            case USER:
//                booleanBuilder =
////                result = repository.findByAllUser_no(pageable);
//                break;
//            case TITLE:
//                result = repository.findAll(pageable);
//                break;
//            case NONE:
//            default:
//                result = repository.findAll(pageable);
//                break;
//        }

        Function<com.project.love_data.model.service.Location, LocationDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    public BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        Long userNo = requestDTO.getUserNo();
        Long locNo = requestDTO.getLocNo();
        String keyword = requestDTO.getKeyword();
        List<String> tagList = requestDTO.getTagList();
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        QLocation qLocation = QLocation.location;
        boolean isContainDeletedLocation = false;

        switch (requestDTO.getSearchType()) {
            case DISABLED_USER:
                isContainDeletedLocation = true;
            case USER:
                conditionBuilder.and(qLocation.user_no.eq(userNo));
                break;
            case USER_TAG:
                conditionBuilder.and(qLocation.loc_no.eq(userNo));
                for (String s : tagList) {
                    conditionBuilder.and(qLocation.tagSet.contains(s));
                }
                break;
            case DISABLED_TITLE:
                isContainDeletedLocation = true;
            case TITLE:
                conditionBuilder.and(qLocation.loc_name.contains(keyword));
                break;
            case DISABLED_TITLE_TAG:
                isContainDeletedLocation = true;
            case TITLE_TAG:
                conditionBuilder.and(qLocation.loc_name.contains(keyword));
                for (String s : tagList) {
                    conditionBuilder.and(qLocation.tagSet.contains(s));
                }
                break;
            case DISABLED_TAG:
                isContainDeletedLocation = true;
            case TAG:
                for (String s : tagList) {
                    conditionBuilder.and(qLocation.tagSet.contains(s));
                }
                break;
            case DISABLED:
                isContainDeletedLocation = true;
                break;
        }

        switch (requestDTO.getDistrictType()) {
            case 강원도:
                conditionBuilder.and(qLocation.siDo.contains("강원도"));
                break;
            case 경기도:
                conditionBuilder.and(qLocation.siDo.contains("경기도"));
                break;
            case 경상남도:
                conditionBuilder.and(qLocation.siDo.contains("경상남도"));
                break;
            case 경상북도:
                conditionBuilder.and(qLocation.siDo.contains("경상북도"));
                break;
            case 전라남도:
                conditionBuilder.and(qLocation.siDo.contains("전라남도"));
                break;
            case 전라북도:
                conditionBuilder.and(qLocation.siDo.contains("전라북도"));
                break;
            case 충청남도:
                conditionBuilder.and(qLocation.siDo.contains("충청남도"));
                break;
            case 충청북도:
                conditionBuilder.and(qLocation.siDo.contains("충청북도"));
                break;
            case 광주광역시:
                conditionBuilder.and(qLocation.siDo.contains("광주광역시"));
                break;
            case 대구광역시:
                conditionBuilder.and(qLocation.siDo.contains("대구광역시"));
                break;
            case 대전광역시:
                conditionBuilder.and(qLocation.siDo.contains("대전광역시"));
                break;
            case 부산광역시:
                conditionBuilder.and(qLocation.siDo.contains("부산광역시"));
                break;
            case 서울특별시:
                conditionBuilder.and(qLocation.siDo.contains("서울특별시"));
                break;
            case 울산광역시:
                conditionBuilder.and(qLocation.siDo.contains("울산광역시"));
                break;
            case 인천광역시:
                conditionBuilder.and(qLocation.siDo.contains("인천광역시"));
                break;
            case 세종특별자치시:
                conditionBuilder.and(qLocation.siDo.contains("세종특별자치시"));
                break;
            case 제주특별자치도:
                conditionBuilder.and(qLocation.siDo.contains("제주특별자치도"));
                break;
            case 전국:
            default:
        }

        if (isContainDeletedLocation) {
            return conditionBuilder;
        } else {
            return conditionBuilder.and(qLocation.is_deleted.ne(true));
        }
    }

    public List<Location> locationNameSearch(String loc_name) {
        return locationNameSearch(loc_name, MatchOption.CONTAIN);
    }

    // TODO 안쓰는거 지워야할 거
    public List<Location> locationNameSearch(String loc_name, MatchOption matchOption) {
        StringBuilder sb = new StringBuilder();
        switch (matchOption) {
            case START_WITH:
                sb.append(loc_name);
                sb.insert(0, "%");
                break;
            case END_WITH:
                sb.append(loc_name);
                sb.append("%");
                break;
            case CONTAIN:
                sb.append(loc_name);
                sb.insert(0, "%");
                sb.append("%");
                break;
            default:
                return null;
        }

        Optional<List<Location>> lists = repository.findByLoc_nameContaining(sb.toString());

        return lists.orElse(new ArrayList<>());
    }

    public Location selectLoc(Long loc_no) {
        Optional<Location> result = repository.findById(loc_no);

        return result.orElse(null);
    }

    public LocationDTO selectLocDTO(Long loc_no) {
        Optional<Location> result = repository.findById(loc_no);

        if (result.isPresent()) {
            Location item = result.get();
            return entityToDto(item);
        } else {
            return null;
        }
    }

    public Location selectLiveLoc(Long loc_no) {
        Optional<Location> result = repository.findLiveLocByLoc_no(loc_no);

        return result.isPresent() ? result.get() : null;
    }

    public Location selectLiveLoc(String loc_uuid) {
        Optional<Location> result = repository.findLiveLocByUUID(loc_uuid);

        return result.isPresent() ? result.get() : null;
    }

    public Location selectLoc(String loc_uuid) {
        Optional<Location> result = repository.findLocByUUID(loc_uuid);

        return result.isPresent() ? result.get() : null;
    }

    public LocationDTO selectLocDTO(String loc_uuid) {
        Optional<Location> result = repository.findLocByUUID(loc_uuid);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    public LocationDTO selectLiveLocDTO(String loc_uuid) {
        Optional<Location> result = repository.findLiveLocByUUID(loc_uuid);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    public LocationDTO selectLiveLocDTO(Long loc_no) {
        Optional<Location> result = repository.findLiveLocByLoc_no(loc_no);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    public void update(Location loc) {
        repository.save(loc);
    }

    public void permaDelete(Location loc) {
        List<LocationImage> list = new ArrayList<LocationImage>(loc.getImgSet());
        Set<Comment> cmtSet = loc.getCmtSet();

        if (list != null) {
            for (LocationImage locationImage : list) {
                imgService.delete(locationImage.getImg_no());
                if (imgService.getImage(locationImage.getImg_no()).is_deleted()) {
                    deletedImageInfoService.register(locationImage.getImg_no(), "LOC_IMG",
                            locationImage.getUser_no(), "LOC^" + locationImage.getImg_uuid());
                    imgService.permaDelete(locationImage.getImg_uuid());
                }
            }
        }

        loc.setImgSet(null);

        update(loc);

        if (cmtSet != null && !cmtSet.isEmpty()) {
            for (Comment cmt : cmtSet) {
                cmtService.permaDelete(cmt);
            }
        }

        repository.deleteByLoc_uuid(loc.getLoc_uuid());
    }

    public boolean delete(Long locNo) {
        Location loc = selectLoc(locNo);

        if (!loc.is_deleted()) {
//            boolean isLocImageDeleted = true;
//            List<LocationImage> finishedList = new ArrayList<>();
//            for (LocationImage locationImage : loc.getImgSet()) {
//                imgService.delete(locationImage.getImg_no());
//                if (!imgService.getImage(locationImage.getImg_no()).is_deleted()) {
//                    isLocImageDeleted = false;
//                    for (LocationImage image : finishedList) {
//                        imgService.rollback(image.getImg_no());
//                    }
//                    break;
//                }
//                finishedList.add(locationImage);
//            }
//
//            if (isLocImageDeleted) {
//                disableLocation(loc);
//                return true;
//            }

            for (LocationImage image : loc.getImgSet()) {
                imgService.delete(image.getImg_no());
            }
            disableLocation(loc);
            return true;
        }

        return false;
    }

    public boolean delete(String uuid) {
        Location loc = selectLoc(uuid);

        if (!loc.is_deleted()) {
//            boolean isLocImageDeleted = true;
//            List<LocationImage> finishedList = new ArrayList<>();
//            for (LocationImage locationImage : loc.getImgSet()) {
//                imgService.delete(locationImage.getImg_no());
//                if (imgService.getImage(locationImage.getImg_no()).is_deleted() != true) {
//                    isLocImageDeleted = false;
//                    for (LocationImage image : finishedList) {
//                        imgService.rollback(image.getImg_no());
//                    }
//                    break;
//                }
//                finishedList.add(locationImage);
//            }
//
//            if (isLocImageDeleted) {
//                disableLocation(loc);
//                return true;
//            }

            for (LocationImage image : loc.getImgSet()) {
                imgService.delete(image.getImg_no());
            }
            disableLocation(loc);
            return true;
        }

        return false;
    }

    public boolean rollback(Long locNo) {
        Location loc = selectLoc(locNo);

        if (loc.is_deleted()) {
//            boolean isLocImageDeleted = false;
//            List<LocationImage> locImageList = imgService.getLastDeletedImageList(locNo);
//            List<LocationImage> finishedList = new ArrayList<>();
//            for (LocationImage image : locImageList) {
//                imgService.rollback(image.getImg_no());
//                if (imgService.getImage(image.getImg_no()).is_deleted()) {
//                    isLocImageDeleted = true;
//                    for (LocationImage locationImage : finishedList) {
//                        imgService.delete(locationImage.getImg_no());
//                    }
//                    break;
//                }
//                finishedList.add(image);
//            }
//
//            if (!isLocImageDeleted) {
//                loc = enableLocation(loc);
//                return true;
//            }

            for (LocationImage image : loc.getImgSet()) {
                imgService.rollback(image.getImg_no());
            }
            enableLocation(loc);
            return true;
        }

        return false;
    }

    public boolean rollback(String uuid) {
        Location loc = selectLoc(uuid);

        if (loc.is_deleted()) {
//            boolean isLocImageDeleted = false;
//            List<LocationImage> locImageList = imgService.getLastDeletedImageList(loc.getLoc_no());
//            List<LocationImage> finishedList = new ArrayList<>();
//            for (LocationImage image : locImageList) {
//                imgService.rollback(image.getImg_no());
//                if (imgService.getImage(image.getImg_no()).is_deleted()) {
//                    isLocImageDeleted = true;
//                    for (LocationImage locationImage : finishedList) {
//                        imgService.delete(locationImage.getImg_no());
//                    }
//                    break;
//                }
//                finishedList.add(image);
//            }
//
//            if (!isLocImageDeleted) {
//                loc = enableLocation(loc);
//                return true;
//            }

            for (LocationImage image : loc.getImgSet()) {
                imgService.rollback(image.getImg_no());
            }
            enableLocation(loc);
            return true;
        }

        return false;
    }

    public boolean onClickLike(Long loc_no) {
        Location entity = selectLoc(loc_no);

        if (entity == null) {
            return false;
        }

        entity.setLikeCount(entity.getLikeCount() + 1);

        update(entity);

        return true;
    }

    public boolean onClickLikeDec(Long loc_no) {
        Location entity = selectLoc(loc_no);

        if (entity == null) {
            return false;
        }

        entity.setLikeCount(entity.getLikeCount() - 1);

        update(entity);

        return true;
    }

    public void incViewCount(Long loc_no) {
//        LocationDTO dto = selectLocDTO(loc_no);
//
//        dto.setViewCount(dto.getViewCount() + 1);

        Location entity = selectLoc(loc_no);

        entity.setViewCount(entity.getViewCount() + 1);

        update(entity);
    }

    public Location edit(Map<String, String> reqParam, List<String> tagList, List<String> filePath) {
        List<LocationImage> imgList = new ArrayList<>();
        List<LocationImage> newImgList = new ArrayList<>();
        List<LocationImage> duplicatedImg = new ArrayList<>();
        Location entity = selectLoc(reqParam.get("loc_uuid"));
        entity = updateLocationEntity(entity, reqParam);

        // 업데이트 된 태그 정보 삽입
        entity.setTagSet(new HashSet<>());
        for (String item : tagList) {
            entity.addLocTag(item);
        }

//        imgList = imgService.getAllLiveImageByLocNo(entity.getLoc_no());
//        int i = 0;
//        for (LocationImage img : imgList) {
//            if (!filePath.get(i + 1).equals(img.getImg_uuid())) {
//                delete(img.getImg_no());
//            } else {
//                duplicatedImg.add(img);
//            }
//            i += 2;
//        }
//
//        int j = 0;
//        imgList.clear();
//        for (i = 0; i < filePath.size(); i += 2) {
//            // filePath.get(0)  ==  Parent Folder (URI)
//            // filePath.get(i)  ==  fileNames
//            if (!filePath.get(i + 1).equals(duplicatedImg.get(j).getImg_uuid())) {
//                LocationImage locImage = imgService.getImageEntity(reqParam.get("user_no"), filePath.get(i), filePath.get(i + 1), entity, i / 2);
//                LocationImage imgEntity = imgService.update(locImage);
//                imgList.add(imgEntity);
//            } else {
//                imgList.add(duplicatedImg.get(j));
//            }
//            j += 1;
//        }

        imgList = imgService.getAllLiveImageByLocNo(entity.getLoc_no());

        first :
        for (int i = 0; i < imgList.size(); i++) {
            second :
            for (int j = 1; j < filePath.size(); j += 2) {
                if (filePath.get(j).equals(imgList.get(i).getImg_uuid())) {
                    duplicatedImg.add(imgList.get(i));
//                    imgList.get(i).set_deleted(true);
                    imgService.update(imgList.get(i));
                    continue first;
                }
            }

            imgService.delete(imgList.get(i).getImg_uuid());
        }
        imgList.clear();

        first :
        for (int i = 0; i < filePath.size(); i += 2) {
            // filePath.get(i)  ==  Parent Folder (URI)
            // filePath.get(i+1)  ==  fileNames
            second :
            for (int j = 0; j < duplicatedImg.size(); j++) {
                if (filePath.get(i + 1).equals(duplicatedImg.get(j).getImg_uuid())) {
                    imgList.add(duplicatedImg.get(j));
                    continue first;
                }
            }

            //                    CourseImage locImage = imgService.getImageEntity(reqParam.get("user_no"),
//                            filePath.get(i), filePath.get(i+1), entity.getLoc_no(), (i/2));
//                    CourseImage imgEntity = imgService.update(locImage);
//                    imgList.add(imgEntity);
            LocationImage locImage = imgService.getImageEntity(reqParam.get("user_no"), filePath.get(i), filePath.get(i + 1), entity, i / 2);
            LocationImage imgEntity = imgService.update(locImage);
            imgList.add(imgEntity);
        }

        entity.setImgSet(new HashSet<>(imgList));
        entity.setThumbnail(imgList.get(0).getImg_url());

        repository.save(entity);

//        log.info("entity : " + entity);

        return entity;
    }

    private Location updateLocationEntity(Location entity, Map<String, String> reqParam) {
        entity.setLoc_name(reqParam.get("name"));
        entity.setFullRoadAddr(reqParam.get("fullAddr"));
        entity.setRoadAddr(reqParam.get("roadAddr"));
        entity.setAddrDetail(reqParam.get("addrDetail"));
        entity.setSiDo(reqParam.get("siDoName"));
        entity.setSiGunGu(reqParam.get("siGunGuName"));
        entity.setInfo(reqParam.get("info"));
        entity.setTel(reqParam.get("tel"));
        entity.setZipNo(reqParam.get("zipNo"));

        return entity;
    }

    public List<Location> findLocByUserNo(Long userNo) {
        Optional<List<Location>> locationList = repository.findByAllUser_no(userNo);

        return locationList.orElse(null);
    }

    private Location disableLocation(Location loc) {
        loc.set_deleted(true);

        update(loc);

        return selectLoc(loc.getLoc_no());
    }

    private Location enableLocation(Location loc) {
        loc.set_deleted(false);

        update(loc);

        return selectLoc(loc.getLoc_no());
    }

    /**
     * This method returns Location List of given count and dateDuration.
     * Locations that are returns Subtract now and location.
     * So it should be Now() - Location.regDate = dateDuration
     * Also Limit the result amount by count
     *
     * @param count        How much result amount you want
     * @param dateDuration How much date diff you want
     * @return List of Locations or if is invalid returns null
     */
    public List<Location> recentLocationList(int count, int dateDuration) {
        if (count <= 0) {
//            log.info("Count is below 0. Please input value above 1");
            return null;
        }

        if (dateDuration < 0) {
//            log.info("Date duration is under 0. Please input value above 0);
            return null;
        }

        Optional<List<Location>> items = repository.getRecentLocationsByCountAndDateDuration(count, dateDuration);

        return items.orElse(null);
    }

    public List<Location> hotLocationList(int count, int dateDuration, int minLikeCount) {
        if (count <= 0) {
//            log.info("Count is below 0. Please input value above 1");
            return null;
        }

        if (dateDuration < 0) {
//            log.info("Date duration is below 0. Please input value above 1");
            return null;
        }

        if (minLikeCount <= 0) {
//            log.info("minLikeCount Should above at least 1. Change minLike Count to 1");
            minLikeCount = 1;
        }

        Optional<List<Location>> items = repository.getHotLocationsByCountAndDateDuration(count, dateDuration, minLikeCount);

        return items.orElse(null);
    }
}
