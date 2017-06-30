package com.kcb360.newkcb.entity;

/**
 * Created by xinshichao on 2017/6/13.
 * <p>
 * 通用bean
 */

public class NormalBean {

    /**
     * code : 2
     * context : 发布成功！
     * carOrderId : 1199
     * orderNum : 861497320923499
     */

    private int code;
    private String context;
    private int carOrderId;
    private String orderNum;

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

    public int getCarOrderId() {
        return carOrderId;
    }

    public void setCarOrderId(int carOrderId) {
        this.carOrderId = carOrderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
