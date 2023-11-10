package com.ylan.quartzdemo3.config;

import com.ylan.quartzdemo3.common.TaskStatusEnum;
import com.ylan.quartzdemo3.model.entity.TaskEntity;
import com.ylan.quartzdemo3.model.vo.TaskRequestVO;
import com.ylan.quartzdemo3.service.TaskService;
import com.ylan.quartzdemo3.utils.SpringContextUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 17:11
 * @description 监听器 Spring Boot容器启动时，加载并启动所有任务
 */

@Component
@Order(value = 1)  // 指定顺序
// CommandLineRunner => 项目启动之前，预先加载数据
public class QuartzManager implements CommandLineRunner{

    private static final Logger log = LoggerFactory.getLogger(SpringContextUtils.class);
    private final TaskService taskService;

    public QuartzManager(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }

    public void start() {
        List<TaskEntity> tasks = taskService.selectTasks();
        tasks.forEach((task)->{
            if (TaskStatusEnum.START.getCode().equals(task.getStatus()) && !StringUtils.isEmpty(task.getCron())) {
                TaskRequestVO data = new TaskRequestVO();
                BeanUtils.copyProperties(task,data);
                taskService.addJob(data);
            }
        });
        log.info("[Info]=>DynamicScheduler定时任务启动完成");
    }

}