package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.*;
import com.project.love_data.model.service.*;
import com.project.love_data.repository.ReportClusterRepository;
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
public class ReportClusterService {
    @Autowired
    ReportClusterRepository repository;

    public ReportCluster register(Map<String, String> reqParam) {
        ReportCluster temp = selectByPostNoPostType(Long.valueOf(reqParam.get("postNo")), reqParam.get("postType"));

        if (temp != null) {
            log.warn("Duplicated ReportCluster Found");
            return null;
        }

        ReportCluster entity = ReportCluster.builder()
                .postNo(Long.valueOf(reqParam.get("postNo")))
                .postType(reqParam.get("postType"))
                .build();

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
}
