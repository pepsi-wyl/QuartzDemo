package com.example.quartzdemo2.task.job;

import com.example.quartzdemo2.task.TaskService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author by pepsi-wyl
 * @date 2023-11-06 17:32
 * @Description SecondJob
 */

@DisallowConcurrentExecution  // 实现在相同 Quartz Scheduler 集群中，相同 JobKey 的 JobDetail ，保证在多个 JVM 进程中，有且仅有一个节点在执行
public class SecondJob extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(SecondJob.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final AtomicInteger counts = new AtomicInteger();

    /**
     *  QuartzJobBean 实现了 org.quartz.Job 接口，提供了 Quartz 每次创建 Job 执行定时逻辑时，将该 Job Bean 的依赖属性注入
     */
    // 注入Service
//    @Autowired
    @Resource
    private TaskService taskService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        taskService.task();
        log.info("[Thread:" + Thread.currentThread().getName() + "]" + "[SecondJobStartTime:" + jobExecutionContext.getTrigger().getStartTime() + "]" + "[This Time:" + dateFormat.format(new Date()) + ", Job-" + counts.incrementAndGet() + "]");
    }
}