package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.*;
import com.project.love_data.model.service.Report;
import com.project.love_data.model.service.ReportCluster;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReportManageService {
    @Autowired
    ReportService repService;
    @Autowired
    ReportClusterService rcService;

    public Report registerReport(Map<String, String> reqParam) {
        ReportCluster rcEntity = rcService.selectByPostNoPostType(Long.valueOf(reqParam.get("postNo")),
                reqParam.get("postType"));

        if (rcEntity == null) {
            rcEntity = rcService.register(reqParam);
            if (rcEntity == null) {
                log.warn("Error Occurs during registering ReportCluster");
                return null;
            }
        }
        reqParam.put("rcNo", String.valueOf(rcEntity.getRcNo()));

        return repService.register(reqParam);
    }

    // ReportCluster의 페이지를 가져오는 메소드
    public ReportClusterPageResultDTO<ReportClusterDTO, ReportCluster> getReportClusterPage(ReportClusterPageRequestDTO pageRequestDTO) {
        return rcService.getList(pageRequestDTO);
    }

    // Report의 페이지를 가져오는 메소드
    public ReportPageResultDTO<ReportDTO, Report> getReportPage(ReportPageRequestDTO pageRequestDTO) {
        return repService.getList(pageRequestDTO);
    }

    // 주어진 명령(삭제 혹은 무시)에 따라 신고를 처리하는 메소드
    public void handleReport() {

    }
}
