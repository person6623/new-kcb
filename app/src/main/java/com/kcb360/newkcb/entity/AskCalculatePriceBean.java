package com.kcb360.newkcb.entity;

/**
 * Created by xinshichao on 2017/6/12.
 * <p>
 * 询价总价返回
 */

public class AskCalculatePriceBean {

    /**
     * code : 1
     * context : 获取成功
     * payMoney : 5560
     */

    private int code;
    private String context;
    private String payMoney;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }
}
