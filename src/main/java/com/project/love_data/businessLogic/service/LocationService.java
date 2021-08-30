package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.*;
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

    public Location register(Map<String, String> reqParam, List<String> tagList, List<String> filePath) {
        LocationDTO dto = getLocationDto(reqParam, tagList);
        List<LocationImage> imgList = new ArrayList<>();
        Location entity = dtoToEntity(dto);

        repository.save(entity);

        for (int i = 0; i < filePath.size(); i+=2) {
            // filePath.get(i)  ==  Parent Folder (URI)
            // filePath.get(i+1)  ==  fileNames
            LocationImage locImage = imgService.getImageEntity(reqParam.get("user_no"), filePath.get(i), filePath.get(i+1), entity, i/2);
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
                .build();

        return entity;
    }

    public LocationDTO entityToDto(Location entity) {
        LocationDTO dto = LocationDTO.builder()
                .loc_no(entity.getLoc_no())
                .loc_name(entity.getLoc_name())
                .loc_uuid(entity.getLoc_uuid())
                .user_no(entity.getUser_no())
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

        for (int i = 0; i <= maxCmtIndex; i++) {
            for (int j = 0; j < tempCmtList.size(); j++) {
                if (tempCmtList.get(j).getCmtIdx() == i) {
                    cmtList.add(tempCmtList.get(j));
                    break;
                }
            }
        }

        dto.setImgList(imgList);
        dto.setCmtList(cmtList);

        return dto;
    }

    private LocationDTO getLocationDto(Map<String, String> reqParam, List<String> tagList) {
        LocationDTO loc = LocationDTO.builder()
                .loc_name(reqParam.get("name"))
                .user_no(Long.valueOf(reqParam.get("user_no")))
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
        switch (requestDTO.getSortingOrder()){
            case ASC:
                flagASC = true;
                break;
            default:
                flagASC = false;
                break;
        }

        switch (requestDTO.getSortCriterion()){
            case DATE:
                if (flagASC) {
                    pageable = requestDTO.getPageable(Sort.by("regDate").ascending());
                } else {
                    pageable =  requestDTO.getPageable(Sort.by("regDate").descending());
                }
                break;
            case LIKE:
                if (flagASC) {
                    pageable = requestDTO.getPageable(Sort.by("likeCount").ascending());
                } else {
                    pageable =  requestDTO.getPageable(Sort.by("likeCount").descending());
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

        switch (requestDTO.getSearchType()){
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

        if (isContainDeletedLocation) {
            return conditionBuilder;
        } else {
            return conditionBuilder.and(qLocation.is_deleted.ne(true));
        }
    }

    public List<Location> locationNameSearch(String loc_name){
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

    public Location selectLoc(String loc_uuid) {
        Optional<Location> result = repository.findLocByUUID(loc_uuid);

        return result.isPresent() ? result.get() : null;
    }

    public LocationDTO selectLocDTO(String loc_uuid) {
        Optional<Location> result = repository.findLocByUUID(loc_uuid);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    public void update(Location loc) {
        repository.save(loc);
    }

    public void permaDelete(Location loc) {
        List<LocationImage> list = new ArrayList<LocationImage>(loc.getImgSet());
        Set<Comment> cmtSet = loc.getCmtSet();

        for (LocationImage locationImage : list) {
            locationImage.setLocation(null);

            imgService.update(locationImage);

            imgService.permaDelete(locationImage.getImg_uuid());
        }

        for (Comment cmt : cmtSet) {
            cmtService.permaDelete(cmt);
        }

        loc.setImgSet(null);

        update(loc);

        repository.deleteByLoc_uuid(loc.getLoc_uuid());
    }

    public void delete(Long locNo) {
        Location loc = selectLoc(locNo);

        if (!loc.is_deleted()) {
            disableLocation(loc);
            for (LocationImage locationImage : loc.getImgSet()) {
                imgService.delete(locationImage.getImg_no());
            }
        }
    }

    public void delete(String uuid) {
        Location loc = selectLoc(uuid);

        if (!loc.is_deleted()) {
            disableLocation(loc);
        }
    }

    public void rollback(Long locNo) {
        Location loc = selectLoc(locNo);

        if (loc.is_deleted()){
            loc = enableLocation(loc);
            List<LocationImage> locImageList = imgService.getLastDeletedImageList(locNo);
            for (LocationImage image : locImageList) {
                imgService.rollback(image.getImg_no());
            }
        }
    }

    public void rollback(String uuid) {
        Location loc = selectLoc(uuid);

        if (loc.is_deleted()){
            loc = enableLocation(loc);
            List<LocationImage> locImageList = imgService.getLastDeletedImageList(imgService.getFirstIndexOfLastDeletedLocationImage(loc.getLoc_no()));
            for (LocationImage image : locImageList) {
                imgService.rollback(image.getImg_no());
            }
        }
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

    public boolean onClickLikeUndo(Long loc_no) {
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
        Location entity = selectLoc(reqParam.get("loc_uuid"));

        // 업데이트 된 태그 정보 삽입
        entity.setTagSet(new HashSet<>());
        for (String item : tagList) {
            entity.addLocTag(item);
        }

        LocationDTO dto = entityToDto(entity);
//        boolean flag = false;
        // Todo 기존에 장소에 등록된 이미지가 업데이트 된 장소와 연결이 끊여졌을 때 어떻게 동작할 지

        for (int i = 0; i < filePath.size(); i+=2) {
            // filePath.get(0)  ==  Parent Folder (URI)
            // filePath.get(i)  ==  fileNames
//            if (dto.getImgList().size() > i) {
//                for (int j = 0; j < dto.getImgList().size(); j++) {
//                    if (dto.getImgList().get(j).getImg_uuid().equals(filePath.get(i))){
//                        imgList.add(imgService.editImageEntityIndex(filePath.get(i), (long) (i - 1)));
////                        flag = true;
//                        continue;
//                    }
//                }
//            }
//            log.info(filePath.get(i));
            // Todo 현재는 기존에 이미 등록되어 있던 이미지는 삭제하고, 새로 등록하도록 했음
            // 나중에는 기존에 이미  등록되어 있던 이미지를 삭제하지 않고, 업데이트해서 등록하도록 바꿀것
            imgService.delete(filePath.get(i+1));

            LocationImage locImage = imgService.getImageEntity(reqParam.get("user_no"), filePath.get(i), filePath.get(i+1), entity, i/2);
            LocationImage imgEntity = imgService.update(locImage);
            imgList.add(imgEntity);
        }

        entity.setImgSet(new HashSet<>(imgList));
        entity.setThumbnail(imgList.get(0).getImg_url());

        repository.save(entity);

//        log.info("entity : " + entity);

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
}
