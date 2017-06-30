package com.kcb360.newkcb.fragment;

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

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.activity.CustomerIssueOrderActivity;
import com.kcb360.newkcb.activity.CustomerIssueSuccessActivity;
import com.kcb360.newkcb.activity.DriverEmptyCarListActivity;
import com.kcb360.newkcb.activity.InMapSelectActivity;
import com.kcb360.newkcb.base.BaseFragment;
import com.kcb360.newkcb.base.MyApplication;
import com.kcb360.newkcb.base.StandardUtil;
import com.kcb360.newkcb.entity.InMapSelectResultBean;
import com.kcb360.newkcb.entity.NormalBean;
import com.kcb360.newkcb.net.ExceptionUtil;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.NormalUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/5/18.
 * <p>
 * 发布订单(后半)
 * (查找空车跳转)
 */

public class FormalOrderRearFragment extends BaseFragment {

    // 布局相关
    // 姓名, 手机, 报价, 行程详情
    private EditText nameEt, phoneEt, quoteEt, detailsRouteEt;
    // 发票, 回城路线
    private Switch invoiceSc, takeAirSc, routeOutSc;
    // 途经点, 终点, 预估价格, 返回上一界面
    private TextView passTv, edPlaceTv, reckonPriceTv, backToAlterTv;
    // 途径点选择, 结束地点选择, 回城路线不同, 接送机
    private RelativeLayout passPlaceAddRl, edPlaceAddRl, routeOutRl, takeAirRl;
    // 回城路线不同全布局,  报价
    private LinearLayout routeOutAllLl, quoteLl;
    // 查找空车
    private ImageView seekNullIv;
    // 提交
    private Button subBtn;

    // 数据相关
    // 发布成团传参map
    private Map<String, String> formalRearMap;
    // 成团订单thread
    private FormalOrderIssueThread formalOrderIssueThread;
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 发布询价返回
    private NormalBean issueNormalBean;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), issueNormalBean.getContext(), Toast.LENGTH_SHORT).show();
                    if (issueNormalBean.getCode() == 2) {
                        startActivity(new Intent(getActivity(), CustomerIssueSuccessActivity.class));
                    }
                    break;
                case 2:
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
        if (formalRearMap == null) {
            FormalOrderFrontFragment formalOrderFrontFragment = (FormalOrderFrontFragment) CustomerIssueOrderActivity.fragments
                    .get("formalFront");
            formalRearMap = formalOrderFrontFragment.getFormalFrontMap();
        }
    }

    @Override
    protected void intiView(View view, Bundle savedInstanceState) {
        nameEt = (EditText) view.findViewById(R.id.ask_order_rear_name_et);
        phoneEt = (EditText) view.findViewById(R.id.ask_order_rear_phone_et);
        quoteEt = (EditText) view.findViewById(R.id.ask_order_rear_quote_et);
        detailsRouteEt = (EditText) view.findViewById(R.id.ask_order_rear_details_route_et);

        invoiceSc = (Switch) view.findViewById(R.id.ask_order_rear_invoice_sc);
        takeAirSc = (Switch) view.findViewById(R.id.ask_order_rear_take_air_sc);
        routeOutSc = (Switch) view.findViewById(R.id.ask_order_rear_route_out_sc);

        passTv = (TextView) view.findViewById(R.id.ask_order_rear_pass_tv);
        edPlaceTv = (TextView) view.findViewById(R.id.ask_order_rear_ed_palce_tv);
        reckonPriceTv = (TextView) view.findViewById(R.id.ask_order_rear_reckon_price_tv);
        backToAlterTv = (TextView) view.findViewById(R.id.ask_order_rear_back_to_alter_tv);

        passPlaceAddRl = (RelativeLayout) view.findViewById(R.id.ask_order_rear_pass_all_rl);
        edPlaceAddRl = (RelativeLayout) view.findViewById(R.id.ask_order_rear_ed_palce_all_rl);
        routeOutRl = (RelativeLayout) view.findViewById(R.id.ask_order_rear_route_out_rl);
        takeAirRl = (RelativeLayout) view.findViewById(R.id.ask_order_rear_take_air_rl);

        routeOutAllLl = (LinearLayout) view.findViewById(R.id.ask_order_rear_route_out_all_ll);
        quoteLl = (LinearLayout) view.findViewById(R.id.ask_order_rear_quote_ll);

        seekNullIv = (ImageView) view.findViewById(R.id.ask_order_rear_seek_null_iv);

        subBtn = (Button) view.findViewById(R.id.ask_order_rear_sub_btn);

        invoiceSc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        takeAirSc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                if (CustomerIssueOrderActivity.fragments.containsKey("formalFront")) {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.customer_issue_order_content_fl,
                            CustomerIssueOrderActivity.fragments.get("formalFront")).commit();
                } else {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.customer_issue_order_content_fl,
                            new FormalOrderFrontFragment()).commit();
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

        edPlaceAddRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.context, InMapSelectActivity.class);
                intent.putExtra(StandardUtil.MARKER_TYPE, StandardUtil.SINGULAR);
                startActivityForResult(intent, 3);
            }
        });

        routeOutRl.setVisibility(View.VISIBLE);
        takeAirRl.setVisibility(View.VISIBLE);
        quoteLl.setVisibility(View.VISIBLE);

        seekNullIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DriverEmptyCarListActivity.class);
                intent.putExtra("role", StandardUtil.ROLE_CUSTOMER);
                startActivity(intent);
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
                formalRearMap.putAll(((AskOrderFrontFragment) CustomerIssueOrderActivity.fragments.get("askFront")).getAskFrontMap());
                formalRearMap.put("realName", nameEt.getText().toString());
                formalRearMap.put("customerPhone", phoneEt.getText().toString());
                if (invoiceSc.isChecked()) {
                    formalRearMap.put("isBill", "1");
                } else {
                    formalRearMap.put("isBill", "0");
                }
                if (takeAirSc.isChecked()) {
                    formalRearMap.put("isShuttle", "1");
                } else {
                    formalRearMap.put("isShuttle", "0");
                }
                formalRearMap.put("needCarNum", "1");
                formalRearMap.put("carLife", "1");
                formalRearMap.put("carBrand", "-1");
                formalRearMap.put("offerTerm", "-1");
                formalRearMap.put("isShuttle", "0");
                formalRearMap.put("carNature", "0");
                formalRearMap.put("chooseRoute", "-1");
                formalRearMap.put("puWay", "【phone-成团订单】");
                formalRearMap.put("orderPrice", quoteEt.getText().toString());

                formalOrderIssueThread = new FormalOrderIssueThread();
                es.execute(formalOrderIssueThread);
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
        if (quoteEt.getText().toString().equals("")) {
            Toast.makeText(MyApplication.context, "请输入报价", Toast.LENGTH_SHORT).show();
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
            formalRearMap.put("newWayPoints", allMapStr);
        } else if (requestCode == 3 && data != null) {
            beans = data.getParcelableArrayListExtra("mapSelect");
            edPlaceTv.setText(beans.get(0).getTitle());
            formalRearMap.put("newDestination", beans.get(0).getTitle() + "-" +
                    beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
        }
    }

    class FormalOrderIssueThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(formalRearMap, UrlUtil.PU_ASK_ORDER);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    issueNormalBean = JSONUtil.normalJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (issueNormalBean != null) {
                handler.sendEmptyMessage(1);
            }
        }
    }
}
