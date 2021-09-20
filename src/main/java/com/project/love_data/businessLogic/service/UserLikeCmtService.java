package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.UserLikeCmt;
import com.project.love_data.repository.UserLikeCmtRepository;
import com.project.love_data.repository.UserLikeCmtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserLikeCmtService {
    @Autowired
    UserLikeCmtRepository repository;

    public UserLikeCmt register (Long cmtNo, Long userNo) {
        UserLikeCmt UserLikeCmt = insert(cmtNo, userNo);
        UserLikeCmt entity = update(UserLikeCmt);

        return entity;
    }

    public UserLikeCmt update(UserLikeCmt entity){
        repository.save(entity);
        Optional<UserLikeCmt> item = repository.findByUuid(entity.getUuid());
        return item.orElse(null);
    }

    public UserLikeCmt insert(Long cmtNo, Long userNo) {
        UserLikeCmt entity = UserLikeCmt.builder()
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
    
    public UserLikeCmt selectById (Long id) {
        Optional<UserLikeCmt> item = repository.findById(id);
        
        return item.orElse(null);
    }
    
    public UserLikeCmt selectByUUID (String uuid) {
        Optional<UserLikeCmt> item = repository.findByUuid(uuid);

        return item.orElse(null);
    }
    
    public List<UserLikeCmt> selectByAllcmtNo (Long cmtNo) {
        Optional<List<UserLikeCmt>> items = repository.findByUser_no(cmtNo);

        return items.orElse(null);
    }
    
    public List<UserLikeCmt> selectByAllUserNo (Long userNo) {
        Optional<List<UserLikeCmt>> items = repository.findByUser_no(userNo);
        
        return items.orElse(null);
    }

    public UserLikeCmt selectBycmtNoAndUserNo(Long cmtNo, Long userNo) {
        Optional<UserLikeCmt> item = repository.findByCmt_noAndUser_no(cmtNo, userNo);

        return item.orElse(null);
    }
}
