package com.kcb360.newkcb.entity;

/**
 * Created by xinshichao on 2017/6/26.
 * <p>
 * 版本更新
 */

public class VersionBean {

    /**
     * appName :
     * apkName : zhuanche.apk
     * verName :
     * verCode : 71
     * verInfo : 更新内容:(1)优化了添加车辆的图片添加功能;(2)优化了添加银行卡和提现功能;(3)优化了部分界面;
     */

    private String appName;
    private String apkName;
    private String verName;
    private String verCode;
    private String verInfo;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getVerInfo() {
        return verInfo;
    }

    public void setVerInfo(String verInfo) {
        this.verInfo = verInfo;
    }
}
