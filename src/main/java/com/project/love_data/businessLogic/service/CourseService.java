package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.CourseDTO;
import com.project.love_data.dto.CourseImageDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.resource.CourseImage;
import com.project.love_data.model.service.CorLocMapper;
import com.project.love_data.model.service.Course;
import com.project.love_data.model.service.QCourse;
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
    private final CorLocMapperService corLocMapperService;
    private final DeletedImageInfoService deletedImageInfoService;

    public Course update(Map<String, String> reqParam, List<String> tagList, List<String> filePath) {
        CourseDTO dto = updateCourseDto(reqParam, tagList);
        List<CourseImage> imgList = new ArrayList<>();
        Course entity = dtoToEntity(dto);
        CourseImage firstImage = new CourseImage();
        List<Long> locNoList = new ArrayList<>();
        List<CourseImage> duplicatedImg = new ArrayList<>();

        repository.save(entity);

        Optional<Course> item = repository.findCorByUUID(entity.getCor_uuid());

        entity = item.orElse(null);

        imgList = imgService.getLiveImagesByCorNo(Long.valueOf(reqParam.get("cor_no")));

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
                    if (i == 0) {
                        firstImage = duplicatedImg.get(0);
                    }
                    imgList.add(duplicatedImg.get(j));
                    continue first;
                }
            }

            CourseImage locImage = imgService.getImageEntity(reqParam.get("user_no"),
                    filePath.get(i), filePath.get(i+1), entity.getCor_no(), (i/2));
            CourseImage imgEntity = imgService.update(locImage);
            imgList.add(imgEntity);
            if (i == 0) {
                firstImage = imgEntity;
            }
        }

        if (imgList.isEmpty()) {
            entity.setThumbnail(duplicatedImg.get(0).getImg_url());
        } else {
            entity.setThumbnail(firstImage.getImg_url());
        }

        List<CorLocMapper> corLocMappers = corLocMapperService.getLocationsByCorNo(Long.valueOf(reqParam.get("cor_no")));

        for (int i = 0; i < corLocMappers.size(); i++) {
            corLocMapperService.permaDelete(corLocMappers.get(i).getClm_No());
        }

        for (int i = 1; i <= Integer.parseInt(reqParam.get("location_length")); i++) {
            locNoList.add(Long.parseLong(reqParam.get("loc_no_" + i)));
        }

        corLocMapperService.register(entity.getCor_no(),locNoList);

        repository.save(entity);

        log.info("entity : " + entity);

        return entity;
    }

    public Course register(Map<String, String> reqParam, List<String> tagList, List<String> filePath) {
        CourseDTO dto = getCourseDto(reqParam, tagList);
        List<CourseImage> imgList = new ArrayList<>();
        Course entity = dtoToEntity(dto);
        CourseImage courseImage;
        List<Long> locNoList = new ArrayList<>();

        repository.save(entity);

        Optional<Course> item = repository.findCorByUUID(entity.getCor_uuid());

        entity = item.orElse(null);

        for (int i = 0; i < filePath.size(); i += 2) {
            // filePath.get(i)  ==  Parent Folder (URI)
            // filePath.get(i+1)  ==  fileNames
            CourseImage locImage = imgService.getImageEntity(reqParam.get("user_no"),
                    filePath.get(i), filePath.get(i+1), entity.getCor_no(), (i/2));
            CourseImage imgEntity = imgService.update(locImage);
            imgList.add(imgEntity);
        }

        entity.setThumbnail(imgList.get(0).getImg_url());

        for (int i = 1; i <= Integer.parseInt(reqParam.get("location_length")); i++) {
            locNoList.add(Long.parseLong(reqParam.get("loc_no_" + i)));
        }

        corLocMapperService.register(entity.getCor_no(),locNoList);

        repository.save(entity);

        log.info("entity : " + entity);

        return entity;
    }

    public Course dtoToEntity(CourseDTO dto) {
        Course entity = Course.builder()
                .cor_no(dto.getCor_no())
                .cor_name(dto.getCor_name())
                .cor_uuid(dto.getCor_uuid())
                .user_no(dto.getUser_no())
                .cost(dto.getCost())
                .location_count(dto.getLocation_count())
                .est_type(dto.getEst_type())
                .est_value(dto.getEst_value())
                .transportation(dto.getTransportation())
                .cor_uuid(dto.getCor_uuid())
                .info(dto.getInfo())
                .tagSet(dto.getTagSet())
                .likeCount(dto.getLikeCount())
                .thumbnail(dto.getThumbnail())
                .viewCount(dto.getViewCount())
                .is_deleted(dto.is_deleted())
                .accommodations_info(dto.getAccommodations_info())
                .is_reported(dto.is_reported())
                .build();

        return entity;
    }

    public CourseDTO entityToDto(Course entity) {
        CourseDTO dto = CourseDTO.builder()
                .cor_no(entity.getCor_no())
                .cor_name(entity.getCor_name())
                .cor_uuid(entity.getCor_uuid())
                .cost(entity.getCost())
                .location_count(entity.getLocation_count())
                .est_type(entity.getEst_type())
                .est_value(entity.getEst_value())
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
                .accommodations_info(entity.getAccommodations_info())
                .is_reported(entity.is_reported())
                .build();

        return dto;
    }

    public CourseDTO updateCourseDto(Map<String, String> reqParam, List<String> tagList) {
        CourseDTO courseDTO = selectCorDTO(Long.valueOf(reqParam.get("cor_no")));

        courseDTO.setCor_name(reqParam.get("name"));
        courseDTO.setInfo(reqParam.get("info"));
        courseDTO.setTransportation(reqParam.get("transportation"));
        courseDTO.setCost(reqParam.get("cost"));
        courseDTO.setLocation_count(Integer.parseInt(reqParam.get("location_length")));
        courseDTO.setEst_type(reqParam.get("est_type"));
        courseDTO.setEst_value(reqParam.get("est_value"));

        if ("date".equals(courseDTO.getEst_type())) {
            courseDTO.setAccommodations_info(reqParam.get("accommodations"));
        }

        Set<String> tags = new HashSet<>();
        for (String item : tagList) {
            tags.add(item);
        }

        courseDTO.setTagSet(tags);

        return courseDTO;
    }

    // Todo 해당 부분 수정하기
    private CourseDTO getCourseDto(Map<String, String> reqParam, List<String> tagList) {
        CourseDTO courseDTO = CourseDTO.builder()
                .cor_name(reqParam.get("name"))
                .user_no(Long.valueOf(reqParam.get("user_no")))
                .info(reqParam.get("info"))
                .transportation(reqParam.get("transportation"))
                .cost(reqParam.get("cost"))
                .location_count(Integer.parseInt(reqParam.get("location_length")))
                .est_type(reqParam.get("est_type"))
                .est_value(reqParam.get("est_value"))
                .build();

        if ("date".equals(courseDTO.getEst_type())) {
            courseDTO.setAccommodations_info(reqParam.get("accommodations"));
        }

        Set<String> tags = new HashSet<>();
        for (String item : tagList) {
            tags.add(item);
        }

        courseDTO.setTagSet(tags);

        return courseDTO;
    }

    public PageResultDTO<CourseDTO, Course> getList(PageRequestDTO requestDTO) {
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
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<Course> result = repository.findAll(booleanBuilder, pageable);

        Function<Course, CourseDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    public BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        Long userNo = requestDTO.getUserNo();
        Long corNo = requestDTO.getLocNo();
        String keyword = requestDTO.getKeyword();
        List<String> tagList = requestDTO.getTagList();
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        QCourse qCourse = QCourse.course;
        boolean isContainDeletedCourse = false;

        switch (requestDTO.getSearchType()){
            case DISABLED_USER:
                isContainDeletedCourse = true;
            case USER:
                conditionBuilder.and(qCourse.user_no.eq(userNo));
                break;
            case USER_TAG:
                conditionBuilder.and(qCourse.user_no.eq(userNo));
                for (String s : tagList) {
                    conditionBuilder.and(qCourse.tagSet.contains(s));
                }
                break;
            case DISABLED_TITLE:
                isContainDeletedCourse = true;
            case TITLE:
                conditionBuilder.and(qCourse.cor_name.contains(keyword));
                break;
            case DISABLED_TITLE_TAG:
                isContainDeletedCourse = true;
            case TITLE_TAG:
                conditionBuilder.and(qCourse.cor_name.contains(keyword));
                for (String s : tagList) {
                    conditionBuilder.and(qCourse.tagSet.contains(s));
                }
                break;
            case DISABLED_TAG:
                isContainDeletedCourse = true;
            case TAG:
                for (String s : tagList) {
                    conditionBuilder.and(qCourse.tagSet.contains(s));
                }
                break;
            case DISABLED:
                isContainDeletedCourse = true;
                break;
            case NONE:
            default:
                return conditionBuilder.and(qCourse.is_deleted.ne(true));
        }

        if (isContainDeletedCourse) {
            return conditionBuilder;
        } else {
            return conditionBuilder.and(qCourse.is_deleted.ne(true));
        }
    }

    public Course selectCor(Long corNo) {
        Optional<Course> result = repository.findById(corNo);

        return result.orElse(null);
    }

    public CourseDTO selectCorDTO(Long corNo) {
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

    public CourseDTO selectCorDTO(String cor_uuid) {
        Optional<Course> result = repository.findCorByUUID(cor_uuid);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    public Course update(Course cor) {
        return repository.save(cor);
    }

    public boolean permaDelete(Long corNo) {
//        List<CourseImage> list = new ArrayList<CourseImage>();
//        list = imgService.getAllImagesByCorNo(cor.getCor_no());
//
//        for (CourseImage CourseImage : list) {
//            CourseImage.setCor_no(null);
//
//            imgService.update(CourseImage);
//
//            imgService.permaDelete(CourseImage.getImg_uuid());
//        }
//
//        repository.deleteByCor_uuid(cor.getCor_uuid());

        Course corEntity = selectCor(corNo);

        if (corEntity == null) {
            return false;
        }

        List<CourseImage> list = new ArrayList<CourseImage>();
        list = imgService.getLiveImagesByCorNo(corNo);

        for (CourseImage courseImage : list) {
            imgService.delete(courseImage.getImg_no());
            if (imgService.getAllImage(courseImage.getImg_no()).is_deleted()) {
                deletedImageInfoService.register(courseImage.getImg_no(), "COR_IMG",
                        courseImage.getUser_no(), "COR^" + courseImage.getImg_uuid());
                imgService.permaDelete(courseImage.getImg_uuid());
            } else {
                log.warn("ERROR During delete");
                log.warn(courseImage);
                continue;
            }
        }

        List<CorLocMapper> corLocMapperList = corLocMapperService.getLocationsByCorNo(corEntity.getCor_no());

        for (CorLocMapper mapper : corLocMapperList) {
            corLocMapperService.permaDelete(mapper.getClm_No());
        }

        repository.deleteByCor_uuid(corEntity.getCor_uuid());

        return true;
    }

    public boolean delete(Long corNo) {
        Course cor = selectCor(corNo);

        if (!cor.is_deleted()) {
            List<CourseImage> imageList = imgService.getLiveImagesByCorNo(corNo);

            if (imageList != null ){
                for (CourseImage image : imageList) {
                    imgService.delete(image.getImg_no());
                }
            }

            disableCourse(cor);
            return true;
        }

        return false;
    }

    public boolean delete(String uuid) {
        Course cor = selectCor(uuid);

        if (!cor.is_deleted()) {
            List<CourseImage> imageList = imgService.getLiveImagesByCorNo(cor.getCor_no());

            if (imageList != null ){
                for (CourseImage image : imageList) {
                    imgService.delete(image.getImg_no());
                }
            }
            disableCourse(cor);
            return true;
        }
        return false;
    }

    public boolean rollback(Long corNo) {
        Course cor = selectCor(corNo);

        if (cor.is_deleted()){
//            boolean isCourseRollback = true;
//            List<CourseImage> list = new ArrayList<CourseImage>();
//            List<CourseImage> finishedList = new ArrayList<>();
//            list = imgService.getLastDeletedCourseImageList(corNo);
//
//            if (list == null) {
//                return false;
//            }
//
//            for (CourseImage CourseImage : list) {
//                imgService.rollback(CourseImage.getImg_no());
//                if (imgService.getAllImage(CourseImage.getImg_no()).is_deleted()) {
//                    isCourseRollback = false;
//                    for (CourseImage courseImage : finishedList) {
//                        imgService.delete(courseImage.getImg_no());
//                    }
//                    break;
//                }
//                finishedList.add(CourseImage);
//            }
//
//            if (isCourseRollback) {
//                enableCourse(cor);
//                return true;
//            }
            List<CourseImage> imageList = imgService.getLastDeletedCourseImageList(corNo);

            if (imageList != null ){
                for (CourseImage image : imageList) {
                    imgService.rollback(image.getImg_no());
                }
            }

            enableCourse(cor);
            return true;
        }
        return false;
    }

    public boolean rollback(String uuid) {
        Course cor = selectCor(uuid);

        if (cor.is_deleted()){
//            boolean isCourseRollback = true;
//            List<CourseImage> list = new ArrayList<CourseImage>();
//            List<CourseImage> finishedList = new ArrayList<>();
//            list = imgService.getLastDeletedCourseImageList(cor.getCor_no());
//
//            if (list == null) {
//                return false;
//            }
//
//            for (CourseImage CourseImage : list) {
//                imgService.rollback(CourseImage.getImg_no());
//                if (imgService.getAllImage(CourseImage.getImg_no()).is_deleted()) {
//                    isCourseRollback = false;
//                    for (CourseImage courseImage : finishedList) {
//                        imgService.delete(courseImage.getImg_no());
//                    }
//                    break;
//                }
//                finishedList.add(CourseImage);
//            }
//
//            if (isCourseRollback) {
//                enableCourse(cor);
//                return true;
//            }
            List<CourseImage> imageList = imgService.getLastDeletedCourseImageList(cor.getCor_no());

            if (imageList != null ){
                for (CourseImage image : imageList) {
                    imgService.rollback(image.getImg_no());
                }
            }

            enableCourse(cor);
            return true;
        }
        return false;
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

    public boolean onClickLikeDec(Long corNo) {
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

    public List<Course> findCorByUserNo(Long userNo) {
        Optional<List<Course>> CourseList = repository.findByAllUser_no(userNo);

        return CourseList.orElse(null);
    }

    private Course disableCourse(Course cor) {
        cor.set_deleted(true);

        update(cor);

        return selectCor(cor.getCor_no());
    }

    private Course enableCourse(Course cor) {
        cor.set_deleted(false);

        update(cor);

        return selectCor(cor.getCor_no());
    }

    public List<Course> courseNameSearch(String cor_name){
        return courseNameSearch(cor_name, MatchOption.CONTAIN);
    }

    // TODO 안쓰는거 지워야할 거
    public List<Course> courseNameSearch(String cor_name, MatchOption matchOption) {
        StringBuilder sb = new StringBuilder();
        switch (matchOption) {
            case START_WITH:
                sb.append(cor_name);
                sb.insert(0, "%");
                break;
            case END_WITH:
                sb.append(cor_name);
                sb.append("%");
                break;
            case CONTAIN:
                sb.append(cor_name);
                sb.insert(0, "%");
                sb.append("%");
                break;
            default:
                return null;
        }

        Optional<List<Course>> lists = repository.findByCor_nameContaining(sb.toString());

        return lists.orElse(null);
    }

    public void updateThumbnail(Long corNo) {
        Course course = selectCor(corNo);
        CourseImageDTO corImgDTO = imgService.entityToDTO(imgService.getCourseAllThumbnail(corNo));

        course.setThumbnail(corImgDTO.getImg_url());

        update(course);
    }

    public List<Course> recentCourseList(int count, int dateDuration) {
        if (count <= 0) {
//            log.info("Count is below 0. Please input value above 1");
            return null;
        }

        if (dateDuration < 0) {
//            log.info("Date duration is below 0. Please input value above 0");
            return null;
        }

        Optional<List<Course>> items = repository.getRecentCoursesByCountAndDateDuration(count, dateDuration);

        return items.orElse(null);
    }

    public List<Course> hotCourseList(int count, int dateDuration, int minLikeCount) {
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

        Optional<List<Course>> items = repository.getHotCoursesByCountAndDateDuration(count, dateDuration, minLikeCount);

        return items.orElse(null);
    }
}
