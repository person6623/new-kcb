package com.kcb360.newkcb.entity;

import java.util.List;

/**
 * Created by xinshichao on 2017/6/8.
 * <p>
 * 司机空车订单列表
 */

public class DriverEmptyCarListBean {


    /**
     * others : null
     * rows : [{"atime":{"date":18,"day":4,"hours":16,"minutes":9,"month":4,"nanos":0,"seconds":54,"time":1495094994000,"timezoneOffset":-480,"year":117},"beyondPrice":0,"beyondRound":0,"carConfig":"","carNature":0,"carSeats":20,"driverPhone":"18280391836","driverRelname":"王师傅","driverScore":8,"freeEnd":{"date":11,"day":0,"hours":10,"minutes":43,"month":5,"nanos":0,"seconds":7,"time":1497148987000,"timezoneOffset":-480,"year":117},"freeStart":{"date":24,"day":3,"hours":10,"minutes":42,"month":4,"nanos":0,"seconds":47,"time":1495593767000,"timezoneOffset":-480,"year":117},"id":7,"plateNum":"川AWS169","runArea":"","waitPoint":"成都市-春熙路（地铁站）-104.147788|30.635141"},{"atime":{"date":18,"day":4,"hours":16,"minutes":9,"month":4,"nanos":0,"seconds":53,"time":1495094993000,"timezoneOffset":-480,"year":117},"beyondPrice":0,"beyondRound":0,"carConfig":"","carNature":0,"carSeats":20,"driverPhone":"18280391835","driverRelname":"李师傅","driverScore":8,"freeEnd":{"date":11,"day":0,"hours":10,"minutes":43,"month":5,"nanos":0,"seconds":7,"time":1497148987000,"timezoneOffset":-480,"year":117},"freeStart":{"date":24,"day":3,"hours":10,"minutes":42,"month":4,"nanos":0,"seconds":45,"time":1495593765000,"timezoneOffset":-480,"year":117},"id":6,"plateNum":"川AWS167","runArea":"","waitPoint":"成都市-天府五街（地铁站）-104.147788|30.635141"},{"atime":{"date":18,"day":4,"hours":16,"minutes":9,"month":4,"nanos":0,"seconds":52,"time":1495094992000,"timezoneOffset":-480,"year":117},"beyondPrice":0,"beyondRound":0,"carConfig":"","carNature":0,"carSeats":20,"driverPhone":"18280391834","driverRelname":"辛师傅","driverScore":8,"freeEnd":{"date":11,"day":0,"hours":10,"minutes":43,"month":5,"nanos":0,"seconds":7,"time":1497148987000,"timezoneOffset":-480,"year":117},"freeStart":{"date":24,"day":3,"hours":10,"minutes":42,"month":4,"nanos":0,"seconds":42,"time":1495593762000,"timezoneOffset":-480,"year":117},"id":5,"plateNum":"川AWS166","runArea":"","waitPoint":"成都市-华府大道（地铁站）-104.147788|30.635141"},{"atime":{"date":18,"day":4,"hours":16,"minutes":9,"month":4,"nanos":0,"seconds":51,"time":1495094991000,"timezoneOffset":-480,"year":117},"beyondPrice":0,"beyondRound":0,"carConfig":"","carNature":0,"carSeats":20,"driverPhone":"18280391833","driverRelname":"武师傅","driverScore":8,"freeEnd":{"date":11,"day":0,"hours":10,"minutes":43,"month":5,"nanos":0,"seconds":7,"time":1497148987000,"timezoneOffset":-480,"year":117},"freeStart":{"date":24,"day":3,"hours":10,"minutes":42,"month":4,"nanos":0,"seconds":39,"time":1495593759000,"timezoneOffset":-480,"year":117},"id":4,"plateNum":"川AT1902","runArea":"","waitPoint":"成都市-锦城广场（地铁站）-104.147788|30.635141"},{"atime":{"date":18,"day":4,"hours":16,"minutes":9,"month":4,"nanos":0,"seconds":50,"time":1495094990000,"timezoneOffset":-480,"year":117},"beyondPrice":0,"beyondRound":0,"carConfig":"","carNature":0,"carSeats":20,"driverPhone":"18280391833","driverRelname":"肖师傅","driverScore":8,"freeEnd":{"date":11,"day":0,"hours":10,"minutes":43,"month":5,"nanos":0,"seconds":7,"time":1497148987000,"timezoneOffset":-480,"year":117},"freeStart":{"date":24,"day":3,"hours":10,"minutes":42,"month":4,"nanos":0,"seconds":37,"time":1495593757000,"timezoneOffset":-480,"year":117},"id":3,"plateNum":"川AT1903","runArea":"","waitPoint":"成都市-金融城（地铁站）-104.147788|30.635141"}]
     * total : 6
     */

    private String others;
    private int total;
    private List<RowsBean> rows;

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

    public static class RowsBean {
        /**
         * atime : {"date":18,"day":4,"hours":16,"minutes":9,"month":4,"nanos":0,"seconds":54,"time":1495094994000,"timezoneOffset":-480,"year":117}
         * beyondPrice : 0
         * beyondRound : 0
         * carConfig :
         * carNature : 0
         * carSeats : 20
         * driverPhone : 18280391836
         * driverRelname : 王师傅
         * driverScore : 8
         * freeEnd : {"date":11,"day":0,"hours":10,"minutes":43,"month":5,"nanos":0,"seconds":7,"time":1497148987000,"timezoneOffset":-480,"year":117}
         * freeStart : {"date":24,"day":3,"hours":10,"minutes":42,"month":4,"nanos":0,"seconds":47,"time":1495593767000,"timezoneOffset":-480,"year":117}
         * id : 7
         * plateNum : 川AWS169
         * runArea :
         * waitPoint : 成都市-春熙路（地铁站）-104.147788|30.635141
         */

        private AtimeBean atime;
        private float beyondPrice;
        private int beyondRound;
        private String carConfig;
        private int carNature;
        private int carSeats;
        private String driverPhone;
        private String driverRelname;
        private float driverScore;
        private FreeEndBean freeEnd;
        private FreeStartBean freeStart;
        private int id;
        private String plateNum;
        private String runArea;
        private String waitPoint;

        public AtimeBean getAtime() {
            return atime;
        }

        public void setAtime(AtimeBean atime) {
            this.atime = atime;
        }

        public float getBeyondPrice() {
            return beyondPrice;
        }

        public void setBeyondPrice(float beyondPrice) {
            this.beyondPrice = beyondPrice;
        }

        public int getBeyondRound() {
            return beyondRound;
        }

        public void setBeyondRound(int beyondRound) {
            this.beyondRound = beyondRound;
        }

        public String getCarConfig() {
            return carConfig;
        }

        public void setCarConfig(String carConfig) {
            this.carConfig = carConfig;
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

        public String getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
        }

        public String getDriverRelname() {
            return driverRelname;
        }

        public void setDriverRelname(String driverRelname) {
            this.driverRelname = driverRelname;
        }

        public float getDriverScore() {
            return driverScore;
        }

        public void setDriverScore(float driverScore) {
            this.driverScore = driverScore;
        }

        public FreeEndBean getFreeEnd() {
            return freeEnd;
        }

        public void setFreeEnd(FreeEndBean freeEnd) {
            this.freeEnd = freeEnd;
        }

        public FreeStartBean getFreeStart() {
            return freeStart;
        }

        public void setFreeStart(FreeStartBean freeStart) {
            this.freeStart = freeStart;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlateNum() {
            return plateNum;
        }

        public void setPlateNum(String plateNum) {
            this.plateNum = plateNum;
        }

        public String getRunArea() {
            return runArea;
        }

        public void setRunArea(String runArea) {
            this.runArea = runArea;
        }

        public String getWaitPoint() {
            return waitPoint;
        }

        public void setWaitPoint(String waitPoint) {
            this.waitPoint = waitPoint;
        }

        public static class AtimeBean {
            /**
             * date : 18
             * day : 4
             * hours : 16
             * minutes : 9
             * month : 4
             * nanos : 0
             * seconds : 54
             * time : 1495094994000
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
        }

        public static class FreeEndBean {
            /**
             * date : 11
             * day : 0
             * hours : 10
             * minutes : 43
             * month : 5
             * nanos : 0
             * seconds : 7
             * time : 1497148987000
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
        }

        public static class FreeStartBean {
            /**
             * date : 24
             * day : 3
             * hours : 10
             * minutes : 42
             * month : 4
             * nanos : 0
             * seconds : 47
             * time : 1495593767000
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
        }
    }
}
