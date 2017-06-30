package com.kcb360.newkcb.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by xinshichao on 2017/6/26.
 * <p>
 * 包车价格设置列表
 */

public class DriverCharterSettingListBean implements Parcelable {

    /**
     * others : null
     * rows : [{"account":"15828251216","addTime":{"date":26,"day":1,"hours":9,"minutes":12,"month":5,"nanos":0,"seconds":11,"time":1498439531000,"timezoneOffset":-480,"year":117},"areaName":"成都市","areaType":0,"carSeats":30,"charterPrice":"50-300,100-400","endTime":{"date":28,"day":3,"hours":0,"minutes":0,"month":5,"nanos":0,"seconds":0,"time":1498579200000,"timezoneOffset":-480,"year":117},"feastRatio":25,"freeKm":30,"id":22,"moreFreeKmPrice":20,"oilWear":2.1,"startTime":{"date":27,"day":2,"hours":0,"minutes":0,"month":5,"nanos":0,"seconds":0,"time":1498492800000,"timezoneOffset":-480,"year":117}},{"account":"15828251216","addTime":{"date":26,"day":1,"hours":9,"minutes":9,"month":5,"nanos":0,"seconds":6,"time":1498439346000,"timezoneOffset":-480,"year":117},"areaName":"成都市","areaType":0,"carSeats":36,"charterPrice":"350-","endTime":{"date":28,"day":3,"hours":0,"minutes":0,"month":5,"nanos":0,"seconds":0,"time":1498579200000,"timezoneOffset":-480,"year":117},"feastRatio":20,"freeKm":70,"id":21,"moreFreeKmPrice":20,"oilWear":1.5,"startTime":{"date":26,"day":1,"hours":0,"minutes":0,"month":5,"nanos":0,"seconds":0,"time":1498406400000,"timezoneOffset":-480,"year":117}},{"account":"15828251216","addTime":{"date":26,"day":1,"hours":9,"minutes":7,"month":5,"nanos":0,"seconds":19,"time":1498439239000,"timezoneOffset":-480,"year":117},"areaName":"成都市","areaType":0,"carSeats":39,"charterPrice":"350-","endTime":{"date":28,"day":3,"hours":0,"minutes":0,"month":5,"nanos":0,"seconds":0,"time":1498579200000,"timezoneOffset":-480,"year":117},"feastRatio":10,"freeKm":50,"id":20,"moreFreeKmPrice":12,"oilWear":1.5,"startTime":{"date":27,"day":2,"hours":0,"minutes":0,"month":5,"nanos":0,"seconds":0,"time":1498492800000,"timezoneOffset":-480,"year":117}}]
     * total : 3
     */

    private Object others;
    private int total;
    private List<RowsBean> rows;

    protected DriverCharterSettingListBean(Parcel in) {
        total = in.readInt();
        rows = in.createTypedArrayList(RowsBean.CREATOR);
    }

    public static final Creator<DriverCharterSettingListBean> CREATOR = new Creator<DriverCharterSettingListBean>() {
        @Override
        public DriverCharterSettingListBean createFromParcel(Parcel in) {
            return new DriverCharterSettingListBean(in);
        }

        @Override
        public DriverCharterSettingListBean[] newArray(int size) {
            return new DriverCharterSettingListBean[size];
        }
    };

    public Object getOthers() {
        return others;
    }

    public void setOthers(Object others) {
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
        dest.writeInt(total);
        dest.writeTypedList(rows);
    }

    public static class RowsBean implements Parcelable {
        /**
         * account : 15828251216
         * addTime : {"date":26,"day":1,"hours":9,"minutes":12,"month":5,"nanos":0,"seconds":11,"time":1498439531000,"timezoneOffset":-480,"year":117}
         * areaName : 成都市
         * areaType : 0
         * carSeats : 30
         * charterPrice : 50-300,100-400
         * endTime : {"date":28,"day":3,"hours":0,"minutes":0,"month":5,"nanos":0,"seconds":0,"time":1498579200000,"timezoneOffset":-480,"year":117}
         * feastRatio : 25
         * freeKm : 30
         * id : 22
         * moreFreeKmPrice : 20
         * oilWear : 2.1
         * startTime : {"date":27,"day":2,"hours":0,"minutes":0,"month":5,"nanos":0,"seconds":0,"time":1498492800000,"timezoneOffset":-480,"year":117}
         */

        private String account;
        private AddTimeBean addTime;
        private String areaName;
        private int areaType;
        private int carSeats;
        private String charterPrice;
        private EndTimeBean endTime;
        private int feastRatio;
        private float freeKm;
        private int id;
        private float moreFreeKmPrice;
        private double oilWear;
        private StartTimeBean startTime;

        protected RowsBean(Parcel in) {
            account = in.readString();
            addTime = in.readParcelable(AddTimeBean.class.getClassLoader());
            areaName = in.readString();
            areaType = in.readInt();
            carSeats = in.readInt();
            charterPrice = in.readString();
            endTime = in.readParcelable(EndTimeBean.class.getClassLoader());
            feastRatio = in.readInt();
            freeKm = in.readFloat();
            id = in.readInt();
            moreFreeKmPrice = in.readFloat();
            oilWear = in.readDouble();
            startTime = in.readParcelable(StartTimeBean.class.getClassLoader());
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

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public AddTimeBean getAddTime() {
            return addTime;
        }

        public void setAddTime(AddTimeBean addTime) {
            this.addTime = addTime;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getAreaType() {
            return areaType;
        }

        public void setAreaType(int areaType) {
            this.areaType = areaType;
        }

        public int getCarSeats() {
            return carSeats;
        }

        public void setCarSeats(int carSeats) {
            this.carSeats = carSeats;
        }

        public String getCharterPrice() {
            return charterPrice;
        }

        public void setCharterPrice(String charterPrice) {
            this.charterPrice = charterPrice;
        }

        public EndTimeBean getEndTime() {
            return endTime;
        }

        public void setEndTime(EndTimeBean endTime) {
            this.endTime = endTime;
        }

        public int getFeastRatio() {
            return feastRatio;
        }

        public void setFeastRatio(int feastRatio) {
            this.feastRatio = feastRatio;
        }

        public float getFreeKm() {
            return freeKm;
        }

        public void setFreeKm(float freeKm) {
            this.freeKm = freeKm;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public float getMoreFreeKmPrice() {
            return moreFreeKmPrice;
        }

        public void setMoreFreeKmPrice(float moreFreeKmPrice) {
            this.moreFreeKmPrice = moreFreeKmPrice;
        }

        public double getOilWear() {
            return oilWear;
        }

        public void setOilWear(double oilWear) {
            this.oilWear = oilWear;
        }

        public StartTimeBean getStartTime() {
            return startTime;
        }

        public void setStartTime(StartTimeBean startTime) {
            this.startTime = startTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(account);
            dest.writeParcelable(addTime, flags);
            dest.writeString(areaName);
            dest.writeInt(areaType);
            dest.writeInt(carSeats);
            dest.writeString(charterPrice);
            dest.writeParcelable(endTime, flags);
            dest.writeInt(feastRatio);
            dest.writeFloat(freeKm);
            dest.writeInt(id);
            dest.writeFloat(moreFreeKmPrice);
            dest.writeDouble(oilWear);
            dest.writeParcelable(startTime, flags);
        }

        public static class AddTimeBean implements Parcelable {
            /**
             * date : 26
             * day : 1
             * hours : 9
             * minutes : 12
             * month : 5
             * nanos : 0
             * seconds : 11
             * time : 1498439531000
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

        public static class EndTimeBean implements Parcelable {
            /**
             * date : 28
             * day : 3
             * hours : 0
             * minutes : 0
             * month : 5
             * nanos : 0
             * seconds : 0
             * time : 1498579200000
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

            protected EndTimeBean(Parcel in) {
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

            public static final Creator<EndTimeBean> CREATOR = new Creator<EndTimeBean>() {
                @Override
                public EndTimeBean createFromParcel(Parcel in) {
                    return new EndTimeBean(in);
                }

                @Override
                public EndTimeBean[] newArray(int size) {
                    return new EndTimeBean[size];
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

        public static class StartTimeBean implements Parcelable {
            /**
             * date : 27
             * day : 2
             * hours : 0
             * minutes : 0
             * month : 5
             * nanos : 0
             * seconds : 0
             * time : 1498492800000
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

            protected StartTimeBean(Parcel in) {
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

            public static final Creator<StartTimeBean> CREATOR = new Creator<StartTimeBean>() {
                @Override
                public StartTimeBean createFromParcel(Parcel in) {
                    return new StartTimeBean(in);
                }

                @Override
                public StartTimeBean[] newArray(int size) {
                    return new StartTimeBean[size];
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
