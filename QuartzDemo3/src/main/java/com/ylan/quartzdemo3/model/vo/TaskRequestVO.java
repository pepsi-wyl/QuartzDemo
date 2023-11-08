package com.ylan.quartzdemo3.model.vo;

import lombok.Data;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 14:03
 * @Descrption 任务请求类
 */

@Data
public class TaskRequestVO {
    // 任务编号
    private Integer id;

    // 任务名称
    private String jobName;

    // Cron表达式
    private String cron;

    // 任务状态 0-关闭 2-开启
    private String status;

    // 每页显示条数
    private int pageSize = 10;

    // 当前页数
    private int pageCurrent = 1;
}