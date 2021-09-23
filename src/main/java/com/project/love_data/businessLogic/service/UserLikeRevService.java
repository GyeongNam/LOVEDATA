package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.UserLikeRev;
import com.project.love_data.repository.UserLikeRevRepository;
import com.project.love_data.repository.UserLikeRevRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserLikeRevService {
    @Autowired
    UserLikeRevRepository repository;

    public UserLikeRev register (Long revNo, Long userNo) {
        UserLikeRev UserLikeRev = insert(revNo, userNo);
        UserLikeRev entity = update(UserLikeRev);

        return entity;
    }

    public UserLikeRev update(UserLikeRev entity){
        repository.save(entity);
        Optional<UserLikeRev> item = repository.findByUuid(entity.getUuid());
        return item.orElse(null);
    }

    public UserLikeRev insert(Long revNo, Long userNo) {
        UserLikeRev entity = UserLikeRev.builder()
                .rev_no(revNo)
                .user_no(userNo)
                .build();

        return entity;
    }

    public boolean delete(Long revNo, Long userNo) {
        repository.deleteByCmt_noAndUser_no(revNo, userNo);

        if (repository.findByRev_noAndUser_no(revNo, userNo).isPresent()) {
            return  false;
        } else {
            return true;
        }
    }
    
    public UserLikeRev selectById (Long id) {
        Optional<UserLikeRev> item = repository.findById(id);
        
        return item.orElse(null);
    }
    
    public UserLikeRev selectByUUID (String uuid) {
        Optional<UserLikeRev> item = repository.findByUuid(uuid);

        return item.orElse(null);
    }
    
    public List<UserLikeRev> selectByAllRevNo (Long revNo) {
        Optional<List<UserLikeRev>> items = repository.findByUser_no(revNo);

        return items.orElse(null);
    }
    
    public List<UserLikeRev> selectByAllUserNo (Long userNo) {
        Optional<List<UserLikeRev>> items = repository.findByUser_no(userNo);
        
        return items.orElse(null);
    }

    public UserLikeRev selectByRevNoAndUserNo(Long revNo, Long userNo) {
        Optional<UserLikeRev> item = repository.findByRev_noAndUser_no(revNo, userNo);

        return item.orElse(null);
    }
}
