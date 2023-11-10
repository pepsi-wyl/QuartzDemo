package com.ylan.quartzdemo3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylan.quartzdemo3.common.CodeToMsg;
import com.ylan.quartzdemo3.common.ResponseFactory;
import com.ylan.quartzdemo3.common.Result;
import com.ylan.quartzdemo3.common.TaskStatusEnum;
import com.ylan.quartzdemo3.mapper.TaskMapper;
import com.ylan.quartzdemo3.model.entity.TaskEntity;
import com.ylan.quartzdemo3.model.vo.TaskRequestVO;
import com.ylan.quartzdemo3.service.TaskService;
import com.ylan.quartzdemo3.task.TaskManager;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 16:47
 * @description 任务Service实现类
 */

@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskMapper, TaskEntity> implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskManager taskManager;

    /**
     * 所有任务
     */
    @Override
    public List<TaskEntity> selectTasks() {
        return taskMapper.selectAll();
    }

    /**
     * 分页-任务列表
     */
    @Override
    public Result selectTaskListByPage(TaskRequestVO taskRequestVO) {
        // PageHelper分页插件
        PageHelper.startPage(taskRequestVO.getPageCurrent(), taskRequestVO.getPageSize());
        List<TaskEntity> list = taskMapper.selectTaskInfos(taskRequestVO);
        PageInfo<TaskEntity> pageInfo = new PageInfo<>(list);
        return ResponseFactory.build(pageInfo);
    }

    /**
     * 添加任务
     */
    @Override
    public Result addJob(TaskRequestVO taskRequestVO) {

        // Cron表达式校验
        if (!CronExpression.isValidExpression(taskRequestVO.getCron())) {
            log.error("[Error]=>开启任务失败，定时任务表达式有误：{}", taskRequestVO.getCron());
            return ResponseFactory.build(CodeToMsg.TASK_CRON_ERROR);
        }

        // Quartz添加Job并开启
        if (!taskManager.addJob(taskRequestVO)) {
            // 开启任务异常
            return ResponseFactory.build(CodeToMsg.TASK_EXCEPTION);
        }

        // Task数据库插入操作
        if (taskIsNotExistByJobName(taskRequestVO.getJobName())) {
            TaskEntity task = new TaskEntity();
            BeanUtils.copyProperties(taskRequestVO, task);
            task.setCreateTime(new Date());
            taskMapper.insertSelective(task);
            // 添加成功
            return ResponseFactory.build();
        } else {
            // 添加失败 任务已经存在
            log.error("[Error]=>定时任务已存在：{}", taskRequestVO.getCron());
            return ResponseFactory.build(CodeToMsg.TASK_CRON_DOUBLE);
        }
    }

    @Override
    public Result delete(TaskRequestVO taskRequestVO) {

        TaskEntity taskByTaskId = taskMapper.selectByPrimaryKey(taskRequestVO.getId());
        TaskEntity taskByJobName = taskMapper.selectByJobName(taskRequestVO.getJobName());

        // 任务不存在
        if (Objects.isNull(taskByJobName) && Objects.isNull(taskByTaskId)){
            log.error("[Error]=>删除任务失败，定时任务不存在=>taskByJobName：{}，taskByTaskId：{}", taskByJobName, taskByTaskId);
            return ResponseFactory.build(CodeToMsg.TASK_NOT_EXITS);
        }

        // jobName 和 ID记录 都不为空
        if (!Objects.isNull(taskByTaskId) && !Objects.isNull(taskByJobName)){
            // jobName 和 ID 记录不对应
            if (!taskByJobName.equals(taskByTaskId)){
                log.error("[Error]=>删除任务失败，任务名称和ID不匹配=>taskByJobName：{}，taskByTaskId：{}", taskByJobName, taskByTaskId);
                return ResponseFactory.build(CodeToMsg.TASK_JobNameAndIdNotMatch);
            }
        }

        // 准备删除任务的数据
        TaskEntity task = new TaskEntity();
        if (!Objects.isNull(taskByTaskId)) {
            BeanUtils.copyProperties(taskByTaskId, task);
        } else {
            BeanUtils.copyProperties(taskByJobName, task);
        }

        // Quartz删除任务操作
        if (!taskManager.deleteJob(task)) {
            // 删除任务异常
            return ResponseFactory.build(CodeToMsg.TASK_EXCEPTION);
        }

        // Task数据库更新操作
        taskMapper.deleteByPrimaryKey(task.getId());

        // 删除成功
        return ResponseFactory.build();
    }

    @Override
    public Result updateJob(TaskRequestVO taskRequestVO) {

        // Cron表达式校验
        if (!CronExpression.isValidExpression(taskRequestVO.getCron())) {
            log.error("[Error]=>修改任务失败，定时任务表达式有误：{}", taskRequestVO.getCron());
            return ResponseFactory.build(CodeToMsg.TASK_CRON_ERROR);
        }

        TaskEntity taskByTaskId = taskMapper.selectByPrimaryKey(taskRequestVO.getId());
        TaskEntity taskByJobName = taskMapper.selectByJobName(taskRequestVO.getJobName());

        // 任务不存在
        if (Objects.isNull(taskByJobName) && Objects.isNull(taskByTaskId)){
            log.error("[Error]=>修改任务失败，定时任务不存在=>taskByJobName：{}，taskByTaskId：{}", taskByJobName, taskByTaskId);
            return ResponseFactory.build(CodeToMsg.TASK_NOT_EXITS);
        }

        // jobName 和 ID记录 都不为空
        if (!Objects.isNull(taskByTaskId) && !Objects.isNull(taskByJobName)){
            // jobName 和 ID 记录不对应
            if (!taskByJobName.equals(taskByTaskId)){
                log.error("[Error]=>修改任务失败，任务名称和ID不匹配=>taskByJobName：{}，taskByTaskId：{}", taskByJobName, taskByTaskId);
                return ResponseFactory.build(CodeToMsg.TASK_JobNameAndIdNotMatch);
            }
        }

        // 准备更新任务的数据
        TaskEntity task = new TaskEntity();
        if (!Objects.isNull(taskByTaskId)) {
            BeanUtils.copyProperties(taskByTaskId, task);
        } else {
            BeanUtils.copyProperties(taskByJobName, task);
        }
        task.setCron(taskRequestVO.getCron());
        task.setUpdateTime(new Date());

        // Quartz更新任务操作
        if (!taskManager.updateJob(task)) {
            // 更新任务异常
            return ResponseFactory.build(CodeToMsg.TASK_EXCEPTION);
        }

        // Task数据库更新操作
        taskMapper.updateByPrimaryKeySelective(task);

        // 更新成功
        return ResponseFactory.build();
    }


    /**
     * 暂停任务
     */
    @Override
    public Result pauseJob(Integer taskId) {
        // Task数据库中获取当前任务
        TaskEntity task = taskMapper.selectByPrimaryKey(taskId);

        // Task数据库任务不存在
        if (Objects.isNull(task)) {
            return ResponseFactory.build(CodeToMsg.TASK_NOT_EXITS);
        }

        // Quartz暂停任务失败
        if (!taskManager.pauseJob(task)) {
            return ResponseFactory.build(CodeToMsg.TASK_EXCEPTION);
        }

        // 任务设置状态 并写回数据库
        task.setStatus(TaskStatusEnum.STOP.getCode());
        taskMapper.updateByPrimaryKeySelective(task);

        // 任务暂停成功
        return ResponseFactory.build();
    }

    /**
     * 恢复任务
     */
    @Override
    public Result resumeJob(Integer taskId) {
        // Task数据库中获取当前任务
        TaskEntity task = taskMapper.selectByPrimaryKey(taskId);

        // Task数据库任务不存在
        if (Objects.isNull(task)) {
            return ResponseFactory.build(CodeToMsg.TASK_NOT_EXITS);
        }

        // Quartz恢复任务失败
        if (!taskManager.resumeJob(task)) {
            return ResponseFactory.build(CodeToMsg.TASK_EXCEPTION);
        }

        // 任务设置状态 并写回数据库
        task.setStatus(TaskStatusEnum.START.getCode());
        taskMapper.updateByPrimaryKeySelective(task);

        // 任务恢复成功
        return ResponseFactory.build();
    }

    /**
     * 任务务是否存在按照JobName
     * @param jobName
     * @return
     */
    private boolean taskIsNotExistByJobName(String jobName) {
        return taskMapper.selectByJobName(jobName) == null;
    }

}