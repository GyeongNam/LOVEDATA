package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.ReportClusterDTO;
import com.project.love_data.dto.ReportDTO;
import com.project.love_data.model.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReportClusterService {

    public ReportCluster register() {
        ReportCluster entity = ReportCluster.builder()
                .build();

        return entity;
    }

    public ReportCluster dtoToEntity(ReportClusterDTO dto) {
        ReportCluster entity = ReportCluster.builder()
                .rcNo(dto.getRcNo())
                .rcUuid(dto.getRcUuid())
                .postNo(dto.getPostNo())
                .postType(dto.getPostType())
                .rcStatus(dto.getRcStatus())
                .rcComplete(dto.isRcComplete())
                .build();

        return entity;
    }

    public ReportClusterDTO entityToDto(ReportCluster entity) {
        ReportClusterDTO dto = ReportClusterDTO.builder()
                .rcNo(entity.getRcNo())
                .rcUuid(entity.getRcUuid())
                .postNo(entity.getPostNo())
                .postType(entity.getPostType())
                .rcStatus(entity.getRcStatus())
                .rcComplete(entity.isRcComplete())
                .build();

        return dto;
    }


}
