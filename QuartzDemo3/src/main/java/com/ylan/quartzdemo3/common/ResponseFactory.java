package com.ylan.quartzdemo3.common;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 15:55
 * @description 响应工厂类
 */

public class ResponseFactory {

    // 公共构建方法
    private static Result commonBuild(int code, String errmsg) {
        Result result = new Result();

        // 设置Code
        result.setCode(code);

        // 设置Msg
        if (errmsg == null || errmsg.trim().length() == 0)
            result.setMsg(CodeToMsg.getMsg(code));
        else
            result.setMsg(errmsg);

        return result;
    }

    // Success
    public static Result build() {
        return commonBuild(CodeToMsg.SUCCESS, null);
    }

    // Success + Data
    public static Result build(Object data) {
        Result json = commonBuild(CodeToMsg.SUCCESS, null);
        json.setData(data);
        return json;
    }

    // Code + Msg
    public static Result build(int code) {
        return commonBuild(code, CodeToMsg.getMsg(code));
    }

}