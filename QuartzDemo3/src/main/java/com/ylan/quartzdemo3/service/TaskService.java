package com.ylan.quartzdemo3.service;

import com.ylan.quartzdemo3.common.Result;
import com.ylan.quartzdemo3.model.entity.TaskEntity;
import com.ylan.quartzdemo3.model.vo.TaskRequestVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 14:08
 * @description 任务Service
 */

public interface TaskService {

    /**
     * 获取所有任务
     */
    List<TaskEntity> selectTasks();

    /**
     * 获取任务列表分页
     */
    Result selectTaskListByPage(TaskRequestVO taskRequestVO);

    /**
     * 添加定时任务
     */
    Result addJob(TaskRequestVO taskRequestVO);

    /**
     * 删除任务
     */
    Result delete(TaskRequestVO taskRequestVO);

    /**
     * 更新任务
     */
    Result updateJob(TaskRequestVO taskRequestVO);

    /**
     * 暂停任务
     */
    Result pauseJob(Integer taskId);

    /**
     * 恢复任务
     */
    Result resumeJob(Integer taskId);
}