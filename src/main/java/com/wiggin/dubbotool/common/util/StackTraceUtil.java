package com.wiggin.dubbotool.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @description: 异常栈
 * @author: wiggin
 * @date: 2025-04-07 11:06
 **/
public class StackTraceUtil {
    /**
     * 获取异常栈信息
     *
     * @param throwable
     * @return
     */
    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
