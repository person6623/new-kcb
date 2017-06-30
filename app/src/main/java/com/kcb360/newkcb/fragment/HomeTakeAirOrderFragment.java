package com.kcb360.newkcb.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.kcb360.newkcb.activity.LoginActivity;
import com.kcb360.newkcb.base.BaseFragmentVFour;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.entity.TakeAirOrderBean;
import com.kcb360.newkcb.net.ExceptionUtil;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;
import com.kcb360.newkcb.xlistviewaliter.XListView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/6/21.
 * <p>
 * 接送机(首页)
 */

public class HomeTakeAirOrderFragment extends BaseFragmentVFour {

    // 布局相关
    // xlistview
    private XListView xlv;
    // adapter
    private TakeAirOrderAdapter adapter;

    // 数据相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 获取接送机订单线程
    private TakeAirOrderThread takeAirOrderThread;
    // 接送机订单传参map
    private Map<String, String> map;
    // 页数控制
    private int page = 1;
    // 接送机返回暂存
    private TakeAirOrderBean takeAirOrderBean;
    // 接送机返回存储
    private TakeAirOrderBean oldTakeAirOrderBean;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    if (msg.obj.toString().contains("请重新登录")) {
                        DiyDialog.fromToDialog(getActivity(), "您当前未登录,请登录后查看", new DiyDialog.OnFromToListener() {
                            @Override
                            public void onFromToListener() {
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }
                        });
                    }
                    break;
                case 1:
                    page++;
                    adapter.setBean(oldTakeAirOrderBean);
                    xlv.setAdapter(adapter);
                    xlv.stopRefresh();
                    xlv.stopLoadMore();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "无更多数据", Toast.LENGTH_SHORT).show();
                    if (oldTakeAirOrderBean == null || oldTakeAirOrderBean.getRows().size() == 0) {
                        adapter.setBean(oldTakeAirOrderBean);
                        xlv.setAdapter(adapter);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.fragment_take_air_order;
    }

    @Override
    protected void intiData() {
        /**
         * 此处只去今日订单
         */
        map = new HashMap<>();
        // 获取用户(选定车辆)信息
        SharedPreferences sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        // 获取今日日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date(System.currentTimeMillis());

        map.put("page", page + "");
        map.put("rows", "5");
        map.put("mbName", sp.getString("_cName", ""));
        map.put("pickType", "2");
        map.put("reqWay", "phone");
        map.put("isMeet", "1");
        map.put("orderState", "1");
        map.put("sT", sdf.format(date) + "00:00:00");
        map.put("eT", sdf.format(date) + "23:59:59");
        takeAirOrderThread = new TakeAirOrderThread();
        es.execute(takeAirOrderThread);
    }

    @Override
    protected void intiView(View view, Bundle savedInstanceState) {
        xlv = (XListView) view.findViewById(R.id.home_take_air_order_xlv);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                oldTakeAirOrderBean = null;
                intiData();
            }

            @Override
            public void onLoadMore() {
                intiData();
            }
        });

        adapter = new TakeAirOrderAdapter();
    }

    class TakeAirOrderAdapter extends BaseAdapter {

        private TakeAirOrderBean bean;

        public void setBean(TakeAirOrderBean bean) {
            this.bean = bean;
        }

        @Override
        public int getCount() {
            return bean != null && bean.getRows() != null && bean.getRows().size() != 0 ? bean.getRows().size() : 0;
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
            TakeAirOrderViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_on_way_order, parent, false);
                vh = new TakeAirOrderViewHolder();
                vh.allLl = (LinearLayout) convertView.findViewById(R.id.take_order_sub_all_ll);
                vh.destinationTv = (TextView) convertView.findViewById(R.id.take_order_sub_destination_tv);
//                vh.flightTv = (TextView) convertView.findViewById(R.id.take_order_flight_tv);
//                vh.customerPhoneTv = (TextView) convertView.findViewById(R.id.take_order_sub_customer_phone_tv);
                vh.customerNumTv = (TextView) convertView.findViewById(R.id.take_order_sub_customer_num_tv);
                vh.PriceTv = (TextView) convertView.findViewById(R.id.take_order_sub_order_price_tv);
//                vh.acceptTimeTv = (TextView) convertView.findViewById(R.id.take_order_sub_accept_time_tv);
                convertView.setTag(vh);
            } else {
                vh = (TakeAirOrderViewHolder) convertView.getTag();
            }
            vh.allLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiyDialog.fromToDialog(getActivity(), "是否拨打:" + bean.getRows().get(position).getCustomers()
                            , new DiyDialog.OnFromToListener() {
                                @Override
                                public void onFromToListener() {
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bean.getRows().get(position).getCustomers()));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                }
            });
            vh.destinationTv.setText("起: " + bean.getRows().get(position).getDepartPlace() + "\n终: " + bean.getRows().get(position).getDestination());
//            if (bean.getRows().get(position).getFlightNum().equals("")) {
//                vh.flightTv.setVisibility(View.GONE);
//            } else {
//                vh.flightTv.setVisibility(View.VISIBLE);
//                vh.flightTv.setText("航班号:" + bean.getRows().get(position).getFlightNum());
//            }
//            vh.customerPhoneTv.setText(bean.getRows().get(position).getCustomerPhone());
            vh.customerNumTv.setText(bean.getRows().get(position).getCustomers() + "人");
            vh.PriceTv.setText(bean.getRows().get(position).getOrderPrice() + " 元");
//            vh.acceptTimeTv.setText(timeCompose(bean.getRows().get(position).getReserveOrderTime()));
            return convertView;
        }

        class TakeAirOrderViewHolder {
            private LinearLayout allLl;
            private TextView destinationTv;
            //            private TextView flightTv;
//            private TextView customerPhoneTv;
            private TextView customerNumTv;
            private TextView PriceTv;
//            private TextView acceptTimeTv;
        }
    }

    public String timeCompose(TakeAirOrderBean.RowsBean.ReserveOrderTimeBean reserveOrderTime) {
        Date date = new Date(Long.parseLong(reserveOrderTime.getTime()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    class TakeAirOrderThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(map, UrlUtil.GET_PICKUP_ORDER_LIST);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    takeAirOrderBean = JSONUtil.takeAirOrderJsonAnalytic(res);
                    if (takeAirOrderBean.getRows() != null) {
                        if (oldTakeAirOrderBean == null) {
                            oldTakeAirOrderBean = takeAirOrderBean;
                        } else {
                            oldTakeAirOrderBean.getRows().addAll(takeAirOrderBean.getRows());
                            oldTakeAirOrderBean.setOthers(takeAirOrderBean.getOthers());
                            oldTakeAirOrderBean.setTotal(takeAirOrderBean.getTotal());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (takeAirOrderBean == null || takeAirOrderBean.getRows().size() == 0) {
                handler.sendEmptyMessage(2);
                return;
            }
            handler.sendEmptyMessage(1);

        }
    }
}
