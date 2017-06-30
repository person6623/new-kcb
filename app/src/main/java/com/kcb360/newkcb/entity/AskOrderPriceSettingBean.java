package com.kcb360.newkcb.entity;

/**
 * Created by xinshichao on 2017/6/12.
 * <p>
 * 获取包车价格设置
 */

public class AskOrderPriceSettingBean {

    /**
     * code : 1
     * context : 获取成功
     * price : 2100
     * freeKm : 10
     * freeKmPrice : 5
     * oilWear : 1.4
     * lonAndLat : 104.065749,30.657345
     */

    private int code;
    private String context;
    private String price;
    private float freeKm;
    private float freeKmPrice;
    private double oilWear;
    private String lonAndLat;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getFreeKm() {
        return freeKm;
    }

    public void setFreeKm(float freeKm) {
        this.freeKm = freeKm;
    }

    public float getFreeKmPrice() {
        return freeKmPrice;
    }

    public void setFreeKmPrice(float freeKmPrice) {
        this.freeKmPrice = freeKmPrice;
    }

    public double getOilWear() {
        return oilWear;
    }

    public void setOilWear(double oilWear) {
        this.oilWear = oilWear;
    }

    public String getLonAndLat() {
        return lonAndLat;
    }

    public void setLonAndLat(String lonAndLat) {
        this.lonAndLat = lonAndLat;
    }
}
