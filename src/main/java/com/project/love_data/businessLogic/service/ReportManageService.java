package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.*;
import com.project.love_data.model.service.Report;
import com.project.love_data.model.service.ReportCluster;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.project.love_data.util.ConstantValues.reportTypeMapEN2KR;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReportManageService {
    @Autowired
    ReportService repService;
    @Autowired
    ReportClusterService rcService;

    public Report registerReport(Map<String, String> reqParam) {
        ReportCluster rcEntity = rcService.register(reqParam);

        if (rcEntity == null) {
            log.warn("Error Occurs during registering ReportCluster");
            return null;
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

    public List<String> getENReportTypeList(Long rcNo) {
        return repService.getReportTypesByRcNo(rcNo);
    }

    public List<String> getKRReportTypeList(Long rcNo) {
        List<String> holder = repService.getReportTypesByRcNo(rcNo);
        List<String> result = new ArrayList<>();

        for (String s : holder) {
            result.add(reportTypeMapEN2KR.get(s));
        }

        return result;
    }

    public Report getReportByRepNo(Long repNo) {
        return repService.select(repNo);
    }

    public List<Report> getReportsByUserNo(Long userNo) {
        return repService.findAllByUserNo(userNo);
    }

    public List<ReportCluster> getReportClustersByRcUserNo(Long rcUserNo) {
        return rcService.selectAllByRcUserNo(rcUserNo);
    }

    public ReportCluster getReportClusterByRcNo(Long rcNo) {
        return rcService.select(rcNo);
    }

    public ReportClusterDTO getReportClusterDTO(ReportCluster entity) {
        return rcService.entityToDto(entity);
    }

    public boolean processReportCluster(Long rcNo, List<Long> repNoList, String result, String adminComment) {
//        String msg = "";
//        ReportCluster rcEntity = rcService.select(rcNo);
//
//        if (rcEntity == null) {
//            return false;
//        }
//
//        for (int i = 0; i < repNoList.size(); i++) {
//            Report repEntity = repService.select(repNoList.get(i));
//            if (repEntity == null) {
//                continue;
//            }
//
//            msg += "Ticket( " + repEntity.getRepNo() + ")" + repEntity.getUserNo() + "번 유저가 " + repEntity.getRepType() + "으로 신고했습니다.(" + repEntity.getRegDate() + ")\r\n";
//        }

        if (!processReport(repNoList, result)) {
            return false;
        }

        syncReportClusterRepCount(rcNo);

        if (!rcService.setCompleteAndResult(rcNo, adminComment)) {
            return false;
        }

        return true;
    }

    public ReportCluster getReportClusterEntity(ReportClusterDTO dto) {
        return rcService.dtoToEntity(dto);
    }

    // 주어진 명령(삭제 혹은 무시)에 따라 신고를 처리하는 메소드
    public boolean processReport(List<Long> repNoList, String result) {
        List<Long> completeList = new ArrayList<>();
        boolean isError = false;

        for (Long repNo : repNoList) {
            if (!repService.processReport(repNo, result)) {
                log.warn("Error occurs during process report");
                isError = true;
                break;
            } else {
                completeList.add(repNo);
            }
        }

        if (isError) {
            for (Long rcNo : completeList) {
                if (!repService.undoReport(rcNo)){
                    log.warn("Error occurs during undo process report");
                    return false;
                }
            }
            return false;
        }

        return true;
    }

    public Integer recentReportCount(int dateDuration) {
        return repService.recentReportCount(dateDuration);
    }

    public Integer reportCount(Long rcNo, Boolean complete) {
        return repService.reportCount(rcNo, complete);
    }

    public Long getRcNo(Long postNo, String postType) {
        ReportCluster rcEntity = rcService.selectByPostNoPostType(postNo, postType);

        if (rcEntity == null) {
            return null;
        }

        return rcEntity.getRcNo();
    }

    public boolean syncReportClusterRepCount(Long rcNo) {
        if (rcNo == null) {
            return false;
        }

        if (rcNo < 0) {
            return false;
        }

        Integer count = reportCount(rcNo, false);

        if (count == null) {
            return false;
        }

        return rcService.setRepCount(rcNo, count);
    }
}
