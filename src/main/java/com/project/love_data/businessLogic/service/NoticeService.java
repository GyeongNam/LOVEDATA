package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.*;
import com.project.love_data.repository.NoticeRepository;
import com.project.love_data.repository.QuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    NoticeRepository noticeRepository;

    public void not_update(Notice notice){
        noticeRepository.save(notice);
    }

    public void not_delete(Notice notice) {
        noticeRepository.delete(notice);
    }

    public void qu_update(Questions questions){
        questionsRepository.save(questions);
    }

    public void qu_delete(Questions questions) {
        questionsRepository.delete(questions);
    }

    public List<Notice> not_select_all(){
        Optional<List<Notice>> notice = noticeRepository.noti_find_All();
        return notice.orElse(new ArrayList<>());
    }

    public List<Questions> qu_select_all(){
        Optional<List<Questions>> questions = questionsRepository.qu_find_All();
        return questions.orElse(new ArrayList<>());
    }

    public Notice noti_select_no(String no){
        Notice notice = noticeRepository.noti_find_no(no);
        return notice;
    }

    public Questions qu_select_no(String no){
        Questions questions = questionsRepository.qu_find_no(no);
        return questions;
    }
}
