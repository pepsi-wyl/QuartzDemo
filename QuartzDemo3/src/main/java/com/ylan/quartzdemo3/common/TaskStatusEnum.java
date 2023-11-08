package com.ylan.quartzdemo3.common;

import lombok.Getter;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 16:59
 * @description 任务状态枚举
 */

public enum TaskStatusEnum {

    // 枚举
    START("2", "开启"),
    STOP("0", "关闭");


    // 任务状态code
    @Getter
    private String code;

    // 任务状态msg
    @Getter
    private String msg;


    TaskStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}