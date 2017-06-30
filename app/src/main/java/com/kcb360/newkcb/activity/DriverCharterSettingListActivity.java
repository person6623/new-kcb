package com.kcb360.newkcb.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.entity.DriverCharterSettingListBean;
import com.kcb360.newkcb.entity.NormalBean;
import com.kcb360.newkcb.net.ExceptionUtil;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.NormalUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;
import com.kcb360.newkcb.xlistviewaliter.XListView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/6/6.
 * <p>
 * 包车价格设置查看列表
 */

public class DriverCharterSettingListActivity extends BaseActivity {

    // 布局相关
    // 返回
    private LinearLayout backLl;
    // xlv
    private XListView xlv;
    // adapter
    private DriverCharterSettingListAdapter adapter;

    // 数据相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 获取司机包车设置价格列表线程
    private GetDriverCharterSettingListThread getDriverCharterSettingListThread;
    // 删除包车价格设置线程
    private DelCharterSettingThread delCharterSettingThread;
    // 删除包车设置传参
    private Map<String, String> delCharterSettingMap;
    // 包车价格列表传参
    private Map<String, String> charterSettingMap;
    // 页数控制
    private int page = 1;
    // 包车价格返回数据暂存
    private DriverCharterSettingListBean driverCharterSettingListBean;
    // 包车价格返回数据存储
    private DriverCharterSettingListBean oldDriverCharterSettingListBean;
    // 删除包车设置返回
    private NormalBean delCharterSettingBean;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(DriverCharterSettingListActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    page++;
                    adapter.setBean(oldDriverCharterSettingListBean);
                    xlv.setAdapter(adapter);
                    xlv.setPullRefreshEnable(false);
                    xlv.setPullLoadEnable(false);
                    break;
                case 2:
                    Toast.makeText(DriverCharterSettingListActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                    if (oldDriverCharterSettingListBean == null || oldDriverCharterSettingListBean.getRows().size() == 0) {
                        adapter.setBean(oldDriverCharterSettingListBean);
                        xlv.setAdapter(adapter);
                    }
                    xlv.setPullRefreshEnable(false);
                    xlv.setPullLoadEnable(false);
                    break;
                case 3:
                    Toast.makeText(DriverCharterSettingListActivity.this, delCharterSettingBean.getContext(), Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_driver_charter_setting_list;
    }

    @Override
    protected void initData() {
        if (charterSettingMap == null) {
            charterSettingMap = new HashMap<>();
        }
        charterSettingMap.clear();
        if (!NormalUtil.loginTimeRatio(DriverCharterSettingListActivity.this)) {
            return;
        }
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);

        charterSettingMap.put("page", page + "");
        charterSettingMap.put("rows", "5");
        charterSettingMap.put("find", sp.getString("_cName", ""));
        getDriverCharterSettingListThread = new GetDriverCharterSettingListThread();
        es.execute(getDriverCharterSettingListThread);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backLl = findView(R.id.driver_charter_setting_list_back_ll);
        xlv = findView(R.id.driver_charter_setting_list_xlv);

        backLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                oldDriverCharterSettingListBean = null;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });

        adapter = new DriverCharterSettingListAdapter();

    }

    class DriverCharterSettingListAdapter extends BaseAdapter {

        private DriverCharterSettingListBean bean;

        public void setBean(DriverCharterSettingListBean bean) {
            this.bean = bean;
        }

        @Override
        public int getCount() {
            return bean == null || bean.getRows() == null || bean.getRows().size() == 0 ? 0 : bean.getRows().size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            DriverCharterSettingListViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(DriverCharterSettingListActivity.this)
                        .inflate(R.layout.item_driver_charter_setting_list, parent, false);
                vh = new DriverCharterSettingListViewHolder();
                vh.seatsTv = (TextView) convertView.findViewById(R.id.item_driver_charter_setting_list_seats_tv);
                vh.timeTv = (TextView) convertView.findViewById(R.id.item_driver_charter_setting_list_time_tv);
                vh.areaTv = (TextView) convertView.findViewById(R.id.item_driver_charter_setting_list_area_tv);
                vh.seatPriceTv = (TextView) convertView.findViewById(R.id.item_driver_charter_setting_list_seat_price_tv);
                vh.outScopeTv = (TextView) convertView.findViewById(R.id.item_driver_charter_setting_list_out_scope_tv);
                vh.delTv = (TextView) convertView.findViewById(R.id.item_driver_charter_setting_list_del_tv);
                vh.alterTv = (TextView) convertView.findViewById(R.id.item_driver_charter_setting_list_alter_tv);
                convertView.setTag(vh);
            } else {
                vh = (DriverCharterSettingListViewHolder) convertView.getTag();
            }
            vh.seatsTv.setText(bean.getRows().get(position).getCarSeats() + " 座");
            vh.timeTv.setText(NormalUtil.timeToDate(bean.getRows().get(position).getStartTime().getTime()) + " 至 "
                    + NormalUtil.timeToDate(bean.getRows().get(position).getEndTime().getTime()));
            vh.areaTv.setText(bean.getRows().get(position).getAreaName());
            String seatPrice = bean.getRows().get(position).getCharterPrice().replace("-", "公里内")
                    .replace(",", "元/天\n") + "元/天";
            vh.seatPriceTv.setText(seatPrice);
            vh.outScopeTv.setText(bean.getRows().get(position).getFreeKm() + "公里内免费\n超出则"
                    + bean.getRows().get(position).getMoreFreeKmPrice() + "元/公里");

            vh.delTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (delCharterSettingMap == null) {
                        delCharterSettingMap = new HashMap<>();
                    } else {
                        delCharterSettingMap.clear();
                    }
                    delCharterSettingThread = new DelCharterSettingThread();
                    es.execute(delCharterSettingThread);
                }
            });

            vh.alterTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DriverCharterSettingListActivity.this, DriverCharterSettingActivity.class);
                    intent.putExtra("charterSetting", bean.getRows().get(position));
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class DriverCharterSettingListViewHolder {
            private TextView seatsTv;
            private TextView timeTv;
            private TextView areaTv;
            private TextView seatPriceTv;
            private TextView outScopeTv;
            private TextView delTv;
            private TextView alterTv;
        }
    }

    class GetDriverCharterSettingListThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(charterSettingMap, UrlUtil.TOUC_LIST);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    driverCharterSettingListBean = JSONUtil.driverCharterSettingListJsonAnalytic(res);
                    if (oldDriverCharterSettingListBean == null) {
                        oldDriverCharterSettingListBean = driverCharterSettingListBean;
                    } else {
                        oldDriverCharterSettingListBean.setOthers(driverCharterSettingListBean.getOthers());
                        oldDriverCharterSettingListBean.setTotal(driverCharterSettingListBean.getTotal());
                        oldDriverCharterSettingListBean.getRows().addAll(driverCharterSettingListBean.getRows());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (driverCharterSettingListBean != null && driverCharterSettingListBean.getRows() != null) {
                handler.sendEmptyMessage(1);
            } else {
                handler.sendEmptyMessage(2);
            }
        }
    }

    class DelCharterSettingThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(delCharterSettingMap, UrlUtil.AD_UPDE_TOUC);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    delCharterSettingBean = JSONUtil.normalJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            handler.sendEmptyMessage(3);
        }
    }

}
