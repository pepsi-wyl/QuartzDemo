package com.ylan.quartzdemo3.common;

import lombok.Data;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 15:52
 * @description Result响应结果类
 */

@Data
public class Result {
    private int code;
    private String msg;
    private Object data;
}