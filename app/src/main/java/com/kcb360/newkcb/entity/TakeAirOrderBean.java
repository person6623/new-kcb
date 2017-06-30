package com.kcb360.newkcb.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by xinshichao on 2017/6/21.
 */

public class TakeAirOrderBean implements Parcelable {

    /**
     * others : 0
     * rows : [{"askTime":{"date":19,"day":4,"hours":17,"minutes":10,"month":0,"nanos":0,"seconds":33,"time":1484817033000,"timezoneOffset":-480,"year":117},"customerPhone":"13244354355","customers":2,"departPlace":"双流机场1号楼","destination":"成都市崇州市崇州ipc枢纽大楼","driver":"","driverPhone":"","flightNum":"139999","flyOrDownTime":{"date":19,"day":4,"hours":17,"minutes":8,"month":0,"nanos":0,"seconds":0,"time":1484816880000,"timezoneOffset":-480,"year":117},"id":62,"isShuttle":0,"lonAndLat":"103.724544|30.620711","mbName":"13999999999","orderHandSelPrice":0,"orderNum":"861484817033791","orderPrice":68,"orderState":0,"plateNum":"川AH6195","realName":"ur n","reserveOrderTime":{"date":19,"day":4,"hours":17,"minutes":8,"month":0,"nanos":0,"seconds":0,"time":1484816880000,"timezoneOffset":-480,"year":117},"routeType":1,"teamNo":"8112010001","useDayEnd":null,"useDayStart":null},{"askTime":{"date":19,"day":4,"hours":16,"minutes":35,"month":0,"nanos":0,"seconds":32,"time":1484814932000,"timezoneOffset":-480,"year":117},"customerPhone":"15464566456","customers":3,"departPlace":"双流机场1号楼","destination":"成都市崇州市崇州ipc枢纽大楼","driver":"","driverPhone":"","flightNum":"446564","flyOrDownTime":{"date":19,"day":4,"hours":16,"minutes":29,"month":0,"nanos":0,"seconds":0,"time":1484814540000,"timezoneOffset":-480,"year":117},"id":61,"isShuttle":0,"lonAndLat":"103.724544|30.620711","mbName":"13999999999","orderHandSelPrice":0,"orderNum":"861484814932240","orderPrice":86,"orderState":0,"plateNum":"川AH6195","realName":"123456","reserveOrderTime":{"date":19,"day":4,"hours":16,"minutes":33,"month":0,"nanos":0,"seconds":0,"time":1484814780000,"timezoneOffset":-480,"year":117},"routeType":1,"teamNo":"8112010001","useDayEnd":null,"useDayStart":null}]
     * total : 2
     */

    private String others;
    private String total;
    private List<RowsBean> rows;

    protected TakeAirOrderBean(Parcel in) {
        others = in.readString();
        total = in.readString();
        rows = in.createTypedArrayList(RowsBean.CREATOR);
    }

    public static final Parcelable.Creator<TakeAirOrderBean> CREATOR = new Parcelable.Creator<TakeAirOrderBean>() {
        @Override
        public TakeAirOrderBean createFromParcel(Parcel in) {
            return new TakeAirOrderBean(in);
        }

        @Override
        public TakeAirOrderBean[] newArray(int size) {
            return new TakeAirOrderBean[size];
        }
    };

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
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
        dest.writeString(total);
        dest.writeTypedList(rows);
    }

    public static class RowsBean implements Parcelable {
        /**
         * askTime : {"date":19,"day":4,"hours":17,"minutes":10,"month":0,"nanos":0,"seconds":33,"time":1484817033000,"timezoneOffset":-480,"year":117}
         * customerPhone : 13244354355
         * customers : 2
         * departPlace : 双流机场1号楼
         * destination : 成都市崇州市崇州ipc枢纽大楼
         * driver :
         * driverPhone :
         * flightNum : 139999
         * flyOrDownTime : {"date":19,"day":4,"hours":17,"minutes":8,"month":0,"nanos":0,"seconds":0,"time":1484816880000,"timezoneOffset":-480,"year":117}
         * id : 62
         * isShuttle : 0
         * lonAndLat : 103.724544|30.620711
         * mbName : 13999999999
         * orderHandSelPrice : 0
         * orderNum : 861484817033791
         * orderPrice : 68
         * orderState : 0
         * plateNum : 川AH6195
         * realName : ur n
         * reserveOrderTime : {"date":19,"day":4,"hours":17,"minutes":8,"month":0,"nanos":0,"seconds":0,"time":1484816880000,"timezoneOffset":-480,"year":117}
         * routeType : 1
         * teamNo : 8112010001
         * useDayEnd : null
         * useDayStart : null
         */

        private AskTimeBean askTime;
        private String customerPhone;
        private String customers;
        private String departPlace;
        private String destination;
        private String driver;
        private String driverPhone;
        private String flightNum;
        private FlyOrDownTimeBean flyOrDownTime;
        private String id;
        private String isShuttle;
        private String lonAndLat;
        private String mbName;
        private String orderHandSelPrice;
        private String orderNum;
        private String orderPrice;
        private String orderState;
        private String plateNum;
        private String realName;
        private ReserveOrderTimeBean reserveOrderTime;
        private String routeType;
        private String teamNo;
        private UseDayEndBean useDayEnd;
        private UseDayStartBean useDayStart;

        protected RowsBean(Parcel in) {
            askTime = in.readParcelable(AskTimeBean.class.getClassLoader());
            customerPhone = in.readString();
            customers = in.readString();
            departPlace = in.readString();
            destination = in.readString();
            driver = in.readString();
            driverPhone = in.readString();
            flightNum = in.readString();
            flyOrDownTime = in.readParcelable(FlyOrDownTimeBean.class.getClassLoader());
            id = in.readString();
            isShuttle = in.readString();
            lonAndLat = in.readString();
            mbName = in.readString();
            orderHandSelPrice = in.readString();
            orderNum = in.readString();
            orderPrice = in.readString();
            orderState = in.readString();
            plateNum = in.readString();
            realName = in.readString();
            reserveOrderTime = in.readParcelable(ReserveOrderTimeBean.class.getClassLoader());
            routeType = in.readString();
            teamNo = in.readString();
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

        public AskTimeBean getAskTime() {
            return askTime;
        }

        public void setAskTime(AskTimeBean askTime) {
            this.askTime = askTime;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public String getCustomers() {
            return customers;
        }

        public void setCustomers(String customers) {
            this.customers = customers;
        }

        public String getDepartPlace() {
            return departPlace;
        }

        public void setDepartPlace(String departPlace) {
            this.departPlace = departPlace;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

        public String getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
        }

        public String getFlightNum() {
            return flightNum;
        }

        public void setFlightNum(String flightNum) {
            this.flightNum = flightNum;
        }

        public FlyOrDownTimeBean getFlyOrDownTime() {
            return flyOrDownTime;
        }

        public void setFlyOrDownTime(FlyOrDownTimeBean flyOrDownTime) {
            this.flyOrDownTime = flyOrDownTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsShuttle() {
            return isShuttle;
        }

        public void setIsShuttle(String isShuttle) {
            this.isShuttle = isShuttle;
        }

        public String getLonAndLat() {
            return lonAndLat;
        }

        public void setLonAndLat(String lonAndLat) {
            this.lonAndLat = lonAndLat;
        }

        public String getMbName() {
            return mbName;
        }

        public void setMbName(String mbName) {
            this.mbName = mbName;
        }

        public String getOrderHandSelPrice() {
            return orderHandSelPrice;
        }

        public void setOrderHandSelPrice(String orderHandSelPrice) {
            this.orderHandSelPrice = orderHandSelPrice;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getOrderState() {
            return orderState;
        }

        public void setOrderState(String orderState) {
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

        public ReserveOrderTimeBean getReserveOrderTime() {
            return reserveOrderTime;
        }

        public void setReserveOrderTime(ReserveOrderTimeBean reserveOrderTime) {
            this.reserveOrderTime = reserveOrderTime;
        }

        public String getRouteType() {
            return routeType;
        }

        public void setRouteType(String routeType) {
            this.routeType = routeType;
        }

        public String getTeamNo() {
            return teamNo;
        }

        public void setTeamNo(String teamNo) {
            this.teamNo = teamNo;
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
            dest.writeParcelable(askTime, flags);
            dest.writeString(customerPhone);
            dest.writeString(customers);
            dest.writeString(departPlace);
            dest.writeString(destination);
            dest.writeString(driver);
            dest.writeString(driverPhone);
            dest.writeString(flightNum);
            dest.writeParcelable(flyOrDownTime, flags);
            dest.writeString(id);
            dest.writeString(isShuttle);
            dest.writeString(lonAndLat);
            dest.writeString(mbName);
            dest.writeString(orderHandSelPrice);
            dest.writeString(orderNum);
            dest.writeString(orderPrice);
            dest.writeString(orderState);
            dest.writeString(plateNum);
            dest.writeString(realName);
            dest.writeParcelable(reserveOrderTime, flags);
            dest.writeString(routeType);
            dest.writeString(teamNo);
            dest.writeParcelable(useDayEnd, flags);
            dest.writeParcelable(useDayStart, flags);
        }

        public static class AskTimeBean implements Parcelable {
            /**
             * date : 19
             * day : 4
             * hours : 17
             * minutes : 10
             * month : 0
             * nanos : 0
             * seconds : 33
             * time : 1484817033000
             * timezoneOffset : -480
             * year : 117
             */

            private String date;
            private String day;
            private String hours;
            private String minutes;
            private String month;
            private String nanos;
            private String seconds;
            private String time;
            private String timezoneOffset;
            private String year;

            protected AskTimeBean(Parcel in) {
                date = in.readString();
                day = in.readString();
                hours = in.readString();
                minutes = in.readString();
                month = in.readString();
                nanos = in.readString();
                seconds = in.readString();
                time = in.readString();
                timezoneOffset = in.readString();
                year = in.readString();
            }

            public static final Creator<AskTimeBean> CREATOR = new Creator<AskTimeBean>() {
                @Override
                public AskTimeBean createFromParcel(Parcel in) {
                    return new AskTimeBean(in);
                }

                @Override
                public AskTimeBean[] newArray(int size) {
                    return new AskTimeBean[size];
                }
            };

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }

            public String getMinutes() {
                return minutes;
            }

            public void setMinutes(String minutes) {
                this.minutes = minutes;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getNanos() {
                return nanos;
            }

            public void setNanos(String nanos) {
                this.nanos = nanos;
            }

            public String getSeconds() {
                return seconds;
            }

            public void setSeconds(String seconds) {
                this.seconds = seconds;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(String timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(date);
                dest.writeString(day);
                dest.writeString(hours);
                dest.writeString(minutes);
                dest.writeString(month);
                dest.writeString(nanos);
                dest.writeString(seconds);
                dest.writeString(time);
                dest.writeString(timezoneOffset);
                dest.writeString(year);
            }
        }

        public static class FlyOrDownTimeBean implements Parcelable {
            /**
             * date : 19
             * day : 4
             * hours : 17
             * minutes : 8
             * month : 0
             * nanos : 0
             * seconds : 0
             * time : 1484816880000
             * timezoneOffset : -480
             * year : 117
             */

            private String date;
            private String day;
            private String hours;
            private String minutes;
            private String month;
            private String nanos;
            private String seconds;
            private String time;
            private String timezoneOffset;
            private String year;

            protected FlyOrDownTimeBean(Parcel in) {
                date = in.readString();
                day = in.readString();
                hours = in.readString();
                minutes = in.readString();
                month = in.readString();
                nanos = in.readString();
                seconds = in.readString();
                time = in.readString();
                timezoneOffset = in.readString();
                year = in.readString();
            }

            public static final Creator<FlyOrDownTimeBean> CREATOR = new Creator<FlyOrDownTimeBean>() {
                @Override
                public FlyOrDownTimeBean createFromParcel(Parcel in) {
                    return new FlyOrDownTimeBean(in);
                }

                @Override
                public FlyOrDownTimeBean[] newArray(int size) {
                    return new FlyOrDownTimeBean[size];
                }
            };

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }

            public String getMinutes() {
                return minutes;
            }

            public void setMinutes(String minutes) {
                this.minutes = minutes;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getNanos() {
                return nanos;
            }

            public void setNanos(String nanos) {
                this.nanos = nanos;
            }

            public String getSeconds() {
                return seconds;
            }

            public void setSeconds(String seconds) {
                this.seconds = seconds;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(String timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(date);
                dest.writeString(day);
                dest.writeString(hours);
                dest.writeString(minutes);
                dest.writeString(month);
                dest.writeString(nanos);
                dest.writeString(seconds);
                dest.writeString(time);
                dest.writeString(timezoneOffset);
                dest.writeString(year);
            }
        }

        public static class ReserveOrderTimeBean implements Parcelable {
            /**
             * date : 19
             * day : 4
             * hours : 17
             * minutes : 8
             * month : 0
             * nanos : 0
             * seconds : 0
             * time : 1484816880000
             * timezoneOffset : -480
             * year : 117
             */

            private String date;
            private String day;
            private String hours;
            private String minutes;
            private String month;
            private String nanos;
            private String seconds;
            private String time;
            private String timezoneOffset;
            private String year;

            protected ReserveOrderTimeBean(Parcel in) {
                date = in.readString();
                day = in.readString();
                hours = in.readString();
                minutes = in.readString();
                month = in.readString();
                nanos = in.readString();
                seconds = in.readString();
                time = in.readString();
                timezoneOffset = in.readString();
                year = in.readString();
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

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }

            public String getMinutes() {
                return minutes;
            }

            public void setMinutes(String minutes) {
                this.minutes = minutes;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getNanos() {
                return nanos;
            }

            public void setNanos(String nanos) {
                this.nanos = nanos;
            }

            public String getSeconds() {
                return seconds;
            }

            public void setSeconds(String seconds) {
                this.seconds = seconds;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(String timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(date);
                dest.writeString(day);
                dest.writeString(hours);
                dest.writeString(minutes);
                dest.writeString(month);
                dest.writeString(nanos);
                dest.writeString(seconds);
                dest.writeString(time);
                dest.writeString(timezoneOffset);
                dest.writeString(year);
            }
        }

        public static class UseDayEndBean implements Parcelable {
            /**
             * date : 19
             * day : 4
             * hours : 17
             * minutes : 10
             * month : 0
             * nanos : 0
             * seconds : 33
             * time : 1484817033000
             * timezoneOffset : -480
             * year : 117
             */

            private String date;
            private String day;
            private String hours;
            private String minutes;
            private String month;
            private String nanos;
            private String seconds;
            private String time;
            private String timezoneOffset;
            private String year;

            protected UseDayEndBean(Parcel in) {
                date = in.readString();
                day = in.readString();
                hours = in.readString();
                minutes = in.readString();
                month = in.readString();
                nanos = in.readString();
                seconds = in.readString();
                time = in.readString();
                timezoneOffset = in.readString();
                year = in.readString();
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

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }

            public String getMinutes() {
                return minutes;
            }

            public void setMinutes(String minutes) {
                this.minutes = minutes;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getNanos() {
                return nanos;
            }

            public void setNanos(String nanos) {
                this.nanos = nanos;
            }

            public String getSeconds() {
                return seconds;
            }

            public void setSeconds(String seconds) {
                this.seconds = seconds;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(String timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(date);
                dest.writeString(day);
                dest.writeString(hours);
                dest.writeString(minutes);
                dest.writeString(month);
                dest.writeString(nanos);
                dest.writeString(seconds);
                dest.writeString(time);
                dest.writeString(timezoneOffset);
                dest.writeString(year);
            }
        }

        public static class UseDayStartBean implements Parcelable {
            /**
             * date : 19
             * day : 4
             * hours : 17
             * minutes : 10
             * month : 0
             * nanos : 0
             * seconds : 33
             * time : 1484817033000
             * timezoneOffset : -480
             * year : 117
             */

            private String date;
            private String day;
            private String hours;
            private String minutes;
            private String month;
            private String nanos;
            private String seconds;
            private String time;
            private String timezoneOffset;
            private String year;

            protected UseDayStartBean(Parcel in) {
                date = in.readString();
                day = in.readString();
                hours = in.readString();
                minutes = in.readString();
                month = in.readString();
                nanos = in.readString();
                seconds = in.readString();
                time = in.readString();
                timezoneOffset = in.readString();
                year = in.readString();
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

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }

            public String getMinutes() {
                return minutes;
            }

            public void setMinutes(String minutes) {
                this.minutes = minutes;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public String getNanos() {
                return nanos;
            }

            public void setNanos(String nanos) {
                this.nanos = nanos;
            }

            public String getSeconds() {
                return seconds;
            }

            public void setSeconds(String seconds) {
                this.seconds = seconds;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(String timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(date);
                dest.writeString(day);
                dest.writeString(hours);
                dest.writeString(minutes);
                dest.writeString(month);
                dest.writeString(nanos);
                dest.writeString(seconds);
                dest.writeString(time);
                dest.writeString(timezoneOffset);
                dest.writeString(year);
            }
        }
    }

}
