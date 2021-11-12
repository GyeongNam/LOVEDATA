package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.UserRecentLoc;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.UserRecentLocRepository;
import com.project.love_data.util.RecentLoc_Delete_Condition;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.project.love_data.util.ConstantValues.MAX_LOCATION_HISTORY_COUNT;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserRecentLocService {
    @Autowired
    UserRecentLocRepository repository;
    @Autowired
    UserService userService;

    public UserRecentLoc register(Long locNo, Long userNo) {
        if (!isDuplicated(locNo, userNo) && keepMaxHistoryCount(userNo)) {
            UserRecentLoc UserRecentLoc = insert(locNo, userNo);
            UserRecentLoc entity = update(UserRecentLoc);

            return entity;
        }

        return null;
    }

    public UserRecentLoc update(UserRecentLoc entity) {
        repository.save(entity);
        Optional<UserRecentLoc> item = repository.findByUuid(entity.getUuid());
        return item.orElse(null);
    }

    public boolean keepMaxHistoryCount(Long userNo) {
        List<UserRecentLoc> lists = selectByAllUserNo(userNo);
        User user = userService.select(userNo);

        if (user == null) {
            log.warn("Can't find result match with " + userNo + "(userNo)");
            log.warn("Please Check userNo is correct");
            return false;
        }

        if (lists.size() >= MAX_LOCATION_HISTORY_COUNT) {
            UserRecentLoc del = null;
            del = lists.get(0);
            for (UserRecentLoc item : lists) {
                if (del.getUserRecentLoc_no() > item.getUserRecentLoc_no()) {
                    del = item;
                }
            }

            repository.delete(del);

            UserRecentLoc temp = selectByUUID(del.getUuid());

            if (temp != null) {
                log.warn("Error occurred during deleting UserRecentLoc");
                log.warn("Please Check");
                return false;
            }
        }
        return true;
    }

    public boolean isDuplicated(Long locNo, Long userNo){
        UserRecentLoc item = selectByLocNoAndUserNo(locNo, userNo);

        if (item == null) {
            return false;
        } else {
//            log.info("Duplicated Found!");
            delete(locNo, userNo);

            item = UserRecentLoc.builder()
                    .loc_no(locNo)
                    .user_no(userNo)
                    .build();

            repository.save(item);
            return true;
        }
    }

    public UserRecentLoc insert(Long locNo, Long userNo) {
        UserRecentLoc entity = UserRecentLoc.builder()
                .loc_no(locNo)
                .user_no(userNo)
                .build();

        return entity;
    }

    public RecentLoc_Delete_Condition remove(Long locNo, Long userNo) {
        if (isDeleted(locNo, userNo)) {
            return RecentLoc_Delete_Condition.ALREADY_DELETED;
        }

       if (delete(locNo, userNo)){
           return RecentLoc_Delete_Condition.SUCCESSFUL;
       } else {
           return RecentLoc_Delete_Condition.FAILED;
       }
    }

    public boolean isDeleted(Long locNo, Long userNo) {
        UserRecentLoc item = selectByLocNoAndUserNo(locNo, userNo);

        if (item == null ){
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(Long locNo, Long userNo) {
        repository.deleteByLoc_noAndUser_no(locNo, userNo);

        if (repository.findByLoc_noAndUser_no(locNo, userNo).isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public UserRecentLoc selectById(Long id) {
        Optional<UserRecentLoc> item = repository.findById(id);

        return item.orElse(null);
    }

    public UserRecentLoc selectByUUID(String uuid) {
        Optional<UserRecentLoc> item = repository.findByUuid(uuid);

        return item.orElse(null);
    }

    public List<UserRecentLoc> selectByAllLocNo(Long LocNo) {
        Optional<List<UserRecentLoc>> items = repository.findByUser_no(LocNo);

        return items.orElse(null);
    }

    public List<UserRecentLoc> selectByAllUserNo(Long userNo) {
        Optional<List<UserRecentLoc>> items = repository.findByUser_no(userNo);

        return items.orElse(null);
    }

    public UserRecentLoc selectByLocNoAndUserNo(Long locNo, Long userNo) {
        Optional<UserRecentLoc> item = repository.findByLoc_noAndUser_no(locNo, userNo);

        return item.orElse(null);
    }

    public List<UserRecentLoc> selectByUserNo(Long userNo) {
        Optional<List<UserRecentLoc>> items = repository.findUser_no(userNo);

        return items.orElse(null);
    }
}
