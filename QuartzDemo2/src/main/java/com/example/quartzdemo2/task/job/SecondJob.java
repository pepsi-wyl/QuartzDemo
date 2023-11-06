package com.example.quartzdemo2.task.job;

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
 * @date 2023-11-06 17:32
 * @Description SecondJob
 */

public class SecondJob extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(SecondJob.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final AtomicInteger counts = new AtomicInteger();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("[Thread:" + Thread.currentThread().getName() + "]" + "[SecondJobStartTime:" + jobExecutionContext.getTrigger().getStartTime() + "]" + "[This Time:" + dateFormat.format(new Date()) + ", Job-" + counts.incrementAndGet() + "]");
    }
}