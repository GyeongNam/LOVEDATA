package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.UserLikeCor;
import com.project.love_data.repository.UserLikeCorRepository;
import com.project.love_data.repository.UserLikeCorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserLikeCorService {
    @Autowired
    UserLikeCorRepository repository;

    public UserLikeCor register (Long corNo, Long userNo) {
        UserLikeCor UserLikeCor = insert(corNo, userNo);
        UserLikeCor entity = update(UserLikeCor);

        return entity;
    }

    public UserLikeCor update(UserLikeCor entity){
        repository.save(entity);
        Optional<UserLikeCor> item = repository.findByUuid(entity.getUuid());
        return item.orElse(null);
    }

    public UserLikeCor insert(Long corNo, Long userNo) {
        UserLikeCor entity = UserLikeCor.builder()
                .cor_no(corNo)
                .user_no(userNo)
                .build();

        return entity;
    }

    public boolean delete(Long corNo, Long userNo) {
        repository.deleteByCor_noAndUser_no(corNo, userNo);

        if (repository.findByCor_noAndUser_no(corNo, userNo).isPresent()) {
            return  false;
        } else {
            return true;
        }
    }
    
    public UserLikeCor selectById (Long id) {
        Optional<UserLikeCor> item = repository.findById(id);
        
        return item.orElse(null);
    }
    
    public UserLikeCor selectByUUID (String uuid) {
        Optional<UserLikeCor> item = repository.findByUuid(uuid);

        return item.orElse(null);
    }
    
    public List<UserLikeCor> selectByAllCorNo (Long corNo) {
        Optional<List<UserLikeCor>> items = repository.findByUser_no(corNo);

        return items.orElse(null);
    }
    
    public List<UserLikeCor> selectByAllUserNo (Long userNo) {
        Optional<List<UserLikeCor>> items = repository.findByUser_no(userNo);
        
        return items.orElse(null);
    }

    public UserLikeCor selectByCorNoAndUserNo(Long corNo, Long userNo) {
        Optional<UserLikeCor> item = repository.findByCor_noAndUser_no(corNo, userNo);

        return item.orElse(null);
    }
}
