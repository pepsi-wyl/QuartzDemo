package com.ylan.quartzdemo3.task.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 17:30
 * @description 测试任务
 */

@Component

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class SimpleJob extends QuartzJobBean {

    private static final Log log = LogFactory.getLog(SimpleJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("幼年是盼盼，青年是晶晶，中年是冰墩墩，生活见好逐渐发福");
    }

}