package com.kcb360.newkcb.entity;

import java.util.List;

/**
 * Created by xinshichao on 2017/5/8.
 * <p>
 * 优惠券
 */

public class CouponBean {

    /**
     * others : null
     * rows : [{"addTime":{"date":20,"day":1,"hours":19,"minutes":2,"month":2,"nanos":0,"seconds":59,"time":1490007779000,"timezoneOffset":-480,"year":117},"discountCondition":6,"discountName":"分享优惠","discountNo":"00018112010001","discountPrice":"5","discountRatio":"20","endTime":{"date":31,"day":0,"hours":0,"minutes":0,"month":11,"nanos":0,"seconds":0,"time":1514649600000,"timezoneOffset":-480,"year":117},"id":579,"recName":"18982208376","receiptName":"17828028717","startTime":{"date":23,"day":1,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":1485100800000,"timezoneOffset":-480,"year":117},"useState":1,"useTime":{"date":2,"day":2,"hours":13,"minutes":32,"month":4,"nanos":0,"seconds":54,"time":1493703174000,"timezoneOffset":-480,"year":117}},{"addTime":{"date":12,"day":0,"hours":11,"minutes":15,"month":2,"nanos":0,"seconds":37,"time":1489288537000,"timezoneOffset":-480,"year":117},"discountCondition":3,"discountName":"散客接送卷","discountNo":"00038112010001","discountPrice":"10","discountRatio":"20","endTime":{"date":31,"day":0,"hours":0,"minutes":0,"month":11,"nanos":0,"seconds":0,"time":1514649600000,"timezoneOffset":-480,"year":117},"id":3,"recName":"18982208376","receiptName":"17828028717","startTime":{"date":20,"day":2,"hours":0,"minutes":0,"month":11,"nanos":0,"seconds":0,"time":1482163200000,"timezoneOffset":-480,"year":116},"useState":0,"useTime":{"date":8,"day":3,"hours":17,"minutes":40,"month":2,"nanos":0,"seconds":11,"time":1488966011000,"timezoneOffset":-480,"year":117}},{"addTime":{"date":12,"day":0,"hours":11,"minutes":15,"month":2,"nanos":0,"seconds":37,"time":1489288537000,"timezoneOffset":-480,"year":117},"discountCondition":2,"discountName":"旅游包车","discountNo":"00048112010001","discountPrice":"200","discountRatio":"5","endTime":{"date":30,"day":0,"hours":0,"minutes":0,"month":3,"nanos":0,"seconds":0,"time":1493481600000,"timezoneOffset":-480,"year":117},"id":4,"recName":"18982208376","receiptName":"17828028717","startTime":{"date":12,"day":1,"hours":0,"minutes":0,"month":11,"nanos":0,"seconds":0,"time":1481472000000,"timezoneOffset":-480,"year":116},"useState":0,"useTime":null}]
     * total : 3
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
         * addTime : {"date":20,"day":1,"hours":19,"minutes":2,"month":2,"nanos":0,"seconds":59,"time":1490007779000,"timezoneOffset":-480,"year":117}
         * discountCondition : 6
         * discountName : 分享优惠
         * discountNo : 00018112010001
         * discountPrice : 5
         * discountRatio : 20
         * endTime : {"date":31,"day":0,"hours":0,"minutes":0,"month":11,"nanos":0,"seconds":0,"time":1514649600000,"timezoneOffset":-480,"year":117}
         * id : 579
         * recName : 18982208376
         * receiptName : 17828028717
         * startTime : {"date":23,"day":1,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":1485100800000,"timezoneOffset":-480,"year":117}
         * useState : 1
         * useTime : {"date":2,"day":2,"hours":13,"minutes":32,"month":4,"nanos":0,"seconds":54,"time":1493703174000,"timezoneOffset":-480,"year":117}
         */

        private AddTimeBean addTime;
        private int discountCondition;
        private String discountName;
        private String discountNo;
        private String discountPrice;
        private String discountRatio;
        private EndTimeBean endTime;
        private int id;
        private String recName;
        private String receiptName;
        private StartTimeBean startTime;
        private int useState;
        private UseTimeBean useTime;

        public AddTimeBean getAddTime() {
            return addTime;
        }

        public void setAddTime(AddTimeBean addTime) {
            this.addTime = addTime;
        }

        public int getDiscountCondition() {
            return discountCondition;
        }

        public void setDiscountCondition(int discountCondition) {
            this.discountCondition = discountCondition;
        }

        public String getDiscountName() {
            return discountName;
        }

        public void setDiscountName(String discountName) {
            this.discountName = discountName;
        }

        public String getDiscountNo() {
            return discountNo;
        }

        public void setDiscountNo(String discountNo) {
            this.discountNo = discountNo;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getDiscountRatio() {
            return discountRatio;
        }

        public void setDiscountRatio(String discountRatio) {
            this.discountRatio = discountRatio;
        }

        public EndTimeBean getEndTime() {
            return endTime;
        }

        public void setEndTime(EndTimeBean endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRecName() {
            return recName;
        }

        public void setRecName(String recName) {
            this.recName = recName;
        }

        public String getReceiptName() {
            return receiptName;
        }

        public void setReceiptName(String receiptName) {
            this.receiptName = receiptName;
        }

        public StartTimeBean getStartTime() {
            return startTime;
        }

        public void setStartTime(StartTimeBean startTime) {
            this.startTime = startTime;
        }

        public int getUseState() {
            return useState;
        }

        public void setUseState(int useState) {
            this.useState = useState;
        }

        public UseTimeBean getUseTime() {
            return useTime;
        }

        public void setUseTime(UseTimeBean useTime) {
            this.useTime = useTime;
        }

        public static class AddTimeBean {
            /**
             * date : 20
             * day : 1
             * hours : 19
             * minutes : 2
             * month : 2
             * nanos : 0
             * seconds : 59
             * time : 1490007779000
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

        public static class EndTimeBean {
            /**
             * date : 31
             * day : 0
             * hours : 0
             * minutes : 0
             * month : 11
             * nanos : 0
             * seconds : 0
             * time : 1514649600000
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

        public static class StartTimeBean {
            /**
             * date : 23
             * day : 1
             * hours : 0
             * minutes : 0
             * month : 0
             * nanos : 0
             * seconds : 0
             * time : 1485100800000
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

        public static class UseTimeBean {
            /**
             * date : 2
             * day : 2
             * hours : 13
             * minutes : 32
             * month : 4
             * nanos : 0
             * seconds : 54
             * time : 1493703174000
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
