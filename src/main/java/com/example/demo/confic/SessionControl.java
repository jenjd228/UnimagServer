package com.example.demo.confic;

import com.example.demo.web.service.AuthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SessionControl {

    private final Logger logger = Logger.getLogger(SessionControl.class);

    @Autowired
    private AuthService authService;

    @Scheduled(fixedRate = 18000000) //3600000 - 1 день
    public void scheduleFixedRateTask() {
        logger.info("Session remove");
        authService.removeAllMembers();
    }

}
