package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.UserDislikeRev;
import com.project.love_data.repository.UserDislikeRevRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserDislikeRevService {
    @Autowired
    UserDislikeRevRepository repository;

    public UserDislikeRev register (Long revNo, Long userNo) {
        UserDislikeRev UserDislikeRev = insert(revNo, userNo);
        UserDislikeRev entity = update(UserDislikeRev);

        return entity;
    }

    public UserDislikeRev update(UserDislikeRev entity){
        repository.save(entity);
        Optional<UserDislikeRev> item = repository.findByUuid(entity.getUuid());
        return item.orElse(null);
    }

    public UserDislikeRev insert(Long revNo, Long userNo) {
        UserDislikeRev entity = UserDislikeRev.builder()
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
    
    public UserDislikeRev selectById (Long id) {
        Optional<UserDislikeRev> item = repository.findById(id);
        
        return item.orElse(null);
    }
    
    public UserDislikeRev selectByUUID (String uuid) {
        Optional<UserDislikeRev> item = repository.findByUuid(uuid);

        return item.orElse(null);
    }
    
    public List<UserDislikeRev> selectByAllRevNo (Long revNo) {
        Optional<List<UserDislikeRev>> items = repository.findByUser_no(revNo);

        return items.orElse(null);
    }
    
    public List<UserDislikeRev> selectByAllUserNo (Long userNo) {
        Optional<List<UserDislikeRev>> items = repository.findByUser_no(userNo);
        
        return items.orElse(null);
    }

    public UserDislikeRev selectByRevNoAndUserNo(Long revNo, Long userNo) {
        Optional<UserDislikeRev> item = repository.findByRev_noAndUser_no(revNo, userNo);

        return item.orElse(null);
    }
}
