package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.BizRegDTO;
import com.project.love_data.model.service.BizReg;
import com.project.love_data.model.service.Point;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.BizRegRepository;
import com.project.love_data.repository.PointRepository;
import com.project.love_data.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
public class BizRegService {
    @Autowired
    BizRegRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PointRepository pointRepository;

    public BizReg dtoToEntity(BizRegDTO dto){
        BizReg entity = BizReg.builder()
                .brNo(dto.getBrNo())
                .userNo(dto.getUserNo())
                .uuid(dto.getUuid())
                .certified(dto.getCertified())
                .deleted(dto.getDeleted())
                .bizName(dto.getBizName())
                .bizCeoName(dto.getBizCeoName())
                .bizCall(dto.getBizCall())
                .build();

        return entity;
    }

    public BizRegDTO entityToDto(BizReg br) {
        BizRegDTO dto = BizRegDTO.builder()
                .brNo(br.getBrNo())
                .userNo(br.getUserNo())
                .uuid(br.getUuid())
                .url(br.getUrl())
                .certified(br.getCertified())
                .regDate(br.getRegDate())
                .modDate(br.getModDate())
                .deleted(br.getDeleted())
                .bizName(br.getBizName())
                .bizCeoName(br.getBizCeoName())
                .bizCall(br.getBizCall())
                .build();

        return dto;
    }

    public BizReg select(Long brNo) {
        if (brNo == null) {
            return null;
        }

        if (brNo < 0) {
            return null;
        }

        Optional<BizReg> item = repository.findById(brNo);

        return item.orElse(null);
    }

    public BizReg select(String uuid) {
        if (uuid == null) {
            return null;
        }

        if (uuid.equals("")) {
            return null;
        }

        Optional<BizReg> item = repository.findBizRegByUuid(uuid);

        return item.orElse(null);
    }

    public BizReg findByUserNo(Long userNo) {
        if (userNo == null) {
            return null;
        }

        if (userNo < 0) {
            return null;
        }

        Optional<List<BizReg>> item = repository.findAllByUserNo(userNo);

        if (item.isPresent()) {
            if (item.get().size() != 0) {
                return item.get().get(0);
            }
        }

        return null;
    }

    public BizReg findByUserNoAndDeleted(Long userNo, Boolean deleted) {
        if (userNo == null || deleted == null) {
            return null;
        }

        if (userNo < 0) {
            return null;
        }

        Optional<List<BizReg>> item = repository.findAllByUserNoAndDeleted(userNo, deleted);

        if (item.isPresent()) {
            if (item.get().size() != 0) {
                return item.get().get(0);
            }
        }

        return null;
    }

    public void save(BizReg entity) {
        repository.save(entity);
    }

    public BizReg registration(Map<String, String> reqParam) {
        Optional<User> userItem = userRepository.findById(Long.valueOf(reqParam.get("userNo")));
        BizReg bizRegEntity = findByUserNoAndDeleted(userItem.get().getUser_no(), false);

        if (!userItem.isPresent()) {
            return null;
        }

        if (bizRegEntity != null) {
            bizRegEntity.setUserNo(Long.valueOf(reqParam.get("userNo")));
            bizRegEntity.setBizCall(reqParam.get("bizCall"));
            bizRegEntity.setBizCeoName(reqParam.get("bizCeoName"));
            bizRegEntity.setBizName(reqParam.get("bizName"));

            if (reqParam.containsKey("uuid")) {
                bizRegEntity.setUuid(reqParam.get("uuid"));
            }

            if (reqParam.containsKey("url")) {
                bizRegEntity.setUrl(reqParam.get("url"));
            }

            save(bizRegEntity);

            return select(reqParam.get("uuid"));
        }

        BizReg entity = BizReg.builder()
                .userNo(Long.valueOf(reqParam.get("userNo")))
                .bizCall(reqParam.get("bizCall"))
                .bizCeoName(reqParam.get("bizCeoName"))
                .bizName(reqParam.get("bizName"))
                .build();

        if (reqParam.containsKey("uuid")) {
            entity.setUuid(reqParam.get("uuid"));
        }

        if (reqParam.containsKey("url")) {
            entity.setUrl(reqParam.get("url"));
        }

        save(entity);

        // BIZ 권한 추가
//        User userEntity = userItem.get();
//
//        if (!userEntity.getRoleSet().contains(UserRole.BIZ)) {
//            userEntity.addUserRole(UserRole.BIZ);
//            userRepository.save(userEntity);
//        }

        return select(reqParam.get("uuid"));
    }

    public boolean delete(Long brNo) {
        if (brNo == null) {
            return false;
        }

        if (brNo < 0) {
            return false;
        }

        BizReg entity = select(brNo);

        if (entity == null) {
            return false;
        }

        entity.setDeleted(true);
        save(entity);

        entity = select(brNo);

        if (entity.getDeleted()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(String uuid) {
        if (uuid == null) {
            return false;
        }

        if (uuid.equals("")) {
            return false;
        }

        BizReg entity = select(uuid);

        if (entity == null) {
            return false;
        }

        entity.setDeleted(true);
        save(entity);

        entity = select(uuid);

        if (entity.getDeleted()) {
            return true;
        } else {
            return false;
        }
    }

    public void permadelete(Long brNo) {
        if (brNo == null) {
            return;
        }

        if (brNo < 0) {
            return;
        }

        repository.deleteByBrNo(brNo);
    }

    public void permadelete(String uuid) {
        if (uuid == null) {
            return;
        }

        if (uuid.equals("")) {
            return;
        }

        repository.deleteByUuid(uuid);
    }

    public List<BizReg> findAllByUserAll(){
        Optional<List<BizReg>> item = repository.findAllByUserAll();
        return item.orElse(new ArrayList<>());
    }

    public List<BizReg> findAllLiveByCertifiedTrue(){
        Optional<List<BizReg>> item = repository.findAllLiveByCertified(true);
        return item.orElse(new ArrayList<>());
    }

    public Long findplupoint(String user_no){
        Long item = pointRepository.findplupoint(user_no);
        return item;
    }
    public Long findmapoint(String user_no){
        Long item = pointRepository.findmapoint(user_no);
        return item;
    }

    public List<Point> points_find_user_no(String user_no){
        Optional<List<Point>> item = pointRepository.points_find_user_no(user_no);
        return item.orElse(new ArrayList<>());
    }
}
