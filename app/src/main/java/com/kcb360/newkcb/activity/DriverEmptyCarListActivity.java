package com.kcb360.newkcb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.base.StandardUtil;
import com.kcb360.newkcb.entity.CustomerOrderListBean;
import com.kcb360.newkcb.entity.DriverEmptyCarListBean;
import com.kcb360.newkcb.net.NormalUtil;
import com.kcb360.newkcb.net.ExceptionUtil;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;
import com.kcb360.newkcb.xlistviewaliter.XListView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/5/24.
 * <p>
 * 空车师傅列表
 * ******
 * 传入信息:
 * role: 师傅StandardUtil.ROLE_DRIVER, 乘客StandardUtil.ROLE_CUSTOMER
 * 师傅时需传入driverPhone
 * 乘客时需传入 座位数, 开始结束时间, 起点
 */

public class DriverEmptyCarListActivity extends BaseActivity {

    // 布局相关
    // 返回
    private LinearLayout backLl;
    // 空车设置无时样式
    private RelativeLayout nullRl;
    // 设定空车
    private TextView settingTv;
    // xlv
    private XListView xlv;
    // adapter
    private DriverEmptyCarAdapter adapter;

    // 传入数据
    // 角色区分
    private int role;

    // 数据相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 空车订单线程
    private DriverEmptyCarThread driverEmptyCarThread;
    // 空车订单传参map
    private Map<String, String> emptyCarListMap;
    // 页数控制
    private int page = 1;
    // 空车订单暂存bean
    private DriverEmptyCarListBean driverEmptyCarListBean;
    // 空车订单存储bean
    private DriverEmptyCarListBean oldDriverEmptyCarListBean;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(DriverEmptyCarListActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    nullRl.setVisibility(View.GONE);
                    page++;
                    adapter.setBean(oldDriverEmptyCarListBean);
                    xlv.setAdapter(adapter);
                    xlv.stopRefresh();
                    xlv.stopLoadMore();
                    if (driverEmptyCarListBean.getRows().size() < 5) {
                        xlv.setPullLoadEnable(false);
                    }
                    break;
                case 2:
                    Toast.makeText(DriverEmptyCarListActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                    if (oldDriverEmptyCarListBean == null || oldDriverEmptyCarListBean.getRows().size() == 0) {
                        if (role == StandardUtil.ROLE_DRIVER) {
                            nullRl.setVisibility(View.VISIBLE);
                        }
                        adapter.setBean(oldDriverEmptyCarListBean);
                        xlv.setAdapter(adapter);
                    }
                    xlv.stopRefresh();
                    xlv.stopLoadMore();
                    if (driverEmptyCarListBean.getRows().size() < 5) {
                        xlv.setPullLoadEnable(false);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_driver_empty_car;
    }

    @Override
    protected void initData() {
        emptyCarListMap = new HashMap<>();
        if (role == StandardUtil.ROLE_DRIVER) {
            emptyCarListMap.put("driverPhone", getIntent().getStringExtra("driverPhone"));
            emptyCarListMap.put("page", page + "");
            emptyCarListMap.put("rows", "5");
            emptyCarListMap.put("carNature", "-1");
            emptyCarListMap.put("carSeats", "0");
        } else if (role == StandardUtil.ROLE_CUSTOMER) {
            CustomerOrderListBean.MyOrderBean myOrderBean = getIntent().getParcelableExtra("customer");
            emptyCarListMap.put("page", page + "");
            emptyCarListMap.put("rows", "5");
            emptyCarListMap.put("carNature", "-1");
            if (myOrderBean != null) {
                emptyCarListMap.put("carSeats", myOrderBean.getCarSeats() + "");
                emptyCarListMap.put("freeStart", myOrderBean.getUseDayStart());
                emptyCarListMap.put("freeEnd", myOrderBean.getUseDayEnd());
            }
            emptyCarListMap.put("carSeats", "0");
            emptyCarListMap.put("carSeats", "0");
            // 参数缺省中
        } else {
            Toast.makeText(DriverEmptyCarListActivity.this, "未能获取到传入信息, 当前界面为所有司机空车链表", Toast.LENGTH_SHORT).show();
        }
        driverEmptyCarThread = new DriverEmptyCarThread();
        es.execute(driverEmptyCarThread);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        role = getIntent().getIntExtra("role", 2);

        backLl = findView(R.id.driver_enpty_car_back_ll);
        nullRl = findView(R.id.driver_empty_null_rl);
        settingTv = findView(R.id.driver_empty_setting_tv);
        xlv = findView(R.id.driver_empty_xlv);

        backLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        settingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(DriverEmptyCarListActivity.this, DriverIssueEmptyActivity.class), 1);
            }
        });

        adapter = new DriverEmptyCarAdapter();
        xlv.setAdapter(adapter);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                oldDriverEmptyCarListBean = null;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            page = 1;
            oldDriverEmptyCarListBean = null;
            initData();
        }
    }

    class DriverEmptyCarAdapter extends BaseAdapter {

        private DriverEmptyCarListBean bean;

        public void setBean(DriverEmptyCarListBean bean) {
            this.bean = bean;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return bean != null && bean.getRows().size() != 0 ? bean.getRows().size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DriverEmptyCarViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(DriverEmptyCarListActivity.this).inflate(R.layout.item_driver_empty_car, parent, false);
                vh = new DriverEmptyCarViewHolder();
                vh.plateTv = (TextView) convertView.findViewById(R.id.item_driver_empty_car_plate_tv);
                vh.seatsTv = (TextView) convertView.findViewById(R.id.item_driver_empty_car_seats_tv);
                vh.stayCityTv = (TextView) convertView.findViewById(R.id.item_driver_empty_car_stay_city_tv);
                vh.scopeTv = (TextView) convertView.findViewById(R.id.item_driver_empty_car_scope_tv);
                vh.scopeTv = (TextView) convertView.findViewById(R.id.item_driver_empty_car_scope_tv);
                vh.notGoGl = (GridLayout) convertView.findViewById(R.id.item_driver_empty_car_not_go_gl);
                vh.stTimeTv = (TextView) convertView.findViewById(R.id.item_driver_empty_car_st_time_tv);
                vh.edTimeTv = (TextView) convertView.findViewById(R.id.item_driver_empty_car_ed_time_tv);
                vh.outScopeTv = (TextView) convertView.findViewById(R.id.item_driver_empty_car_scope_price_tv);
                convertView.setTag(vh);
            } else {
                vh = (DriverEmptyCarViewHolder) convertView.getTag();
            }
            vh.plateTv.setText(bean.getRows().get(position).getPlateNum());
            vh.seatsTv.setText(bean.getRows().get(position).getCarSeats() + " 座");
            vh.stayCityTv.setText(bean.getRows().get(position).getWaitPoint().split("-")[1]);
            vh.scopeTv.setText(bean.getRows().get(position).getRunArea());
            vh.stTimeTv.setText(NormalUtil.timeToDate(bean.getRows().get(position).getFreeStart().getTime()));
            vh.edTimeTv.setText(NormalUtil.timeToDate(bean.getRows().get(position).getFreeEnd().getTime()));
            vh.outScopeTv.setText(bean.getRows().get(position).getBeyondRound() + "公里内免费,超出" +
                    bean.getRows().get(position).getBeyondPrice() + "元/公里");
            return convertView;
        }

        class DriverEmptyCarViewHolder {
            private TextView plateTv;
            private TextView seatsTv;
            private TextView stayCityTv;
            private TextView scopeTv;
            private GridLayout notGoGl;
            private TextView stTimeTv;
            private TextView edTimeTv;
            private TextView outScopeTv;
        }
    }

    class DriverEmptyCarThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().NoCookieRequest(emptyCarListMap, UrlUtil.FIND_EMPTY_CARS);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    driverEmptyCarListBean = JSONUtil.DriverEmptyCarListJsonAnalytic(res);
                    if (oldDriverEmptyCarListBean == null) {
                        oldDriverEmptyCarListBean = driverEmptyCarListBean;
                    } else {
                        oldDriverEmptyCarListBean.getRows().addAll(driverEmptyCarListBean.getRows());
                        oldDriverEmptyCarListBean.setOthers(driverEmptyCarListBean.getOthers());
                        oldDriverEmptyCarListBean.setTotal(driverEmptyCarListBean.getTotal());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
                return;
            }
            if (driverEmptyCarListBean.getRows().size() == 0) {
                handler.sendEmptyMessage(2);
            } else {
                handler.sendEmptyMessage(1);
            }
        }
    }
}
