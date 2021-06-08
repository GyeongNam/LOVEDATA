package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.UserDTO;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User dtoToEntity(UserDTO dto){
        User entity = User.builder()
                .user_email(dto.getUser_email())
                .user_pw(dto.getUser_pw())
                .user_nic(dto.getUser_nic())
                .user_name(dto.getUser_name())
                .user_phone(dto.getUser_phone())
                .user_birth(dto.getUser_birth())
                .user_sex(dto.isUser_sex())
                .user_social(dto.isUser_social())
                .user_email_re(dto.isUser_email_re())
                .user_Activation(dto.isUser_Activation())
                .profile_pic(dto.getProfile_pic())
                .cmtSet(new HashSet<>(dto.getCmtList()))
                .roleSet(dto.getRoleSet())
                .likeLoc(dto.getLikeLoc())
                .recentLoc(dto.getRecentLoc())
                .user_no(dto.getUser_no())
                .social_info(dto.getSocial_info())
                .build();

        return entity;
    }

    public UserDTO entityToDto(User entity){
        UserDTO dto = UserDTO.builder()
                .user_email(entity.getUser_email())
                .user_pw(entity.getUser_pw())
                .user_nic(entity.getUser_nic())
                .user_name(entity.getUser_name())
                .user_phone(entity.getUser_phone())
                .user_birth(entity.getUser_birth())
                .user_sex(entity.isUser_sex())
                .user_social(entity.isUser_social())
                .user_email_re(entity.isUser_email_re())
                .user_Activation(entity.isUser_Activation())
                .profile_pic(entity.getProfile_pic())
                .roleSet(entity.getRoleSet())
                .likeLoc(entity.getLikeLoc())
                .recentLoc(entity.getRecentLoc())
                .user_no(entity.getUser_no())
                .social_info(entity.getSocial_info())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        List<Comment> tempCmtList = new ArrayList<>(entity.getCmtSet());
        List<Comment> cmtList = new ArrayList<>();

        for (int i = 0; i < tempCmtList.size(); i++) {
            for (int j = 0; j < tempCmtList.size(); j++) {
                cmtList.add(tempCmtList.get(j));
                break;
            }
        }

        dto.setCmtList(cmtList);

        return dto;
    }

    public User select(long id) {
        Optional<User> item = userRepository.findById(id);

        return item.orElse(null);
    }

    public User select(String email){
        Optional<User> item = userRepository.findUserByEmail(email);

        return item.orElse(null);
    }

    public void update(User user){
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void addLikeLocation(User user, Location location) {
        user.addLikeLocation(location);

        update(user);
    }

    public void removeLikeLocation(User user, Location location) {
        user.removeLikeLocation(location);

        update(user);
    }

    public void removeLikeLocation(User user, int index) {
        user.removeLikeLocation(index);

        update(user);
    }

    public void addRecentLocation(User user, Location location){
        user.addRecentLocation(location);

        update(user);
    }

    public void removeRecentLocation(User user, Location location){
        user.removeRecentLocation(location);

        update(user);
    }

    public void removeRecentLocation(User user, int index){
        user.removeRecentLocation(index);

        update(user);
    }
}