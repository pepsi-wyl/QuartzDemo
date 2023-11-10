package com.ylan.quartzdemo3.task;

import com.ylan.quartzdemo3.model.entity.TaskEntity;
import com.ylan.quartzdemo3.model.vo.TaskRequestVO;
import com.ylan.quartzdemo3.utils.SpringContextUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 17:05
 * @description 任务管理器
 */

@Component("taskManager")
public class TaskManager {

    private static final Logger log = LoggerFactory.getLogger(TaskManager.class);
    public static final String JOB_DEFAULT_GROUP_NAME = "JOB_DEFAULT_GROUP_NAME";
    public static final String TRIGGER_DEFAULT_GROUP_NAME = "TRIGGER_DEFAULT_GROUP_NAME";

    @Autowired
    private Scheduler scheduler;

    /**
     * 添加任务
     */
    public boolean addJob(TaskRequestVO taskRequestVO) {
        boolean flag = true;
        try {
            // 通过传进来的JobName名字，即Spring中托管的Bean的名称，利用Spring工具类获取到完整类名
            String className = SpringContextUtils.getBean(taskRequestVO.getJobName()).getClass().getName();

            JobDetail jobDetail = JobBuilder.newJob()
                    .withIdentity(new JobKey(taskRequestVO.getJobName(), JOB_DEFAULT_GROUP_NAME))        // JobKey = JobName + JOB_DEFAULT_GROUP_NAME
                    .ofType((Class<Job>) Class.forName(className))                                       // 触发与此JobDetail关联的触发器时，将实例化并执行的类。
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(new TriggerKey(taskRequestVO.getJobName(), TRIGGER_DEFAULT_GROUP_NAME)) // TriggerKey = JobName + TRIGGER_DEFAULT_GROUP_NAME
                    .forJob(jobDetail)                                                                    // 绑定JobDetail
                    .withSchedule(CronScheduleBuilder.cronSchedule(taskRequestVO.getCron()))              // 设置Cron表达式
                    .build();

            // 绑定 JobDetail 和 Trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // 开始执行
            scheduler.start();
            log.info("[Info]=>添加定时任务成功：{}，任务名称：{}，表达式：{}", new JobKey(taskRequestVO.getJobName(), JOB_DEFAULT_GROUP_NAME) + "-" + new TriggerKey(taskRequestVO.getJobName(), TRIGGER_DEFAULT_GROUP_NAME), taskRequestVO.getJobName(), taskRequestVO.getCron());
        } catch (Exception e) {
            log.error("[Error]=>添加定时任务异常：{}", e.getMessage(), e);
            flag = false;
        }
        return flag;
    }

    /**
     * 更新任务
     */
    public boolean updateJob(TaskEntity task) {
        boolean flag = true;
        try {
            JobKey jobKey = new JobKey(task.getJobName(), JOB_DEFAULT_GROUP_NAME);                    // JobKey = JobName + JOB_DEFAULT_GROUP_NAME
            TriggerKey triggerKey = new TriggerKey(task.getJobName(), TRIGGER_DEFAULT_GROUP_NAME);    // TriggerKey = JobName + TRIGGER_DEFAULT_GROUP_NAME

            // 判断 JobKey 和 TriggerKey 是否存在 ===> 任务是否存在
            if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
                // 获取之前的JobDetail
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                // 重新创建新的Trigger
                Trigger newTrigger = TriggerBuilder.newTrigger()
                        .forJob(jobDetail)
                        .withSchedule(CronScheduleBuilder.cronSchedule(task.getCron()))
                        .withIdentity(triggerKey)
                        .build();
                // 对JobDetail重新绑定Trigger
                scheduler.rescheduleJob(triggerKey, newTrigger);
                log.info("[Info]=>更新任务成功，任务TaskId：{}，任务名称：{}，表达式：{}", task.getId(), task.getJobName(), task.getCron());
            } else {
                flag = false;
                log.error("[Error]=>更新任务失败，任务不存在，JobKey：{}，TriggerKey：{}，任务TaskId：{}，任务名称：{}，表达式：{}", jobKey, triggerKey, task.getId(), task.getJobName(), task.getCron());
            }
        } catch (SchedulerException e) {
            flag = false;
            log.error("[Error]=>更新定时任务失败:{}", e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 删除任务
     */
    public boolean deleteJob(TaskEntity task) {
        boolean flag = true;
        try {
            // 通过JobKey删除任务，如果JobKey不存在，则会抛出异常。
            scheduler.deleteJob(JobKey.jobKey(task.getJobName(), JOB_DEFAULT_GROUP_NAME));
            log.info("[Info]=>任务删除成功：TaskID={}，JobName={}", task.getId(), task.getJobName());
        } catch (SchedulerException e) {
            flag = false;
            log.error("[Error]=>删除定时任务失败:{}", e.getMessage(), e);
        }
        return flag;
    }

    /**
     * 暂停任务
     */
    public boolean pauseJob(TaskEntity task) {
        boolean flag = true;
        try {
            // 通过JobKey暂停任务，如果JobKey不存在，则会抛出异常。
            scheduler.pauseJob(JobKey.jobKey(task.getJobName(), JOB_DEFAULT_GROUP_NAME));
            log.info("[Info]=>任务暂停成功：TaskID={}，JobName={}", task.getId(), task.getJobName());
        } catch (SchedulerException e) {
            log.error("[Error]=>暂停定时任务失败:{}", e.getMessage(), e);
            flag = false;
        }
        return flag;
    }

    /**
     * 恢复任务
     */
    public boolean resumeJob(TaskEntity task) {
        boolean flag = true;
        try {
            // 通过JobKey恢复任务，如果JobKey不存在，则会抛出异常。
            scheduler.resumeJob(JobKey.jobKey(task.getJobName(), JOB_DEFAULT_GROUP_NAME));
            log.info("[Info]=>任务恢复成功：TaskID={}，JobName={}", task.getId(), task.getJobName());
        } catch (SchedulerException e) {
            log.error("[Error]=>恢复定时任务失败:{}", e.getMessage(), e);
            flag = false;
        }
        return flag;
    }

}