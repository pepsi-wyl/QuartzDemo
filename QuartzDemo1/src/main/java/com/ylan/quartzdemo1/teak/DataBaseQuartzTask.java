package com.ylan.quartzdemo1.teak;

import com.ylan.quartzdemo1.teak.job.DataBaseJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author by pepsi-wyl
 * @date 2023-11-06 14:46
 * @Description DataBaseQuartzTask
 */

@Component
public class DataBaseQuartzTask implements ApplicationRunner {
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
                .storeDurably()                                      // 没有 Trigger 关联时任务是否被保留 < 创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留 >
                .build();

        // 3、构建Trigger实例
        // Trigger是Quartz的触发器，会去通知Scheduler何时去执行对应Job。
        Trigger printWordsTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dataBaseJobTrigger-1", "dataBaseJobTrigger-Group")
                .startNow()   // 立即生效
//                .startAt(new Date(date.getTime() + 5000))
//                .endAt(new Date(date.getTime() + 10001))
                .withSchedule(
                        CronScheduleBuilder
                                .cronSchedule("0/3 * * * * ?") // CronTrigger 基于日历的作业调度   corn表达式-每隔3s执行一次
                )
                .build();

        Set<Trigger> set = new HashSet<>();
        set.add(printWordsTrigger);

        /**
         * 配置 JDBC 持久化后需要配置启动时对数据库中的quartz的任务进行覆盖，不然项目启动不起来
         * boolean replace 表示启动时是否对数据库中的quartz的任务进行覆盖
         */
        //4、Scheduler绑定JobDetail和Trigger
        scheduler.scheduleJob(printWordsJobDetail, set,true);

//        log.info("--------DataBaseQuartzTask start ! ------------");
//        scheduler.start();
//        TimeUnit.MINUTES.sleep(10);
//        scheduler.shutdown();
//        log.info("--------DataBaseQuartzTask shutdown ! ------------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
    }
}