package com.ylan.quartzdemo3.config;

import com.ylan.quartzdemo3.task.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 17:13
 */

@Configuration
public class QuartzConfig {

    @Autowired
    private QuartzJobFactory quartzJobFactory;

    /**
     * 设置Scheduler对象
     *
     * @return Scheduler
     * @throws IOException io
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        // 设置QuartzJobFactory
        scheduler.setJobFactory(quartzJobFactory);
        return scheduler;
    }

//    public static class SimpleJobConfiguration {
//
//        // JobDetail
//        @Bean
//        public JobDetail simpleJobDetail() {
//            return JobBuilder.newJob(SimpleJob.class)
//                    .withDescription("SimpleJob任务描述")
//                    .withIdentity("SimpleJobDetail", "SimpleJobDetail-Group")
//                    .storeDurably()
//                    .build();
//        }
//
//        // Trigger
//        @Bean
//        public Trigger firstJobTrigger() {
//            return TriggerBuilder.newTrigger()
//                    .forJob(simpleJobDetail())
//                    .withIdentity("SimpleJobTrigger", "SimpleJobTrigger-Group")
//                    .withSchedule(
//                            CronScheduleBuilder
//                                    .cronSchedule("0/1 * * * * ?")
//                    )
//                    .startNow()
//                    .build();
//        }
//    }

}