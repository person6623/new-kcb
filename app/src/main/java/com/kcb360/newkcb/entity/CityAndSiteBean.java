package com.kcb360.newkcb.entity;

import java.util.List;

/**
 * Created by xinshichao on 2017/6/16.
 * <p>
 * 城市and景点
 */

public class CityAndSiteBean {

    /**
     * others : null
     * rows : [{"addTime":{"date":18,"day":0,"hours":20,"minutes":20,"month":5,"nanos":0,"seconds":44,"time":1497788444000,"timezoneOffset":-480,"year":117},"areaName":"稻城亚丁","areaType":1,"id":6,"provinceCode":"510000","provinceName":"四川省"},{"addTime":{"date":18,"day":0,"hours":20,"minutes":20,"month":5,"nanos":0,"seconds":15,"time":1497788415000,"timezoneOffset":-480,"year":117},"areaName":"九寨沟","areaType":1,"id":5,"provinceCode":"510000","provinceName":"四川省"},{"addTime":{"date":18,"day":0,"hours":7,"minutes":49,"month":5,"nanos":0,"seconds":13,"time":1497743353000,"timezoneOffset":-480,"year":117},"areaName":"阿坝藏族羌族自治州","areaType":0,"id":4,"provinceCode":"510000","provinceName":"四川省"},{"addTime":{"date":18,"day":0,"hours":7,"minutes":50,"month":5,"nanos":0,"seconds":57,"time":1497743457000,"timezoneOffset":-480,"year":117},"areaName":"甘孜藏族自治州","areaType":0,"id":3,"provinceCode":"510000","provinceName":"四川省"},{"addTime":{"date":18,"day":0,"hours":7,"minutes":51,"month":5,"nanos":0,"seconds":8,"time":1497743468000,"timezoneOffset":-480,"year":117},"areaName":"凉山彝族自治州","areaType":0,"id":1,"provinceCode":"510000","provinceName":"四川省"}]
     * total : 5
     */

    private Object others;
    private int total;
    private List<RowsBean> rows;

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

    public static class RowsBean {
        /**
         * addTime : {"date":18,"day":0,"hours":20,"minutes":20,"month":5,"nanos":0,"seconds":44,"time":1497788444000,"timezoneOffset":-480,"year":117}
         * areaName : 稻城亚丁
         * areaType : 1
         * id : 6
         * provinceCode : 510000
         * provinceName : 四川省
         */

        private AddTimeBean addTime;
        private String areaName;
        private int areaType;
        private int id;
        private String provinceCode;
        private String provinceName;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public static class AddTimeBean {
            /**
             * date : 18
             * day : 0
             * hours : 20
             * minutes : 20
             * month : 5
             * nanos : 0
             * seconds : 44
             * time : 1497788444000
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
