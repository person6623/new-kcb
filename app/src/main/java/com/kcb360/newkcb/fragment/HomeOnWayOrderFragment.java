package com.kcb360.newkcb.fragment;

import android.content.Intent;
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
import com.kcb360.newkcb.base.BaseFragmentVFour;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.entity.OnWayOrderBean;
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
 * 顺风车(首页)
 */

public class HomeOnWayOrderFragment extends BaseFragmentVFour {

    // 布局相关
    // xlistview
    private XListView xlv;
    // adapter
    private OnWayOrderAdapter adapter;

    // 数据相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 顺风车订单想成
    private OnWayOrderThread onWayOrderThread;
    // 顺风车订单map
    private Map<String, String> map;
    // 顺风车返回暂存
    private OnWayOrderBean onWayOrderBean;
    // 顺风车返回存储
    private OnWayOrderBean oldOnWayOrderBean;
    // 页数控制
    private int page = 1;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    page++;
                    adapter.setBean(oldOnWayOrderBean);
                    xlv.setAdapter(adapter);
                    xlv.stopRefresh();
                    xlv.stopLoadMore();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "无更多数据", Toast.LENGTH_SHORT).show();
                    if (oldOnWayOrderBean == null || oldOnWayOrderBean.getRows().size() == 0) {
                        adapter.setBean(oldOnWayOrderBean);
                        xlv.setAdapter(adapter);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.fragment_home_on_way_order;
    }

    @Override
    protected void intiData() {
        map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", "4");
        onWayOrderThread = new OnWayOrderThread();
        es.execute(onWayOrderThread);
    }

    @Override
    protected void intiView(View view, Bundle savedInstanceState) {
        xlv = (XListView) view.findViewById(R.id.home_on_way_order_xlv);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                oldOnWayOrderBean = null;
                intiData();
            }

            @Override
            public void onLoadMore() {
                intiData();
            }
        });

        adapter = new OnWayOrderAdapter();
    }

    class OnWayOrderAdapter extends BaseAdapter {

        private OnWayOrderBean bean;

        public void setBean(OnWayOrderBean bean) {
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
            OnWayOrderViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_on_way_order, parent, false);
                vh = new OnWayOrderViewHolder();
                vh.allLl = (LinearLayout) convertView.findViewById(R.id.take_order_sub_all_ll);
                vh.desnationTv = (TextView) convertView.findViewById(R.id.take_order_sub_destination_tv);
//                vh.phoneTv = (TextView) convertView.findViewById(R.id.take_order_sub_customer_phone_tv);
                vh.customerNumTv = (TextView) convertView.findViewById(R.id.take_order_sub_customer_num_tv);
//                vh.acceptTimeTv = (TextView) convertView.findViewById(R.id.take_order_sub_accept_time_tv);
//                vh.priceLl = (LinearLayout) convertView.findViewById(R.id.take_order_sub_price_ll);
                vh.orderPriceTv = (TextView) convertView.findViewById(R.id.take_order_sub_order_price_tv);
//                vh.thanksPriceTv = (TextView) convertView.findViewById(R.id.take_order_sub_thanks_price_tv);
                convertView.setTag(vh);
            } else {
                vh = (OnWayOrderViewHolder) convertView.getTag();
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
            vh.desnationTv.setText("起:" + bean.getRows().get(position).getDepartPlace() + "\n"
                    + "终:" + bean.getRows().get(position).getDestination());
//            vh.phoneTv.setText(bean.getRows().get(position).getCustomerPhone());
            vh.customerNumTv.setText(bean.getRows().get(position).getCustomers() + "");
//            vh.acceptTimeTv.setText(timeCompose(bean.getRows().get(position).getReserveOrderTime()));
//            vh.priceLl.setVisibility(View.VISIBLE);
            vh.orderPriceTv.setText("订单总价:" + ((float) Math.round(bean.getRows().get(position).getOrderPrice() * 85) / 100) + "元");
//            vh.thanksPriceTv.setText("感谢费:" + bean.getRows().get(position).getThanksFee() + "元");
            return convertView;
        }

        class OnWayOrderViewHolder {
            private LinearLayout allLl;
            private TextView desnationTv;
            //            private TextView phoneTv;
            private TextView customerNumTv;
            //            private TextView acceptTimeTv;
//            private LinearLayout priceLl;
            private TextView orderPriceTv;
//            private TextView thanksPriceTv;
        }
    }

    public String timeCompose(OnWayOrderBean.RowsBean.ReserveOrderTimeBean reserveOrderTime) {
        Date date = new Date(reserveOrderTime.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    class OnWayOrderThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(map, UrlUtil.FREE_ORDER_LIST);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    onWayOrderBean = JSONUtil.onWayOrderJsonAnalytic(res);
                    if (onWayOrderBean.getRows() != null) {
                        if (oldOnWayOrderBean == null) {
                            oldOnWayOrderBean = onWayOrderBean;
                        } else {
                            oldOnWayOrderBean.getRows().addAll(onWayOrderBean.getRows());
                            oldOnWayOrderBean.setOthers(onWayOrderBean.getOthers());
                            oldOnWayOrderBean.setTotal(onWayOrderBean.getTotal());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (onWayOrderBean == null || onWayOrderBean.getRows().size() == 0) {
                handler.sendEmptyMessage(2);
                return;
            }
            handler.sendEmptyMessage(1);
        }
    }
}
