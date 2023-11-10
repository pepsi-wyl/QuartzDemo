package com.ylan.quartzdemo3.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by pepsi-wyl
 * @date 2023-11-08 15:56
 * @description 返回码转化Msg
 */

public class CodeToMsg {

    private static final Map<Integer, String> MSG = new HashMap<>();

    /**
     * 系统级别
     */
    public static final int SUCCESS = 200;
    public static final int ERROR = 500;

    /**
     * 任务级别
     */
    public static final int TASK_NOT_EXITS = 100001;
    public static final int TASK_EXCEPTION = 100002;
    public static final int TASK_CRON_ERROR = 100003;
    public static final int TASK_CRON_DOUBLE = 100004;
    public static final int TASK_JobNameAndIdNotMatch = 100005;


    static {
        /**
         * 系统级别
         */
        MSG.put(SUCCESS, "请求成功");
        MSG.put(ERROR, "服务器异常");

        /**
         * 任务级别
         */
        MSG.put(TASK_NOT_EXITS, "定时任务不存在");
        MSG.put(TASK_EXCEPTION, "设置定时任务失败");
        MSG.put(TASK_CRON_ERROR, "表达式有误");
        MSG.put(TASK_CRON_DOUBLE, "定时任务已经存在");
        MSG.put(TASK_JobNameAndIdNotMatch, "任务名称和ID不匹配");
    }


    public static String getMsg(int errorCode) {
        return MSG.get(errorCode);
    }
}