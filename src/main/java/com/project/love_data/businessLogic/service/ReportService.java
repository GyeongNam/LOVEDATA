package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.*;
import com.project.love_data.model.service.*;
import com.project.love_data.model.user.User;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReportClusterRepository rcRepository;
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
            case "PROFILE_PIC":
                Optional<User> userItem = userRepository.findById(Long.valueOf(reqParam.get("postNo")));
                if (!userItem.isPresent()){
                    return null;
                }
                break;
            default:
                log.info("Report postType is not defined");
                return null;
        }

        Optional<List<Report>> repItems = repository.findAllByUserNo(Long.valueOf(reqParam.get("userNo")));
        if (repItems.isPresent()) {
            List<Report> reportList = repItems.get();

            for (Report report : reportList) {
                if (report.getRcNo().equals(Long.valueOf(reqParam.get("rcNo")))) {
                    if (Boolean.valueOf(reqParam.get("dupCheck"))) {
                        break;
                    } else {
                        return null;
                    }
                }
            }
        }

        Report entity = Report.builder()
                .userNo(Long.valueOf(reqParam.get("userNo")))
                .repContent(reqParam.get("repContent"))
                .rcNo(Long.valueOf(reqParam.get("rcNo")))
                .repType(reqParam.get("repType"))
                .build();

        save(entity);

        updatePostReportStatus(reqParam.get("postType"), Long.valueOf(reqParam.get("postNo")));

        return entity;
    }

    public Report dtoToEntity(ReportDTO dto) {
        Report entity = Report.builder()
                .repNo(dto.getRepNo())
                .rcNo(dto.getRcNo())
                .repUuid(dto.getRepUuid())
                .userNo(dto.getUserNo())
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

    public List<Report> findAllByRcNo(Long rcNo) {
        if (rcNo == null) {
            return null;
        }

        if (rcNo < 0) {
            return null;
        }

        Optional<List<Report>> items = repository.findAllByRcNo(rcNo);

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

    public List<Report> findAllByPostNoAndPostType(Long postNo, String postType) {
        if (postNo == null || postType == null) {
            return null;
        }

        if (postNo < 0 || postType.equals("")) {
            return null;
        }

        Optional<ReportCluster> items = rcRepository.findByPostNoAndPostType(postNo, postType);
        
        if (!items.isPresent()) {
            return null;
        }
        
        Optional<List<Report>> repItems = repository.findAllByRcNo(items.get().getRcNo());

        return repItems.orElse(null);
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

        Function<Report, ReportDTO> fn = (entity -> entityToDto(entity));

        return new ReportPageResultDTO<>(result, fn);
    }

    public BooleanBuilder getSearch(ReportPageRequestDTO requestDTO) {
        Long rcNo = requestDTO.getRcNo();
        Long userNo = requestDTO.getUserNo();
        String repType = requestDTO.getRepType();
        String result = requestDTO.getResult();
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        QReport qReport = QReport.report;

        switch (requestDTO.getCompleteType()) {
            case COMPLETE:
                conditionBuilder.and(qReport.complete.eq(true));
                break;
            case ALL:
                break;
            case PROGRESS:
            default:
                conditionBuilder.and(qReport.complete.eq(false));
        }

        switch (requestDTO.getSearchType()) {
            case SEARCH:
                conditionBuilder.and(qReport.rcNo.eq(rcNo));
            case NONE:
            default:
        }

        if (repType != null && !repType.equals("ALL")) {
            conditionBuilder.and(qReport.repType.eq(repType));
        }

        if (result != null) {
            conditionBuilder.and(qReport.result.eq(result));
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
        List<Report> reportList = findAllByPostNoAndPostType(postNo, postType);

        if (reportList.size() >= REPORT_STATUS_CHANGE_SIZE) {
            switch (postType) {
                case "LOC":
                    Optional<Location> locItem = locRepository.findById(postNo);
                    if (locItem.isPresent()) {
                        Location locEntity = locItem.get();
                        if (!locEntity.is_reported()) {
                            locEntity.set_reported(true);
                            locEntity.set_deleted(true);
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
                            corEntity.set_deleted(true);
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
                            comEntity.set_deleted(true);
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
                            revEntity.set_deleted(true);
                            revRepository.save(revEntity);
                        }
                    }
                    break;
            }
        }
    }

    public List<String> getReportTypesByRcNo(Long rcNo) {
        if (rcNo == null) {
            return null;
        }

        if (rcNo < 0) {
            return null;
        }

        Optional<List<String>> items = repository.findRepTypesByRcNo(rcNo);
        List<String> result = new ArrayList<>();

        if (items.isPresent()) {
            List<String> item = items.get();

            for (String s : item) {
                if (!result.contains(s)) {
                    result.add(s);
                }
            }

            return result;
        }

        return null;
    }

    public boolean processReport(Long repNo, String result) {
        if (repNo == null) {
            return false;
        }

        if (repNo < 0) {
            return false;
        }

        Report repEntity = select(repNo);

        repEntity.setResult(result);
        repEntity.setComplete(true);

        save(repEntity);

        repEntity = select(repNo);

        if (!repEntity.getResult().equals(result)) {
            return false;
        }

        if (!repEntity.isComplete()) {
            return false;
        }

        return true;
    }

    public boolean undoReport(Long repNo) {
        if (repNo == null) {
            return false;
        }

        if (repNo < 0) {
            return false;
        }

        Report repEntity = select(repNo);

        repEntity.setResult(null);
        repEntity.setComplete(false);

        save(repEntity);

        repEntity = select(repNo);

        if (repEntity.getResult() != null) {
            return false;
        }

        if (repEntity.isComplete()) {
            return false;
        }

        return true;
    }

    public Integer recentReportCount(int dateDuration) {
        if (dateDuration < 0) {
            return null;
        }

       Optional<List<Report>> items = repository.getRecentReportsByDateDuration(dateDuration);

        if (items.isPresent()) {
            return items.get().size();
        }

        return null;
    }

    public Integer reportCount (Long rcNo, Boolean complete) {
        if (rcNo == null) {
            return 0;
        }

        if (rcNo < 0) {
            return 0;
        }

        Optional<List<Report>> items = null;

        if (complete == null) {
            items = repository.findAllByRcNo(rcNo);
        } else {
            items = repository.findAllByRcNoAndComplete(rcNo, complete);
        }

        if (items.isPresent()) {
            return items.get().size();
        }

        return 0;
    }
}
