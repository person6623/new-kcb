package com.kcb360.newkcb.activity;

import android.app.Dialog;
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
import com.kcb360.newkcb.entity.WithDrawCashBean;
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
 * Created by xinshichao on 2017/5/5.
 * <p>
 * 提现
 */

public class WithDrawCashActivity extends BaseActivity {

    // 布局相关
    // 返回
    private ImageView backIv;
    // xlistview
    private XListView xlv;
    // adapter
    private WithDrawCashAdapter adapter;


    // 数据获取
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 线程
    private WithDrawCashThread withDrawCashThread;
    // 传参map
    private Map<String, String> map;
    // 页数
    private int page = 1;
    // progressDialog
    private Dialog dg;
    // 数据暂存
    private WithDrawCashBean withDrawCashBean;
    // 数据存储
    private WithDrawCashBean oldWithDrawCashBean;
    // 状态
    private String[] stateArr;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dg != null && dg.isShowing()) {
                dg.dismiss();
            }
            switch (msg.what) {
                case 0:
                    DiyDialog.loginDialog(WithDrawCashActivity.this, msg.obj.toString());
                    break;
                case 1:
                    page++;
                    adapter.setBean(oldWithDrawCashBean);
                    xlv.setAdapter(adapter);
                    break;
                case 2:
                    DiyDialog.loginDialog(WithDrawCashActivity.this, "无更多数据");
                    if (oldWithDrawCashBean == null) {
                        adapter.setBean(oldWithDrawCashBean);
                        xlv.setAdapter(adapter);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public int setLayout() {
        return R.layout.activity_with_draw_cash;
    }

    @Override
    protected void initData() {
        map = new HashMap<>();
        map.put("pageSize", page + "");
        withDrawCashThread = new WithDrawCashThread();
        es.execute(withDrawCashThread);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backIv = findView(R.id.with_draw_cash_back_iv);
        xlv = findView(R.id.with_draw_cash_xlv);

        backIv.setOnClickListener(new View.OnClickListener() {
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
                oldWithDrawCashBean = null;
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });

        adapter = new WithDrawCashAdapter();

        stateArr = getResources().getStringArray(R.array.with_draw_cash);
    }

    class WithDrawCashAdapter extends BaseAdapter {

        private WithDrawCashBean bean;

        public void setBean(WithDrawCashBean bean) {
            this.bean = bean;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return bean != null && bean.getDrawList().size() != 0 ? bean.getDrawList().size() : 0;
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
            WithDrawCashViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(WithDrawCashActivity.this).inflate(
                        R.layout.xlistview_item_with_draw_cash, parent, false);
                vh = new WithDrawCashViewHolder();
                vh.leftTv = (TextView) convertView.findViewById(R.id.xlv_item_with_draw_cash_left_tv);
                vh.typeTv = (TextView) convertView.findViewById(R.id.xlv_item_with_draw_cash_type_tv);
                vh.statusTv = (TextView) convertView.findViewById(R.id.xlv_item_with_draw_cash_status_tv);
                vh.noteTv = (TextView) convertView.findViewById(R.id.xlv_item_with_draw_cash_note_tv);
                vh.aTimeTv = (TextView) convertView.findViewById(R.id.xlv_item_with_draw_cash_atime_tv);
                vh.aMoneyTv = (TextView) convertView.findViewById(R.id.xlv_item_with_draw_cash_amoney_tv);
                convertView.setTag(vh);
            } else {
                vh = (WithDrawCashViewHolder) convertView.getTag();
            }
            switch (bean.getDrawList().get(position).getState()) {
                case -1:
                    vh.leftTv.setBackgroundColor(0xffD74F52);
                    vh.statusTv.setTextColor(0xffD74F52);
                    vh.statusTv.setText(stateArr[5]);
                    vh.aMoneyTv.setTextColor(0xffD74F52);
                    break;
                case 0:
                    vh.leftTv.setBackgroundColor(0xffF89A33);
                    vh.statusTv.setTextColor(0xffF89A33);
                    vh.statusTv.setText(stateArr[0]);
                    vh.aMoneyTv.setTextColor(0xffF89A33);
                    break;
                case 1:
                    vh.leftTv.setBackgroundColor(0xff49C155);
                    vh.statusTv.setTextColor(0xff49C155);
                    vh.statusTv.setText(stateArr[1]);
                    vh.aMoneyTv.setTextColor(0xff49C155);
                    break;
                case 2:
                    vh.leftTv.setBackgroundColor(0xffD74F52);
                    vh.statusTv.setTextColor(0xffD74F52);
                    vh.statusTv.setText(stateArr[2]);
                    vh.aMoneyTv.setTextColor(0xffD74F52);
                    break;
                case 3:
                    vh.leftTv.setBackgroundColor(0xffD74F52);
                    vh.statusTv.setTextColor(0xffD74F52);
                    vh.statusTv.setText(stateArr[3]);
                    vh.aMoneyTv.setTextColor(0xffD74F52);
                    break;
                case 4:
                    vh.leftTv.setBackgroundColor(0xffF89A33);
                    vh.statusTv.setTextColor(0xffF89A33);
                    vh.statusTv.setText(stateArr[4]);
                    vh.aMoneyTv.setTextColor(0xffF89A33);
                    break;
            }
            vh.typeTv.setText(bean.getDrawList().get(position).getNote());
            vh.noteTv.setText(bean.getDrawList().get(position).getBankName() +
                    bean.getDrawList().get(position).getAccountNumber().substring(
                            bean.getDrawList().get(position).getAccountNumber().length() - 4,
                            bean.getDrawList().get(position).getAccountNumber().length()));
            vh.aTimeTv.setText(bean.getDrawList().get(position).getAtime());
            vh.aMoneyTv.setText(bean.getDrawList().get(position).getDrawMoney() + "");
            return convertView;
        }

        class WithDrawCashViewHolder {
            private TextView leftTv;
            private TextView typeTv;
            private TextView statusTv;
            private TextView noteTv;
            private TextView aTimeTv;
            private TextView aMoneyTv;
        }
    }

    class WithDrawCashThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(map, UrlUtil.DRAW_CASH_LIST);
                if (res.length() < 4) {
                    msg.obj = res;
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    if (oldWithDrawCashBean == null) {
                        withDrawCashBean = JSONUtil.withDrawCashBeanJsonAnalytic(res);
                        oldWithDrawCashBean = JSONUtil.withDrawCashBeanJsonAnalytic(res);
                    } else {
                        oldWithDrawCashBean.getDrawList().addAll(withDrawCashBean.getDrawList());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = e.toString();
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (withDrawCashBean.getDrawList().size() == 0) {
                handler.sendEmptyMessage(2);
            }
            handler.sendEmptyMessage(1);
        }
    }
}
