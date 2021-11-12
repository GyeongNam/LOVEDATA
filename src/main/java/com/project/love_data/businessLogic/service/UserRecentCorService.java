package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.UserRecentCor;
import com.project.love_data.model.service.UserRecentLoc;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.UserRecentCorRepository;
import com.project.love_data.util.RecentLoc_Delete_Condition;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.project.love_data.util.ConstantValues.MAX_LOCATION_HISTORY_COUNT;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserRecentCorService {
    @Autowired
    UserRecentCorRepository repository;
    @Autowired
    UserService userService;

    public UserRecentCor register(Long corNo, Long userNo) {
        if (!isDuplicated(corNo, userNo) && keepMaxHistoryCount(userNo)) {
            UserRecentCor UserRecentCor = insert(corNo, userNo);
            UserRecentCor entity = update(UserRecentCor);

            return entity;
        }

        return null;
    }

    public UserRecentCor update(UserRecentCor entity) {
        repository.save(entity);
        Optional<UserRecentCor> item = repository.findByUuid(entity.getUuid());
        return item.orElse(null);
    }

    public boolean keepMaxHistoryCount(Long userNo) {
        List<UserRecentCor> lists = selectByAllUserNo(userNo);
        User user = userService.select(userNo);

        if (user == null) {
            log.warn("Can't find result match with " + userNo + "(userNo)");
            log.warn("Please Check userNo is correct");
            return false;
        }

        if (lists.size() >= MAX_LOCATION_HISTORY_COUNT) {
            UserRecentCor del = null;
            del = lists.get(0);
            for (UserRecentCor item : lists) {
                if (del.getUserRecentCor_no() > item.getUserRecentCor_no()) {
                    del = item;
                }
            }

            repository.delete(del);

            UserRecentCor temp = selectByUUID(del.getUuid());

            if (temp != null) {
                log.warn("Error occurred during deleting UserRecentCor");
                log.warn("Please Check");
                return false;
            }
        }
        return true;
    }

    public boolean isDuplicated(Long corNo, Long userNo){
        UserRecentCor item = selectByCorNoAndUserNo(corNo, userNo);

        if (item == null) {
            return false;
        } else {
//            log.info("Duplicated Found!");
            delete(corNo, userNo);

            item = UserRecentCor.builder()
                    .cor_no(corNo)
                    .user_no(userNo)
                    .build();

            repository.save(item);
            return true;
        }
    }

    public UserRecentCor insert(Long corNo, Long userNo) {
        UserRecentCor entity = UserRecentCor.builder()
                .cor_no(corNo)
                .user_no(userNo)
                .build();

        return entity;
    }

    public RecentLoc_Delete_Condition remove(Long corNo, Long userNo) {
        if (isDeleted(corNo, userNo)) {
            return RecentLoc_Delete_Condition.ALREADY_DELETED;
        }

       if (delete(corNo, userNo)){
           return RecentLoc_Delete_Condition.SUCCESSFUL;
       } else {
           return RecentLoc_Delete_Condition.FAILED;
       }
    }

    public boolean isDeleted(Long corNo, Long userNo) {
        UserRecentCor item = selectByCorNoAndUserNo(corNo, userNo);

        if (item == null ){
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(Long corNo, Long userNo) {
        repository.deleteByCor_noAndUser_no(corNo, userNo);

        if (repository.findByCor_noAndUser_no(corNo, userNo).isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public UserRecentCor selectById(Long id) {
        Optional<UserRecentCor> item = repository.findById(id);

        return item.orElse(null);
    }

    public UserRecentCor selectByUUID(String uuid) {
        Optional<UserRecentCor> item = repository.findByUuid(uuid);

        return item.orElse(null);
    }

    public List<UserRecentCor> selectByAllCorNo(Long corNo) {
        Optional<List<UserRecentCor>> items = repository.findByUser_no(corNo);

        return items.orElse(null);
    }

    public List<UserRecentCor> selectByAllUserNo(Long userNo) {
        Optional<List<UserRecentCor>> items = repository.findByUser_no(userNo);

        return items.orElse(null);
    }

    public UserRecentCor selectByCorNoAndUserNo(Long corNo, Long userNo) {
        Optional<UserRecentCor> item = repository.findByCor_noAndUser_no(corNo, userNo);

        return item.orElse(null);
    }
    public List<UserRecentCor> selectByUserNo(Long userNo) {
        Optional<List<UserRecentCor>> items = repository.findUser_no(userNo);

        return items.orElse(null);
    }
}
