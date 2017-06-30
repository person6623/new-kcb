package com.kcb360.newkcb.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by xinshichao on 2017/6/21.
 */

public class OnWayOrderBean implements Parcelable{

    /**
     * others : null
     * rows : [{"addTime":{"date":1,"day":6,"hours":9,"minutes":47,"month":3,"nanos":0,"seconds":25,"time":1491011245000,"timezoneOffset":-480,"year":117},"customerPhone":"13999999999","customers":1,"departPlace":"武侯区科华南路环球中心当代艺术中心东","depositPrice":0,"destination":"环球中心E5区","driverPhone":"","id":6,"orderNum":"661491011245173","orderPrice":50,"orderState":0,"plateNum":"","realName":"sdf","remark":"","reserveOrderTime":{"date":2,"day":0,"hours":3,"minutes":0,"month":3,"nanos":0,"seconds":0,"time":1491073200000,"timezoneOffset":-480,"year":117},"teamNo":"8112010001","thanksFee":0,"type":1,"useDayEnd":{"date":2,"day":0,"hours":3,"minutes":0,"month":3,"nanos":0,"seconds":0,"time":1491073200000,"timezoneOffset":-480,"year":117},"useDayStart":{"date":2,"day":0,"hours":3,"minutes":0,"month":3,"nanos":0,"seconds":0,"time":1491073200000,"timezoneOffset":-480,"year":117}},{"addTime":{"date":1,"day":6,"hours":9,"minutes":46,"month":3,"nanos":0,"seconds":13,"time":1491011173000,"timezoneOffset":-480,"year":117},"customerPhone":"13999999999","customers":1,"departPlace":"武侯区新世纪环球购物中心","depositPrice":0,"destination":"成都市委","driverPhone":"","id":5,"orderNum":"661491011173858","orderPrice":50,"orderState":0,"plateNum":"","realName":"sdf","remark":"","reserveOrderTime":{"date":5,"day":3,"hours":4,"minutes":0,"month":3,"nanos":0,"seconds":0,"time":1491336000000,"timezoneOffset":-480,"year":117},"teamNo":"8112010001","thanksFee":0,"type":0,"useDayEnd":{"date":2,"day":0,"hours":3,"minutes":0,"month":3,"nanos":0,"seconds":0,"time":1491073200000,"timezoneOffset":-480,"year":117},"useDayStart":{"date":2,"day":0,"hours":3,"minutes":0,"month":3,"nanos":0,"seconds":0,"time":1491073200000,"timezoneOffset":-480,"year":117}}]
     * total : 2
     */

    private String others;
    private int total;
    private List<RowsBean> rows;


    protected OnWayOrderBean(Parcel in) {
        others = in.readString();
        total = in.readInt();
        rows = in.createTypedArrayList(RowsBean.CREATOR);
    }

    public static final Creator<OnWayOrderBean> CREATOR = new Creator<OnWayOrderBean>() {
        @Override
        public OnWayOrderBean createFromParcel(Parcel in) {
            return new OnWayOrderBean(in);
        }

        @Override
        public OnWayOrderBean[] newArray(int size) {
            return new OnWayOrderBean[size];
        }
    };

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(others);
        dest.writeInt(total);
        dest.writeTypedList(rows);
    }


    public static class RowsBean implements Parcelable {
        /**
         * addTime : {"date":1,"day":6,"hours":9,"minutes":47,"month":3,"nanos":0,"seconds":25,"time":1491011245000,"timezoneOffset":-480,"year":117}
         * customerPhone : 13999999999
         * customers : 1
         * departPlace : 武侯区科华南路环球中心当代艺术中心东
         * depositPrice : 0
         * destination : 环球中心E5区
         * driverPhone :
         * id : 6
         * orderNum : 661491011245173
         * orderPrice : 50
         * orderState : 0
         * plateNum :
         * realName : sdf
         * remark :
         * reserveOrderTime : {"date":2,"day":0,"hours":3,"minutes":0,"month":3,"nanos":0,"seconds":0,"time":1491073200000,"timezoneOffset":-480,"year":117}
         * teamNo : 8112010001
         * thanksFee : 0
         * type : 1
         * useDayEnd : {"date":2,"day":0,"hours":3,"minutes":0,"month":3,"nanos":0,"seconds":0,"time":1491073200000,"timezoneOffset":-480,"year":117}
         * useDayStart : {"date":2,"day":0,"hours":3,"minutes":0,"month":3,"nanos":0,"seconds":0,"time":1491073200000,"timezoneOffset":-480,"year":117}
         */

        private AddTimeBean addTime;
        private String customerPhone;
        private int customers;
        private String departPlace;
        private float depositPrice;
        private String destination;
        private String driverPhone;
        private String lonAndLat;
        private int id;
        private String orderNum;
        private float orderPrice;
        private int orderState;
        private String plateNum;
        private String realName;
        private String remark;
        private ReserveOrderTimeBean reserveOrderTime;
        private String teamNo;
        private float thanksFee;
        private int type;
        private UseDayEndBean useDayEnd;
        private UseDayStartBean useDayStart;

        protected RowsBean(Parcel in) {
            addTime = in.readParcelable(AddTimeBean.class.getClassLoader());
            customerPhone = in.readString();
            customers = in.readInt();
            departPlace = in.readString();
            depositPrice = in.readFloat();
            destination = in.readString();
            driverPhone = in.readString();
            lonAndLat = in.readString();
            id = in.readInt();
            orderNum = in.readString();
            orderPrice = in.readFloat();
            orderState = in.readInt();
            plateNum = in.readString();
            realName = in.readString();
            remark = in.readString();
            reserveOrderTime = in.readParcelable(ReserveOrderTimeBean.class.getClassLoader());
            teamNo = in.readString();
            thanksFee = in.readFloat();
            type = in.readInt();
            useDayEnd = in.readParcelable(UseDayEndBean.class.getClassLoader());
            useDayStart = in.readParcelable(UseDayStartBean.class.getClassLoader());
        }

        public static final Creator<RowsBean> CREATOR = new Creator<RowsBean>() {
            @Override
            public RowsBean createFromParcel(Parcel in) {
                return new RowsBean(in);
            }

            @Override
            public RowsBean[] newArray(int size) {
                return new RowsBean[size];
            }
        };

        public String getLonAndLat() {
            return lonAndLat;
        }

        public void setLonAndLat(String lonAndLat) {
            this.lonAndLat = lonAndLat;
        }

        public AddTimeBean getAddTime() {
            return addTime;
        }

        public void setAddTime(AddTimeBean addTime) {
            this.addTime = addTime;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public int getCustomers() {
            return customers;
        }

        public void setCustomers(int customers) {
            this.customers = customers;
        }

        public String getDepartPlace() {
            return departPlace;
        }

        public void setDepartPlace(String departPlace) {
            this.departPlace = departPlace;
        }

        public float getDepositPrice() {
            return depositPrice;
        }

        public void setDepositPrice(float depositPrice) {
            this.depositPrice = depositPrice;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
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

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public float getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(float orderPrice) {
            this.orderPrice = orderPrice;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public String getPlateNum() {
            return plateNum;
        }

        public void setPlateNum(String plateNum) {
            this.plateNum = plateNum;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public ReserveOrderTimeBean getReserveOrderTime() {
            return reserveOrderTime;
        }

        public void setReserveOrderTime(ReserveOrderTimeBean reserveOrderTime) {
            this.reserveOrderTime = reserveOrderTime;
        }

        public String getTeamNo() {
            return teamNo;
        }

        public void setTeamNo(String teamNo) {
            this.teamNo = teamNo;
        }

        public float getThanksFee() {
            return thanksFee;
        }

        public void setThanksFee(float thanksFee) {
            this.thanksFee = thanksFee;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public UseDayEndBean getUseDayEnd() {
            return useDayEnd;
        }

        public void setUseDayEnd(UseDayEndBean useDayEnd) {
            this.useDayEnd = useDayEnd;
        }

        public UseDayStartBean getUseDayStart() {
            return useDayStart;
        }

        public void setUseDayStart(UseDayStartBean useDayStart) {
            this.useDayStart = useDayStart;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(addTime, flags);
            dest.writeString(customerPhone);
            dest.writeInt(customers);
            dest.writeString(departPlace);
            dest.writeFloat(depositPrice);
            dest.writeString(destination);
            dest.writeString(driverPhone);
            dest.writeString(lonAndLat);
            dest.writeInt(id);
            dest.writeString(orderNum);
            dest.writeFloat(orderPrice);
            dest.writeInt(orderState);
            dest.writeString(plateNum);
            dest.writeString(realName);
            dest.writeString(remark);
            dest.writeParcelable(reserveOrderTime, flags);
            dest.writeString(teamNo);
            dest.writeFloat(thanksFee);
            dest.writeInt(type);
            dest.writeParcelable(useDayEnd, flags);
            dest.writeParcelable(useDayStart, flags);
        }

        public static class AddTimeBean implements Parcelable {
            /**
             * date : 1
             * day : 6
             * hours : 9
             * minutes : 47
             * month : 3
             * nanos : 0
             * seconds : 25
             * time : 1491011245000
             * timezoneOffset : -480
             * year : 117
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            protected AddTimeBean(Parcel in) {
                date = in.readInt();
                day = in.readInt();
                hours = in.readInt();
                minutes = in.readInt();
                month = in.readInt();
                nanos = in.readInt();
                seconds = in.readInt();
                time = in.readLong();
                timezoneOffset = in.readInt();
                year = in.readInt();
            }

            public static final Creator<AddTimeBean> CREATOR = new Creator<AddTimeBean>() {
                @Override
                public AddTimeBean createFromParcel(Parcel in) {
                    return new AddTimeBean(in);
                }

                @Override
                public AddTimeBean[] newArray(int size) {
                    return new AddTimeBean[size];
                }
            };

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(date);
                dest.writeInt(day);
                dest.writeInt(hours);
                dest.writeInt(minutes);
                dest.writeInt(month);
                dest.writeInt(nanos);
                dest.writeInt(seconds);
                dest.writeLong(time);
                dest.writeInt(timezoneOffset);
                dest.writeInt(year);
            }
        }

        public static class ReserveOrderTimeBean implements Parcelable {
            /**
             * date : 2
             * day : 0
             * hours : 3
             * minutes : 0
             * month : 3
             * nanos : 0
             * seconds : 0
             * time : 1491073200000
             * timezoneOffset : -480
             * year : 117
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            protected ReserveOrderTimeBean(Parcel in) {
                date = in.readInt();
                day = in.readInt();
                hours = in.readInt();
                minutes = in.readInt();
                month = in.readInt();
                nanos = in.readInt();
                seconds = in.readInt();
                time = in.readLong();
                timezoneOffset = in.readInt();
                year = in.readInt();
            }

            public static final Creator<ReserveOrderTimeBean> CREATOR = new Creator<ReserveOrderTimeBean>() {
                @Override
                public ReserveOrderTimeBean createFromParcel(Parcel in) {
                    return new ReserveOrderTimeBean(in);
                }

                @Override
                public ReserveOrderTimeBean[] newArray(int size) {
                    return new ReserveOrderTimeBean[size];
                }
            };

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(date);
                dest.writeInt(day);
                dest.writeInt(hours);
                dest.writeInt(minutes);
                dest.writeInt(month);
                dest.writeInt(nanos);
                dest.writeInt(seconds);
                dest.writeLong(time);
                dest.writeInt(timezoneOffset);
                dest.writeInt(year);
            }
        }

        public static class UseDayEndBean implements Parcelable {
            /**
             * date : 2
             * day : 0
             * hours : 3
             * minutes : 0
             * month : 3
             * nanos : 0
             * seconds : 0
             * time : 1491073200000
             * timezoneOffset : -480
             * year : 117
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            protected UseDayEndBean(Parcel in) {
                date = in.readInt();
                day = in.readInt();
                hours = in.readInt();
                minutes = in.readInt();
                month = in.readInt();
                nanos = in.readInt();
                seconds = in.readInt();
                time = in.readLong();
                timezoneOffset = in.readInt();
                year = in.readInt();
            }

            public static final Creator<UseDayEndBean> CREATOR = new Creator<UseDayEndBean>() {
                @Override
                public UseDayEndBean createFromParcel(Parcel in) {
                    return new UseDayEndBean(in);
                }

                @Override
                public UseDayEndBean[] newArray(int size) {
                    return new UseDayEndBean[size];
                }
            };

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(date);
                dest.writeInt(day);
                dest.writeInt(hours);
                dest.writeInt(minutes);
                dest.writeInt(month);
                dest.writeInt(nanos);
                dest.writeInt(seconds);
                dest.writeLong(time);
                dest.writeInt(timezoneOffset);
                dest.writeInt(year);
            }
        }

        public static class UseDayStartBean implements Parcelable {
            /**
             * date : 2
             * day : 0
             * hours : 3
             * minutes : 0
             * month : 3
             * nanos : 0
             * seconds : 0
             * time : 1491073200000
             * timezoneOffset : -480
             * year : 117
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            protected UseDayStartBean(Parcel in) {
                date = in.readInt();
                day = in.readInt();
                hours = in.readInt();
                minutes = in.readInt();
                month = in.readInt();
                nanos = in.readInt();
                seconds = in.readInt();
                time = in.readLong();
                timezoneOffset = in.readInt();
                year = in.readInt();
            }

            public static final Creator<UseDayStartBean> CREATOR = new Creator<UseDayStartBean>() {
                @Override
                public UseDayStartBean createFromParcel(Parcel in) {
                    return new UseDayStartBean(in);
                }

                @Override
                public UseDayStartBean[] newArray(int size) {
                    return new UseDayStartBean[size];
                }
            };

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(date);
                dest.writeInt(day);
                dest.writeInt(hours);
                dest.writeInt(minutes);
                dest.writeInt(month);
                dest.writeInt(nanos);
                dest.writeInt(seconds);
                dest.writeLong(time);
                dest.writeInt(timezoneOffset);
                dest.writeInt(year);
            }
        }
    }
}
