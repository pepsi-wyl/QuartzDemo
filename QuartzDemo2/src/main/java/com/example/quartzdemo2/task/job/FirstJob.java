package com.example.quartzdemo2.task.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author by pepsi-wyl
 * @date 2023-11-06 15:49
 * @Description FirstJob
 */

@DisallowConcurrentExecution   // 实现在相同 Quartz Scheduler 集群中，相同 JobKey 的 JobDetail ，保证在多个 JVM 进程中，有且仅有一个节点在执行
public class FirstJob extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(FirstJob.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final AtomicInteger counts = new AtomicInteger();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("[Thread:" + Thread.currentThread().getName() + "]" + "[FirstJobStartTime:" + jobExecutionContext.getTrigger().getStartTime() + "]" + "[This Time:" + dateFormat.format(new Date()) + ", Job-" + counts.incrementAndGet() + "]");
    }
}