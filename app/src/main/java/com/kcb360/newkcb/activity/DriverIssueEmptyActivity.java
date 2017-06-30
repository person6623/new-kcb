package com.kcb360.newkcb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.base.MyApplication;
import com.kcb360.newkcb.base.StandardUtil;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.entity.CarInfoBean;
import com.kcb360.newkcb.entity.InMapSelectResultBean;
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
 * Created by xinshichao on 2017/5/23.
 * <p>
 * 车主发布空车
 */

public class DriverIssueEmptyActivity extends BaseActivity {

    // 布局相关
    // 返回
    private LinearLayout backLl;
    // 车牌号, 车辆大小, 待命城市, 区域, 开始时间, 结束时间
    private RelativeLayout carPlateRl, carSizeAllRl, stayCityAllRl, regionAllRl, stTimeAllRl, edTimeAllRl;
    // 发布
    private Button subBtn;
    // 车牌号, 待命城市, 区域, 开始时间, 结束时间
    private TextView plateNumTv, stayCityTv, regoinTv, stTimeTv, edTimeTv;
    // 车辆大小, 手机号, 超范围公里数, 超范围单价
    private EditText carSizeEt, phoneEt, outScopeEt, unitPriceEt;

    // 数据相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 发布空车线程
    private DriverIssueEmptyThread driverIssueEmptyThread;
    // 发布空车传参
    private Map<String, String> issueEmptyMap;
    // 发布空车返回
    private NormalBean issueEmptyBean;
    // 车辆信息线程
    private GetCarInfoThread getCarInfoThread;
    // 车辆信息返回存储
    private CarInfoBean carInfoBean;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(DriverIssueEmptyActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(DriverIssueEmptyActivity.this, issueEmptyBean.getContext(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String carInfoStr = "";
                    for (int i = 0; i < carInfoBean.getMyCarList().size(); i++) {
                        if (i == 0) {
                            carInfoStr = carInfoBean.getMyCarList().get(i).getPlateNum();
                        } else {
                            carInfoStr = carInfoStr + "," + carInfoBean.getMyCarList().get(i).getPlateNum();
                        }
                    }
                    DiyDialog.itemSelDialog(DriverIssueEmptyActivity.this, "请选择车辆", carInfoStr.split(",")
                            , new DiyDialog.OnItemSelectListener() {
                                @Override
                                public void onItemSelectListener(int position, String content) {
                                    plateNumTv.setText(content);
                                }
                            });
                    break;
                case 3:
                    if (carInfoBean == null) {
                        Toast.makeText(DriverIssueEmptyActivity.this, "未能获取车辆数据", Toast.LENGTH_SHORT).show();
                    } else if (carInfoBean.getMyCarList() == null || carInfoBean.getMyCarList().size() == 0) {
                        Toast.makeText(DriverIssueEmptyActivity.this, "您尚未添加车辆", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DriverIssueEmptyActivity.this, "未能获取车辆数据", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_driver_issue_empty;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backLl = findView(R.id.driver_issue_empty_back_ll);
        carPlateRl = findView(R.id.driver_issue_empty_car_plate_all_rl);
        carSizeAllRl = findView(R.id.driver_issue_empty_car_size_all_rl);
        stayCityAllRl = findView(R.id.driver_issue_empty_stay_city_all_rl);
        regionAllRl = findView(R.id.driver_issue_empty_region_all_rl);
        stTimeAllRl = findView(R.id.driver_issue_empty_st_time_all_rl);
        edTimeAllRl = findView(R.id.driver_issue_empty_ed_time_all_rl);

        subBtn = findView(R.id.driver_issue_empty_sub_btn);

//        pltaeEt = findView(R.id.driver_issue_empty_car_plate_et);
        carSizeEt = findView(R.id.driver_issue_empty_car_size_et);
//        phoneEt = findView(R.id.driver_issue_empty_car_phone_et);
        plateNumTv = findView(R.id.driver_issue_empty_title_car_plate_num_tv);
        stayCityTv = findView(R.id.driver_issue_empty_stay_city_tv);
        regoinTv = findView(R.id.driver_issue_empty_region_tv);
        stTimeTv = findView(R.id.driver_issue_empty_st_time_tv);
        edTimeTv = findView(R.id.driver_issue_empty_ed_time_tv);
        outScopeEt = findView(R.id.driver_issue_empty_out_scope_et);
        unitPriceEt = findView(R.id.driver_issue_empty_unit_price_et);

//        natureSp = findView(R.id.driver_issue_empty_car_nature_sp);
//        configSp = findView(R.id.driver_issue_empty_car_config_sp);


        backLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        regionAllRl.setVisibility(View.VISIBLE);
        regionAllRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NormalUtil.loginTimeRatio(DriverIssueEmptyActivity.this)) {
                    return;
                }
                startActivityForResult(new Intent(DriverIssueEmptyActivity.this, PossibleRegionActivity.class), 2);
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NormalUtil.loginTimeRatio(DriverIssueEmptyActivity.this)) {
                    return;
                }
                if (!doCheck()) {
                    return;
                }
                issueEmptyMap.put("plateNum", plateNumTv.getText().toString());
                issueEmptyMap.put("carSeats", carSizeEt.getText().toString());
//                issueEmptyMap.put("driverPhone", phoneEt.getText().toString());
                issueEmptyMap.put("freeStart", stTimeTv.getText().toString());
                issueEmptyMap.put("freeEnd", edTimeTv.getText().toString());
//                issueEmptyMap.put("runArea", "成都市");
                issueEmptyMap.put("beyondRound", outScopeEt.getText().toString());
                issueEmptyMap.put("beyondPrice", outScopeEt.getText().toString());
                driverIssueEmptyThread = new DriverIssueEmptyThread();
                es.execute(driverIssueEmptyThread);
            }
        });

        carPlateRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCarInfoThread = new GetCarInfoThread();
                es.execute(getCarInfoThread);
            }
        });

        stayCityAllRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.context, InMapSelectActivity.class);
                intent.putExtra(StandardUtil.MARKER_TYPE, StandardUtil.SINGULAR);
                startActivityForResult(intent, 1);
            }
        });

        stTimeAllRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiyDialog.dateOrTimeSelectDialog(DriverIssueEmptyActivity.this, new DiyDialog.OnTimeDataListener() {
                    @Override
                    public void onTimeDataListener(String date) {
                        stTimeTv.setText(date);
                    }
                });
            }
        });

        edTimeAllRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiyDialog.dateOrTimeSelectDialog(DriverIssueEmptyActivity.this, new DiyDialog.OnTimeDataListener() {
                    @Override
                    public void onTimeDataListener(String date) {
                        if (stTimeTv.getText().equals("") || stTimeTv.getText().equals("请选择时间")) {
                            edTimeTv.setText(date);
                            return;
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        try {
                            if (sdf.parse(date).getTime() < sdf.parse(stTimeTv.getText().toString()).getTime()) {
                                Toast.makeText(MyApplication.context, "设定时间小于出发时间", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            edTimeTv.setText(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        edTimeTv.setText(date);
                    }
                });
            }
        });

        issueEmptyMap = new HashMap<>();
//        ArrayAdapter<String> natureArrayAdapter;
//        final String[] natureArr = getResources().getStringArray(R.array.car_nature);
//        natureArrayAdapter = new ArrayAdapter<>(DriverIssueEmptyActivity.this, android.R.layout.simple_spinner_item, natureArr);
//        natureSp.setAdapter(natureArrayAdapter);
//        natureSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                issueEmptyMap.put("carNature", position + "");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        ArrayAdapter<String> configArrayAdapter;
//        final String[] configArr = getResources().getStringArray(R.array.car_config);
//        configArrayAdapter = new ArrayAdapter<>(DriverIssueEmptyActivity.this, android.R.layout.simple_spinner_item, configArr);
//        configSp.setAdapter(configArrayAdapter);
//        configSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                issueEmptyMap.put("carConfig", configArr[position]);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    private boolean doCheck() {
        if (plateNumTv.getText().toString().equals("")) {
            Toast.makeText(DriverIssueEmptyActivity.this, "请选择车辆", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (carSizeEt.getText().toString().equals("")) {
            Toast.makeText(DriverIssueEmptyActivity.this, "请输入座位数", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (!issueEmptyMap.containsKey("carNature")) {
//            Toast.makeText(DriverIssueEmptyActivity.this, "请选择车辆性质", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (!issueEmptyMap.containsKey("carConfig") || issueEmptyMap.get("carConfig").equals("请选择车辆配置")) {
//            Toast.makeText(DriverIssueEmptyActivity.this, "请选择车辆配置", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (phoneEt.getText().toString().equals("")) {
//            Toast.makeText(DriverIssueEmptyActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if (stayCityTv.getText().toString().equals("")) {
            Toast.makeText(DriverIssueEmptyActivity.this, "请选择待命城市", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (regoinTv.getText().toString().equals("")) {
            Toast.makeText(DriverIssueEmptyActivity.this, "请选择可跑区域", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (stTimeTv.getText().toString().equals("")) {
            Toast.makeText(DriverIssueEmptyActivity.this, "请选择空车开始时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edTimeTv.getText().toString().equals("")) {
            Toast.makeText(DriverIssueEmptyActivity.this, "请选择空车结束时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (outScopeEt.getText().toString().equals("")) {
            Toast.makeText(DriverIssueEmptyActivity.this, "请添加超范围", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (unitPriceEt.getText().toString().equals("")) {
            Toast.makeText(DriverIssueEmptyActivity.this, "请添加超范围单价", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<InMapSelectResultBean> beans;
        if (requestCode == 1 && data != null) {
            beans = data.getParcelableArrayListExtra("mapSelect");
            stayCityTv.setText(beans.get(0).getTitle());
            beans.get(0).setTitle(beans.get(0).getTitle().replace(" ", "-"));
            issueEmptyMap.put("waitPoint", beans.get(0).getTitle().substring(beans.get(0).getTitle().indexOf("-") + 1,
                    beans.get(0).getTitle().length()) + "-" +
                    beans.get(0).getTitle().substring(0, beans.get(0).getTitle().indexOf("-")) + "-" +
                    beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
        } else if (requestCode == 2 && data != null) {
            regoinTv.setText(data.getStringExtra("select"));
            issueEmptyMap.put("runRound", data.getStringExtra("scope"));
            issueEmptyMap.put("runArea", data.getStringExtra("select"));
        }
    }

    class DriverIssueEmptyThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(issueEmptyMap, UrlUtil.ADD_EMPTY_CAR);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    issueEmptyBean = JSONUtil.normalJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (issueEmptyBean != null) {
                handler.sendEmptyMessage(1);
            }
        }
    }

    class GetCarInfoThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(new HashMap<String, String>(), UrlUtil.FIND_MY_CAR_LIST);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    carInfoBean = JSONUtil.carInfoJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (carInfoBean != null && carInfoBean.getMyCarList().size() != 0) {
                handler.sendEmptyMessage(2);
            } else {
                handler.sendEmptyMessage(3);
            }
        }
    }
}
