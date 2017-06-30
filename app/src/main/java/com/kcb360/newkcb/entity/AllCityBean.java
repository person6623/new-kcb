package com.kcb360.newkcb.entity;

import java.util.List;

/**
 * Created by xinshichao on 2017/6/28.
 * <p>
 * 空车设置用全部城市
 */

public class AllCityBean {

    /**
     * code : 1
     * context : 查询成功！
     * cityList : [{"cityCode":"653200","cityMark":"","cityName":"和田地区","hotOrder":0,"lonAndLat":"","pinyin":"hetian","pinyinSimple":"ht","plateNumShort":"新R","provinceId":"650000","specialArea":0,"startAddress":""},{"cityCode":"653100","cityMark":"","cityName":"喀什地区","hotOrder":0,"lonAndLat":"","pinyin":"kashen","pinyinSimple":"ks","plateNumShort":"新Q","provinceId":"650000","specialArea":0,"startAddress":""},{"cityCode":"650100","cityMark":"","cityName":"乌鲁木齐市","hotOrder":0,"lonAndLat":"","pinyin":"wulumuqi","pinyinSimple":"wlmq","plateNumShort":"新A","provinceId":"650000","specialArea":0,"startAddress":""},{"cityCode":"652100","cityMark":"","cityName":"吐鲁番地区","hotOrder":0,"lonAndLat":"","pinyin":"tulufan","pinyinSimple":"tlf","plateNumShort":"新K","provinceId":"650000","specialArea":0,"startAddress":""},{"cityCode":"652200","cityMark":"","cityName":"哈密地区","hotOrder":0,"lonAndLat":"","pinyin":"hami","pinyinSimple":"hm","plateNumShort":"新L","provinceId":"650000","specialArea":0,"startAddress":""},{"cityCode":"652300","cityMark":"","cityName":"昌吉回族自治州","hotOrder":0,"lonAndLat":"","pinyin":"changji","pinyinSimple":"cj","plateNumShort":"新B","provinceId":"650000","specialArea":0,"startAddress":""},{"cityCode":"652700","cityMark":"","cityName":"博尔塔拉蒙古自治州","hotOrder":0,"lonAndLat":"","pinyin":"boer","pinyinSimple":"be","plateNumShort":"新E","provinceId":"650000","specialArea":0,"startAddress":""},{"cityCode":"652800","cityMark":"","cityName":"巴音郭楞蒙古自治州","hotOrder":0,"lonAndLat":"","pinyin":"bayin","pinyinSimple":"by","plateNumShort":"新M","provinceId":"650000","specialArea":0,"startAddress":""},{"cityCode":"652900","cityMark":"","cityName":"阿克苏地区","hotOrder":0,"lonAndLat":"","pinyin":"akesu","pinyinSimple":"aks","plateNumShort":"新N","provinceId":"650000","specialArea":0,"startAddress":""},{"cityCode":"653000","cityMark":"","cityName":"克孜勒苏柯尔克孜自治州","hotOrder":0,"lonAndLat":"","pinyin":"kezile","pinyinSimple":"kzl","plateNumShort":"新P","provinceId":"650000","specialArea":0,"startAddress":""},{"cityCode":"650200","cityMark":"","cityName":"克拉玛依市","hotOrder":0,"lonAndLat":"","pinyin":"kelamayi","pinyinSimple":"klmy","plateNumShort":"新J","provinceId":"650000","specialArea":0,"startAddress":""}]
     */

    private int code;
    private String context;
    private List<CityListBean> cityList;

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

    public List<CityListBean> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBean> cityList) {
        this.cityList = cityList;
    }

    public static class CityListBean {
        /**
         * cityCode : 653200
         * cityMark :
         * cityName : 和田地区
         * hotOrder : 0
         * lonAndLat :
         * pinyin : hetian
         * pinyinSimple : ht
         * plateNumShort : 新R
         * provinceId : 650000
         * specialArea : 0
         * startAddress :
         */

        private String cityCode;
        private String cityMark;
        private String cityName;
        private int hotOrder;
        private String lonAndLat;
        private String pinyin;
        private String pinyinSimple;
        private String plateNumShort;
        private String provinceId;
        private int specialArea;
        private String startAddress;

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityMark() {
            return cityMark;
        }

        public void setCityMark(String cityMark) {
            this.cityMark = cityMark;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getHotOrder() {
            return hotOrder;
        }

        public void setHotOrder(int hotOrder) {
            this.hotOrder = hotOrder;
        }

        public String getLonAndLat() {
            return lonAndLat;
        }

        public void setLonAndLat(String lonAndLat) {
            this.lonAndLat = lonAndLat;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getPinyinSimple() {
            return pinyinSimple;
        }

        public void setPinyinSimple(String pinyinSimple) {
            this.pinyinSimple = pinyinSimple;
        }

        public String getPlateNumShort() {
            return plateNumShort;
        }

        public void setPlateNumShort(String plateNumShort) {
            this.plateNumShort = plateNumShort;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public int getSpecialArea() {
            return specialArea;
        }

        public void setSpecialArea(int specialArea) {
            this.specialArea = specialArea;
        }

        public String getStartAddress() {
            return startAddress;
        }

        public void setStartAddress(String startAddress) {
            this.startAddress = startAddress;
        }
    }
}
