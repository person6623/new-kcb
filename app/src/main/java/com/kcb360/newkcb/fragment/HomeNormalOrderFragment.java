package com.kcb360.newkcb.fragment;

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
import com.kcb360.newkcb.entity.CustomerOrderListBean;
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
 * Created by xinshichao on 2017/6/21.
 * <p>
 * 成团订单(首页)
 */

public class HomeNormalOrderFragment extends BaseFragmentVFour {

    // 布局相关
    // xlistview
    private XListView xlv;
    // adapter
    private CustomerOrderListAdapter adapter;
    // 状态控制
    private int isState = 0;
    // 类型控制 询价, 成团
    private String isType = "成团";

    // 数据相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 用户订单获取
    private CustomerOrderListThread customerOrderListThread;
    // 用户订单传参
    private Map<String, String> customerOrderMap;
    // 页数控制
    private int page = 1;
    // 用户订单数据暂存
    private CustomerOrderListBean customerOrderListBean;
    // 用户订单数据存储
    private CustomerOrderListBean oldCustomerOrderListBean;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), ExceptionUtil.exceptionSwitch(msg.obj.toString())
                            , Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    page++;
                    adapter.setBean(oldCustomerOrderListBean);
                    xlv.setAdapter(adapter);
                    xlv.stopRefresh();
                    xlv.stopLoadMore();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "无更多数据", Toast.LENGTH_SHORT).show();
                    if (oldCustomerOrderListBean == null || oldCustomerOrderListBean.getMyOrder().size() == 0) {
                        adapter.setBean(oldCustomerOrderListBean);
                        xlv.setAdapter(adapter);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.fragment_charter_order;
    }

    @Override
    protected void intiData() {
        if (customerOrderMap == null) {
            customerOrderMap = new HashMap<>();
        } else {
            customerOrderMap.clear();
        }
        customerOrderMap.put("state", isState + "");
        customerOrderMap.put("remark", isType);
        customerOrderMap.put("pageSize", page + "");
        customerOrderMap.put("rows", "5");
        customerOrderListThread = new CustomerOrderListThread();
        es.execute(customerOrderListThread);
    }

    @Override
    protected void intiView(View view, Bundle savedInstanceState) {
        xlv = (XListView) view.findViewById(R.id.charter_order_xlv);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                oldCustomerOrderListBean = null;
                intiData();
            }

            @Override
            public void onLoadMore() {
                intiData();
            }
        });

        adapter = new CustomerOrderListAdapter();
    }

    class CustomerOrderListAdapter extends BaseAdapter {

        private CustomerOrderListBean bean;

        public void setBean(CustomerOrderListBean bean) {
            this.bean = bean;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return bean != null && bean.getMyOrder() != null && bean.getMyOrder().size() > 0 ? bean.getMyOrder().size() : 0;
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
            CustomerOrderListViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_customer_order_list,
                        parent, false);
                vh = new CustomerOrderListViewHolder();
                vh.orderStateTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_order_state_tv);
                vh.stPlaceTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_st_place_tv);
                vh.edPlaceTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_ed_place_tv);
                vh.timeTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_time_tv);
                vh.seatsPriceTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_seats_price_tv);
                vh.nameTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_name_tv);
                vh.phoneTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_phone_tv);
                vh.functionLl = (LinearLayout) convertView.findViewById(R.id.item_customer_order_list_function_ll);
                convertView.setTag(vh);
            } else {
                vh = (CustomerOrderListViewHolder) convertView.getTag();
            }
            if (bean.getMyOrder().get(position).getRemark().contains("询价")) {
                vh.orderStateTv.setText("询价订单");
            } else {
                vh.orderStateTv.setText("成团订单");
            }
            vh.stPlaceTv.setText(bean.getMyOrder().get(position).getDepartPlace());
            vh.edPlaceTv.setText(bean.getMyOrder().get(position).getDestination());
            vh.timeTv.setText(bean.getMyOrder().get(position).getUseDayStart());
            if (isType.equals("询价")) {
                vh.seatsPriceTv.setText(bean.getMyOrder().get(position).getCarSeats() + " 座");
            } else {
                vh.seatsPriceTv.setText(bean.getMyOrder().get(position).getCarSeats() + " 座/" +
                        bean.getMyOrder().get(position).getOrderPrice() + " 元");
            }
            vh.nameTv.setText(bean.getMyOrder().get(position).getRealName());
            vh.phoneTv.setText(bean.getMyOrder().get(position).getCustomerPhone());
            vh.functionLl.setVisibility(View.GONE);
            return convertView;
        }

        class CustomerOrderListViewHolder {
            private TextView orderStateTv;
            private TextView stPlaceTv;
            private TextView edPlaceTv;
            private TextView timeTv;
            private TextView seatsPriceTv;
            private TextView nameTv;
            private TextView phoneTv;
            private LinearLayout functionLl;
        }
    }

    class CustomerOrderListThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(customerOrderMap, UrlUtil.GET_ORDER_LIST_NEW);
                if (res.length() < 4) {
                    msg.obj = res;
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    customerOrderListBean = JSONUtil.customerOrderListJsonAnalytic(res);
                    if (customerOrderListBean.getMyOrder() != null) {
                        if (oldCustomerOrderListBean == null) {
                            oldCustomerOrderListBean = customerOrderListBean;
                        } else {
                            oldCustomerOrderListBean.getMyOrder().addAll(customerOrderListBean.getMyOrder());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = e.toString();
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (customerOrderListBean == null) {
                handler.sendEmptyMessage(2);
            } else {
                handler.sendEmptyMessage(1);
            }
        }
    }
}
