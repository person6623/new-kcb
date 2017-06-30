package com.kcb360.newkcb.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.base.MyApplication;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.entity.AllCityBean;
import com.kcb360.newkcb.entity.DriverCharterSettingListBean;
import com.kcb360.newkcb.entity.NormalBean;
import com.kcb360.newkcb.net.ExceptionUtil;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.NormalUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/5/25.
 * <p>
 * 司机包车价格设置
 */

public class DriverCharterSettingActivity extends BaseActivity {

    // 布局相关
    // RadioGroup
    private RadioGroup typeRg;
    // radiobutton
    private RadioButton normalRb, spotAreaRb;
    // 返回
    private LinearLayout backLl;
    // 待命城市, 开始时间, 结束时间
    private RelativeLayout stayCityAllRl, stRl, edRl;
    // 待命城市&&所在景区title, 开始时间, 结束时间
    private TextView waitPointTitleTv, waitPointTv, stTv, edTv;
    // 车辆座位数, 公里油耗, 免费范围, 公里单价, 50公里以下日价, 100公里以下日价, 150以下, 200以下, 250以下, 300以下, 350以下, 假日上浮比
    private EditText seatsEt, consumeEt, outScopeEt, kmPriceEt, dayKmEt, dayOneKmEt, dayOneHalfKmEt, dayTweKmEt, dayTweHalfKmEt, dayThreeKmEt, dayThreeHalfEt, dayOffEt;
    // 提交
    private Button subBtn;

    // 数据相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 提交出车设置线程
    private SubDriverCharterSettingThread subDriverCharterSettingThread;
    // 提交出车设置参数
    private Map<String, String> driverCharterMap;
    // 返回值存储
    private NormalBean driverCharterBean;
    // 获取所有城市线程
    private GetAllCityThread getAllCityThread;
    // 获取城市传参
    private Map<String, String> getAllCityMap;
    // 获取城市返回值存储
    private AllCityBean allCityBean;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(DriverCharterSettingActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(DriverCharterSettingActivity.this, driverCharterBean.getContext(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String cityStr = "";
                    for (int i = 0; i < allCityBean.getCityList().size(); i++) {
                        if (i == 0) {
                            cityStr = allCityBean.getCityList().get(0).getCityName();
                        } else {
                            cityStr = cityStr + "," + allCityBean.getCityList().get(i).getCityName();
                        }
                    }
                    DiyDialog.itemSelDialog(DriverCharterSettingActivity.this, "请选择城市", cityStr.split(",")
                            , new DiyDialog.OnItemSelectListener() {
                                @Override
                                public void onItemSelectListener(int position, String content) {
                                    waitPointTv.setText(content);
                                }
                            });
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_driver_charter_setting;
    }

    @Override
    protected void initData() {
        driverCharterMap = new HashMap<>();
        if (getIntent().getParcelableExtra("charterSetting") != null) {
            DriverCharterSettingListBean.RowsBean bean = getIntent().getParcelableExtra("charterSetting");
            waitPointTv.setText(bean.getAreaName());
            seatsEt.setText(bean.getCarSeats() + "");
            consumeEt.setText(bean.getOilWear() + "");
            for (int i = 0; i < bean.getCharterPrice().split(",").length; i++) {
                String dayPrice = bean.getCharterPrice().split(",")[i];
                if (dayPrice.split("-")[0].equals("50")) {
                    dayKmEt.setText(dayPrice.split("-")[1]);
                }
                if (dayPrice.split("-")[0].equals("100")) {
                    dayOneKmEt.setText(dayPrice.split("-")[1]);
                }
                if (dayPrice.split("-")[0].equals("150")) {
                    dayOneHalfKmEt.setText(dayPrice.split("-")[1]);
                }
                if (dayPrice.split("-")[0].equals("200")) {
                    dayTweKmEt.setText(dayPrice.split("-")[1]);
                }
                if (dayPrice.split("-")[0].equals("250")) {
                    dayTweHalfKmEt.setText(dayPrice.split("-")[1]);
                }
                if (dayPrice.split("-")[0].equals("300")) {
                    dayThreeKmEt.setText(dayPrice.split("-")[1]);
                }
                if (dayPrice.split("-")[0].equals("350")) {
                    dayThreeHalfEt.setText(dayPrice.split("-")[1]);
                }
            }
            outScopeEt.setText(bean.getFreeKm() + "");
            kmPriceEt.setText(bean.getMoreFreeKmPrice() + "");
            stTv.setText(NormalUtil.timeToDate(bean.getStartTime().getTime()));
            edTv.setText(NormalUtil.timeToDate(bean.getEndTime().getTime()));
            dayOffEt.setText(bean.getFeastRatio() + "");
            // 修改必加id
            driverCharterMap.put("toucId", bean.getId() + "");
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backLl = findView(R.id.driver_charter_setting_back_ll);

        typeRg = findView(R.id.driver_charter_setting_rg);
        normalRb = findView(R.id.driver_charter_setting_normal_rb);
        spotAreaRb = findView(R.id.driver_charter_setting_special_scenic_spot_area_rb);

        stayCityAllRl = findView(R.id.driver_charter_setting_stay_city_all_rl);
        stRl = findView(R.id.driver_charter_setting_st_time_all_rl);
        edRl = findView(R.id.driver_charter_setting_ed_time_all_rl);

        waitPointTitleTv = findView(R.id.driver_charter_setting_position_title_tv);
        waitPointTv = findView(R.id.driver_charter_setting_stay_city_tv);
        stTv = findView(R.id.driver_charter_setting_st_time_tv);
        edTv = findView(R.id.driver_charter_setting_ed_time_tv);

        seatsEt = findView(R.id.driver_charter_setting_seats_et);
        consumeEt = findView(R.id.driver_charter_setting_consume_et);
        outScopeEt = findView(R.id.driver_charter_setting_out_scope_tv);
        kmPriceEt = findView(R.id.driver_charter_setting_km_price_tv);

        dayKmEt = findView(R.id.driver_charter_setting_day_km_et);
        dayOneKmEt = findView(R.id.driver_charter_setting_day_one_h_km_et);
        dayOneHalfKmEt = findView(R.id.driver_charter_setting_day_one_half_h_km_et);
        dayTweKmEt = findView(R.id.driver_charter_setting_day_twe_h_km_et);
        dayTweHalfKmEt = findView(R.id.driver_charter_setting_day_twe_half_h_km_et);
        dayThreeKmEt = findView(R.id.driver_charter_setting_day_three_h_km_et);
        dayThreeHalfEt = findView(R.id.driver_charter_setting_day_three_half_h_km_et);

        dayOffEt = findView(R.id.driver_charter_setting_day_off_et);

        subBtn = findView(R.id.driver_charter_setting_sub_btn);

        backLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        typeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.driver_charter_setting_normal_rb:
                        waitPointTitleTv.setText("待命城市");
                        normalRb.setBackgroundColor(0xff999999);
                        spotAreaRb.setBackgroundColor(0xffFFFFFF);
                        break;
                    case R.id.driver_charter_setting_special_scenic_spot_area_rb:
                        waitPointTitleTv.setText("选择景区");
                        normalRb.setBackgroundColor(0xffFFFFFF);
                        spotAreaRb.setBackgroundColor(0xff999999);
                        break;
                }
            }
        });

        stayCityAllRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllCity();
            }
        });

        stRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiyDialog.dateOrTimeSelectDialog(DriverCharterSettingActivity.this, new DiyDialog.OnTimeDataListener() {
                    @Override
                    public void onTimeDataListener(String date) {
                        stTv.setText(date);
                        driverCharterMap.put("startTime", date);
                    }
                });
            }
        });

        edRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiyDialog.dateOrTimeSelectDialog(DriverCharterSettingActivity.this, new DiyDialog.OnTimeDataListener() {
                    @Override
                    public void onTimeDataListener(String date) {
                        if (stTv.getText().equals("") || stTv.getText().equals("请选择时间")) {
                            edTv.setText(date);
                            return;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        try {
                            if (sdf.parse(date).getTime() < sdf.parse(stTv.getText().toString()).getTime()) {
                                Toast.makeText(MyApplication.context, "设定时间小于出发时间", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            edTv.setText(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        edTv.setText(date);
                        driverCharterMap.put("endTime", date);
                    }
                });
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doCheck()) {
                    return;
                }
                if (!NormalUtil.loginTimeRatio(DriverCharterSettingActivity.this)) {
                    Toast.makeText(DriverCharterSettingActivity.this, "登录有误,请重新登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dayKmEt.getText().toString().equals("") &&
                        dayOneKmEt.getText().toString().equals("") &&
                        dayOneHalfKmEt.getText().toString().equals("") &&
                        dayTweKmEt.getText().toString().equals("") &&
                        dayTweHalfKmEt.getText().toString().equals("") &&
                        dayThreeKmEt.getText().toString().equals("") &&
                        dayThreeHalfEt.getText().toString().equals("")) {
                    Toast.makeText(DriverCharterSettingActivity.this, "至少填写一条日行价格", Toast.LENGTH_SHORT).show();
                }
                String dayPrice = "";
                if (!dayKmEt.getText().toString().equals("")) {
                    dayPrice = dayPrice + ",50-" + dayKmEt.getText().toString();
                }
                if (!dayOneKmEt.getText().toString().equals("")) {
                    dayPrice = dayPrice + ",100-" + dayOneKmEt.getText().toString();
                }
                if (!dayOneHalfKmEt.getText().toString().equals("")) {
                    dayPrice = dayPrice + ",150-" + dayOneHalfKmEt.getText().toString();
                }
                if (!dayTweKmEt.getText().toString().equals("")) {
                    dayPrice = dayPrice + ",200-" + dayTweKmEt.getText().toString();
                }
                if (!dayTweHalfKmEt.getText().toString().equals("")) {
                    dayPrice = dayPrice + ",250-" + dayTweHalfKmEt.getText().toString();
                }
                if (!dayThreeKmEt.getText().toString().equals("")) {
                    dayPrice = dayPrice + ",300-" + dayThreeKmEt.getText().toString();
                }
                if (!dayThreeHalfEt.getText().toString().equals("")) {
                    dayPrice = dayPrice + ",350-" + dayThreeHalfEt.getText().toString();
                }
                if (dayPrice.indexOf(",") == 0) {
                    dayPrice = dayPrice.substring(1, dayPrice.length());
                }
                SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                driverCharterMap.put("account", sp.getString("_cName", ""));
                driverCharterMap.put("charterPrice", dayPrice);
                driverCharterMap.put("areaType", "0");
                driverCharterMap.put("areaName", waitPointTv.getText().toString());
                driverCharterMap.put("carSeats", seatsEt.getText().toString());
                driverCharterMap.put("oilWear", consumeEt.getText().toString());
                driverCharterMap.put("freeKm", outScopeEt.getText().toString());
                driverCharterMap.put("moreFreeKmPrice", kmPriceEt.getText().toString());
                driverCharterMap.put("startTime", stTv.getText().toString());
                driverCharterMap.put("endTime", edTv.getText().toString());
                driverCharterMap.put("feastRatio", dayOffEt.getText().toString());
                subDriverCharterSettingThread = new SubDriverCharterSettingThread();
                es.execute(subDriverCharterSettingThread);
            }
        });
    }

    private boolean doCheck() {
        if (waitPointTv.equals("")) {
            Toast.makeText(this, "请选择待命地点", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (seatsEt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写座位数", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (consumeEt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写每公里油耗", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dayKmEt.getText().toString().equals("") &&
                dayOneKmEt.getText().toString().equals("") &&
                dayOneHalfKmEt.getText().toString().equals("") &&
                dayTweKmEt.getText().toString().equals("") &&
                dayTweHalfKmEt.getText().toString().equals("") &&
                dayThreeKmEt.getText().toString().equals("") &&
                dayThreeHalfEt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写日价格(至少一项)", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (outScopeEt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写免费接送范围公里数", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (kmPriceEt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写超范围公里单价", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (stTv.getText().toString().equals("")) {
            Toast.makeText(this, "请选择包车设置开始时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edTv.getText().toString().equals("")) {
            Toast.makeText(this, "请选择包车设置结束时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dayOffEt.getText().toString().equals("")) {
            Toast.makeText(this, "请输入假日价格上浮比例", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getAllCity() {
        if (getAllCityMap == null) {
            getAllCityMap = new HashMap<>();
        } else {
            getAllCityMap.clear();
        }
        getAllCityThread = new GetAllCityThread();
        es.execute(getAllCityThread);
    }

    class SubDriverCharterSettingThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(driverCharterMap, UrlUtil.AD_UPDE_TOUC);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    driverCharterBean = JSONUtil.normalJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            handler.sendEmptyMessage(1);
        }
    }

    class GetAllCityThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().NoCookieRequest(getAllCityMap, UrlUtil.GET_ALL_CITY);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    allCityBean = JSONUtil.allCityBeanJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            handler.sendEmptyMessage(2);
        }
    }
}
