package com.project.love_data.businessLogic.service;
import com.project.love_data.dto.CalenderDTO;
import com.project.love_data.dto.CommentDTO;
import com.project.love_data.dto.UserDTO;
import com.project.love_data.model.service.Calender;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.UserLikeLoc;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.CalenderRepository;
import com.project.love_data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CalenderService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CalenderRepository calenderRepository;

    public Calender dtoToEntity(CalenderDTO dto){
        Calender entity = Calender.builder()
                .cal_no(dto.getCal_no())
                .user_mail(dto.getUser_mail())
                .title(dto.getTitle())
                .start(dto.getStart())
                .end(dto.getEnd())
                .color(dto.getColor())
                .road(dto.getRoad())
                .road2(dto.getRoad2())
                .text(dto.getText())
                .all_day(dto.isAll_day())
                .build();

        return entity;
    }

    public CalenderDTO entityToDto(Calender entity) {
        CalenderDTO dto = CalenderDTO.builder()
                .cal_no(entity.getCal_no())
                .user_mail(entity.getUser_mail())
                .title(entity.getTitle())
                .start(entity.getStart())
                .end(entity.getEnd())
                .color(entity.getColor())
                .road(entity.getRoad())
                .road2(entity.getRoad2())
                .text(entity.getText())
                .all_day(entity.isAll_day())
                .build();

        return dto;
    }

    public void update(Calender calender){
        calenderRepository.save(calender);
    }

    public void delete(Calender calender) {
        calenderRepository.delete(calender);
    }

    public List<Calender> Cal_select(String email) {
        Optional<List<Calender>> item = calenderRepository.findcal(email);

        return item.orElse(new ArrayList<>());
//        return item.isPresent() ? entityToDto(item.get()) : null;
    }
    public Calender cal_select_no(String no){
        Calender calender = calenderRepository.findcal_no(no);
        return calender;
    }

}
