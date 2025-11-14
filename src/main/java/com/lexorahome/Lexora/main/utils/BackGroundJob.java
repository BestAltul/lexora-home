package com.lexorahome.Lexora.main.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BackGroundJob {

    @Scheduled(cron="0 0 0 * * *")
    public void doComparison(){

    }
}
