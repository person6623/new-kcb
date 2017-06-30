package com.kcb360.newkcb.net;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.kcb360.newkcb.entity.AllCityBean;
import com.kcb360.newkcb.entity.AskCalculatePriceBean;
import com.kcb360.newkcb.entity.AskOrderPriceSettingBean;
import com.kcb360.newkcb.entity.BankCardListBean;
import com.kcb360.newkcb.entity.CarInfoBean;
import com.kcb360.newkcb.entity.CityAndSiteBean;
import com.kcb360.newkcb.entity.CouponBean;
import com.kcb360.newkcb.entity.CustomerOrderListBean;
import com.kcb360.newkcb.entity.DriverCharterSettingListBean;
import com.kcb360.newkcb.entity.DriverEmptyCarListBean;
import com.kcb360.newkcb.entity.LoginBean;
import com.kcb360.newkcb.entity.NormalBean;
import com.kcb360.newkcb.entity.OnWayOrderBean;
import com.kcb360.newkcb.entity.ProvinceBean;
import com.kcb360.newkcb.entity.RegistBean;
import com.kcb360.newkcb.entity.SeatsListBean;
import com.kcb360.newkcb.entity.TakeAirOrderBean;
import com.kcb360.newkcb.entity.TradeNotesBean;
import com.kcb360.newkcb.entity.UserPurseBean;
import com.kcb360.newkcb.entity.VerifyBean;
import com.kcb360.newkcb.entity.VersionBean;
import com.kcb360.newkcb.entity.WithDrawCashBean;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinshichao on 2017/5/31.
 * <p>
 * 解析
 */

public class JSONUtil {

    // 版本更新
    public static VersionBean versionJsonAnalytic(String json) {
        Gson gson = new Gson();
        VersionBean bean = gson.fromJson(json, VersionBean.class);
        return bean;
    }

    // 登录
    public static LoginBean LoginJsonAnalytic(String json) {
        Gson gson = new Gson();
        LoginBean bean = gson.fromJson(json, LoginBean.class);
        return bean;
    }

    // 注册
    public static RegistBean RegistJsonAnalytic(String json) {
        Gson gson = new Gson();
        RegistBean bean = gson.fromJson(json, RegistBean.class);
        return bean;
    }

    // 验证码
    public static VerifyBean VerifyJsonAnalytic(String json) {
        Gson gson = new Gson();
        VerifyBean bean = gson.fromJson(json, VerifyBean.class);
        return bean;
    }

    // 司机空车列表
    public static DriverEmptyCarListBean DriverEmptyCarListJsonAnalytic(String json) {
        Gson gson = new Gson();
        DriverEmptyCarListBean bean = gson.fromJson(json, DriverEmptyCarListBean.class);
        return bean;
    }

    // 包车价格计算参数
    public static AskOrderPriceSettingBean AskOrderPriceSettingJsonAnalytic(String json) {
        Gson gson = new Gson();
        AskOrderPriceSettingBean bean = gson.fromJson(json, AskOrderPriceSettingBean.class);
        return bean;
    }

    // 询价总价
    public static AskCalculatePriceBean askCalculatePriceJsonAnalytic(String json) {
        Gson gson = new Gson();
        AskCalculatePriceBean bean = gson.fromJson(json, AskCalculatePriceBean.class);
        return bean;
    }

    // 通用解析
    public static NormalBean normalJsonAnalytic(String json) {
        Gson gson = new Gson();
        NormalBean bean = gson.fromJson(json, NormalBean.class);
        return bean;
    }

    // 用户订单列表
    public static CustomerOrderListBean customerOrderListJsonAnalytic(String json) {
        Gson gson = new Gson();
        CustomerOrderListBean bean = gson.fromJson(json, CustomerOrderListBean.class);
        return bean;
    }

    // 询价/成团/包车设置 座位数
    public static List<SeatsListBean> seatsListJsonAnalytic(String json) {
        Gson gson = new Gson();
        JSONArray ja = null;
        List<SeatsListBean> seatsListBeens = new ArrayList<>();
        try {
            ja = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < ja.length(); i++) {
            SeatsListBean seatsListBean = null;
            try {
                seatsListBean = gson.fromJson(ja.getString(i), SeatsListBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            seatsListBeens.add(seatsListBean);
        }
        return seatsListBeens;
    }

    // 省份获取
    public static List<ProvinceBean> provincesListJsonAnalytic(String json) {
        Gson gson = new Gson();
        JSONArray ja = null;
        List<ProvinceBean> provinceBeens = new ArrayList<>();
        try {
            ja = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < ja.length(); i++) {
            ProvinceBean provinceBean = null;
            try {
                provinceBean = gson.fromJson(ja.getString(i), ProvinceBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            provinceBeens.add(provinceBean);
        }
        return provinceBeens;
    }

    // 城市与地点
    public static CityAndSiteBean cityAndSiteJsonAnalytic(String json) {
        Gson gson = new Gson();
        CityAndSiteBean bean = gson.fromJson(json, CityAndSiteBean.class);
        return bean;
    }

    // 顺风车订单
    public static OnWayOrderBean onWayOrderJsonAnalytic(String json) {
        Gson gson = new Gson();
        OnWayOrderBean bean = gson.fromJson(json, OnWayOrderBean.class);
        return bean;
    }

    // 接送机订单
    public static TakeAirOrderBean takeAirOrderJsonAnalytic(String json) {
        Gson gson = new Gson();
        TakeAirOrderBean bean = gson.fromJson(json, TakeAirOrderBean.class);
        return bean;
    }

    // 包车价格设置列表
    public static DriverCharterSettingListBean driverCharterSettingListJsonAnalytic(String json) {
        Gson gson = new Gson();
        DriverCharterSettingListBean bean = gson.fromJson(json, DriverCharterSettingListBean.class);
        return bean;
    }

    // 城市
    public static AllCityBean allCityBeanJsonAnalytic(String json) {
        Gson gson = new Gson();
        AllCityBean bean = gson.fromJson(json, AllCityBean.class);
        return bean;
    }

    // 车辆详情
    public static CarInfoBean carInfoJsonAnalytic(String json) {
        Gson gson = new Gson();
        CarInfoBean bean = gson.fromJson(json, CarInfoBean.class);
        return bean;
    }

    // 钱包信息解析
    public static UserPurseBean userPurseJsonAnalytic(String json) {
        Gson gson = new Gson();
        UserPurseBean bean = gson.fromJson(json, UserPurseBean.class);
        return bean;
    }

    // 优惠券解析
    public static CouponBean couponJsonAnalytic(String json) {
        Gson gson = new Gson();
        CouponBean bean = gson.fromJson(json, CouponBean.class);
        return bean;
    }

    // 提现记录解析
    public static WithDrawCashBean withDrawCashBeanJsonAnalytic(String json) {
        Gson gson = new Gson();
        WithDrawCashBean bean = gson.fromJson(json, WithDrawCashBean.class);
        return bean;
    }

    // 交易记录解析
    public static TradeNotesBean tradeNotesJsonAnalytic(String json) {
        Gson gson = new Gson();
        TradeNotesBean bean = gson.fromJson(json, TradeNotesBean.class);
        return bean;
    }

    // 银行卡列表解析
    public static BankCardListBean bankCardListJsonAnalytic(String json) {
        Gson gson = new Gson();
        BankCardListBean bean = gson.fromJson(json, BankCardListBean.class);
        return bean;
    }
}
