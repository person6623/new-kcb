package com.kcb360.newkcb.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinshichao on 2017/5/18.
 * <p>
 * 发布订单(前半)
 */

public class FormalOrderFrontFragment extends BaseFragment {

    // 布局相关
    // 上车地点, 途经点, 下车点, 开始时间, 结束时间, 选择车辆, 选择车辆标题
    private TextView stPlaceTv, passTv, edPlaceTv, stTimeTv, edTimeTv, selectCarTv, selectCarTitleTv;
    // 上车地点添加, 途经点添加, 下车地点添加, 开始时间添加, 结束时间添加, 车辆添加
    private RelativeLayout stPlaceAddRl, passAddRl, edPlaceAddRl, stTimeRl, edTimeRl, customerNumRl;
    // 查找空车
    private ImageView seekNullIv;
    // 下一步
    private Button nextBtn;

    // 数据相关
    private Map<String, String> formalFrontMap;

    @Override
    protected int setLayout() {
        return R.layout.fragment_ask_order_front;
    }

    public Map<String, String> getFormalFrontMap() {
        return formalFrontMap;
    }

    @Override
    protected void intiData() {
        formalFrontMap = new HashMap<>();
    }

    @Override
    protected void intiView(View view, Bundle savedInstanceState) {
        stPlaceTv = (TextView) view.findViewById(R.id.ask_order_front_st_place_tv);
        passTv = (TextView) view.findViewById(R.id.ask_order_front_pass_tv);
        edPlaceTv = (TextView) view.findViewById(R.id.ask_order_front_ed_place_tv);
        stTimeTv = (TextView) view.findViewById(R.id.ask_order_front_st_time_tv);
        edTimeTv = (TextView) view.findViewById(R.id.ask_order_front_ed_time_tv);
        selectCarTv = (TextView) view.findViewById(R.id.ask_order_front_costomer_num_tv);
        selectCarTitleTv = (TextView) view.findViewById(R.id.ask_order_front_costomer_num_title_tv);

        stPlaceAddRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_st_place_all_rl);
        passAddRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_pass_all_rl);
        edPlaceAddRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_ed_place_all_rl);
        stTimeRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_st_time_all_rl);
        edTimeRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_ed_time_all_rl);
        customerNumRl = (RelativeLayout) view.findViewById(R.id.ask_order_front_costomer_num_all_rl);

        seekNullIv = (ImageView) view.findViewById(R.id.ask_order_front_seek_null_iv);

        nextBtn = (Button) view.findViewById(R.id.ask_order_front_next_btn);

        selectCarTitleTv.setText("选择车辆/人数");

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
                        formalFrontMap.put("useCarStart", date);
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
                        formalFrontMap.put("useCarEnd", date);
                    }
                });
            }
        });

        customerNumRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiyDialog.edittextDialog(getActivity(), "请填入人数", new DiyDialog.OnEditChangeListener() {
                    @Override
                    public void onEditChangeListener(String edit) {
                        selectCarTv.setText(edit);
                        formalFrontMap.put("carSeats", edit);
                        formalFrontMap.put("seatNum", "1");
                    }
                });
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
                                    new FormalOrderRearFragment()).commit();
//                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                } else {
                    getActivity().getFragmentManager().beginTransaction().replace(R.id.customer_issue_order_content_fl,
                            new FormalOrderRearFragment()).commit();
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
            formalFrontMap.put("departPlace", beans.get(0).getTitle());
            if (formalFrontMap.containsKey("lonAndLat") && formalFrontMap.get("lonAndLat").contains(",")) {
                formalFrontMap.put("lonAndLat", beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude +
                        formalFrontMap.get("lonAndLat").substring(
                                formalFrontMap.get("lonAndLat").indexOf(","), formalFrontMap.get("lonAndLat").length()));
            } else {
                formalFrontMap.put("lonAndLat", beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
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
            formalFrontMap.put("wayPoints", allMapStr);
        } else if (requestCode == 3 && data != null) {
            beans = data.getParcelableArrayListExtra("mapSelect");
            edPlaceTv.setText(beans.get(0).getTitle());
            formalFrontMap.put("destination", beans.get(0).getTitle());
            if (formalFrontMap.containsKey("lonAndLat")) {
                if (formalFrontMap.get("lonAndLat").contains(",")) {
                    formalFrontMap.put("lonAndLat", formalFrontMap.get("lonAndLat").substring(0, formalFrontMap.get("lonAndLat").indexOf(",")) +
                            "," + beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
                } else {
                    formalFrontMap.put("lonAndLat", formalFrontMap.get("lonAndLat") +
                            "," + beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
                }
            } else {
                formalFrontMap.put("lonAndLat", "," + beans.get(0).getLatLng().longitude + "|" + beans.get(0).getLatLng().latitude);
            }
        }
    }
}
