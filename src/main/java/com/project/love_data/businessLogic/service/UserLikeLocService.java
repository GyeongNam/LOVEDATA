package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.UserLikeLoc;
import com.project.love_data.repository.UserLikeLocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserLikeLocService {
    @Autowired
    UserLikeLocRepository repository;

    public UserLikeLoc register (Long locNo, Long userNo) {
        UserLikeLoc userLikeLoc = insert(locNo, userNo);
        UserLikeLoc entity = update(userLikeLoc);

        return entity;
    }

    public UserLikeLoc update(UserLikeLoc entity){
        repository.save(entity);
        Optional<UserLikeLoc> item = repository.findByUuid(entity.getUuid());
        return item.orElse(null);
    }

    public UserLikeLoc insert(Long locNo, Long userNo) {
        UserLikeLoc entity = UserLikeLoc.builder()
                .loc_no(locNo)
                .user_no(userNo)
                .build();

        return entity;
    }

    public boolean delete(Long locNo, Long userNo) {
        repository.deleteByLoc_noAndUser_no(locNo, userNo);

        if (repository.findByLoc_noAndUser_no(locNo, userNo).isPresent()) {
            return  false;
        } else {
            return true;
        }
    }
    
    public UserLikeLoc selectById (Long id) {
        Optional<UserLikeLoc> item = repository.findById(id);
        
        return item.orElse(null);
    }
    
    public UserLikeLoc selectByUUID (String uuid) {
        Optional<UserLikeLoc> item = repository.findByUuid(uuid);

        return item.orElse(null);
    }
    
    public List<UserLikeLoc> selectByAllLocNo (Long LocNo) {
        Optional<List<UserLikeLoc>> items = repository.findByUser_no(LocNo);

        return items.orElse(null);
    }
    
    public List<UserLikeLoc> selectByAllUserNo (Long userNo) {
        Optional<List<UserLikeLoc>> items = repository.findByUser_no(userNo);
        
        return items.orElse(null);
    }

    public UserLikeLoc selectByLocNoAndUserNo(Long locNo, Long userNo) {
        Optional<UserLikeLoc> item = repository.findByLoc_noAndUser_no(locNo, userNo);

        return item.orElse(null);
    }
}
