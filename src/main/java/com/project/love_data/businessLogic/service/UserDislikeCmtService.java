package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.UserDislikeCmt;
import com.project.love_data.repository.UserDislikeCmtRepository;
import com.project.love_data.repository.UserDislikeCmtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserDislikeCmtService {
    @Autowired
    UserDislikeCmtRepository repository;

    public UserDislikeCmt register (Long cmtNo, Long userNo) {
        UserDislikeCmt UserDislikeCmt = insert(cmtNo, userNo);
        UserDislikeCmt entity = update(UserDislikeCmt);

        return entity;
    }

    public UserDislikeCmt update(UserDislikeCmt entity){
        repository.save(entity);
        Optional<UserDislikeCmt> item = repository.findByUuid(entity.getUuid());
        return item.orElse(null);
    }

    public UserDislikeCmt insert(Long cmtNo, Long userNo) {
        UserDislikeCmt entity = UserDislikeCmt.builder()
                .cmt_no(cmtNo)
                .user_no(userNo)
                .build();

        return entity;
    }

    public boolean delete(Long cmtNo, Long userNo) {
        repository.deleteByCmt_noAndUser_no(cmtNo, userNo);

        if (repository.findByCmt_noAndUser_no(cmtNo, userNo).isPresent()) {
            return  false;
        } else {
            return true;
        }
    }
    
    public UserDislikeCmt selectById (Long id) {
        Optional<UserDislikeCmt> item = repository.findById(id);
        
        return item.orElse(null);
    }
    
    public UserDislikeCmt selectByUUID (String uuid) {
        Optional<UserDislikeCmt> item = repository.findByUuid(uuid);

        return item.orElse(null);
    }
    
    public List<UserDislikeCmt> selectByAllcmtNo (Long cmtNo) {
        Optional<List<UserDislikeCmt>> items = repository.findByUser_no(cmtNo);

        return items.orElse(null);
    }
    
    public List<UserDislikeCmt> selectByAllUserNo (Long userNo) {
        Optional<List<UserDislikeCmt>> items = repository.findByUser_no(userNo);
        
        return items.orElse(null);
    }

    public UserDislikeCmt selectBycmtNoAndUserNo(Long cmtNo, Long userNo) {
        Optional<UserDislikeCmt> item = repository.findByCmt_noAndUser_no(cmtNo, userNo);

        return item.orElse(null);
    }
}
