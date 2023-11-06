package com.ylan.quartzdemo1;

import com.ylan.quartzdemo1.entity.User;
import com.ylan.quartzdemo1.mapper.UserMapper;
import com.ylan.quartzdemo1.teak.DataBaseQuartzTask;
import com.ylan.quartzdemo1.teak.MultiQuartzTask;
import com.ylan.quartzdemo1.teak.SimpleQuartzTask;
import com.ylan.quartzdemo1.teak.job.DataBaseJob;
import com.ylan.quartzdemo1.utils.SpringContextJobUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class QuartzDemo1Application {
    private static final Logger log = LoggerFactory.getLogger(QuartzDemo1Application.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        SpringApplication.run(QuartzDemo1Application.class, args);
        log.info("Current Thread : {}, The time is now {}", Thread.currentThread().getName(), dateFormat.format(new Date()));
//        SimpleQuartzTask.start();
//        MultiQuartzTask.start();
//        DataBaseQuartzTask.start();
    }


}