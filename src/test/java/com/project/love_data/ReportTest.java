package com.project.love_data;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.*;
import com.project.love_data.model.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class ReportTest {
    @Autowired
    ReportManageService reportManageService;
    @Autowired
    LocationService locService;
    @Autowired
    CourseService corService;
    @Autowired
    CommentService comService;
    @Autowired
    ReviewService revService;
    @Autowired
    ReportClusterService rcService;

    @Test
    public void RegisterReport() {
        LocationDTO locDTO = null;
        CourseDTO corDTO = null;
        CommentDTO comDTO = null;
        ReviewDTO revDTO = null;
        Map<String, String> reqParam = new HashMap<>();

        Report locReport = null;
        Report corReport = null;
        Report comReport = null;
        Report revReport = null;

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResultDTO<LocationDTO, Location> locPageResultDTO = locService.getList(pageRequestDTO);
        if (!locPageResultDTO.getDtoList().isEmpty()) {
            locDTO = locPageResultDTO.getDtoList().get(0);
            pageRequestDTO.setLocNo(locDTO.getLoc_no());
            System.out.println("장소 : " + locDTO);
        }

        PageResultDTO<CourseDTO, Course> corPageResultDTO = corService.getList(pageRequestDTO);
        if (!corPageResultDTO.getDtoList().isEmpty()) {
            corDTO = corPageResultDTO.getDtoList().get(0);
            pageRequestDTO.setCorNo(corDTO.getCor_no());
            System.out.println("코스 : " + corDTO);
        }

        if (pageRequestDTO.getLocNo() != null) {
            PageResultDTO<CommentDTO, Comment> comPageResultDTO = comService.getCmtPage(pageRequestDTO);
            if (!comPageResultDTO.getDtoList().isEmpty()) {
                comDTO = comPageResultDTO.getDtoList().get(0);
                System.out.println("댓글 : " + comDTO.getCmtNo() + ", " + comDTO.getCmtContent());
            }
        }

        if (pageRequestDTO.getCorNo() != null) {
            PageResultDTO<ReviewDTO, Review> revPageResultDTO = revService.getRevPage(pageRequestDTO);
            if (!revPageResultDTO.getDtoList().isEmpty()) {
                revDTO = revPageResultDTO.getDtoList().get(0);
                System.out.println("리뷰 : " + revDTO);
            }
        }

        if (locDTO != null) {
            reqParam.put("postNo", String.valueOf(locDTO.getLoc_no()));
            reqParam.put("userNo", String.valueOf(locDTO.getUser_no()));
            reqParam.put("postType", "LOC");
            reqParam.put("repContent", "장소 신고 테스트");
            reqParam.put("repType", "신고 테스트 1");

            locReport = reportManageService.registerReport(reqParam);
            System.out.println(locReport);

            reqParam.put("repContent", "중복 확인 테스트");

            locReport = reportManageService.registerReport(reqParam);
            System.out.println(locReport);
        }

        if (corDTO != null) {
            reqParam.clear();
            reqParam.put("postNo", String.valueOf(corDTO.getCor_no()));
            reqParam.put("userNo", String.valueOf(corDTO.getUser_no()));
            reqParam.put("postType", "COR");
            reqParam.put("repContent", "코스 신고 테스트");
            reqParam.put("repType", "신고 테스트 2");

            corReport = reportManageService.registerReport(reqParam);
            System.out.println(corReport);
        }

        if (comDTO != null) {
            reqParam.clear();
            reqParam.put("postNo", String.valueOf(comDTO.getCmtNo()));
            reqParam.put("userNo", String.valueOf(comDTO.getUser().getUser_no()));
            reqParam.put("postType", "COM");
            reqParam.put("repContent", "댓글 신고 테스트");
            reqParam.put("repType", "신고 테스트 3");

            comReport = reportManageService.registerReport(reqParam);
            System.out.println(comReport);
        }

        if (revDTO != null) {
            reqParam.clear();
            reqParam.put("postNo", String.valueOf(revDTO.getRevNo()));
            reqParam.put("userNo", String.valueOf(revDTO.getUserNo()));
            reqParam.put("postType", "REV");
            reqParam.put("repContent", "리뷰 신고 테스트");
            reqParam.put("repType", "신고 테스트 4");

            revReport = reportManageService.registerReport(reqParam);
            System.out.println(revReport);
        }
    }

    @Test
    public void addEmptyReportCluster() {
        LocationDTO locDTO = null;
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResultDTO<LocationDTO, Location> locPageResultDTO = locService.getList(pageRequestDTO);
        if (!locPageResultDTO.getDtoList().isEmpty()) {
            locDTO = locPageResultDTO.getDtoList().get(0);

            for (int i = 0; i < 10; i++) {
                Map<String, String> reqParam = new HashMap<>();
                reqParam.put("postNo", String.valueOf(locDTO.getLoc_no()));
                reqParam.put("userNo", String.valueOf(locDTO.getUser_no()));
                reqParam.put("postType", "LOC");
                reqParam.put("repContent", "임시");
                reqParam.put("repType", "임시");

                Report report = reportManageService.registerReport(reqParam);
                System.out.println(i + ")\n");
                System.out.println(report);

                reqParam.put("postNo", ("123123" + i));
                ReportCluster reportCluster = rcService.register(reqParam);
                System.out.println(reportCluster + "\n");
            }
        }
    }

    @Test
    public void ListTest() {
        ReportPageRequestDTO requestDTO = ReportPageRequestDTO.builder()
                .completeType(ReportPageCompleteType.ALL).build();
        ReportPageResultDTO<ReportDTO, Report> reportPageResultDTO = reportManageService.getReportPage(requestDTO);

        System.out.println("Report");
        for (ReportDTO reportDTO : reportPageResultDTO.getDtoList()) {
            System.out.println((reportPageResultDTO.getDtoList().indexOf(reportDTO) + 1) +  ") " + reportDTO);
        }

        ReportClusterPageRequestDTO pageRequestDTO = ReportClusterPageRequestDTO.builder()
                .completeType(ReportPageCompleteType.ALL)
                .build();
        ReportClusterPageResultDTO<ReportClusterDTO, ReportCluster> reportClusterPageResultDTO
                = reportManageService.getReportClusterPage(pageRequestDTO);

        System.out.println("\n\nReportCluster");
        for (ReportClusterDTO clusterDTO : reportClusterPageResultDTO.getDtoList()) {
            System.out.println((reportClusterPageResultDTO.getDtoList().indexOf(clusterDTO) + 1) + ") " + clusterDTO);
        }
    }

    @Test
    public void SelectTest() {
        System.out.println(reportManageService.getReportClustersByRcUserNo(1L));
        System.out.println(reportManageService.getReportClustersByRcUserNo(2L));
    }
}
