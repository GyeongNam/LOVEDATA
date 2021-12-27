package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.*;
import com.project.love_data.repository.*;
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
    @Autowired
    NoticeIMGRepository noticeIMGRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventAttendRepository eventAttendRepository;
    @Autowired
    EventWinRepository eventWinRepository;

    public void not_update(Notice notice){
        noticeRepository.save(notice);
    }

    public void not_delete(Notice notice) {
        noticeRepository.delete(notice);
    }

    public void qu_update(Questions questions){
        questionsRepository.save(questions);
    }

    public void qu_delete(Questions questions) { questionsRepository.delete(questions); }

    public void notimg_delete(NoticeIMG noticeIMG) {
        noticeIMGRepository.delete(noticeIMG);
    }

    public void notiimg_update(NoticeIMG noticeIMG){
        noticeIMGRepository.save(noticeIMG);
    }

    public void ev_update(Event event){
        eventRepository.save(event);
    }

    public void ev_winupdate(EventWin eventWIn){
        eventWinRepository.save(eventWIn);
    }

    public List<Notice> not_select_all(){
        Optional<List<Notice>> notice = noticeRepository.noti_find_All();
        return notice.orElse(new ArrayList<>());
    }

    public List<Questions> qu_select_all(){
        Optional<List<Questions>> questions = questionsRepository.qu_find_All();
        return questions.orElse(new ArrayList<>());
    }

    public List<Questions> qua_all(){
        Optional<List<Questions>> questions = questionsRepository.qua_All();
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

    public List<Notice> no_search_all(String no, String text){
        Optional<List<Notice>> notices;
        if(no.equals("1")){
            notices = noticeRepository.no_search_all("%"+text+"%");
            return notices.orElse(new ArrayList<>());
        }
        else if(no.equals("2")){
            notices = noticeRepository.no_search_title("%"+text+"%");
            return notices.orElse(new ArrayList<>());
        }
        else {
            notices = noticeRepository.no_search_text("%"+text+"%");
            return notices.orElse(new ArrayList<>());
        }
    }

    public List<NoticeIMG> select_notiimg(String nic){
        Optional<List<NoticeIMG>> imglist = noticeIMGRepository.select_notiimg(nic);
        return imglist.orElse(new ArrayList<>());
    }

    public List<NoticeIMG> select_notiimg_num(String num){
        Optional<List<NoticeIMG>> imglist = noticeIMGRepository.select_notiimg_num(num);
        return imglist.orElse(new ArrayList<>());
    }

    public List<Questions> qu_findAllByUser_no(String user_no){
        Optional<List<Questions>> item = questionsRepository.qu_findAllByUser_no(user_no);
        return item.orElse(new ArrayList<>());
    }

    // 이벤트
    public List<Event> ev_search_all(String no, String text){
        Optional<List<Event>> events;
        if(no.equals("1")){
            events = eventRepository.ev_search_all("%"+text+"%");
            return events.orElse(new ArrayList<>());
        }
        else if(no.equals("2")){
            events = eventRepository.ev_search_title("%"+text+"%");
            return events.orElse(new ArrayList<>());
        }
        else {
            events = eventRepository.ev_search_text("%"+text+"%");
            return events.orElse(new ArrayList<>());
        }
    }

    public List<Event> ev_select_all(){
        Optional<List<Event>> events = eventRepository.ev_find_All();
        return events.orElse(new ArrayList<>());
    }

    public List<Event> ev_find_item(){
        Optional<List<Event>> events = eventRepository.ev_find_item();
        return events.orElse(new ArrayList<>());
    }

    public List<Event> ev_all(){
        Optional<List<Event>> events = eventRepository.ev_All();
        return events.orElse(new ArrayList<>());
    }

    public Event ev_select_no(String no){
        Event event = eventRepository.ev_find_no(no);
        return event;
    }

    public List<EventAttend> evattd_find_UserEvNo(String user_no, String ev_no){
        Optional<List<EventAttend>> events = eventAttendRepository.evattd_find_UserEvNo(user_no,ev_no);
        return events.orElse(new ArrayList<>());
    }

    public List<EventAttend> evattd_find_EvNo(String ev_no){
        Optional<List<EventAttend>> events = eventAttendRepository.evattd_find_EvNo(ev_no);
        return events.orElse(new ArrayList<>());
    }

    public List<EventAttend> evattd_find_UserNo(String user_no){
        Optional<List<EventAttend>> events = eventAttendRepository.evattd_find_UserNo(user_no);
        return events.orElse(new ArrayList<>());
    }

    public List<EventWin> evwin_find_EvNo(String ev_no){
        Optional<List<EventWin>> events = eventWinRepository.evwin_find_EvNo(ev_no);
        return events.orElse(new ArrayList<>());
    }

}
