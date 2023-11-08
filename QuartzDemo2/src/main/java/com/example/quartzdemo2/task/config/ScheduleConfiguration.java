package com.example.quartzdemo2.task.config;

import com.example.quartzdemo2.task.job.FirstJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by pepsi-wyl
 * @date 2023-11-06 16:10
 * @Description Quartz配置类
 */

// 自动配置
@Configuration
public class ScheduleConfiguration {

    public static class SimpleJobConfiguration {

        // JobDetail
        @Bean
        public JobDetail firstJobDetail() {
            return JobBuilder.newJob(FirstJob.class)     // 绑定任务
                    .withDescription("First Job任务描述")
                    .withIdentity("firstJobDetail", "firstJobDetail-Group")  // JobDetail 的唯一标识是 JobKey ，使用 name + group 两个属性
                    .storeDurably()                      // 没有 Trigger 关联时任务是否被保留 < 创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留 >
                    .build();
        }

        // Trigger
        @Bean
        public Trigger firstJobTrigger() {
            return TriggerBuilder.newTrigger()
                    .forJob(firstJobDetail())            // 绑定任务
                    .withIdentity("firstJobTrigger", "firstJobTrigger-Group")
                    .withSchedule(                       // 使用CronScheduleBuilder
                            CronScheduleBuilder
                                    .cronSchedule("0/1 * * * * ?")  // CronTrigger 基于日历的作业调度   corn表达式-每隔1s执行一次
                    )
                    .startNow()                          // 立即生效
                    .build();
        }
    }
}