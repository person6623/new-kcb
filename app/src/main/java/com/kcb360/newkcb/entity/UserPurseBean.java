package com.kcb360.newkcb.entity;

/**
 * Created by xinshichao on 2017/4/26.
 * <p>
 * 用户钱包
 */

public class UserPurseBean {


    /**
     * code : 1
     * context :
     * memberWallet : {"atime":"2017-02-21 20:31","cName":"15828251216","cashBalance":0,"cashPledge":0,"consumeMoney":0,"dividend":0,"dividendMoney":0,"id":2109,"lookEtimeAgent":"","lookEtimeDriver":"","lookStimeAgent":"","lookStimeDriver":"","notFinish":0,"presentBalance":0,"residualMoney":0,"royalty":0}
     */

    private int code;
    private String context;
    private MemberWalletBean memberWallet;

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

    public MemberWalletBean getMemberWallet() {
        return memberWallet;
    }

    public void setMemberWallet(MemberWalletBean memberWallet) {
        this.memberWallet = memberWallet;
    }

    public static class MemberWalletBean {
        /**
         * atime : 2017-02-21 20:31
         * cName : 15828251216
         * cashBalance : 0
         * cashPledge : 0
         * consumeMoney : 0
         * dividend : 0
         * dividendMoney : 0
         * id : 2109
         * lookEtimeAgent :
         * lookEtimeDriver :
         * lookStimeAgent :
         * lookStimeDriver :
         * notFinish : 0
         * presentBalance : 0
         * residualMoney : 0
         * royalty : 0
         */

        private String atime;
        private String cName;
        private float cashBalance;
        private float cashPledge;
        private float consumeMoney;
        private float dividend;
        private float dividendMoney;
        private int id;
        private String lookEtimeAgent;
        private String lookEtimeDriver;
        private String lookStimeAgent;
        private String lookStimeDriver;
        private float notFinish;
        private float presentBalance;
        private float residualMoney;
        private float royalty;

        public String getAtime() {
            return atime;
        }

        public void setAtime(String atime) {
            this.atime = atime;
        }

        public String getCName() {
            return cName;
        }

        public void setCName(String cName) {
            this.cName = cName;
        }

        public float getCashBalance() {
            return cashBalance;
        }

        public void setCashBalance(float cashBalance) {
            this.cashBalance = cashBalance;
        }

        public float getCashPledge() {
            return cashPledge;
        }

        public void setCashPledge(float cashPledge) {
            this.cashPledge = cashPledge;
        }

        public float getConsumeMoney() {
            return consumeMoney;
        }

        public void setConsumeMoney(float consumeMoney) {
            this.consumeMoney = consumeMoney;
        }

        public float getDividend() {
            return dividend;
        }

        public void setDividend(float dividend) {
            this.dividend = dividend;
        }

        public float getDividendMoney() {
            return dividendMoney;
        }

        public void setDividendMoney(float dividendMoney) {
            this.dividendMoney = dividendMoney;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLookEtimeAgent() {
            return lookEtimeAgent;
        }

        public void setLookEtimeAgent(String lookEtimeAgent) {
            this.lookEtimeAgent = lookEtimeAgent;
        }

        public String getLookEtimeDriver() {
            return lookEtimeDriver;
        }

        public void setLookEtimeDriver(String lookEtimeDriver) {
            this.lookEtimeDriver = lookEtimeDriver;
        }

        public String getLookStimeAgent() {
            return lookStimeAgent;
        }

        public void setLookStimeAgent(String lookStimeAgent) {
            this.lookStimeAgent = lookStimeAgent;
        }

        public String getLookStimeDriver() {
            return lookStimeDriver;
        }

        public void setLookStimeDriver(String lookStimeDriver) {
            this.lookStimeDriver = lookStimeDriver;
        }

        public float getNotFinish() {
            return notFinish;
        }

        public void setNotFinish(float notFinish) {
            this.notFinish = notFinish;
        }

        public float getPresentBalance() {
            return presentBalance;
        }

        public void setPresentBalance(float presentBalance) {
            this.presentBalance = presentBalance;
        }

        public float getResidualMoney() {
            return residualMoney;
        }

        public void setResidualMoney(float residualMoney) {
            this.residualMoney = residualMoney;
        }

        public float getRoyalty() {
            return royalty;
        }

        public void setRoyalty(float royalty) {
            this.royalty = royalty;
        }
    }
}
