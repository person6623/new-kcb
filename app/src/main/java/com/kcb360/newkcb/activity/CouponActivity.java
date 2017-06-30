package com.kcb360.newkcb.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.entity.CouponBean;
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
 * Created by xinshichao on 2017/5/6.
 * <p>
 * 优惠券
 */

public class CouponActivity extends BaseActivity {

    // 布局相关
    // 返回
    private ImageView backIv;
    // xlistview
    private XListView xlv;
    // adapter
    private CouponAdapter adapter;


    // 数据获取
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 线程
    private CouponThread couponThread;
    // 传参map
    private Map<String, String> map;
    // 页数
    private int page = 1;
    // progressDialog
    private Dialog dg;
    // 数据暂存
    private CouponBean couponBean;
    // 数据存储
    private CouponBean oldCouponBean;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dg != null && dg.isShowing()) {
                dg.dismiss();
            }
            switch (msg.what) {
                case 0:
                    DiyDialog.loginDialog(CouponActivity.this, msg.obj.toString());
                    break;
                case 1:
                    page++;
                    adapter.setBean(oldCouponBean);
                    xlv.setAdapter(adapter);
                    xlv.stopRefresh();
                    xlv.stopLoadMore();
                    break;
                case 2:
                    DiyDialog.loginDialog(CouponActivity.this, "无更多数据");
                    if (oldCouponBean.getRows().size() == 0) {
                        adapter.setBean(oldCouponBean);
                        xlv.setAdapter(adapter);
                    }
                    xlv.stopRefresh();
                    xlv.stopLoadMore();
                    break;
            }
            return false;
        }
    });

    @Override
    public int setLayout() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void initData() {
        dg = ProgressDialog.show(CouponActivity.this, "", "获取优惠券中...");
        dg.setCanceledOnTouchOutside(false);
        dg.show();
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        map = new HashMap<>();
        map.put("page", page + "");
        map.put("rows", "7");
        map.put("receiptName", sp.getString("_cName", ""));
        map.put("way", "0");
        couponThread = new CouponThread();
        es.execute(couponThread);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backIv = findView(R.id.coupon_back_iv);
        xlv = findView(R.id.coupon_xlv);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        xlv.setPullLoadEnable(true);
        xlv.setPullRefreshEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                oldCouponBean = null;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });

        adapter = new CouponAdapter();
    }

    class CouponAdapter extends BaseAdapter {

        private CouponBean bean;

        public void setBean(CouponBean bean) {
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
            CouponViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(CouponActivity.this).inflate(R.layout.xlistview_item_coupon, parent, false);
                vh = new CouponViewHolder();
                vh.nameTv = (TextView) convertView.findViewById(R.id.xlv_item_coupon_name_tv);
                vh.maxPriceTv = (TextView) convertView.findViewById(R.id.xlv_item_coupon_max_price_tv);
                vh.ratioTv = (TextView) convertView.findViewById(R.id.xlv_item_coupon_ratio_tv);
                vh.stTimeTv = (TextView) convertView.findViewById(R.id.xlv_item_coupon_st_time_tv);
                vh.edTimeTv = (TextView) convertView.findViewById(R.id.xlv_item_coupon_ed_time_tv);
                vh.stateIv = (ImageView) convertView.findViewById(R.id.xlv_item_coupon_state_iv);
                convertView.setTag(vh);
            } else {
                vh = (CouponViewHolder) convertView.getTag();
                switch (bean.getRows().get(position).getUseState()) {
                    case 0:
                        vh.nameTv.setTextColor(0xff515151);
                        vh.ratioTv.setTextColor(0xffF78100);
                        vh.stateIv.setVisibility(View.GONE);
                        break;
                    case 1:
                        vh.nameTv.setTextColor(0xffAAAAAA);
                        vh.ratioTv.setTextColor(0xffAAAAAA);
                        vh.stateIv.setVisibility(View.VISIBLE);
                        vh.stateIv.setBackgroundResource(R.mipmap.coupon_item_used);
                        break;
                    case 2:
                        vh.nameTv.setTextColor(0xffAAAAAA);
                        vh.ratioTv.setTextColor(0xffAAAAAA);
                        vh.stateIv.setVisibility(View.VISIBLE);
                        vh.stateIv.setBackgroundResource(R.mipmap.coupon_item_overdue);
                        break;
                }
            }
            vh.nameTv.setText(bean.getRows().get(position).getDiscountName());
            vh.maxPriceTv.setText("最高使用金额" + bean.getRows().get(position).getDiscountPrice() + "元");
            vh.ratioTv.setText(bean.getRows().get(position).getDiscountRatio() + "折");
            vh.stTimeTv.setText(getTime(bean.getRows().get(position).getStartTime().getTime()));
            vh.edTimeTv.setText(getTime(bean.getRows().get(position).getEndTime().getTime()));
            switch (bean.getRows().get(position).getUseState()) {
                case 0:
                    vh.nameTv.setTextColor(0xff000000);
                    vh.ratioTv.setTextColor(0xffF78100);
                    vh.stateIv.setVisibility(View.GONE);
                    break;
                case 1:
                    vh.nameTv.setTextColor(0xffD2D2D2);
                    vh.ratioTv.setTextColor(0xffD2D2D2);
                    vh.stateIv.setVisibility(View.VISIBLE);
                    vh.stateIv.setBackgroundResource(R.mipmap.coupon_item_used);
                    break;
                case 2:
                    vh.nameTv.setTextColor(0xffD2D2D2);
                    vh.ratioTv.setTextColor(0xffD2D2D2);
                    vh.stateIv.setVisibility(View.VISIBLE);
                    vh.stateIv.setBackgroundResource(R.mipmap.coupon_item_overdue);
                    break;
            }
            return convertView;
        }

        class CouponViewHolder {
            private TextView nameTv;
            private TextView maxPriceTv;
            private TextView ratioTv;
            private TextView stTimeTv;
            private TextView edTimeTv;
            private ImageView stateIv;
        }
    }

    private String getTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(time));
    }

    class CouponThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(map, UrlUtil.DISCOUNT_LIST);
                if (res.length() < 4) {
                    msg.obj = res;
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    if (oldCouponBean == null) {
                        couponBean = JSONUtil.couponJsonAnalytic(res);
                        oldCouponBean = JSONUtil.couponJsonAnalytic(res);
                    } else {
                        couponBean = JSONUtil.couponJsonAnalytic(res);
                        oldCouponBean.getRows().addAll(couponBean.getRows());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = e.toString();
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (couponBean.getRows().size() == 0) {
                handler.sendEmptyMessage(2);
                return;
            }
            handler.sendEmptyMessage(1);
        }
    }
}
