package com.fiveis.andcrowd.config;

import com.fiveis.andcrowd.service.and.AndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class AndEndDateStatusConfig {

    @Autowired
    private AndService andService;

    @Scheduled(cron = "1 0 0 * * ?")
    public void updateStatusForExpiredItems() {
        System.out.println("Scheduled task is executed at " + new Date());
        andService.updateStatusForExpiredItems();
    }
}
