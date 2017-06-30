package com.kcb360.newkcb.entity;

import java.util.List;

/**
 * Created by xinshichao on 2017/5/9.
 * <p>
 * 提现
 */

public class WithDrawCashBean {

    /**
     * code : 1
     * context : 查询成功！
     * drawList : [{"accountNumber":"6236683810000142554","afterDrawBalance":6.85,"atime":"2017-05-09 14:45","bankAddress":"","bankDisposeTime":"","bankName":"建设银行","bindName":"杜伟","clientNum":"861494312328197","disposeTime":"","drawMoney":1,"id":8,"note":"【余额提现】","recBankNo":"105100000017","state":0}]
     */

    private int code;
    private String context;
    private List<DrawListBean> drawList;

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

    public List<DrawListBean> getDrawList() {
        return drawList;
    }

    public void setDrawList(List<DrawListBean> drawList) {
        this.drawList = drawList;
    }

    public static class DrawListBean {
        /**
         * accountNumber : 6236683810000142554
         * afterDrawBalance : 6.85
         * atime : 2017-05-09 14:45
         * bankAddress :
         * bankDisposeTime :
         * bankName : 建设银行
         * bindName : 杜伟
         * clientNum : 861494312328197
         * disposeTime :
         * drawMoney : 1
         * id : 8
         * note : 【余额提现】
         * recBankNo : 105100000017
         * state : 0
         */

        private String accountNumber;
        private float afterDrawBalance;
        private String atime;
        private String bankAddress;
        private String bankDisposeTime;
        private String bankName;
        private String bindName;
        private String clientNum;
        private String disposeTime;
        private float drawMoney;
        private int id;
        private String note;
        private String recBankNo;
        private int state;

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public float getAfterDrawBalance() {
            return afterDrawBalance;
        }

        public void setAfterDrawBalance(float afterDrawBalance) {
            this.afterDrawBalance = afterDrawBalance;
        }

        public String getAtime() {
            return atime;
        }

        public void setAtime(String atime) {
            this.atime = atime;
        }

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

        public String getBankDisposeTime() {
            return bankDisposeTime;
        }

        public void setBankDisposeTime(String bankDisposeTime) {
            this.bankDisposeTime = bankDisposeTime;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBindName() {
            return bindName;
        }

        public void setBindName(String bindName) {
            this.bindName = bindName;
        }

        public String getClientNum() {
            return clientNum;
        }

        public void setClientNum(String clientNum) {
            this.clientNum = clientNum;
        }

        public String getDisposeTime() {
            return disposeTime;
        }

        public void setDisposeTime(String disposeTime) {
            this.disposeTime = disposeTime;
        }

        public float getDrawMoney() {
            return drawMoney;
        }

        public void setDrawMoney(float drawMoney) {
            this.drawMoney = drawMoney;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getRecBankNo() {
            return recBankNo;
        }

        public void setRecBankNo(String recBankNo) {
            this.recBankNo = recBankNo;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
