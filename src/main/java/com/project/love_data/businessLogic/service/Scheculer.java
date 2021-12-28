package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.Event;
import com.project.love_data.model.service.UserSuspension;
import com.project.love_data.model.user.User;
import com.project.love_data.repository.EventWinRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@Component
@EnableScheduling
public class Scheculer implements ApplicationRunner {

    @Autowired
    UserService userService;
    @Autowired
    ServiceCenterService serviceCenterService;
    @Autowired
    EventWinRepository eventWinRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        user_release();
    }

//  @Scheduled(fixedDelay = 5000) => 5초마다
//  초(0-59)   분(0-59)　　시간(0-23)　　일(1-31)　　월(1-12)　　요일(0-7)
    @Scheduled(cron = "* * 0 * * *", zone = "Asia/Seoul")
    public void user_release() throws ParseException {
        Date date = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//        log.info("스케줄러 작동 :"+ format1);
        List<UserSuspension> userSuspension = userService.findAllByprogress("1");
        for(int i = 0; i<userSuspension.size(); i++){
            Date endDay = format1.parse(userSuspension.get(i).getEnd_day());
            if(date.after(endDay)){
                // 시간이 지났을 때
                List<UserSuspension> end_userSuspension = userService.findStopByUser_no(userSuspension.get(i).getUser_no(),"1");
                List<UserSuspension> re_userSuspension = userService.findStopByUser_no(userSuspension.get(i).getUser_no(),"2");

                if(re_userSuspension.size() > 0){
                    end_userSuspension.get(0).setProgress("0");
                    userService.su_update(end_userSuspension.get(0));

                    java.util.Calendar cal_end = Calendar.getInstance();
                    cal_end.setTime(date);
                    cal_end.add(Calendar.DATE,Integer.parseInt(re_userSuspension.get(0).getStop_day()));

                    re_userSuspension.get(0).setProgress("1");
                    re_userSuspension.get(0).setStart_day(format1.format(date));
                    re_userSuspension.get(0).setEnd_day(format1.format(cal_end.getTime()));
                    userService.su_update(re_userSuspension.get(0));
                }else {
                    end_userSuspension.get(0).setProgress("0");
                    userService.su_update(end_userSuspension.get(0));

                    User user = userService.select(end_userSuspension.get(0).getUser_no());
                    user.setUser_Activation(true);
                    userService.update(user);
                }
            }
        }

        // 이벤트 관리
        List<Event> events = serviceCenterService.ev_select_all();

        for(int i = 0; i<events.size(); i++){
            Date stopDay = format1.parse(events.get(i).getEv_stop());
            if(date.after(stopDay)){
                // 이벤트 비활성화
                events.get(i).setEv_activation(false);
                serviceCenterService.ev_update(events.get(i));
            }
        }
    }
}
