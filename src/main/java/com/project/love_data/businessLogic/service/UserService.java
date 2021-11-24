package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.UserDTO;
import com.project.love_data.model.service.Comment;
import com.project.love_data.model.service.Notice;
import com.project.love_data.model.service.UserSuspension;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.LocationRepository;
import com.project.love_data.repository.UserRepository;
import com.project.love_data.repository.UserSuspensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserLikeLocService likeLocService;
    @Autowired
    UserRecentLocService recentLocService;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    UserSuspensionRepository usRepository;

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
//                .likeLoc(dto.getLikeLoc())
//                .recentLoc(dto.getRecentLoc())
                .user_no(dto.getUser_no())
                .social_info(dto.getSocial_info())
                .social_id(dto.getSocial_id())
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
//                .likeLoc(entity.getLikeLoc())
//                .recentLoc(entity.getRecentLoc())
                .user_no(entity.getUser_no())
                .social_info(entity.getSocial_info())
                .social_id(entity.getSocial_id())
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

    public User selectLive(long id) {
        Optional<User> item = userRepository.selectLiveUser(id);

        return item.orElse(null);
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

    public void su_update(UserSuspension user){ usRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public boolean changeProfilePicToDefault(Long userNo) {
        if (userNo == null) {
            return false;
        }

        if (userNo < 0) {
            return false;
        }

        Optional<User> item = userRepository.findById(userNo);

        if (!item.isPresent()) {
            return false;
        }

        User userEntity = item.get();
        userEntity.setProfile_pic("/image/icon/user/user.png");
        update(userEntity);

        if (!userRepository.findById(userNo).get().getProfile_pic().equals("/image/icon/user/user.png")) {
            return false;
        }

        return true;
    }

    //CHOI
    public UserDTO DTOselect(String email) {
        Optional<User> item = userRepository.findUserByEmail(email);

        return item.isPresent() ? entityToDto(item.get()) : null;
//        return item.orElse(null);
    }
    public List<String> findUserId(String phonenumber) {
        Optional<List<String>> item = userRepository.findId(phonenumber);

        return item.orElse(new ArrayList<>());
    }
    public  User findPw(String pw) {
        User item = userRepository.findPw2(pw);

        return item;
    }
    public User savenewpw(String user_no){
        User item = userRepository.find_user_no(user_no);

        return item;
    }
    public String finduserNo(String email){
        String no = userRepository.finduserNo(email);
        return no;
    }

    public List<User> finduserAll(){
        List<User> item = userRepository.findAll();

        return item;
    }
    public List<User> search_user(String menu, String text){
        Optional<List<User>> item;
        if(menu.equals("1")){
            item = userRepository.name_search("%"+text+"%");
            return item.orElse(new ArrayList<>());
        }
        else{
            item = userRepository.nic_search("%"+text+"%");
            return item.orElse(new ArrayList<>());
        }
    }

    public List<UserSuspension> su_findAllByUser_no(Long user_no) {
        Optional<List<UserSuspension>> userSuspensions = usRepository.findAllByUser_no(user_no);
        return userSuspensions.orElse(new ArrayList<>());
    }
    public List<UserSuspension> findStopByUser_no (Long user_no, String progress){
    Optional<List<UserSuspension>> userSuspensions = usRepository.findStopByUser_no(user_no, progress);
        return userSuspensions.orElse(new ArrayList<>());
    }
    public List<UserSuspension> findAllByprogress (String progress){
        Optional<List<UserSuspension>> userSuspensions = usRepository.findAllByprogress(progress);
        return userSuspensions.orElse(new ArrayList<>());
    }

    public User findByProfilePic(String profilPic) {
        if (profilPic == null) {
            return null;
        }

        if (profilPic.equals("")) {
            return null;
        }

        Optional<List<User>> item = userRepository.selectUserByProfilePic(profilPic);

        if (!item.isPresent() || item.get().size() > 1) {
            return null;
        }

        User userEntity = item.get().get(0);

        return userEntity;
    }
}
