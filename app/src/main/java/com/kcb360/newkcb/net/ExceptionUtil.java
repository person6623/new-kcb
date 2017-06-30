package com.kcb360.newkcb.net;

/**
 * Created by xinshichao on 2017/6/7.
 * <p>
 * 线程集
 */

public class ExceptionUtil {

    // 关于各类异常
    public static String exceptionSwitch(String e) {
        if (e.contains("SocketTimeoutException")) {
            return "连接超时";
        }
        if (e.contains("404")) {
            return "登录失效,请重新登录";
        }
        if (e.contains("500")) {
            return "服务器错误,请重试连接,仍有问题请联系";
        }
        if (e.contains("ConnectException")) {
            return "未开启网络";
        }
        return e;
    }


}
