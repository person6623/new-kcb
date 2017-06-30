package com.kcb360.newkcb.entity;

import java.util.List;

/**
 * Created by xinshichao on 2017/5/4.
 */

public class TradeNotesBean {


    /**
     * others : 827,216.71-744,053.43
     * rows : [{"amoney":40.5,"assist":"971493822919568","atime":{"date":4,"day":4,"hours":11,"minutes":20,"month":4,"nanos":0,"seconds":52,"time":1493868052000,"timezoneOffset":-480,"year":117},"cName":"13999999999","cashBalance":0,"id":2637,"note":"接送机取消预约","status":0,"type":23},{"amoney":36.45,"assist":"971493822919568","atime":{"date":4,"day":4,"hours":11,"minutes":20,"month":4,"nanos":0,"seconds":52,"time":1493868052000,"timezoneOffset":-480,"year":117},"cName":"17828028717","cashBalance":17072.3,"id":2638,"note":"接送机及时退款","status":1,"type":5},{"amoney":36.45,"assist":"971493866430568","atime":{"date":4,"day":4,"hours":11,"minutes":20,"month":4,"nanos":0,"seconds":43,"time":1493868043000,"timezoneOffset":-480,"year":117},"cName":"17828028717","cashBalance":17108.75,"id":2636,"note":"接送机及时退款","status":1,"type":5},{"amoney":40.5,"assist":"971493866430568","atime":{"date":4,"day":4,"hours":11,"minutes":20,"month":4,"nanos":0,"seconds":43,"time":1493868043000,"timezoneOffset":-480,"year":117},"cName":"13999999999","cashBalance":0,"id":2635,"note":"接送机取消预约","status":0,"type":23},{"amoney":36.45,"assist":"971493866737510","atime":{"date":4,"day":4,"hours":11,"minutes":15,"month":4,"nanos":0,"seconds":33,"time":1493867733000,"timezoneOffset":-480,"year":117},"cName":"17828028717","cashBalance":17145.2,"id":2634,"note":"接送机及时退款","status":1,"type":5},{"amoney":40.5,"assist":"971493866737510","atime":{"date":4,"day":4,"hours":11,"minutes":15,"month":4,"nanos":0,"seconds":33,"time":1493867733000,"timezoneOffset":-480,"year":117},"cName":"13999999999","cashBalance":0,"id":2633,"note":"接送机取消预约","status":0,"type":23},{"amoney":40.5,"assist":"971493866737510","atime":{"date":4,"day":4,"hours":10,"minutes":59,"month":4,"nanos":0,"seconds":26,"time":1493866766000,"timezoneOffset":-480,"year":117},"cName":"13999999999","cashBalance":0,"id":2630,"note":"接送机预约(第三方支付)","status":1,"type":5}]
     * total : 1748
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
         * amoney : 40.5
         * assist : 971493822919568
         * atime : {"date":4,"day":4,"hours":11,"minutes":20,"month":4,"nanos":0,"seconds":52,"time":1493868052000,"timezoneOffset":-480,"year":117}
         * cName : 13999999999
         * cashBalance : 0
         * id : 2637
         * note : 接送机取消预约
         * status : 0
         * type : 23
         */

        private double amoney;
        private String assist;
        private AtimeBean atime;
        private String cName;
        private float cashBalance;
        private int id;
        private String note;
        private int status;
        private int type;

        public double getAmoney() {
            return amoney;
        }

        public void setAmoney(double amoney) {
            this.amoney = amoney;
        }

        public String getAssist() {
            return assist;
        }

        public void setAssist(String assist) {
            this.assist = assist;
        }

        public AtimeBean getAtime() {
            return atime;
        }

        public void setAtime(AtimeBean atime) {
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public static class AtimeBean {
            /**
             * date : 4
             * day : 4
             * hours : 11
             * minutes : 20
             * month : 4
             * nanos : 0
             * seconds : 52
             * time : 1493868052000
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
