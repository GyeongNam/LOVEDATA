package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.*;
import com.project.love_data.repository.NoticeRepository;
import com.project.love_data.repository.QuestionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ServiceCenterService {
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

    public Questions secret_check(String qu_no, String user_no){
        Questions questions = questionsRepository.qu_secret_check(qu_no, user_no);
        return questions;
    }

    public List<Questions> qu_search_all(String no, String text){
        Optional<List<Questions>> questions;
        if(no.equals("1")){
            questions = questionsRepository.qu_search_all("%"+text+"%");
            return questions.orElse(new ArrayList<>());
        }
        else if(no.equals("2")){
            questions = questionsRepository.qu_search_title("%"+text+"%");
            return questions.orElse(new ArrayList<>());
        }
        else {
            questions = questionsRepository.qu_search_text("%"+text+"%");
            return questions.orElse(new ArrayList<>());
        }
    }
}
