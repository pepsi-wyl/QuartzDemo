package com.ylan.quartzdemo3.model.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 13:55
 * @Descrption 任务实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder

@Component("userEntity")
public class TaskEntity {
    // 任务编号
    private Integer id;

    // 任务名称
    private String jobName;

    // Cron表达式
    private String cron;

    // 任务状态 0-关闭 2-开启
    private String status;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;
}