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

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReportClusterService {
    @Autowired
    ReportClusterRepository repository;
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

    public ReportCluster register(Map<String, String> reqParam) {
        ReportCluster temp = selectByPostNoPostType(Long.valueOf(reqParam.get("postNo")), reqParam.get("postType"));

        if (temp != null) {
            temp.setRcComplete(false);
            save(temp);
            return selectByPostNoPostType(Long.valueOf(reqParam.get("postNo")), reqParam.get("postType"));
        }

        switch (reqParam.get("postType")) {
            case "LOC" :
                Optional<Location> locItem = locRepository.findById(Long.valueOf(reqParam.get("postNo")));
                if (locItem.isPresent()) {
                    reqParam.put("rcUserNo", String.valueOf(locItem.get().getUser_no()));
                } else {
                    reqParam.put("rcUserNo", null);
                }
                break;
            case "COR" :
                Optional<Course> corItem = corRepository.findById(Long.valueOf(reqParam.get("postNo")));
                if (corItem.isPresent()) {
                    reqParam.put("rcUserNo", String.valueOf(corItem.get().getUser_no()));
                } else {
                    reqParam.put("rcUserNo", null);
                }
                break;
            case "COM" :
                Optional<Comment> comItem = comRepository.findById(Long.valueOf(reqParam.get("postNo")));
                if (comItem.isPresent()) {
                    reqParam.put("rcUserNo", String.valueOf(comItem.get().getUser().getUser_no()));
                } else {
                    reqParam.put("rcUserNo", null);
                }
                break;
            case "REV" :
                Optional<Review> revItem = revRepository.findById(Long.valueOf(reqParam.get("postNo")));
                if (revItem.isPresent()) {
                    reqParam.put("rcUserNo", String.valueOf(revItem.get().getUser_no()));
                } else {
                    reqParam.put("rcUserNo", null);
                }
                break;
            case "PROFILE_PIC" :
                Optional<User> userItem = userRepository.findById(Long.valueOf(reqParam.get("postNo")));
                if (userItem.isPresent()) {
                    reqParam.put("rcUserNo", String.valueOf(userItem.get().getUser_no()));
                } else {
                    reqParam.put("rcUserNo", null);
                }
                break;
        }

        ReportCluster entity = ReportCluster.builder()
                .postNo(Long.valueOf(reqParam.get("postNo")))
                .postType(reqParam.get("postType"))
                .rcUserNo(null)
                .build();

        if (reqParam.get("rcUserNo") != null){
            entity.setRcUserNo(Long.valueOf(reqParam.get("rcUserNo")));
        }

        save(entity);

        Optional<ReportCluster> item = repository.findByRcUuid(entity.getRcUuid());

        return item.orElse(null);
    }

    public ReportCluster dtoToEntity(ReportClusterDTO dto) {
        ReportCluster entity = ReportCluster.builder()
                .rcNo(dto.getRcNo())
                .rcUuid(dto.getRcUuid())
                .postNo(dto.getPostNo())
                .postType(dto.getPostType())
                .rcUserNo(dto.getRcUserNo())
                .rcResult(dto.getRcResult())
                .rcComplete(dto.isRcComplete())
                .repCount(dto.getRepCount())
                .build();

        return entity;
    }

    public ReportClusterDTO entityToDto(ReportCluster entity) {
        ReportClusterDTO dto = ReportClusterDTO.builder()
                .rcNo(entity.getRcNo())
                .rcUuid(entity.getRcUuid())
                .postNo(entity.getPostNo())
                .postType(entity.getPostType())
                .rcUserNo(entity.getRcUserNo())
                .rcResult(entity.getRcResult())
                .rcComplete(entity.isRcComplete())
                .repCount(entity.getRepCount())
                .build();

        return dto;
    }

    public ReportCluster select(Long rcNo) {
        if (rcNo == null) {
            return null;
        }

        if (rcNo < 0) {
            return null;
        }

        Optional<ReportCluster> item = repository.findById(rcNo);

        return item.orElse(null);
    }

    public ReportCluster select(String rcUuid) {
        if (rcUuid == null) {
            return null;
        }

        if (rcUuid.isEmpty()) {
            return null;
        }

        Optional<ReportCluster> item = repository.findByRcUuid(rcUuid);

        return item.orElse(null);
    }

    public ReportCluster selectByPostNoPostType(Long postNo, String postType) {
        Optional<ReportCluster> item = repository.findByPostNoAndPostType(postNo, postType);

        return item.orElse(null);
    }

    public List<ReportCluster> selectAllByRcUserNo(Long rcUserNo) {
        if (rcUserNo == null) {
            return null;
        }

        if (rcUserNo < 0) {
            return null;
        }

        Optional<List<ReportCluster>> items = repository.findAllByRcUserNo(rcUserNo);

        return items.orElse(null);
    }

    public ReportClusterPageResultDTO<ReportClusterDTO, ReportCluster> getList(ReportClusterPageRequestDTO requestDTO) {
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
            case REPORT_COUNT:
            default:
                if (flagASC) {
                    pageable = requestDTO.getPageable(Sort.by("repCount").ascending());
                } else {
                    pageable = requestDTO.getPageable(Sort.by("repCount").descending());
                }
        }

        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<ReportCluster> result = repository.findAll(booleanBuilder, pageable);

        Function<ReportCluster, ReportClusterDTO> fn = (entity -> entityToDto(entity));

        return new ReportClusterPageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSearch(ReportClusterPageRequestDTO requestDTO) {
        Long rcNo = requestDTO.getRcNo();
        Long postNo = requestDTO.getPostNo();
        String postType = requestDTO.getPostType();
        String rcStatus = requestDTO.getRcStatus();
        Boolean rcComplete = requestDTO.getRcComplete();
        Integer repCount = requestDTO.getRepCount();
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        QReportCluster qReportCluster = QReportCluster.reportCluster;

        switch (requestDTO.getSearchPostType()) {
            case LOC:
            case COR:
            case COM:
            case REV:
            case PROFILE_PIC:
                conditionBuilder.and(qReportCluster.postType.eq(postType));
                break;
            case ALL:
            default:
        }

        switch (requestDTO.getCompleteType()) {
            case COMPLETE:
                conditionBuilder.and(qReportCluster.rcComplete.eq(true));
                break;
            case PROGRESS:
                conditionBuilder.and(qReportCluster.rcComplete.eq(false));
                break;
            case ALL:
            default:
        }

        return conditionBuilder;
    }

    public void save(ReportCluster entity) {
        repository.save(entity);
    }

    public boolean delete(Long rcNo) {
        if (rcNo == null) {
            return false;
        }

        if (rcNo < 0) {
            return false;
        }

        repository.deleteByRcNo(rcNo);

        Optional<ReportCluster> item = repository.findById(rcNo);

        return !item.isPresent();
    }

    public boolean delete(String rcUuid) {
        if (rcUuid == null) {
            return false;
        }

        if (rcUuid.isEmpty()) {
            return false;
        }

        repository.deleteByRcUuid(rcUuid);

        Optional<ReportCluster> item = repository.findByRcUuid(rcUuid);

        return !item.isPresent();
    }

    public boolean setRepCount(Long rcNo, Integer repCount) {
        if (rcNo == null || repCount == null) {
            return false;
        }

        if (rcNo < 0 || repCount < 0) {
            return false;
        }

        Optional<ReportCluster> item = repository.findById(rcNo);
        if (item.isPresent()) {
            ReportCluster rcEntity = item.get();
            rcEntity.setRepCount(repCount);

            save(rcEntity);

            item = repository.findById(rcNo);

            if (item.get().getRepCount() == repCount) {
                return true;
            }
        }

        return false;
    }

    public boolean setCompleteAndResult(Long rcNo, String rcResult) {
        if (rcNo == null) {
            return false;
        }

        if (rcNo < 0) {
            return false;
        }

        Optional<ReportCluster> item = repository.findById(rcNo);
        if (!item.isPresent()) {
            return false;
        }

        ReportCluster rcEntity = item.get();
        String oldResult = "";
        if (rcEntity.getRcResult() != null || !rcEntity.getRcResult().equals("")) {
            oldResult = rcEntity.getRcResult() + "\r\n";
        }
        rcEntity.setRcComplete(true);
        rcEntity.setRcResult(oldResult + rcResult + "시간 (" + LocalDateTime.now() + ")");

        save(rcEntity);

        item = repository.findById(rcNo);

        if (!item.get().isRcComplete() || !item.get().getRcResult().equals(rcResult)) {
            return false;
        }

        return true;
    }
}
