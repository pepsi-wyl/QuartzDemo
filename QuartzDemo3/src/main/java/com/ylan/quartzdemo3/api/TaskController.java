package com.ylan.quartzdemo3.api;

import com.ylan.quartzdemo3.common.Result;
import com.ylan.quartzdemo3.model.vo.TaskRequestVO;
import com.ylan.quartzdemo3.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 16:57
 * @description 任务API接口
 */

@RestController("/task")
public class TaskController {

    /**
     * 注入 TaskService
     */
    @Autowired
    private TaskService taskService;

    /**
     * 任务列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody TaskRequestVO taskRequestVO) {
        return taskService.selectTaskListByPage(taskRequestVO);
    }

    /**
     * 增加任务
     */
    @PostMapping("/add")
    public Result add(@RequestBody TaskRequestVO taskRequestVO) {
        return taskService.addJob(taskRequestVO);
    }

    /**
     * 删除任务
     */
    @PostMapping("/del")
    public Result delete(@RequestBody TaskRequestVO taskRequestVO) {
        return taskService.delete(taskRequestVO);
    }

    /**
     * 任务修改
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody TaskRequestVO taskRequestVO) {
        return taskService.updateJob(taskRequestVO);
    }

    /**
     * 暂停任务
     */
    @PostMapping("/pause")
    public Result pause(Integer taskId) {
        return taskService.pauseJob(taskId);
    }

    /**
     * 恢复任务
     */
    @PostMapping("/resume")
    public Result resume(Integer taskId) {
        return taskService.resumeJob(taskId);
    }

}