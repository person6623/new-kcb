package com.kcb360.newkcb.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.base.StandardUtil;
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
 * Created by xinshichao on 2017/5/25.
 * <p>
 * 用户已发布订单
 */

public class CustomerOrderListAcvity extends BaseActivity {

    // 布局相关
    // 返回
    private LinearLayout backLl;
    // radiogroup: 状态, 类型
    private RadioGroup allRg, typeRg;
    // 未完成订单, 成交订单, 过期订单
    private RadioButton notFinishRb, successRb, overdueRb;
    // 询价订单, 成团订单
    private RadioButton askRb, normalRb;
    // xlistview
    private XListView xlv;
    // adapter
    private CustomerOrderListAdapter adapter;
    // 状态控制 1成交订单 2有效未成 3过期订单
    private int isState = 0;
    // 类型控制 询价, 成团
    private String isType = "询价";

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
                    Toast.makeText(CustomerOrderListAcvity.this, ExceptionUtil.exceptionSwitch(msg.obj.toString())
                            , Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    page++;
                    adapter.setBean(oldCustomerOrderListBean);
                    xlv.setAdapter(adapter);
                    xlv.stopRefresh();
                    xlv.stopLoadMore();
//                    if (customerOrderListBean.getMyOrder().size() < 5) {
//                        xlv.setPullLoadEnable(false);
//                    }
                    break;
                case 2:
                    Toast.makeText(CustomerOrderListAcvity.this, "无更多数据", Toast.LENGTH_SHORT).show();
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
        return R.layout.activity_customer_order_list;
    }

    @Override
    protected void initData() {
        if (customerOrderMap == null) {
            customerOrderMap = new HashMap<>();
        } else {
            customerOrderMap.clear();
        }
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        customerOrderMap.put("mbName", sp.getString("_cName", ""));
        customerOrderMap.put("state", isState + "");
        customerOrderMap.put("remark", isType);
        customerOrderMap.put("pageSize", page + "");
        customerOrderMap.put("rows", "5");
        customerOrderListThread = new CustomerOrderListThread();
        es.execute(customerOrderListThread);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backLl = findView(R.id.customer_order_list_back_ll);
        allRg = findView(R.id.customer_order_list_all_rg);
        typeRg = findView(R.id.customer_order_list_type_rg);
        notFinishRb = findView(R.id.customer_order_list_not_finish_rb);
        successRb = findView(R.id.customer_order_list_success_rb);
        overdueRb = findView(R.id.customer_order_list_overdue_rb);
        askRb = findView(R.id.customer_order_list_ask_rb);
        normalRb = findView(R.id.customer_order_list_normal_rb);
        xlv = findView(R.id.customer_order_list_xlv);

        backLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        allRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.customer_order_list_not_finish_rb:
                        notFinishRb.setBackgroundResource(R.drawable.sc_black_bg);
                        successRb.setBackgroundResource(R.drawable.white_bg);
                        overdueRb.setBackgroundResource(R.drawable.white_bg);
                        isState = 2;
                        break;
                    case R.id.customer_order_list_success_rb:
                        notFinishRb.setBackgroundResource(R.drawable.white_bg);
                        successRb.setBackgroundResource(R.drawable.sc_black_bg);
                        overdueRb.setBackgroundResource(R.drawable.white_bg);
                        isState = 1;
                        break;
                    case R.id.customer_order_list_overdue_rb:
                        notFinishRb.setBackgroundResource(R.drawable.white_bg);
                        successRb.setBackgroundResource(R.drawable.white_bg);
                        overdueRb.setBackgroundResource(R.drawable.sc_black_bg);
                        isState = 3;
                        break;
                }
            }
        });

        typeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.customer_order_list_ask_rb:
                        askRb.setBackgroundResource(R.drawable.sc_black_bg);
                        normalRb.setBackgroundResource(R.drawable.white_bg);
                        isType = "询价";
                        oldCustomerOrderListBean = null;
                        page = 1;
                        initData();
                        break;
                    case R.id.customer_order_list_normal_rb:
                        askRb.setBackgroundResource(R.drawable.white_bg);
                        normalRb.setBackgroundResource(R.drawable.sc_black_bg);
                        isType = "成团";
                        oldCustomerOrderListBean = null;
                        page = 1;
                        initData();
                        break;
                }
            }
        });

        adapter = new CustomerOrderListAdapter();
        xlv.setAdapter(adapter);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                oldCustomerOrderListBean = null;
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
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
                convertView = LayoutInflater.from(CustomerOrderListAcvity.this).inflate(R.layout.item_customer_order_list,
                        parent, false);
                vh = new CustomerOrderListViewHolder();
                vh.orderStateTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_order_state_tv);
                vh.stPlaceTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_st_place_tv);
                vh.edPlaceTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_ed_place_tv);
                vh.timeTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_time_tv);
                vh.seatsPriceTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_seats_price_tv);
                vh.nameTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_name_tv);
                vh.phoneTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_phone_tv);
                vh.standCarListTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_stand_car_list_tv);
                vh.cancelTv = (TextView) convertView.findViewById(R.id.item_customer_order_list_cancel_tv);
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
            vh.standCarListTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CustomerOrderListAcvity.this, DriverEmptyCarListActivity.class);
                    intent.putExtra("customer", bean.getMyOrder().get(position));
                    intent.putExtra("role", StandardUtil.ROLE_CUSTOMER);
                    startActivity(intent);
                }
            });
            if (isType.equals("询价")) {
                vh.cancelTv.setVisibility(View.VISIBLE);
            } else {
                vh.cancelTv.setVisibility(View.GONE);
            }
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
            private TextView standCarListTv;
            private TextView cancelTv;
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
                    if (oldCustomerOrderListBean == null) {
                        oldCustomerOrderListBean = customerOrderListBean;
                    } else {
                        oldCustomerOrderListBean.getMyOrder().addAll(customerOrderListBean.getMyOrder());
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
