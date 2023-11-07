package com.example.quartzdemo2.task.config;

import com.example.quartzdemo2.task.job.SecondJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author by pepsi-wyl
 * @date 2023-11-06 17:28
 * @Description Quartz初始化
 */

// 手动配置
@Component
public class ScheduleInit implements ApplicationRunner {

    // 注入 Scheduler
    private final Scheduler scheduler;

    public ScheduleInit(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    // SpringBoot-Run 方法
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(SecondJob.class)     // 绑定任务
                .withIdentity("secondJobDetail", "secondJobDetail-Group") // JobDetail 的唯一标识是 JobKey ，使用 name + group 两个属性
                .storeDurably()                                      // 没有 Trigger 关联时任务是否被保留 < 创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留 >
                .build();
        // 创建Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)                                  // 绑定jobDetail任务
                .withIdentity("secondJobTrigger", "secondJobTrigger-Group")
                .withSchedule(                                      // 使用CronScheduleBuilder
                        CronScheduleBuilder
                                .cronSchedule("0/2 * * * * ?")   // CronTrigger 基于日历的作业调度   corn表达式-每隔2s执行一次
                )
                .startNow()                                         // 立即生效
                .build();
        Set<Trigger> set = new HashSet<>();
        set.add(trigger);

        /**
         * 配置 JDBC 持久化后需要配置启动时对数据库中的quartz的任务进行覆盖，不然项目启动不起来
         * boolean replace 表示启动时是否对数据库中的quartz的任务进行覆盖
         */
        scheduler.scheduleJob(jobDetail, set, true);
    }
}