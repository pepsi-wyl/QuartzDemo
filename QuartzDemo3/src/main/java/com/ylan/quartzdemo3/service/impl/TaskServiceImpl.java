package com.ylan.quartzdemo3.service.impl;

import com.ylan.quartzdemo3.common.Result;
import com.ylan.quartzdemo3.model.entity.TaskEntity;
import com.ylan.quartzdemo3.model.vo.TaskRequestVO;
import com.ylan.quartzdemo3.service.TaskService;

import java.util.List;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 16:47
 * @description 任务Service实现类
 */

public class TaskServiceImpl implements TaskService {

    @Override
    public List<TaskEntity> selectTasks() {
        return null;
    }

    @Override
    public Result selectTaskListByPage(TaskRequestVO taskRequestVO) {
        return null;
    }

    @Override
    public Result addJob(TaskRequestVO taskRequestVO) {
        return null;
    }

    @Override
    public Result delete(TaskRequestVO taskRequestVO) {
        return null;
    }

    @Override
    public Result updateJob(TaskRequestVO taskRequestVO) {
        return null;
    }

    @Override
    public Result pauseJob(Integer taskId) {
        return null;
    }

    @Override
    public Result resumeJob(Integer taskId) {
        return null;
    }
}