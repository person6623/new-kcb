package com.kcb360.newkcb.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.route.DriveRouteResult;
import com.kcb360.newkcb.R;
import com.kcb360.newkcb.activity.CustomerIssueOrderActivity;
import com.kcb360.newkcb.activity.CustomerIssueSuccessActivity;
import com.kcb360.newkcb.activity.DriverEmptyCarListActivity;
import com.kcb360.newkcb.activity.InMapSelectActivity;
import com.kcb360.newkcb.base.BaseFragment;
import com.kcb360.newkcb.base.MyApplication;
import com.kcb360.newkcb.base.StandardUtil;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.entity.AskCalculatePriceBean;
import com.kcb360.newkcb.entity.AskOrderPriceSettingBean;
import com.kcb360.newkcb.entity.InMapSelectResultBean;
import com.kcb360.newkcb.entity.NormalBean;
import com.kcb360.newkcb.net.AMapUtil;
import com.kcb360.newkcb.net.ExceptionUtil;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.NormalUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/5/18.
 * <p>
 * 询价订单(后半)
 */

public class AskOrderRearFragment extends BaseFragment {

    // 布局相关
    // 姓名, 手机, 报价, 行程详情
    private EditText nameEt, phoneEt, quoteEt, detailsRouteEt;
    // 发票, 回城路线
    private Switch invoiceSc, routeOutSc;
    // 途经点, 终点, 预估价格, 返回上一界面
    private TextView passTv, edPlaceTv, reckonPriceTv, backToAlterTv, hintTv;
    // 途径点选择, 结束地点选择, 回城路线不同, 预估费用
    private RelativeLayout passPlaceAddRl, edPlaceAddRl, routeOutRl, reckonPriceAllRl;
    // 回城路线不同全布局
    private LinearLayout routeOutAllLl;
    // 查找空车
    private ImageView seekNullIv;
    // 查询价格, 提交
    private Button priceAskBtn, subBtn;

    // 数据相关
    // 传参map
    private Map<String, String> askRearMap;
    // 发布询价线程
    private AskOrderIssueThread askOrderIssueThread;
    // 发布询价返回
    private NormalBean issueAskBean;
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 获取价格计算线程
    private GetPriceSettingThread getPriceSettingThread;
    // 获取价格计算传参
    private Map<String, String> getPriceSettingMap;
    // 包车价格数据返回
    private AskOrderPriceSettingBean askOrderPriceSettingBean;
    // 计算总价线程
    private CalculatePriceThread calculatePriceThread;
    // 计算总价传参
    private Map<String, String> calculatePriceMap;
    // 计算总价返回
    private AskCalculatePriceBean askCalculatePriceBean;

    // 高德地图路线规划
    // 实例高德地图工具类
    private AMapUtil aMapUtil = new AMapUtil();
    // 地点point集
    private List<String> points;
    // 返回数据保存
    private DriveRouteResult keepDrr;
    // 请求尝试次数控制
    private int isTime = 3;

    // 数据获取dialog
    private Dialog dg;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dg != null && dg.isShowing()) {
                dg.dismiss();
            }
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), issueAskBean.getContext(), Toast.LENGTH_SHORT).show();
                    if (issueAskBean.getCode() == 2) {
                        startActivity(new Intent(getActivity(), CustomerIssueSuccessActivity.class));
                    }
                    break;
                case 2:
                    break;
                case 3:
                    if (askOrderPriceSettingBean.getCode() != 1) {
                        Toast.makeText(getActivity(), askOrderPriceSettingBean.getContext(), Toast.LENGTH_SHORT).show();
                    } else {
                        dg = ProgressDialog.show(getActivity(), "", "计算总价中...");
                        dg.setCanceledOnTouchOutside(false);
                        dg.show();
                        points = new ArrayList<>();
                        final String driverStPoint = askOrderPriceSettingBean.getLonAndLat().replace(",", "|");
                        points.add(driverStPoint);
                        points.add(askRearMap.get("lonAndLat").split(",")[0]);
                        points.add(askRearMap.get("lonAndLat").split(",")[1]);
                        aMapUtil.routeSearchMethod(MyApplication.context, points, new AMapUtil.RSMethodListener() {
                            @Override
                            public void rsMethod(int requestCode, DriveRouteResult driveRouteResult) {
                                if (requestCode == 1000 && driveRouteResult.getPaths().size() != 0) {
                                    final DriveRouteResult driverPassCustomerToFinal = driveRouteResult;
                                    points.add(driverStPoint);
                                    points.add(askRearMap.get("lonAndLat").split(",")[1]);
                                    aMapUtil.routeSearchMethod(MyApplication.context, points, new AMapUtil.RSMethodListener() {
                                        @Override
                                        public void rsMethod(int requestCode, DriveRouteResult driveRouteResult) {
                                            if (requestCode == 1000 && driveRouteResult.getPaths().size() != 0) {
                                                int carSeats = Integer.parseInt(askRearMap.get("carSeats"));
                                                int power;
                                                if (carSeats <= 7) {
                                                    power = 1;
                                                } else if (carSeats >= 8 && carSeats <= 19) {
                                                    power = 2;
                                                } else if (carSeats >= 20 && carSeats <= 39) {
                                                    power = 3;
                                                } else {
                                                    power = 4;
                                                }
                                                float allPrice = keepDrr.getPaths().get(0).getDistance() / 1000 * ((float) askOrderPriceSettingBean.getOilWear())
                                                        + keepDrr.getPaths().get(0).getTolls() * power
                                                        + Float.parseFloat(askOrderPriceSettingBean.getPrice());
                                                if (driverPassCustomerToFinal.getPaths().get(0).getDistance()
                                                        - driveRouteResult.getPaths().get(0).getDistance() - askOrderPriceSettingBean.getFreeKm() > 0) {
                                                    allPrice = allPrice + (driverPassCustomerToFinal.getPaths().get(0).getDistance()
                                                            - driveRouteResult.getPaths().get(0).getDistance() - askOrderPriceSettingBean.getFreeKm())
                                                            * askOrderPriceSettingBean.getFreeKmPrice();
                                                }
                                                calculatePriceMap = new HashMap<>();
                                                calculatePriceMap.put("payMoney", allPrice + "");
                                                if (invoiceSc.isChecked()) {
                                                    calculatePriceMap.put("isBill", "1");
                                                } else {
                                                    calculatePriceMap.put("isBill", "0");
                                                }
                                                calculatePriceThread = new CalculatePriceThread();
                                                es.execute(calculatePriceThread);
                                            } else {
                                                dg.dismiss();
                                                Toast.makeText(getActivity(), "未能获取路线信息,请重新尝试", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    dg.dismiss();
                                    Toast.makeText(getActivity(), "未能获取路线信息,请重新尝试", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    break;
                case 4:
                    Toast.makeText(getActivity(), askCalculatePriceBean.getContext(), Toast.LENGTH_SHORT).show();
                    if (askCalculatePriceBean.getCode() == 1) {
                        reckonPriceTv.setText("¥ " + askCalculatePriceBean.getPayMoney() + " 元");
                    } else {
                        reckonPriceTv.setText("未能获取");
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.fragment_ask_order_rear;
    }

    @Override
    protected void intiData() {
        if (askRearMap == null) {
            AskOrderFrontFragment askOrderFrontFragment = (AskOrderFrontFragment) CustomerIssueOrderActivity.fragments.get("askFront");
            askRearMap = askOrderFrontFragment.getAskFrontMap();
        }
    }

    private void prcieSetting() {
        dg = ProgressDialog.show(getActivity(), "", "正在获取单价信息...");
        dg.setCanceledOnTouchOutside(false);
        dg.show();
        getPriceSettingMap = new HashMap<>();
        getPriceSettingMap.put("areaName",
                askRearMap.get("departPlace").substring(0, askRearMap.get("departPlace").indexOf(" ")));
        getPriceSettingMap.put("endArea",
                askRearMap.get("destination").substring(0, askRearMap.get("destination").indexOf(" ")));
        getPriceSettingMap.put("carSeats", askRearMap.get("carSeats"));
        getPriceSettingMap.put("sTime", askRearMap.get("useCarStart"));
        getPriceSettingMap.put("eTime", askRearMap.get("useCarEnd"));
        // 整合坐标点
        points = new ArrayList<>();
        points.add(askRearMap.get("lonAndLat").split(",")[0]);
        if (askRearMap.containsKey("newDestination")) {
            points.add(askRearMap.get("newDestination").split("-")[1]);
            if (askRearMap.containsKey("wayPoints")) {
                String[] wayPointArr = askRearMap.get("wayPoints").split(",");
                for (int i = 0; i < wayPointArr.length; i++) {
                    points.add(wayPointArr[i].split("-")[1]);
                }
            }
            points.add(askRearMap.get("lonAndLat").split(",")[1]);
            if (askRearMap.containsKey("newWayPoints")) {
                String[] wayPointArr = askRearMap.get("newWayPoints").split(",");
                for (int i = 0; i < wayPointArr.length; i++) {
                    points.add(wayPointArr[i].split("-")[1]);
                }
            }
        } else {
            // 返回时终点依然是起点坐标
            points.add(askRearMap.get("lonAndLat").split(",")[0]);
            if (askRearMap.containsKey("wayPoints")) {
                String[] wayPointArr = askRearMap.get("wayPoints").split(",");
                for (int i = 0; i < wayPointArr.length; i++) {
                    points.add(wayPointArr[i].split("-")[1]);
                }
            }
            points.add(askRearMap.get("lonAndLat").split(",")[1]);
            if (askRearMap.containsKey("wayPoints")) {
                String[] wayPointArr = askRearMap.get("wayPoints").split(",");
                for (int i = wayPointArr.length; 0 < i; i--) {
                    points.add(wayPointArr[i - 1].split("-")[1]);
                }
            }
        }
        aMapUtil.routeSearchMethod(MyApplication.context, points, new AMapUtil.RSMethodListener() {
            @Override
            public void rsMethod(int requestCode, DriveRouteResult driveRouteResult) {
                if (requestCode == 1000 && driveRouteResult.getPaths().size() != 0) {
                    keepDrr = driveRouteResult;
                    getPriceSettingMap.put("km", (driveRouteResult.getPaths().get(0).getDistance() / 1000) + "");
                    getPriceSettingThread = new GetPriceSettingThread();
                    es.execute(getPriceSettingThread);
                } else {
                    if (isTime > 0) {
                        Toast.makeText(MyApplication.context, "未能获取路线,正在重新尝试", Toast.LENGTH_SHORT).show();
                        aMapUtil.reRouteSearch();
                        isTime--;
                    } else {
                        Toast.makeText(MyApplication.context, "获取路线失败", Toast.LENGTH_SHORT).show();
                        dg.dismiss();
                        isTime = 3;
                    }
                }
            }
        });
    }

//    // 此处也是发票需否的请求判断
//    private void calculatePrice() {
//
//    }

    @Override
    protected void intiView(View view, Bundle savedInstanceState) {
        nameEt = (EditText) view.findViewById(R.id.ask_order_rear_name_et);
        phoneEt = (EditText) view.findViewById(R.id.ask_order_rear_phone_et);
        quoteEt = (EditText) view.findViewById(R.id.ask_order_rear_quote_et);
        detailsRouteEt = (EditText) view.findViewById(R.id.ask_order_rear_details_route_et);

        invoiceSc = (Switch) view.findViewById(R.id.ask_order_rear_invoice_sc);
        routeOutSc = (Switch) view.findViewById(R.id.ask_order_rear_route_out_sc);

        passTv = (TextView) view.findViewById(R.id.ask_order_rear_pass_tv);
        edPlaceTv = (TextView) view.findViewById(R.id.ask_order_rear_ed_palce_tv);
        reckonPriceTv = (TextView) view.findViewById(R.id.ask_order_rear_reckon_price_tv);
        backToAlterTv = (TextView) view.findViewById(R.id.ask_order_rear_back_to_alter_tv);
        hintTv = (TextView) view.findViewById(R.id.ask_order_rear_hint_tv);

        passPlaceAddRl = (RelativeLayout) view.findViewById(R.id.ask_order_rear_pass_all_rl);
        edPlaceAddRl = (RelativeLayout) view.findViewById(R.id.ask_order_rear_ed_palce_all_rl);
        routeOutRl = (RelativeLayout) view.findViewById(R.id.ask_order_rear_route_out_rl);
        reckonPriceAllRl = (RelativeLayout) view.findViewById(R.id.ask_order_rear_reckon_price_all_rl);

        routeOutAllLl = (LinearLayout) view.findViewById(R.id.ask_order_rear_route_out_all_ll);

        seekNullIv = (ImageView) view.findViewById(R.id.ask_order_rear_seek_null_iv);

        priceAskBtn = (Button) view.findViewById(R.id.ask_order_rear_price_ask_btn);
        subBtn = (Button) view.findViewById(R.id.ask_order_rear_sub_btn);


        invoiceSc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        routeOutSc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    routeOutAllLl.setVisibility(View.VISIBLE);
                } else {
                    routeOutAllLl.setVisibility(View.GONE);
                }
            }
        });

        backToAlterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CustomerIssueOrderActivity.fragments.containsKey("askFront")) {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.customer_issue_order_content_fl,
                            CustomerIssueOrderActivity.fragments.get("askFront")).commit();
                } else {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.customer_issue_order_content_fl,
                            new AskOrderFrontFragment()).commit();
                }
            }
        });

        passPlaceAddRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.context, InMapSelectActivity.class);
                intent.putExtra(StandardUtil.MARKER_TYPE, StandardUtil.PLURAL);
                startActivityForResult(intent, 2);
            }
        });

        hintTv.setVisibility(View.VISIBLE);

        edPlaceAddRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.context, InMapSelectActivity.class);
                intent.putExtra(StandardUtil.MARKER_TYPE, StandardUtil.SINGULAR);
                startActivityForResult(intent, 3);
            }
        });

        reckonPriceAllRl.setVisibility(View.VISIBLE);

        routeOutRl.setVisibility(View.VISIBLE);

        seekNullIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DriverEmptyCarListActivity.class);
                intent.putExtra("role", StandardUtil.ROLE_CUSTOMER);
                startActivity(intent);
            }
        });

        priceAskBtn.setVisibility(View.VISIBLE);
        priceAskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doCheck()) {
                    return;
                }
                prcieSetting();
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NormalUtil.loginTimeRatio(getActivity())) {
                    return;
                }
                if (!doCheck()) {
                    return;
                }
                dg = ProgressDialog.show(getActivity(), "", "发布订单中...");
                dg.setCanceledOnTouchOutside(false);
                dg.show();
                askRearMap.putAll(((AskOrderFrontFragment) CustomerIssueOrderActivity.fragments.get("askFront")).getAskFrontMap());
                askRearMap.put("realName", nameEt.getText().toString());
                askRearMap.put("customerPhone", phoneEt.getText().toString());
                if (invoiceSc.isChecked()) {
                    askRearMap.put("isBill", "1");
                } else {
                    askRearMap.put("isBill", "0");
                }
                askRearMap.put("needCarNum", "1");
                askRearMap.put("carLife", "1");
                askRearMap.put("carBrand", "-1");
                askRearMap.put("offerTerm", "-1");
                askRearMap.put("isShuttle", "0");
                askRearMap.put("carNature", "0");
                askRearMap.put("chooseRoute", "-1");
                askRearMap.put("puWay", "【phone-询价订单】");
                askRearMap.put("orderPrice", "0");

                askOrderIssueThread = new AskOrderIssueThread();
                es.execute(askOrderIssueThread);
            }
        });
    }

    private boolean doCheck() {
        if (nameEt.getText().toString().equals("")) {
            Toast.makeText(MyApplication.context, "请输入姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneEt.getText().toString().equals("") && phoneEt.getText().length() > 10) {
            Toast.makeText(MyApplication.context, "请输入正确手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (routeOutSc.isChecked()) {
            if (edPlaceTv.getText().toString().equals("")) {
                Toast.makeText(MyApplication.context, "请选择返程终点", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (detailsRouteEt.getText().toString().equals("")) {
            Toast.makeText(MyApplication.context, "请选择填写详细行程", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<InMapSelectResultBean> beans;
        if (requestCode == 2) {
            beans = data.getParcelableArrayListExtra("mapSelect");
            String titleStr = "";
            String allMapStr = "";
            for (int i = 0; i < beans.size(); i++) {
                if (titleStr.equals("")) {
                    titleStr = titleStr + beans.get(i).getTitle();
                } else {
                    titleStr = titleStr + "-" + beans.get(i).getTitle();
                }
                if (allMapStr.equals("")) {
                    allMapStr = beans.get(i).getTitle() + "-" + beans.get(i).getLatLng().longitude + "|" + beans.get(i).getLatLng().latitude;
                } else {
                    allMapStr = allMapStr + "," +
                            beans.get(i).getTitle() + "-" + beans.get(i).getLatLng().longitude + "|" + beans.get(i).getLatLng().latitude;
                }
            }
            passTv.setText(titleStr);
            askRearMap.put("newWayPoints", allMapStr);
        } else if (requestCode == 3 && data != null) {
            beans = data.getParcelableArrayListExtra("mapSelect");
            edPlaceTv.setText(beans.get(0).getTitle());
            askRearMap.put("newDestination", beans.get(0).getTitle() + "-" +
                    beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
        }
    }

    class AskOrderIssueThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(askRearMap, UrlUtil.PU_ASK_ORDER);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    issueAskBean = JSONUtil.normalJsonAnalytic(res);
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

    class GetPriceSettingThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().NoCookieRequest(getPriceSettingMap, UrlUtil.FIND_TOUR);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    return;
                } else {
                    askOrderPriceSettingBean = JSONUtil.AskOrderPriceSettingJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (askOrderPriceSettingBean != null) {
                handler.sendEmptyMessage(3);
            }
        }
    }

    class CalculatePriceThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().NoCookieRequest(calculatePriceMap, UrlUtil.VI_PRICE);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    askCalculatePriceBean = JSONUtil.askCalculatePriceJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (askOrderPriceSettingBean != null) {
                handler.sendEmptyMessage(4);
            }
        }
    }
}
