package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.*;
import com.project.love_data.model.service.*;
import com.project.love_data.repository.*;
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
public class ReportService {
    @Autowired
    ReportRepository repository;
    @Autowired
    LocationRepository locRepository;
    @Autowired
    CourseRepository corRepository;
    @Autowired
    CommentRepository comRepository;
    @Autowired
    ReviewRepository revRepository;
    static final int REPORT_STATUS_CHANGE_SIZE = 5;

    public Report register(Map<String, String> reqParam) {
        switch (reqParam.get("postType")) {
            case "LOC":
                Optional<Location> locItem = locRepository.findById(Long.valueOf(reqParam.get("postNo")));
                if (!locItem.isPresent()) {
                    return null;
                }
                break;
            case "COR":
                Optional<Course> corItem = corRepository.findById(Long.valueOf(reqParam.get("postNo")));
                if (!corItem.isPresent()) {
                    return null;
                }
                break;
            case "COM":
                Optional<Comment> comItem = comRepository.findById(Long.valueOf(reqParam.get("postNo")));
                if (!comItem.isPresent()) {
                    return null;
                }
                break;
            case "REV":
                Optional<Review> revItem = revRepository.findById(Long.valueOf(reqParam.get("postNo")));
                if (!revItem.isPresent()) {
                    return null;
                }
                break;
            default:
                log.info("Report postType is not defined");
                return null;
        }

        Report entity = Report.builder()
                .userNo(Long.valueOf(reqParam.get("userNo")))
                .postNo(Long.valueOf(reqParam.get("postNo")))
                .postType(reqParam.get("postType"))
                .repContent(reqParam.get("repContent"))
                .build();

        // TODO rcNo 추가하기

        save(entity);

        updatePostReportStatus(entity.getPostType(), entity.getPostNo());

        return entity;
    }

    public Report dtoToEntity(ReportDTO dto) {
        Report entity = Report.builder()
                .repNo(dto.getRepNo())
                .rcNo(dto.getRcNo())
                .repUuid(dto.getRepUuid())
                .userNo(dto.getUserNo())
                .postNo(dto.getPostNo())
                .repType(dto.getRepType())
                .repContent(dto.getRepContent())
                .complete(dto.isComplete())
                .result(dto.getResult())
                .build();

        return entity;
    }

    public ReportDTO entityToDto(Report entity) {
        ReportDTO dto = ReportDTO.builder()
                .repNo(entity.getRepNo())
                .rcNo(entity.getRcNo())
                .repUuid(entity.getRepUuid())
                .userNo(entity.getUserNo())
                .postNo(entity.getPostNo())
                .postType(entity.getPostType())
                .repType(entity.getRepType())
                .repContent(entity.getRepContent())
                .complete(entity.isComplete())
                .result(entity.getResult())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    public Report select(Long repNo) {
        if (repNo == null) {
            return null;
        }

        if (repNo < 0) {
            return null;
        }

        Optional<Report> item = repository.findById(repNo);

        return item.orElse(null);
    }

    public Report select(String repUuid) {
        if (repUuid == null) {
            return null;
        }

        if (repUuid.isEmpty()) {
            return null;
        }

        Optional<Report> item = repository.findByRepUuid(repUuid);

        return item.orElse(null);
    }

    public List<Report> findAllByPostNo(Long postNo) {
        if (postNo == null) {
            return null;
        }

        if (postNo < 0) {
            return null;
        }

        Optional<List<Report>> items = repository.findAllByPostNo(postNo);

        return items.orElse(null);
    }

    public List<Report> findAllByUserNo(Long userNo) {
        if (userNo == null) {
            return null;
        }

        if (userNo < 0) {
            return null;
        }

        Optional<List<Report>> items = repository.findAllByUserNo(userNo);

        return items.orElse(null);
    }

    public ReportPageResultDTO<ReportDTO, Report> getList(ReportPageRequestDTO requestDTO) {
//        Pageable pageable = requestDTO.getPageable(Sort.by("viewCount").descending());
        boolean flagASC = false;

        Pageable pageable;
        switch (requestDTO.getSortingOrder()) {
            case ASC:
                flagASC = true;
                break;
            case DES:
            default:
                flagASC = false;
        }

        switch (requestDTO.getSortCriterion()) {
            case DATE:
            default:
                if (flagASC) {
                    pageable = requestDTO.getPageable(Sort.by("regDate").ascending());
                } else {
                    pageable = requestDTO.getPageable(Sort.by("regDate").descending());
                }
        }

        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<Report> result = repository.findAll(booleanBuilder, pageable);

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

        Function<Report, ReportDTO> fn = (entity -> entityToDto(entity));

        return new ReportPageResultDTO<>(result, fn);
    }

    public BooleanBuilder getSearch(ReportPageRequestDTO requestDTO) {
        Long userNo = requestDTO.getUserNo();
        Long postNo = requestDTO.getPostNo();
        String postType = requestDTO.getPostType();
        String repType = requestDTO.getRepType();
        String result = requestDTO.getResult();
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        QReport qReport = QReport.report;

        switch (requestDTO.getSearchPostType()) {
            case LOC:
            case COR:
            case COM:
            case REV:
            case PROFILE_PIC:
                conditionBuilder.and(qReport.postType.eq(postType));
                break;
            case ALL:
            default:
        }

        for (ReportPageSearchType searchType : requestDTO.getSearchType()) {
           switch (searchType) {
               case POST:
                   conditionBuilder.and(qReport.postNo.eq(postNo));
                   break;
               case USER:
                   conditionBuilder.and(qReport.userNo.eq(userNo));
                   break;
               case REP_TYPE:
                   conditionBuilder.and(qReport.repType.eq(repType));
                   break;
               case RESULT:
                   conditionBuilder.and(qReport.result.eq(result));
                   break;
           }
        }

        switch (requestDTO.getCompleteType()) {
            case COMPLETE:
                conditionBuilder.and(qReport.complete.eq(true));
                break;
            case PROGRESS:
                conditionBuilder.and(qReport.complete.eq(false));
                break;
            case ALL:
            default:
        }

        return conditionBuilder;
    }


    public void save(Report entity) {
        repository.save(entity);
    }

    public boolean delete(Long repNo) {
        if (repNo == null) {
            return false;
        }

        if (repNo < 0) {
            return false;
        }

        repository.deleteByRepNo(repNo);

        Optional<Report> item = repository.findById(repNo);

        return !item.isPresent();
    }

    public boolean delete(String repUuid) {
        if (repUuid == null) {
            return false;
        }

        if (repUuid.isEmpty()) {
            return false;
        }

        repository.deleteByRepUuid(repUuid);

        Optional<Report> item = repository.findByRepUuid(repUuid);

        return !item.isPresent();
    }

    private void updatePostReportStatus(String postType, Long postNo) {
        List<Report> reportList = findAllByPostNo(postNo);

        if (reportList.size() >= REPORT_STATUS_CHANGE_SIZE) {
            switch (postType) {
                case "LOC":
                    Optional<Location> locItem = locRepository.findById(postNo);
                    if (locItem.isPresent()) {
                        Location locEntity = locItem.get();
                        if (!locEntity.is_reported()) {
                            locEntity.set_reported(true);
                            locRepository.save(locEntity);
                        }
                    }
                    break;
                case "COR":
                    Optional<Course> corItem = corRepository.findById(postNo);
                    if (corItem.isPresent()) {
                        Course corEntity = corItem.get();
                        if (!corEntity.is_reported()) {
                            corEntity.set_reported(true);
                            corRepository.save(corEntity);
                        }
                    }
                    break;
                case "COM":
                    Optional<Comment> comItem = comRepository.findById(postNo);
                    if (comItem.isPresent()) {
                        Comment comEntity = comItem.get();
                        if (!comEntity.is_reported()) {
                            comEntity.set_reported(true);
                            comRepository.save(comEntity);
                        }
                    }
                    break;
                case "REV":
                    Optional<Review> revItem = revRepository.findById(postNo);
                    if (revItem.isPresent()) {
                        Review revEntity = revItem.get();
                        if (!revEntity.is_reported()) {
                            revEntity.set_reported(true);
                            revRepository.save(revEntity);
                        }
                    }
                    break;
            }
        }
    }
}
