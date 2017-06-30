package com.kcb360.newkcb.entity;

import java.util.List;

/**
 * Created by xinshichao on 2017/6/28.
 * <p>
 * 车辆详情
 */

public class CarInfoBean {

    /**
     * code : 1
     * context : 查询成功
     * myCarList : [{"carBrand":"金龙牌","carCompany":"","carGears":2,"carNature":4,"carSeats":47,"carState":1,"carType":0,"carYear":"2017-04-10 00:00","cfUrl":"/rentImg/carFront_kcb_1491816429369_4438.png","csUrl":"/rentImg/carSide_kcb_1491816429385_4439.png","ctUrl":"","dcUrl":"","disposeTime":"2017-04-10 17:28","dlUrl":"/rentImg/drivingLicense_kcb_1491816429385_4440.png","driverPhone":"15828251216","id":851,"managePhone":"17828174933","mbName":"15828251216","note":"匿名","operation":"","operationUrl":"","orderArea":"成都市","plateNum":"川A99999","regTime":"2017-04-10 17:27","runArea":0,"safeCardUrl":"","teamNo":"8112010001"},{"carBrand":"大众牌","carCompany":"","carGears":2,"carNature":2,"carSeats":47,"carState":1,"carType":1,"carYear":"2017-05-10 00:00","cfUrl":"","csUrl":"","ctUrl":"","dcUrl":"/rentImg/driveCard_kcb_1494402721257_4700.png","disposeTime":"2017-05-10 15:54","dlUrl":"/rentImg/drivingLicense_kcb_1494402721304_4701.png","driverPhone":"15828251216","id":886,"managePhone":"","mbName":"15828251216","note":"测试","operation":"","operationUrl":"","orderArea":"1","plateNum":"川B99999","regTime":"2017-05-10 15:52","runArea":0,"safeCardUrl":"","teamNo":""},{"carBrand":"广汽牌","carCompany":"","carGears":2,"carNature":2,"carSeats":45,"carState":1,"carType":1,"carYear":"2017-05-12 00:00","cfUrl":"","csUrl":"","ctUrl":"","dcUrl":"/rentImg/driveCard_kcb_1494575666257_4718.png","disposeTime":"2017-05-12 15:55","dlUrl":"/rentImg/drivingLicense_kcb_1494575666272_4719.png","driverPhone":"15828251216","id":891,"managePhone":"","mbName":"15828251216","note":"王三","operation":"","operationUrl":"","orderArea":"1","plateNum":"川B123456","regTime":"2017-05-12 15:54","runArea":0,"safeCardUrl":"","teamNo":""}]
     * renTimeList : []
     */

    private int code;
    private String context;
    private List<MyCarListBean> myCarList;
    private List<?> renTimeList;

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

    public List<MyCarListBean> getMyCarList() {
        return myCarList;
    }

    public void setMyCarList(List<MyCarListBean> myCarList) {
        this.myCarList = myCarList;
    }

    public List<?> getRenTimeList() {
        return renTimeList;
    }

    public void setRenTimeList(List<?> renTimeList) {
        this.renTimeList = renTimeList;
    }

    public static class MyCarListBean {
        /**
         * carBrand : 金龙牌
         * carCompany :
         * carGears : 2
         * carNature : 4
         * carSeats : 47
         * carState : 1
         * carType : 0
         * carYear : 2017-04-10 00:00
         * cfUrl : /rentImg/carFront_kcb_1491816429369_4438.png
         * csUrl : /rentImg/carSide_kcb_1491816429385_4439.png
         * ctUrl :
         * dcUrl :
         * disposeTime : 2017-04-10 17:28
         * dlUrl : /rentImg/drivingLicense_kcb_1491816429385_4440.png
         * driverPhone : 15828251216
         * id : 851
         * managePhone : 17828174933
         * mbName : 15828251216
         * note : 匿名
         * operation :
         * operationUrl :
         * orderArea : 成都市
         * plateNum : 川A99999
         * regTime : 2017-04-10 17:27
         * runArea : 0
         * safeCardUrl :
         * teamNo : 8112010001
         */

        private String carBrand;
        private String carCompany;
        private int carGears;
        private int carNature;
        private int carSeats;
        private int carState;
        private int carType;
        private String carYear;
        private String cfUrl;
        private String csUrl;
        private String ctUrl;
        private String dcUrl;
        private String disposeTime;
        private String dlUrl;
        private String driverPhone;
        private int id;
        private String managePhone;
        private String mbName;
        private String note;
        private String operation;
        private String operationUrl;
        private String orderArea;
        private String plateNum;
        private String regTime;
        private int runArea;
        private String safeCardUrl;
        private String teamNo;

        public String getCarBrand() {
            return carBrand;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public String getCarCompany() {
            return carCompany;
        }

        public void setCarCompany(String carCompany) {
            this.carCompany = carCompany;
        }

        public int getCarGears() {
            return carGears;
        }

        public void setCarGears(int carGears) {
            this.carGears = carGears;
        }

        public int getCarNature() {
            return carNature;
        }

        public void setCarNature(int carNature) {
            this.carNature = carNature;
        }

        public int getCarSeats() {
            return carSeats;
        }

        public void setCarSeats(int carSeats) {
            this.carSeats = carSeats;
        }

        public int getCarState() {
            return carState;
        }

        public void setCarState(int carState) {
            this.carState = carState;
        }

        public int getCarType() {
            return carType;
        }

        public void setCarType(int carType) {
            this.carType = carType;
        }

        public String getCarYear() {
            return carYear;
        }

        public void setCarYear(String carYear) {
            this.carYear = carYear;
        }

        public String getCfUrl() {
            return cfUrl;
        }

        public void setCfUrl(String cfUrl) {
            this.cfUrl = cfUrl;
        }

        public String getCsUrl() {
            return csUrl;
        }

        public void setCsUrl(String csUrl) {
            this.csUrl = csUrl;
        }

        public String getCtUrl() {
            return ctUrl;
        }

        public void setCtUrl(String ctUrl) {
            this.ctUrl = ctUrl;
        }

        public String getDcUrl() {
            return dcUrl;
        }

        public void setDcUrl(String dcUrl) {
            this.dcUrl = dcUrl;
        }

        public String getDisposeTime() {
            return disposeTime;
        }

        public void setDisposeTime(String disposeTime) {
            this.disposeTime = disposeTime;
        }

        public String getDlUrl() {
            return dlUrl;
        }

        public void setDlUrl(String dlUrl) {
            this.dlUrl = dlUrl;
        }

        public String getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getManagePhone() {
            return managePhone;
        }

        public void setManagePhone(String managePhone) {
            this.managePhone = managePhone;
        }

        public String getMbName() {
            return mbName;
        }

        public void setMbName(String mbName) {
            this.mbName = mbName;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getOperationUrl() {
            return operationUrl;
        }

        public void setOperationUrl(String operationUrl) {
            this.operationUrl = operationUrl;
        }

        public String getOrderArea() {
            return orderArea;
        }

        public void setOrderArea(String orderArea) {
            this.orderArea = orderArea;
        }

        public String getPlateNum() {
            return plateNum;
        }

        public void setPlateNum(String plateNum) {
            this.plateNum = plateNum;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public int getRunArea() {
            return runArea;
        }

        public void setRunArea(int runArea) {
            this.runArea = runArea;
        }

        public String getSafeCardUrl() {
            return safeCardUrl;
        }

        public void setSafeCardUrl(String safeCardUrl) {
            this.safeCardUrl = safeCardUrl;
        }

        public String getTeamNo() {
            return teamNo;
        }

        public void setTeamNo(String teamNo) {
            this.teamNo = teamNo;
        }
    }
}
