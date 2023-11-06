package com.ylan.quartzdemo1.teak;

import com.ylan.quartzdemo1.teak.job.DataBaseJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author by pepsi-wyl
 * @date 2023-11-06 14:46
 * @Description DataBaseQuartzTask
 */

@Component
public class DataBaseQuartzTask {
    private static final Date date = new Date();
    private static final Logger log = LoggerFactory.getLogger(DataBaseQuartzTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void start() throws SchedulerException, InterruptedException {
        // 1、构建Scheduler调度器实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // 2、构建JobDetail实例
        JobDetail printWordsJobDetail = JobBuilder
                .newJob(DataBaseJob.class)
                .withIdentity("dataBaseJobDetail-1", "dataBaseJobDetail-Group")
                .build();

        // 3、构建Trigger实例
        // Trigger是Quartz的触发器，会去通知Scheduler何时去执行对应Job。
        Trigger printWordsTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dataBaseJobTrigger-1", "dataBaseJobTrigger-Group")
                .startNow()   // 立即生效
                .startAt(new Date(date.getTime() + 5000))
                .endAt(new Date(date.getTime() + 10001))
                .withSchedule(
                        CronScheduleBuilder
                                .cronSchedule("0/1 * * * * ?") // CronTrigger 基于日历的作业调度   corn表达式-每隔1s执行一次
                )
                .build();

        //4、Scheduler绑定JobDetail和Trigger
        scheduler.scheduleJob(printWordsJobDetail, printWordsTrigger);

        log.info("--------DataBaseQuartzTask start ! ------------");
        scheduler.start();
        TimeUnit.MINUTES.sleep(10);
        scheduler.shutdown();
        log.info("--------DataBaseQuartzTask shutdown ! ------------");
    }
}