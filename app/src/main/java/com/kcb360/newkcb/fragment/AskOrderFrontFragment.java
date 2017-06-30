package com.kcb360.newkcb.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.activity.CustomerIssueOrderActivity;
import com.kcb360.newkcb.activity.DriverEmptyCarListActivity;
import com.kcb360.newkcb.activity.InMapSelectActivity;
import com.kcb360.newkcb.base.BaseFragment;
import com.kcb360.newkcb.base.MyApplication;
import com.kcb360.newkcb.base.StandardUtil;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.entity.InMapSelectResultBean;
import com.kcb360.newkcb.entity.SeatsListBean;
import com.kcb360.newkcb.net.ExceptionUtil;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/5/18.
 * <p>
 * 询价订单(前半)
 */

public class AskOrderFrontFragment extends BaseFragment {

    // 布局相关
    // 上车地点, 途经点, 下车点, 开始时间, 结束时间, 选择车辆, 询价提示
    private TextView stPlaceTv, passTv, edPlaceTv, stTimeTv, edTimeTv, selectCarTv, hintTv;
    // 上车地点添加, 途经点添加, 下车地点添加, 开始时间添加, 结束时间添加, 车辆规格
    private RelativeLayout stPlaceAddRl, passAddRl, edPlaceAddRl, stTimeRl, edTimeRl, customerNumRl;
    // 查找空车
    private ImageView seekNullIv;
    // 下一步
    private Button nextBtn;

    // 数据相关
    // 询价前页map
    private Map<String, String> askFrontMap;
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 线程
    private GetSeatsListThread getSeatsListThread;
    // 座位数据存储
    private List<SeatsListBean> seatsListBeans;
    // dialog
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
                    String seats = "";
                    for (int i = 0; i < seatsListBeans.size(); i++) {
                        if (i == 0) {
                            seats = seatsListBeans.get(i).getCarSeats() + "";
                        } else {
                            seats = seats + "," + seatsListBeans.get(i).getCarSeats();
                        }
                    }
                    DiyDialog.itemSelDialog(getActivity(), "请选择座位数", seats.split(","), new DiyDialog.OnItemSelectListener() {
                        @Override
                        public void onItemSelectListener(int position, String content) {
                            selectCarTv.setText(content);
                            askFrontMap.put("carSeats", content);
                            askFrontMap.put("seatNum", "1");
                        }
                    });
                    break;
                case 2:
                    Toast.makeText(getActivity(), "未能获取座位数信息", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.fragment_ask_order_front;
    }

    public Map<String, String> getAskFrontMap() {
        return askFrontMap;
    }

    @Override
    protected void intiData() {
        askFrontMap = new HashMap<>();
    }

    @Override
    protected void intiView(View view, Bundle savedInstanceState) {
        stPlaceTv = (TextView) view.findViewById(R.id.ask_order_front_st_place_tv);
        passTv = (TextView) view.findViewById(R.id.ask_order_front_pass_tv);
        edPlaceTv = (TextView) view.findViewById(R.id.ask_order_front_ed_place_tv);
        stTimeTv = (TextView) view.findViewById(R.id.ask_order_front_st_time_tv);
        edTimeTv = (TextView) view.findViewById(R.id.ask_order_front_ed_time_tv);
        selectCarTv = (TextView) view.findViewById(R.id.ask_order_front_costomer_num_tv);
        hintTv = (TextView) view.findViewById(R.id.ask_order_front_hint_tv);

        stPlaceAddRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_st_place_all_rl);
        passAddRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_pass_all_rl);
        edPlaceAddRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_ed_place_all_rl);
        stTimeRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_st_time_all_rl);
        edTimeRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_ed_time_all_rl);
        customerNumRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_costomer_num_all_rl);

        seekNullIv = (ImageView) view.findViewById(R.id.ask_order_front_seek_null_iv);

        nextBtn = (Button) view.findViewById(R.id.ask_order_front_next_btn);


        hintTv.setVisibility(View.VISIBLE);

        stPlaceAddRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.context, InMapSelectActivity.class);
                intent.putExtra(StandardUtil.MARKER_TYPE, StandardUtil.SINGULAR);
                startActivityForResult(intent, 1);
            }
        });

        passAddRl.setOnClickListener(new View.OnClickListener() {
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

        stTimeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiyDialog.dateOrTimeSelectDialog(getActivity(), new DiyDialog.OnTimeDataListener() {
                    @Override
                    public void onTimeDataListener(String date) {
                        stTimeTv.setText(date);
                        askFrontMap.put("useCarStart", date);
                    }
                });
            }
        });

        edTimeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiyDialog.dateOrTimeSelectDialog(getActivity(), new DiyDialog.OnTimeDataListener() {
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
                        askFrontMap.put("useCarEnd", date);
                    }
                });
            }
        });

        customerNumRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dg = ProgressDialog.show(getActivity(), "", "获取座位数...");
                dg.setCanceledOnTouchOutside(false);
                dg.show();
                getSeatsListThread = new GetSeatsListThread();
                es.execute(getSeatsListThread);
            }
        });

        seekNullIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DriverEmptyCarListActivity.class);
                intent.putExtra("role", StandardUtil.ROLE_CUSTOMER);
                startActivity(intent);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doCheck()) {
                    return;
                }
                if (passTv.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("提示");
                    builder.setMessage("您未选择途径地点,点击取消将返回修改;继续将跳至下一界面");
                    builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            if (CustomerIssueOrderActivity.fragments.containsKey("askRear")) {
//                                getActivity().getFragmentManager().beginTransaction().replace(R.id.customer_issue_order_content_fl,
//                                        CustomerIssueOrderActivity.fragments.get("askRear")).commit();
//                            } else {
                            getActivity().getFragmentManager().beginTransaction().replace(R.id.customer_issue_order_content_fl,
                                    new AskOrderRearFragment()).commit();
//                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                } else {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.customer_issue_order_content_fl,
                            new AskOrderRearFragment()).commit();
                }
            }
        });
    }

    private boolean doCheck() {
        if (stPlaceTv.getText().toString().equals("")) {
            Toast.makeText(MyApplication.context, "请选择上车地点", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edPlaceTv.getText().toString().equals("")) {
            Toast.makeText(MyApplication.context, "请选择下车地点", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (stTimeTv.getText().toString().equals("")) {
            Toast.makeText(MyApplication.context, "请选择开始时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edTimeTv.getText().toString().equals("")) {
            Toast.makeText(MyApplication.context, "请选择结束时间", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (selectCarTv.getText().toString().equals("")) {
//            Toast.makeText(MyApplication.context, "请选择车辆大小", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        askFrontMap.put("", stTimeTv.getText().toString());
//        askFrontMap.put("", edTimeTv.getText().toString());
//        askFrontMap.put("", selectCarTv.getText().toString());
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<InMapSelectResultBean> beans;
        if (requestCode == 1 && data != null) {
            beans = data.getParcelableArrayListExtra("mapSelect");
            stPlaceTv.setText(beans.get(0).getTitle());
            askFrontMap.put("departPlace", beans.get(0).getTitle());
            if (askFrontMap.containsKey("lonAndLat") && askFrontMap.get("lonAndLat").contains(",")) {
                askFrontMap.put("lonAndLat", beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude +
                        askFrontMap.get("lonAndLat").substring(
                                askFrontMap.get("lonAndLat").indexOf(","), askFrontMap.get("lonAndLat").length()));
            } else {
                askFrontMap.put("lonAndLat", beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
            }
        } else if (requestCode == 2 && data != null) {
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
            askFrontMap.put("wayPoints", allMapStr);
        } else if (requestCode == 3 && data != null) {
            beans = data.getParcelableArrayListExtra("mapSelect");
            edPlaceTv.setText(beans.get(0).getTitle());
            askFrontMap.put("destination", beans.get(0).getTitle());
            if (askFrontMap.containsKey("lonAndLat")) {
                if (askFrontMap.get("lonAndLat").contains(",")) {
                    askFrontMap.put("lonAndLat", askFrontMap.get("lonAndLat").substring(0, askFrontMap.get("lonAndLat").indexOf(",")) +
                            "," + beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
                } else {
                    askFrontMap.put("lonAndLat", askFrontMap.get("lonAndLat") +
                            "," + beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
                }
            } else {
                askFrontMap.put("lonAndLat", "," + beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
            }
        }
    }

    class GetSeatsListThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().NoCookieRequest(new HashMap<String, String>(), UrlUtil.SEATS_LIST);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    seatsListBeans = JSONUtil.seatsListJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (seatsListBeans != null && seatsListBeans.size() != 0) {
                handler.sendEmptyMessage(1);
            } else {
                handler.sendEmptyMessage(2);
            }
        }
    }

}
