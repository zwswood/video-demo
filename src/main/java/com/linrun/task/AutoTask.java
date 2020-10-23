package com.linrun.task;

import lombok.extern.log4j.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class AutoTask {
   /* @Scheduled(cron="0/10 * * * * ?")
    public void excuteTask(){
        log.info("自动任务执行=====================");
    }*/
}
