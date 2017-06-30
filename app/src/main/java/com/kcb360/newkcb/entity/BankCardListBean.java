package com.kcb360.newkcb.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by xinshichao on 2017/5/3.
 */

public class BankCardListBean implements Parcelable{

    /**
     * code : 1
     * context : 获取成功
     * bankList : [{"accountNumber":"1234567890","atime":"2017-04-27 17:55","bankAddress":"成都市","bankName":"成都银行","bindName":"测试","bindPhone":"123456789","id":14,"recBankNo":"20"},{"accountNumber":"123456789000000","atime":"2017-04-27 17:56","bankAddress":"成都市","bankName":"招商银行","bindName":"测试2","bindPhone":"123456","id":15,"recBankNo":"20"}]
     */

    private int code;
    private String context;
    private List<BankListBean> bankList;

    protected BankCardListBean(Parcel in) {
        code = in.readInt();
        context = in.readString();
        bankList = in.createTypedArrayList(BankListBean.CREATOR);
    }

    public static final Creator<BankCardListBean> CREATOR = new Creator<BankCardListBean>() {
        @Override
        public BankCardListBean createFromParcel(Parcel in) {
            return new BankCardListBean(in);
        }

        @Override
        public BankCardListBean[] newArray(int size) {
            return new BankCardListBean[size];
        }
    };

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

    public List<BankListBean> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankListBean> bankList) {
        this.bankList = bankList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(context);
        dest.writeTypedList(bankList);
    }

    public static class BankListBean implements Parcelable{
        /**
         * accountNumber : 1234567890
         * atime : 2017-04-27 17:55
         * bankAddress : 成都市
         * bankName : 成都银行
         * bindName : 测试
         * bindPhone : 123456789
         * id : 14
         * recBankNo : 20
         */

        private String accountNumber;
        private String atime;
        private String bankAddress;
        private String bankName;
        private String bindName;
        private String bindPhone;
        private int id;
        private String recBankNo;

        protected BankListBean(Parcel in) {
            accountNumber = in.readString();
            atime = in.readString();
            bankAddress = in.readString();
            bankName = in.readString();
            bindName = in.readString();
            bindPhone = in.readString();
            id = in.readInt();
            recBankNo = in.readString();
        }

        public static final Creator<BankListBean> CREATOR = new Creator<BankListBean>() {
            @Override
            public BankListBean createFromParcel(Parcel in) {
                return new BankListBean(in);
            }

            @Override
            public BankListBean[] newArray(int size) {
                return new BankListBean[size];
            }
        };

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
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

        public String getBindPhone() {
            return bindPhone;
        }

        public void setBindPhone(String bindPhone) {
            this.bindPhone = bindPhone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRecBankNo() {
            return recBankNo;
        }

        public void setRecBankNo(String recBankNo) {
            this.recBankNo = recBankNo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(accountNumber);
            dest.writeString(atime);
            dest.writeString(bankAddress);
            dest.writeString(bankName);
            dest.writeString(bindName);
            dest.writeString(bindPhone);
            dest.writeInt(id);
            dest.writeString(recBankNo);
        }
    }
}
