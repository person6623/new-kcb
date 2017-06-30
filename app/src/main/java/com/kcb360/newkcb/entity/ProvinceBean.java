package com.kcb360.newkcb.entity;

/**
 * Created by xinshichao on 2017/6/16.
 * <p>
 * 省份
 */

public class ProvinceBean {

    /**
     * id : 1
     * provinceCode : 130000
     * provinceMark : 21
     * provinceName : 河北省
     */

    private int id;
    private String provinceCode;
    private String provinceMark;
    private String provinceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceMark() {
        return provinceMark;
    }

    public void setProvinceMark(String provinceMark) {
        this.provinceMark = provinceMark;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
