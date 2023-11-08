create DATABASE quartz_demo;
use quartz_demo;

CREATE TABLE `QUARTZ_TASK` (
                               `id` int unsigned       NOT NULL AUTO_INCREMENT   COMMENT '任务编号',
                               `job_name` varchar(256) DEFAULT NULL              COMMENT '任务名称',
                               `cron` varchar(32)      DEFAULT NULL              COMMENT 'Cron表达式',
                               `status` char(1)        DEFAULT '0'               COMMENT '任务状态 0-关闭 2-开启',
                               `create_time` datetime  DEFAULT NULL              COMMENT '创建时间',
                               `update_time` datetime  DEFAULT NULL              COMMENT '更新时间',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT COMMENT='定时任务表';