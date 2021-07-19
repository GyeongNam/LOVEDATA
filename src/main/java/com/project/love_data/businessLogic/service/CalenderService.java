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
                .user_no(dto.getUser_no())
                .title(dto.getTitle())
                .place(dto.getPlace())
                .start(dto.getStart())
                .end(dto.getEnd())
                .color(dto.getColor())
                .text(dto.getText())
                .all_day(dto.isAll_day())
                .build();

        return entity;
    }

    public CalenderDTO entityToDto(Calender entity) {
        CalenderDTO dto = CalenderDTO.builder()
                .cal_no(entity.getCal_no())
                .user_no(entity.getUser_no())
                .title(entity.getTitle())
                .place(entity.getPlace())
                .start(entity.getStart())
                .end(entity.getEnd())
                .color(entity.getColor())
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

    public CalenderDTO DTOselect(long no) {
        Optional<Calender> item = calenderRepository.findcal(no);

        return item.isPresent() ? entityToDto(item.get()) : null;
    }
}
