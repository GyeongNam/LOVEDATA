package com.project.love_data;

import com.project.love_data.businessLogic.service.*;
import com.project.love_data.dto.*;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import com.project.love_data.repository.CommentRepository;
import com.project.love_data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class CommentTest {
    @Autowired
    CommentRepository cmtRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LocationService locService;
    @Autowired
    CommentService cmtService;

    @Test
    public void InsertCommentInit() {
        Location loc = locService.locationNameSearch("중부대학교 충청", MatchOption.CONTAIN).get(0);

        Comment entity = new Comment();
        for (int i = 0; i < 25; i++) {
            entity = Comment.builder()
                    .cmtContent("Comment Content " + i)
                    .user(userRepository.findById(1L).get())
                    .location(loc)
                    .cmtIdx((long) i)
                    .build();

            cmtRepository.save(entity);

            entity = cmtRepository.findByCmt_uuid(entity.getCmtUuid()).get();

            loc.addCmt(entity);

            locService.update(loc);
        }

        loc = locService.locationNameSearch("중부대학교 고양", MatchOption.CONTAIN).get(0);

        for (int i = 0; i < 25; i++) {
            entity = Comment.builder()
                    .cmtContent("Comment Content " + i)
                    .user(userRepository.findById(1L).get())
                    .location(loc)
                    .cmtIdx((long) i)
                    .build();

            cmtRepository.save(entity);

            entity = cmtRepository.findByCmt_uuid(entity.getCmtUuid()).get();

            loc.addCmt(entity);

            locService.update(loc);
        }
    }

//    @Test
//    public void InsertComment() {
//        for (int i = 0; i < 10; i++) {
//            Location loc = locService.locationNameSearch(String.valueOf(i), MatchOption.CONTAIN).get(0);
//            Comment entity = Comment.builder()
//                    .cmtContent("Comment Content " + i)
//                    .user(userRepository.findById(loc.getUser_no()).get())
//                    .location(loc).build();
//
//            cmtRepository.save(entity);
//
//            entity = cmtRepository.findByCmt_uuid(entity.getCmtUuid()).get();
//            System.out.println(entity.getCmtNo());
//
////            Hibernate.initialize(loc.getCmtSet());
//
//            loc.addCmt(entity);
//
//            System.out.println(loc.getLoc_no() + "\tloc : " + loc);
//            System.out.println("loc comment : ");
//            for (Comment comment : loc.getCmtSet()) {
//                System.out.print(comment + "\t");
//            }
//            System.out.println();
//
//            locService.update(loc);
//        }
//
//        LocationDTO dto = locService.selectLocDTO(1L);
//
//        Comment cmt = Comment.builder()
//                .user(userRepository.findById(0L).get())
//                .cmtContent("Temp")
//                .location(locService.dtoToEntity(dto)).build();
//
//        cmtRepository.save(cmt);
//
//        dto.addCmt(cmt);
//        Location entity = locService.dtoToEntity(dto);
//
//        locService.update(entity);
//    }
//
//    @Test
//    public void readComment(){
//        Location loc = locService.locationNameSearch("중부대학교 충청캠퍼스", MatchOption.CONTAIN).get(0);
////        Location loc = locService.locationNameSearch(String.valueOf(0), SearchOption.CONTAIN).get(0);
//
//        System.out.println("loc Comment Set");
//        for (Comment comment : loc.getCmtSet()) {
//            System.out.println(comment);
//        }
//    }
//
//    @Test
//    public void updateComment() {
//        Location loc = locService.locationNameSearch(String.valueOf(0), MatchOption.CONTAIN).get(0);
//
//        System.out.println("업데이트 전 댓글");
//        System.out.println(loc.getLoc_no() + " : " + loc.getCmtSet());
//
//        for (Comment cmt : loc.getCmtSet()) {
//            cmt.setCmtContent("Update Comment Test");
//            cmtService.update(cmt);
//        }
//
//        System.out.println("업데이트 후 댓글");
//        System.out.println(loc.getLoc_no() + " : " + loc.getCmtSet());
//    }
//
//    @Test
//    public void deleteComment() {
//        Location loc = locService.locationNameSearch("중부대학교 고양캠퍼스", MatchOption.CONTAIN).get(0);
//
//        System.out.println("삭제 전 댓글");
//        System.out.println(loc.getLoc_no() + " : " + loc.getCmtSet());
//
////        for (Comment cmt : loc.getCmtSet()) {
////            cmtService.delete(cmt.getCmtNo());
////        }
//
//        cmtService.delete(cmtService.selectByLocNoAndCmtIdx(loc.getLoc_no(), 1L).getCmtNo());
//
//        System.out.println("삭제 후 댓글");
//        System.out.println(loc.getLoc_no() + " : " + loc.getCmtSet());
//    }
//
//    @Test
//    public void permaDeleteComment() {
//        Location loc = locService.locationNameSearch("중부대학교 고양캠퍼스", MatchOption.CONTAIN).get(0);
//
//        System.out.println("삭제 전 댓글");
//        System.out.println(loc.getLoc_no() + " : " + loc.getCmtSet());
//
////        for (Comment cmt : loc.getCmtSet()) {
////            cmtService.delete(cmt.getCmtNo());
////        }
//
//        cmtService.permaDelete(cmtService.selectByLocNoAndCmtIdx(loc.getLoc_no(), 22L));
//
//        System.out.println("삭제 후 댓글");
//        System.out.println(loc.getLoc_no() + " : " + loc.getCmtSet());
//    }
//
//    @Test
//    public void testList() {
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(4)
//                .build();
//
//        System.out.println("pageRequestDTO = " + pageRequestDTO);
//    }
//
//    @Test
//    public void listComment() {
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(10)
//                .build();
//
//        PageResultDTO<CommentDTO, Comment> resultDTO
//                = cmtService.getCmtPage(pageRequestDTO, CommentPageType.ALL, CommentSortType.IDX_ASC, CommentSearchType.Live);
//
//        System.out.println("PREV = " + resultDTO.isPrev());
//        System.out.println("NEXT = " + resultDTO.isNext());
//        System.out.println("TOTAL : " + resultDTO.getTotalPage());
//
//        System.out.println("-------------------------------------------------");
//        for (CommentDTO commentDTO : resultDTO.getDtoList()) {
//            System.out.println(commentDTO.getCmtNo() + "\tcommentDTO = " + commentDTO);
//        }
//
//        System.out.println("=================================================");
//        List<Integer> temp = resultDTO.getPageList();
//        resultDTO.getPageList().forEach(i -> System.out.println(i));
//    }
//
//    @Test
//    public void pageCommentLocFilter() {
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(10)
//                .locNo(1L)
//                .build();
//
//        PageResultDTO<CommentDTO, Comment> resultDTO
//                = cmtService.getCmtPage(pageRequestDTO, CommentPageType.LOCATION, CommentSortType.IDX_ASC, CommentSearchType.Live);
//
//        System.out.println("PREV = " + resultDTO.isPrev());
//        System.out.println("NEXT = " + resultDTO.isNext());
//        System.out.println("TOTAL : " + resultDTO.getTotalPage());
//
//        System.out.println("-------------------------------------------------");
//        for (CommentDTO commentDTO : resultDTO.getDtoList()) {
//            System.out.println(commentDTO.getCmtNo() + "(commentNo)" + "\tcommentDTO = " + commentDTO);
//            System.out.println(commentDTO.getLocation().getLoc_no() + "(locationNo)");
//        }
//
//        System.out.println("=================================================");
//        List<Integer> temp = resultDTO.getPageList();
//        resultDTO.getPageList().forEach(i -> System.out.println(i));
//    }
}
