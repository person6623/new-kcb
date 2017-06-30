package com.kcb360.newkcb.entity;

import java.util.List;

/**
 * Created by xinshichao on 2017/5/31.
 */

public class LoginBean {

    /**
     * code : 1
     * context : 登录成功！
     * teamInfo : {"addTime":"2016-10-15 20:20","carCount":14,"carSeats":1129,"cashDeposit":10000,"id":1,"levelId":3,"linkMan":"小高","linkPhone":"17828174933","managerName":"17828028717","note":"同意申请成为：明星车队","payWay":1,"secondUrl":"","teamLevel":"明星车队","teamName":"客车帮车队","teamNo":"8112010001","teamNotice":"商旅客车帮","teamPosition":"四川省-成都市-武侯区-天府二街云华路333号","teamQq":"200889651","teamType":"2-6|7-14|15-29|30-55"}
     * seats : 0
     * customer : {"actRole":4,"carCount":0,"companyName":"","cusReadCount":1,"driverReadCount":1,"id":1562,"isAc":2,"openId":"","phone":"17828028717","realName":"杜伟","recName":"18982208376","regArea":"","regWay":"back","seats":0,"teamLeader":"8112010001"}
     */

    private String code;
    private String context;
    private TeamInfoBean teamInfo;
    private List<String> seats;
    private CustomerBean customer;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public TeamInfoBean getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(TeamInfoBean teamInfo) {
        this.teamInfo = teamInfo;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public static class TeamInfoBean {
        /**
         * addTime : 2016-10-15 20:20
         * carCount : 14
         * carSeats : 1129
         * cashDeposit : 10000
         * id : 1
         * levelId : 3
         * linkMan : 小高
         * linkPhone : 17828174933
         * managerName : 17828028717
         * note : 同意申请成为：明星车队
         * payWay : 1
         * secondUrl :
         * teamLevel : 明星车队
         * teamName : 客车帮车队
         * teamNo : 8112010001
         * teamNotice : 商旅客车帮
         * teamPosition : 四川省-成都市-武侯区-天府二街云华路333号
         * teamQq : 200889651
         * teamType : 2-6|7-14|15-29|30-55
         */

        private String addTime;
        private int carCount;
        private int carSeats;
        private int cashDeposit;
        private int id;
        private int levelId;
        private String linkMan;
        private String linkPhone;
        private String managerName;
        private String note;
        private int payWay;
        private String secondUrl;
        private String teamLevel;
        private String teamName;
        private String teamNo;
        private String teamNotice;
        private String teamPosition;
        private String teamQq;
        private String teamType;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getCarCount() {
            return carCount;
        }

        public void setCarCount(int carCount) {
            this.carCount = carCount;
        }

        public int getCarSeats() {
            return carSeats;
        }

        public void setCarSeats(int carSeats) {
            this.carSeats = carSeats;
        }

        public int getCashDeposit() {
            return cashDeposit;
        }

        public void setCashDeposit(int cashDeposit) {
            this.cashDeposit = cashDeposit;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevelId() {
            return levelId;
        }

        public void setLevelId(int levelId) {
            this.levelId = levelId;
        }

        public String getLinkMan() {
            return linkMan;
        }

        public void setLinkMan(String linkMan) {
            this.linkMan = linkMan;
        }

        public String getLinkPhone() {
            return linkPhone;
        }

        public void setLinkPhone(String linkPhone) {
            this.linkPhone = linkPhone;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getPayWay() {
            return payWay;
        }

        public void setPayWay(int payWay) {
            this.payWay = payWay;
        }

        public String getSecondUrl() {
            return secondUrl;
        }

        public void setSecondUrl(String secondUrl) {
            this.secondUrl = secondUrl;
        }

        public String getTeamLevel() {
            return teamLevel;
        }

        public void setTeamLevel(String teamLevel) {
            this.teamLevel = teamLevel;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getTeamNo() {
            return teamNo;
        }

        public void setTeamNo(String teamNo) {
            this.teamNo = teamNo;
        }

        public String getTeamNotice() {
            return teamNotice;
        }

        public void setTeamNotice(String teamNotice) {
            this.teamNotice = teamNotice;
        }

        public String getTeamPosition() {
            return teamPosition;
        }

        public void setTeamPosition(String teamPosition) {
            this.teamPosition = teamPosition;
        }

        public String getTeamQq() {
            return teamQq;
        }

        public void setTeamQq(String teamQq) {
            this.teamQq = teamQq;
        }

        public String getTeamType() {
            return teamType;
        }

        public void setTeamType(String teamType) {
            this.teamType = teamType;
        }
    }

    public static class CustomerBean {
        /**
         * actRole : 4
         * carCount : 0
         * companyName :
         * cusReadCount : 1
         * driverReadCount : 1
         * id : 1562
         * isAc : 2
         * openId :
         * phone : 17828028717
         * realName : 杜伟
         * recName : 18982208376
         * regArea :
         * regWay : back
         * seats : 0
         * teamLeader : 8112010001
         */

        private int actRole;
        private int carCount;
        private String companyName;
        private int cusReadCount;
        private int driverReadCount;
        private int id;
        private int isAc;
        private String openId;
        private String phone;
        private String realName;
        private String recName;
        private String regArea;
        private String regWay;
        private int seats;
        private String teamLeader;

        public int getActRole() {
            return actRole;
        }

        public void setActRole(int actRole) {
            this.actRole = actRole;
        }

        public int getCarCount() {
            return carCount;
        }

        public void setCarCount(int carCount) {
            this.carCount = carCount;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getCusReadCount() {
            return cusReadCount;
        }

        public void setCusReadCount(int cusReadCount) {
            this.cusReadCount = cusReadCount;
        }

        public int getDriverReadCount() {
            return driverReadCount;
        }

        public void setDriverReadCount(int driverReadCount) {
            this.driverReadCount = driverReadCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsAc() {
            return isAc;
        }

        public void setIsAc(int isAc) {
            this.isAc = isAc;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRecName() {
            return recName;
        }

        public void setRecName(String recName) {
            this.recName = recName;
        }

        public String getRegArea() {
            return regArea;
        }

        public void setRegArea(String regArea) {
            this.regArea = regArea;
        }

        public String getRegWay() {
            return regWay;
        }

        public void setRegWay(String regWay) {
            this.regWay = regWay;
        }

        public int getSeats() {
            return seats;
        }

        public void setSeats(int seats) {
            this.seats = seats;
        }

        public String getTeamLeader() {
            return teamLeader;
        }

        public void setTeamLeader(String teamLeader) {
            this.teamLeader = teamLeader;
        }
    }
}
