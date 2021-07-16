package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.CourseDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.resource.CourseImage;
import com.project.love_data.model.service.Course;
import com.project.love_data.model.service.LocationTag;
import com.project.love_data.repository.CourseImageRepository;
import com.project.love_data.repository.CourseRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseService {
    private final CourseRepository repository;
    private final CourseImageRepository imgRepository;
    private final CourseImageService imgService;
    private final ReviewService revService;

    public Course register(Map<String, String> reqParam, List<String> tagList, List<String> filePath) {
        CourseDTO dto = getCourseDto(reqParam, tagList);
        List<CourseImage> imgList = new ArrayList<>();
        Course entity = dtoToEntity(dto);

        for (int i = 1; i < filePath.size(); i++) {
            // filePath.get(0)  ==  Parent Folder (URI)
            // filePath.get(i)  ==  fileNames
            imgList.add(imgService.getImageEntity(reqParam.get("user_no"),
                    filePath.get(0), filePath.get(i), entity.getCor_no(), i-1));
        }

        entity.setImgSet(new HashSet<>(imgList));
        entity.setThumbnail(imgList.get(0).getImg_url());

        repository.save(entity);

        for (CourseImage CourseImage : imgList) {
            imgRepository.save(CourseImage);
        }

        log.info("entity : " + entity);

        return entity;
    }

    public Course dtoToEntity(CourseDTO dto) {
        Course entity = Course.builder()
//                .cor_no(dto.getCor_no())
                .cor_name(dto.getCor_name())
                .cost(dto.getCost())
                .est_time(dto.getEst_time())
                .transportation(dto.getTransportation())
                .cor_uuid(dto.getCor_uuid())
                .info(dto.getInfo())
                .tagSet(dto.getTagSet())
                .imgSet(new HashSet<>(dto.getImgList()))
                .likeCount(dto.getLikeCount())
                .thumbnail(dto.getThumbnail())
                .viewCount(dto.getViewCount())
                .is_deleted(dto.is_deleted())
                .build();

        return entity;
    }

    public CourseDTO entityToDto(Course entity) {
        CourseDTO dto = CourseDTO.builder()
                .cor_no(entity.getCor_no())
                .cor_name(entity.getCor_name())
                .cor_uuid(entity.getCor_uuid())
                .cost(entity.getCost())
                .est_time(entity.getEst_time())
                .transportation(entity.getTransportation())
                .user_no(entity.getUser_no())
                .info(entity.getInfo())
                .tagSet(entity.getTagSet())
                .likeCount(entity.getLikeCount())
                .thumbnail(entity.getThumbnail())
                .viewCount(entity.getViewCount())
                .is_deleted(entity.is_deleted())
                .modDate(entity.getModDate())
                .regDate(entity.getRegDate())
                .build();

        // Image List 변환 및 정렬
        // idx 기준 정렬
        List<CourseImage> tempList = new ArrayList<>();
        List<CourseImage> imgList = new ArrayList<>();
        boolean sortedFlag = false;
        int count = 0;

        for (CourseImage img :
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


//        // Comment List 변환 및 정렬
//        // cmd_idx 기준 정렬
//        List<Comment> tempCmtList = new ArrayList<>(entity.getCmtSet());
//        List<Comment> cmtList = new ArrayList<>();
//
//        for (int i = 0; i < tempCmtList.size(); i++) {
//            for (int j = 0; j < tempCmtList.size(); j++) {
//                if (tempCmtList.get(j).getCmtIdx() == i) {
//                    cmtList.add(tempCmtList.get(j));
//                    break;
//                }
//            }
//        }
//
//        dto.setImgList(imgList);
//        dto.setCmtList(cmtList);

        return dto;
    }

    private CourseDTO getCourseDto(Map<String, String> reqParam, List<String> tagList) {
        CourseDTO courseDTO = CourseDTO.builder()
                .cor_name(reqParam.get("name"))
                .user_no(Long.valueOf(reqParam.get("user_no")))
                .info(reqParam.get("info"))
                .transportation(reqParam.get("transportation"))
                .cost(reqParam.get("cost"))
                .est_time(reqParam.get("est_time"))
                .build();

        Set<String> tags = new HashSet<>();
        for (String item : tagList) {
            tags.add(item);
        }

        courseDTO.setTagSet(tags);

        return courseDTO;
    }

//    public PageResultDTO<CourseDTO, Course> getList(PageRequestDTO requestDTO) {
//        return getList(requestDTO, SearchType.NONE,
//                SortCriterion.VIEW, SortingOrder.DES);
//    }

    public PageResultDTO<CourseDTO, Course> getList(PageRequestDTO requestDTO) {
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

        // Todo 추후 추가하기
//        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Page<Course> result = repository.findAll(booleanBuilder, pageable);

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

        Function<Course, CourseDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

//    public BooleanBuilder getSearch(PageRequestDTO requestDTO) {
//        Long userNo = requestDTO.getUserNo();
//        Long locNo = requestDTO.getLocNo();
//        String keyword = requestDTO.getKeyword();
//        List<String> tagList = requestDTO.getTagList();
//        BooleanBuilder conditionBuilder = new BooleanBuilder();
//        QCourse qCourse = QCourse.Course;
//
//        switch (requestDTO.getSearchType()){
//            case USER:
//                conditionBuilder.and(qCourse.loc_no.eq(userNo));
//                break;
//            case USER_TAG:
//                conditionBuilder.and(qCourse.loc_no.eq(userNo));
//                for (String s : tagList) {
//                    conditionBuilder.and(qCourse.tagSet.contains(s));
//                }
//                break;
//            case TITLE:
//                conditionBuilder.and(qCourse.loc_name.contains(keyword));
//                break;
//            case TITLE_TAG:
//                conditionBuilder.and(qCourse.loc_name.contains(keyword));
//                for (String s : tagList) {
//                    conditionBuilder.and(qCourse.tagSet.contains(s));
//                }
//                break;
//            case TAG:
//                for (String s : tagList) {
//                    conditionBuilder.and(qCourse.tagSet.contains(s));
//                }
//                break;
//            case DISABLED:
//                conditionBuilder.and(qCourse.is_deleted.eq(true));
//                break;
//            case NONE:
//            default:
//                return conditionBuilder.and(qCourse.is_deleted.ne(true));
//        }
//
//        return conditionBuilder.and(qCourse.is_deleted.ne(true));
//    }

    public Course selectCor(Long corNo) {
        Optional<Course> result = repository.findById(corNo);

        return result.orElse(null);
    }

    public CourseDTO selectLocDTO(Long corNo) {
        Optional<Course> result = repository.findById(corNo);

        if (result.isPresent()) {
            Course item = result.get();
            return entityToDto(item);
        } else {
            return null;
        }
    }

    public Course selectCor(String cor_uuid) {
        Optional<Course> result = repository.findCorByUUID(cor_uuid);

        return result.orElse(null);
    }

    public CourseDTO selectLocDTO(String cor_uuid) {
        Optional<Course> result = repository.findCorByUUID(cor_uuid);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    public void update(Course cor) {
        repository.save(cor);
    }

    public void permaDelete(Course cor) {
        List<CourseImage> list = new ArrayList<CourseImage>(cor.getImgSet());
        // Todo 리뷰 항목도 지워지도록 리뷰 리포지토리 추가후 추가작업 진행하기
//        Set<Comment> cmtSet = cor.getCmtSet();

        for (CourseImage CourseImage : list) {
            CourseImage.setCor_no(null);

            imgService.update(CourseImage);

            imgService.permaDelete(CourseImage.getImg_uuid());
        }

//        for (Comment cmt : cmtSet) {
//            revService.permaDelete(cmt);
//        }

        cor.setImgSet(null);

        update(cor);

        repository.deleteByCor_uuid(cor.getCor_uuid());
    }

    public void delete(Long corNo) {
        Course cor = selectCor(corNo);

        if (!cor.is_deleted()) {
            disableCourse(cor);
            for (CourseImage CourseImage : cor.getImgSet()) {
                imgService.delete(CourseImage.getImg_no());
            }
        }
    }

    public void delete(String uuid) {
        Course cor = selectCor(uuid);

        if (!cor.is_deleted()) {
            disableCourse(cor);
        }
    }

    public void rollback(Long corNo) {
        Course cor = selectCor(corNo);

        if (cor.is_deleted()){
            enableCourse(cor);
        }
    }

    public void rollback(String uuid) {
        Course cor = selectCor(uuid);

        if (cor.is_deleted()){
            enableCourse(cor);
        }
    }

    public boolean onClickLike(Long corNo) {
        Course entity = selectCor(corNo);

        if (entity == null) {
            return false;
        }

        entity.setLikeCount(entity.getLikeCount() + 1);

        update(entity);

        return true;
    }

    public boolean onClickLikeUndo(Long corNo) {
        Course entity = selectCor(corNo);

        if (entity == null) {
            return false;
        }

        entity.setLikeCount(entity.getLikeCount() - 1);

        update(entity);

        return true;
    }

    public void incViewCount(Long corNo) {
//        CourseDTO dto = selectLocDTO(corNo);
//
//        dto.setViewCount(dto.getViewCount() + 1);

        Course entity = selectCor(corNo);

        entity.setViewCount(entity.getViewCount() + 1);

        update(entity);
    }

    public Course edit(Map<String, String> reqParam, List<String> tagList, List<String> filePath) {
        List<CourseImage> imgList = new ArrayList<>();
        Course entity = selectCor(reqParam.get("loc_uuid"));

        // 업데이트 된 태그 정보 삽입
        HashSet<String> tags = new HashSet<>(tagList);
        entity.setTagSet(tags);

        CourseDTO dto = entityToDto(entity);
//        boolean flag = false;
        // Todo 기존에 장소에 등록된 이미지가 업데이트 된 장소와 연결이 끊여졌을 때 어떻게 동작할 지

        for (int i = 1; i < filePath.size(); i++) {
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
            log.info(filePath.get(i));
            // Todo 현재는 기존에 이미 등록되어 있던 이미지는 삭제하고, 새로 등록하도록 했음
            // 나중에는 기존에 이미  등록되어 있던 이미지를 삭제하지 않고, 업데이트해서 등록하도록 바꿀것
            imgService.permaDelete(filePath.get(i));
            imgList.add(imgService.getImageEntity(reqParam.get("user_no"), filePath.get(0), filePath.get(i), entity.getCor_no(), i-1));
        }

        entity.setImgSet(new HashSet<>(imgList));
        entity.setThumbnail(imgList.get(0).getImg_url());

        repository.save(entity);

//        log.info("entity : " + entity);

        return entity;
    }

    public List<Course> findCorByUserNo(Long userNo) {
        Optional<List<Course>> CourseList = repository.findByAllUser_no(userNo);

        return CourseList.orElse(null);
    }

    private Course disableCourse(Course loc) {
        loc.set_deleted(true);

        update(loc);

        return selectCor(loc.getCor_no());
    }

    private Course enableCourse(Course loc) {
        loc.set_deleted(false);

        update(loc);

        return selectCor(loc.getCor_no());
    }
}
