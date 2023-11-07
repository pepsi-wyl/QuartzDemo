package com.ylan.quartzdemo1.teak;

import com.ylan.quartzdemo1.teak.job.PrintWordsJob;
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
 * @date 2023-11-06 11:13
 * @Description: SimpleQuartzTask
 */

@Component
public class SimpleQuartzTask {
    private static final Date date = new Date();
    private static final Logger log = LoggerFactory.getLogger(SimpleQuartzTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public  static void start() throws SchedulerException, InterruptedException{
        // 1、构建Scheduler调度器实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // 2、构建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        // JobDetail用来绑定Job，为Job实例提供许多属性：name group jobClass jobDataMap。
        JobDetail printWordsJobDetail = JobBuilder
                .newJob(PrintWordsJob.class)
                .withIdentity("printWordsJobDetail", "printWordsJobDetail-Group")
                .usingJobData("jobDetailName", "printWordsJobDetail")
                .usingJobData("printWordsJobDetailKey","printWordsJobDetail-测试value")
                .build();

        // 3、构建Trigger触发器实例
        // Trigger是Quartz的触发器，会去通知Scheduler何时去执行对应Job。
        Trigger printWordsTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity("printWordsTrigger", "printWordsTrigger-Group")
                .usingJobData("triggerName", "printWordsTrigger")
                .usingJobData("printWordsTriggerKey","printWordsTriggerKey-测试value")
                .startNow()   // 立即生效
                .startAt(new Date(date.getTime() + 5000))
                .endAt(new Date(date.getTime() + 10001))

//                .withSchedule(
//                        SimpleScheduleBuilder
//                                .simpleSchedule()         // SimpleTrigger 基于精准指定间隔，可以实现在一个指定时间段内执行一次作业任务或一个时间段内多次执行作业任务。
//                                .withIntervalInSeconds(1) // 间隔1s
//                                .repeatForever()          // 循环一直执行
//                )

                .withSchedule(
                        CronScheduleBuilder
                                .cronSchedule("0/1 * * * * ?") // CronTrigger 基于日历的作业调度   corn表达式-每隔2s执行一次
                )

                .build();

        //4、Scheduler绑定JobDetail和Trigger
        scheduler.scheduleJob(printWordsJobDetail, printWordsTrigger);

        log.info("--------SimpleQuartzTask start ! ------------");
        scheduler.start();                    // 开始任务
        TimeUnit.MINUTES.sleep(1);     // 睡眠1分钟，决定调度器运行时间 由于定时任务是交由线程池异步执行的，而主线程运行随之结束就导致定时任务也不再执行了，所以需要设置休眠hold住主线程。在真实项目中，项目的进程是一直存活的，因此不需要设置休眠时间。
        scheduler.shutdown();                 // 结束任务
        log.info("--------SimpleQuartzTask shutdown ! ------------");
    }
}