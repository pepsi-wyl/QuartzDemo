package com.ylan.quartzdemo1.teak;

import com.ylan.quartzdemo1.teak.job.PrintWordsJob;
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
 * @date 2023-11-06 11:10
 */

@Component
public class MultiQuartzTask implements ApplicationRunner {
    private static final Date date = new Date();
    private static final Logger log = LoggerFactory.getLogger(MultiQuartzTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static void start() throws SchedulerException, InterruptedException {
        // 1、构建Scheduler调度器实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // 2、创建JobDetail实例，并与PrintWordsJob类绑定      注意要设置storeDurably()否则报错
        JobDetail jobDetail = JobBuilder
                .newJob(PrintWordsJob.class)
                .withIdentity("printWordsJobDetail-1", "printWordsJobDetail-Group")
                .usingJobData("jobDetailName", "printWordsJobDetail-1")
                .usingJobData("printWordsJobDetailKey","printWordsJobDetail-测试value")
                .storeDurably()   // 没有 Trigger 关联时任务是否被保留 < 创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留 >
                .build();

        // 3、分别构建Trigger实例
        Trigger trigger1 = TriggerBuilder
                .newTrigger()
                .withIdentity("printWordsTrigger-1", "printWordsTrigger-Group")
                .usingJobData("triggerName", "printWordsTrigger-1")
                .usingJobData("printWordsTriggerKey","printWordsTriggerKey-测试value")
                .startNow()                // 立即生效
                .forJob(jobDetail)
                .withSchedule(
                        SimpleScheduleBuilder
                            .simpleSchedule()
                            .withIntervalInSeconds(2) // 每隔2s执行一次
                            .repeatForever())         // 永久循环
                .build();
        Trigger trigger2 = TriggerBuilder
                .newTrigger()
                .withIdentity("printWordsTrigger-2", "printWordsTrigger-Group")
                .usingJobData("triggerName", "printWordsTrigger-2")
                .usingJobData("printWordsTriggerKey","printWordsTriggerKey-测试value")
                .startNow()                 // 立即生效
                .forJob(jobDetail)
                .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(3) // 每隔3s执行一次
                            .repeatForever()          // 永久循环
                ).build();

        //4、调度器中添加job
//        scheduler.addJob(jobDetail, false);
//        scheduler.scheduleJob(trigger1);
//        scheduler.scheduleJob(trigger2);

        Set<Trigger> set = new HashSet<>();
        set.add(trigger1);
        set.add(trigger2);
        /**
         * 配置 JDBC 持久化后需要配置启动时对数据库中的quartz的任务进行覆盖，不然项目启动不起来
         * boolean replace 表示启动时是否对数据库中的quartz的任务进行覆盖
         */
        scheduler.scheduleJob(jobDetail, set,true);

//        log.info("--------MultiQuartzTask start ! ------------");
//        scheduler.start();                    // 开始任务
//        TimeUnit.MINUTES.sleep(1);     // 睡眠1分钟，决定调度器运行时间 由于定时任务是交由线程池异步执行的，而主线程运行随之结束就导致定时任务也不再执行了，所以需要设置休眠hold住主线程。在真实项目中，项目的进程是一直存活的，因此不需要设置休眠时间。
//        scheduler.shutdown();                 // 结束任务
//        log.info("--------MultiQuartzTask shutdown ! ------------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
    }
}