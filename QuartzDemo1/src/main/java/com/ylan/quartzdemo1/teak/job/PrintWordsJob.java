package com.ylan.quartzdemo1.teak.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author by pepsi-wyl
 * @date 2023-11-06 9:32
 * @Description: 打印任务
 */

// Job是 Quartz 中的一个接口，接口中只有execute方法，在这个方法中编写业务逻辑。
public class PrintWordsJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(PrintWordsJob.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // JobExecutionContext是任务执行的上下文环境，包含了Quartz运行时的环境以及Job本身的详细数据信息。
    // 当Schedule调度执行一个Job的时候，就会将JobExecutionContext传递给该Job的execute()中，Job就可以通过JobExecutionContext对象获取信息。
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取JobDetail中JobDataMap中的值
        log.info((String) jobExecutionContext.getJobDetail().getJobDataMap().get("printWordsJobDetailKey"));
        // 获取Trigger中JobDataMap中的值
        log.info((String) jobExecutionContext.getTrigger().getJobDataMap().get("printWordsTriggerKey"));
        // 获取JobDetailName和TriggerName
        log.info("[JobDetailName" + jobExecutionContext.getJobDetail().getJobDataMap().get("jobDetailName") + "]" + "[TriggerName:" + jobExecutionContext.getTrigger().getJobDataMap().get("triggerName") + "]");
        // 执行任务方法
        log.info("[Thread:" + Thread.currentThread().getName() + "]" + "[JobStartTime:" + jobExecutionContext.getTrigger().getStartTime() + "]" + "[PrintWordsJob start at:" + dateFormat.format(new Date()) + ", prints: Hello Job-" + new Random().nextInt(100) + "]");
    }
}