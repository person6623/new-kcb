package com.kcb360.newkcb.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by xinshichao on 2017/6/13.
 * <p>
 * 用户订单列表
 */

public class CustomerOrderListBean implements Parcelable {

    /**
     * code : 1
     * context : 查询成功！
     * myOrder : [{"askTime":"2017-06-13 16:42","carBrand":"-1","carLife":"1","carNature":0,"carSeats":39,"chooseFleet":"0","chooseRoute":-1,"customerPhone":"15828251216","departPlace":"成都市 成都大熊猫繁育研究基地","destination":"成都市 成都双流国际机场T2航站楼(国内到达)","driver":"","driverPhone":"","getOrderTime":"","id":1150,"isBill":1,"isShuttle":0,"lineDownOrUp":0,"lonAndLat":"104.134536|30.737884,103.95661|30.57108","lookTelCount":0,"mbName":"15828251216","needCarNum":0,"newDestination":"","newWayPoints":"","offerCount":0,"offerTerm":-1,"orderHandSelPrice":0,"orderNum":"861497343337203","orderPrice":0,"orderState":0,"payTime":0,"plateNum":"","realName":"xsc","receiptHandSel":0,"releaseArea":"成都市","remark":"【phone-询价订单】","remarkPos":"","retaiPayWay":1,"seatNum":1,"sendCount":0,"teamOrSingle":0,"useDayEnd":"2017-06-17 16:41","useDayStart":"2017-06-15 16:41","vieWay":"","wayPoints":""}]
     */

    private int code;
    private String context;
    private List<MyOrderBean> myOrder;

    protected CustomerOrderListBean(Parcel in) {
        code = in.readInt();
        context = in.readString();
        myOrder = in.createTypedArrayList(MyOrderBean.CREATOR);
    }

    public static final Creator<CustomerOrderListBean> CREATOR = new Creator<CustomerOrderListBean>() {
        @Override
        public CustomerOrderListBean createFromParcel(Parcel in) {
            return new CustomerOrderListBean(in);
        }

        @Override
        public CustomerOrderListBean[] newArray(int size) {
            return new CustomerOrderListBean[size];
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

    public List<MyOrderBean> getMyOrder() {
        return myOrder;
    }

    public void setMyOrder(List<MyOrderBean> myOrder) {
        this.myOrder = myOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(context);
        dest.writeTypedList(myOrder);
    }

    public static class MyOrderBean implements Parcelable {
        /**
         * askTime : 2017-06-13 16:42
         * carBrand : -1
         * carLife : 1
         * carNature : 0
         * carSeats : 39
         * chooseFleet : 0
         * chooseRoute : -1
         * customerPhone : 15828251216
         * departPlace : 成都市 成都大熊猫繁育研究基地
         * destination : 成都市 成都双流国际机场T2航站楼(国内到达)
         * driver :
         * driverPhone :
         * getOrderTime :
         * id : 1150
         * isBill : 1
         * isShuttle : 0
         * lineDownOrUp : 0
         * lonAndLat : 104.134536|30.737884,103.95661|30.57108
         * lookTelCount : 0
         * mbName : 15828251216
         * needCarNum : 0
         * newDestination :
         * newWayPoints :
         * offerCount : 0
         * offerTerm : -1
         * orderHandSelPrice : 0
         * orderNum : 861497343337203
         * orderPrice : 0
         * orderState : 0
         * payTime : 0
         * plateNum :
         * realName : xsc
         * receiptHandSel : 0
         * releaseArea : 成都市
         * remark : 【phone-询价订单】
         * remarkPos :
         * retaiPayWay : 1
         * seatNum : 1
         * sendCount : 0
         * teamOrSingle : 0
         * useDayEnd : 2017-06-17 16:41
         * useDayStart : 2017-06-15 16:41
         * vieWay :
         * wayPoints :
         */

        private String askTime;
        private String carBrand;
        private String carLife;
        private int carNature;
        private int carSeats;
        private String chooseFleet;
        private int chooseRoute;
        private String customerPhone;
        private String departPlace;
        private String destination;
        private String driver;
        private String driverPhone;
        private String getOrderTime;
        private int id;
        private int isBill;
        private int isShuttle;
        private int lineDownOrUp;
        private String lonAndLat;
        private int lookTelCount;
        private String mbName;
        private int needCarNum;
        private String newDestination;
        private String newWayPoints;
        private int offerCount;
        private int offerTerm;
        private float orderHandSelPrice;
        private String orderNum;
        private float orderPrice;
        private int orderState;
        private int payTime;
        private String plateNum;
        private String realName;
        private int receiptHandSel;
        private String releaseArea;
        private String remark;
        private String remarkPos;
        private int retaiPayWay;
        private int seatNum;
        private int sendCount;
        private int teamOrSingle;
        private String useDayEnd;
        private String useDayStart;
        private String vieWay;
        private String wayPoints;

        protected MyOrderBean(Parcel in) {
            askTime = in.readString();
            carBrand = in.readString();
            carLife = in.readString();
            carNature = in.readInt();
            carSeats = in.readInt();
            chooseFleet = in.readString();
            chooseRoute = in.readInt();
            customerPhone = in.readString();
            departPlace = in.readString();
            destination = in.readString();
            driver = in.readString();
            driverPhone = in.readString();
            getOrderTime = in.readString();
            id = in.readInt();
            isBill = in.readInt();
            isShuttle = in.readInt();
            lineDownOrUp = in.readInt();
            lonAndLat = in.readString();
            lookTelCount = in.readInt();
            mbName = in.readString();
            needCarNum = in.readInt();
            newDestination = in.readString();
            newWayPoints = in.readString();
            offerCount = in.readInt();
            offerTerm = in.readInt();
            orderHandSelPrice = in.readFloat();
            orderNum = in.readString();
            orderPrice = in.readFloat();
            orderState = in.readInt();
            payTime = in.readInt();
            plateNum = in.readString();
            realName = in.readString();
            receiptHandSel = in.readInt();
            releaseArea = in.readString();
            remark = in.readString();
            remarkPos = in.readString();
            retaiPayWay = in.readInt();
            seatNum = in.readInt();
            sendCount = in.readInt();
            teamOrSingle = in.readInt();
            useDayEnd = in.readString();
            useDayStart = in.readString();
            vieWay = in.readString();
            wayPoints = in.readString();
        }

        public static final Creator<MyOrderBean> CREATOR = new Creator<MyOrderBean>() {
            @Override
            public MyOrderBean createFromParcel(Parcel in) {
                return new MyOrderBean(in);
            }

            @Override
            public MyOrderBean[] newArray(int size) {
                return new MyOrderBean[size];
            }
        };

        public String getAskTime() {
            return askTime;
        }

        public void setAskTime(String askTime) {
            this.askTime = askTime;
        }

        public String getCarBrand() {
            return carBrand;
        }

        public void setCarBrand(String carBrand) {
            this.carBrand = carBrand;
        }

        public String getCarLife() {
            return carLife;
        }

        public void setCarLife(String carLife) {
            this.carLife = carLife;
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

        public String getChooseFleet() {
            return chooseFleet;
        }

        public void setChooseFleet(String chooseFleet) {
            this.chooseFleet = chooseFleet;
        }

        public int getChooseRoute() {
            return chooseRoute;
        }

        public void setChooseRoute(int chooseRoute) {
            this.chooseRoute = chooseRoute;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
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

        public String getGetOrderTime() {
            return getOrderTime;
        }

        public void setGetOrderTime(String getOrderTime) {
            this.getOrderTime = getOrderTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsBill() {
            return isBill;
        }

        public void setIsBill(int isBill) {
            this.isBill = isBill;
        }

        public int getIsShuttle() {
            return isShuttle;
        }

        public void setIsShuttle(int isShuttle) {
            this.isShuttle = isShuttle;
        }

        public int getLineDownOrUp() {
            return lineDownOrUp;
        }

        public void setLineDownOrUp(int lineDownOrUp) {
            this.lineDownOrUp = lineDownOrUp;
        }

        public String getLonAndLat() {
            return lonAndLat;
        }

        public void setLonAndLat(String lonAndLat) {
            this.lonAndLat = lonAndLat;
        }

        public int getLookTelCount() {
            return lookTelCount;
        }

        public void setLookTelCount(int lookTelCount) {
            this.lookTelCount = lookTelCount;
        }

        public String getMbName() {
            return mbName;
        }

        public void setMbName(String mbName) {
            this.mbName = mbName;
        }

        public int getNeedCarNum() {
            return needCarNum;
        }

        public void setNeedCarNum(int needCarNum) {
            this.needCarNum = needCarNum;
        }

        public String getNewDestination() {
            return newDestination;
        }

        public void setNewDestination(String newDestination) {
            this.newDestination = newDestination;
        }

        public String getNewWayPoints() {
            return newWayPoints;
        }

        public void setNewWayPoints(String newWayPoints) {
            this.newWayPoints = newWayPoints;
        }

        public int getOfferCount() {
            return offerCount;
        }

        public void setOfferCount(int offerCount) {
            this.offerCount = offerCount;
        }

        public int getOfferTerm() {
            return offerTerm;
        }

        public void setOfferTerm(int offerTerm) {
            this.offerTerm = offerTerm;
        }

        public float getOrderHandSelPrice() {
            return orderHandSelPrice;
        }

        public void setOrderHandSelPrice(float orderHandSelPrice) {
            this.orderHandSelPrice = orderHandSelPrice;
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

        public int getPayTime() {
            return payTime;
        }

        public void setPayTime(int payTime) {
            this.payTime = payTime;
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

        public int getReceiptHandSel() {
            return receiptHandSel;
        }

        public void setReceiptHandSel(int receiptHandSel) {
            this.receiptHandSel = receiptHandSel;
        }

        public String getReleaseArea() {
            return releaseArea;
        }

        public void setReleaseArea(String releaseArea) {
            this.releaseArea = releaseArea;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemarkPos() {
            return remarkPos;
        }

        public void setRemarkPos(String remarkPos) {
            this.remarkPos = remarkPos;
        }

        public int getRetaiPayWay() {
            return retaiPayWay;
        }

        public void setRetaiPayWay(int retaiPayWay) {
            this.retaiPayWay = retaiPayWay;
        }

        public int getSeatNum() {
            return seatNum;
        }

        public void setSeatNum(int seatNum) {
            this.seatNum = seatNum;
        }

        public int getSendCount() {
            return sendCount;
        }

        public void setSendCount(int sendCount) {
            this.sendCount = sendCount;
        }

        public int getTeamOrSingle() {
            return teamOrSingle;
        }

        public void setTeamOrSingle(int teamOrSingle) {
            this.teamOrSingle = teamOrSingle;
        }

        public String getUseDayEnd() {
            return useDayEnd;
        }

        public void setUseDayEnd(String useDayEnd) {
            this.useDayEnd = useDayEnd;
        }

        public String getUseDayStart() {
            return useDayStart;
        }

        public void setUseDayStart(String useDayStart) {
            this.useDayStart = useDayStart;
        }

        public String getVieWay() {
            return vieWay;
        }

        public void setVieWay(String vieWay) {
            this.vieWay = vieWay;
        }

        public String getWayPoints() {
            return wayPoints;
        }

        public void setWayPoints(String wayPoints) {
            this.wayPoints = wayPoints;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(askTime);
            dest.writeString(carBrand);
            dest.writeString(carLife);
            dest.writeInt(carNature);
            dest.writeInt(carSeats);
            dest.writeString(chooseFleet);
            dest.writeInt(chooseRoute);
            dest.writeString(customerPhone);
            dest.writeString(departPlace);
            dest.writeString(destination);
            dest.writeString(driver);
            dest.writeString(driverPhone);
            dest.writeString(getOrderTime);
            dest.writeInt(id);
            dest.writeInt(isBill);
            dest.writeInt(isShuttle);
            dest.writeInt(lineDownOrUp);
            dest.writeString(lonAndLat);
            dest.writeInt(lookTelCount);
            dest.writeString(mbName);
            dest.writeInt(needCarNum);
            dest.writeString(newDestination);
            dest.writeString(newWayPoints);
            dest.writeInt(offerCount);
            dest.writeInt(offerTerm);
            dest.writeFloat(orderHandSelPrice);
            dest.writeString(orderNum);
            dest.writeFloat(orderPrice);
            dest.writeInt(orderState);
            dest.writeInt(payTime);
            dest.writeString(plateNum);
            dest.writeString(realName);
            dest.writeInt(receiptHandSel);
            dest.writeString(releaseArea);
            dest.writeString(remark);
            dest.writeString(remarkPos);
            dest.writeInt(retaiPayWay);
            dest.writeInt(seatNum);
            dest.writeInt(sendCount);
            dest.writeInt(teamOrSingle);
            dest.writeString(useDayEnd);
            dest.writeString(useDayStart);
            dest.writeString(vieWay);
            dest.writeString(wayPoints);
        }
    }
}
