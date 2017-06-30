package com.kcb360.newkcb.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseFragmentVFour;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.base.BaseFragment;
import com.kcb360.newkcb.entity.TradeNotesBean;
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
 * Created by xinshichao on 2017/5/4.
 * <p>
 * 交易记录
 */

public class TradeNotesFragment extends BaseFragmentVFour {

    // 显示内容类型
    public static final int ALL_NOTES = 0;
    public static final int TAKE_IN_NOTES = 1;
    public static final int TAKE_OUT_NOTES = 2;
    // 当前页面notes
    private int notesType = 0;

    // 布局相关
    // xlistview
    private XListView xlv;
    // 总收入支出布局
    private LinearLayout allTakeLl;
    // 总支出, 总收入
    private TextView takeOutTv, takeInTv;

    // 数据相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 线程
    private TradeNotesThread tradeNotesThread;
    // progressDialog
    private Dialog dg;
    // 传参map
    private Map<String, String> map;
    // 页数
    private int page = 1;
    // 数据暂存
    private TradeNotesBean tradeNotesBean;
    // 数据存储
    private TradeNotesBean oldTradeNotesBean;
    // type对应
    private String[] typeArr;
    // adapter
    private TradeNotesBaseAdapter adapter;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dg != null && dg.isShowing()) {
                dg.dismiss();
            }
            switch (msg.what) {
                case 0:
                    DiyDialog.loginDialog(getActivity(), msg.obj.toString());
                    break;
                case 1:
                    page++;
                    takeOutTv.setText(oldTradeNotesBean.getOthers().split("-")[1]);
                    takeInTv.setText(oldTradeNotesBean.getOthers().split("-")[0]);
                    adapter.setBean(oldTradeNotesBean);
                    xlv.setAdapter(adapter);
                    xlv.stopRefresh();
                    xlv.stopLoadMore();
                    break;
                case 2:
                    DiyDialog.loginDialog(getActivity(), "无更多数据");
                    if (oldTradeNotesBean == null) {
                        takeOutTv.setText("0");
                        takeInTv.setText("0");
                        adapter.setBean(oldTradeNotesBean);
                        xlv.setAdapter(adapter);
                    }
                    xlv.stopRefresh();
                    xlv.stopLoadMore();
                    break;
            }
            return false;
        }
    });

    public void setNotesType(int notesType) {
        this.notesType = notesType;
    }

    @Override
    public int setLayout() {
        return R.layout.xlistview_commonly;
    }

    @Override
    public void intiData() {
        dg = ProgressDialog.show(getActivity(), "", "获取交易记录中...");
        dg.setCanceledOnTouchOutside(false);
        dg.show();
        map = new HashMap<>();
        switch (notesType) {
            case ALL_NOTES:
                map.put("status", "-1");
                break;
            case TAKE_IN_NOTES:
                map.put("status", "0");
                break;
            case TAKE_OUT_NOTES:
                map.put("status", "1");
                break;
        }
        map.put("page", page + "");
        map.put("rows", "7");
        tradeNotesThread = new TradeNotesThread();
        es.execute(tradeNotesThread);
    }

    @Override
    public void intiView(View view, Bundle savedInstanceState) {

        typeArr = getResources().getStringArray(R.array.trade_notes);

        allTakeLl = (LinearLayout) view.findViewById(R.id.xlistview_commonly_ll);
        allTakeLl.setVisibility(View.VISIBLE);
        takeOutTv = (TextView) view.findViewById(R.id.xlistview_take_out_tv);
        takeInTv = (TextView) view.findViewById(R.id.xlistview_take_in_tv);
        xlv = (XListView) view.findViewById(R.id.xlistview_commonly_xl);
        xlv.setPullLoadEnable(true);
        xlv.setPullRefreshEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                oldTradeNotesBean = null;
                intiData();
            }

            @Override
            public void onLoadMore() {
                intiData();
            }
        });

        adapter = new TradeNotesBaseAdapter();
    }

    class TradeNotesBaseAdapter extends BaseAdapter {

        private TradeNotesBean bean;

        public void setBean(TradeNotesBean bean) {
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
            TradeNotesViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.xlistview_item_trade_notes, parent, false);
                vh = new TradeNotesViewHolder();
                vh.leftBgTv = (TextView) convertView.findViewById(R.id.xlv_item_trade_notes_left_tv);
                vh.typeTv = (TextView) convertView.findViewById(R.id.xlv_item_trade_notes_type_tv);
                vh.statusTv = (TextView) convertView.findViewById(R.id.xlv_item_trade_notes_status_tv);
                vh.noteTv = (TextView) convertView.findViewById(R.id.xlv_item_trade_notes_note_tv);
                vh.aTimeTv = (TextView) convertView.findViewById(R.id.xlv_item_trade_notes_atime_tv);
                vh.aMoneyTv = (TextView) convertView.findViewById(R.id.xlv_item_trade_notes_amoney_tv);
                convertView.setTag(vh);
            } else {
                vh = (TradeNotesViewHolder) convertView.getTag();
            }
            if (bean.getRows().get(position).getStatus() == 0) {
                vh.leftBgTv.setBackgroundResource(R.drawable.custom_orange_left_bg);
                vh.statusTv.setText("收入");
                vh.statusTv.setTextColor(0xFFF89A33);
                vh.aMoneyTv.setText("+" + bean.getRows().get(position).getAmoney());
                vh.aMoneyTv.setTextColor(0xFFF89A33);
            } else {
                vh.leftBgTv.setBackgroundResource(R.drawable.custom_red_left_bg);
                vh.statusTv.setText("支出");
                vh.statusTv.setTextColor(0xFFD74F52);
                vh.aMoneyTv.setText("-" + bean.getRows().get(position).getAmoney());
                vh.aMoneyTv.setTextColor(0xFFD74F52);
            }
            vh.typeTv.setText(typeArr[bean.getRows().get(position).getType()]);
            vh.noteTv.setText(bean.getRows().get(position).getNote());
            vh.aTimeTv.setText(getTime(bean.getRows().get(position).getAtime()));
            return convertView;
        }

        class TradeNotesViewHolder {
            private TextView leftBgTv;
            private TextView typeTv;
            private TextView statusTv;
            private TextView noteTv;
            private TextView aTimeTv;
            private TextView aMoneyTv;
        }
    }

    public String getTime(TradeNotesBean.RowsBean.AtimeBean atime) {
        SimpleDateFormat sdfMD = new SimpleDateFormat("MM-dd HH:mm");
        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (sdfYMD.format(new Date(atime.getTime())).substring(0, 5).equals(sdfYMD.format(new Date(System.currentTimeMillis())))) {
            return sdfMD.format(new Date(atime.getTime()));
        }
        return sdfYMD.format(new Date(atime.getTime()));
    }

    class TradeNotesThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(map, UrlUtil.TRADE_RECORD_LIST);
                if (res.length() < 4) {
                    msg.obj = res;
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    if (oldTradeNotesBean == null) {
                        tradeNotesBean = JSONUtil.tradeNotesJsonAnalytic(res);
                        oldTradeNotesBean = JSONUtil.tradeNotesJsonAnalytic(res);
                    } else {
                        tradeNotesBean = JSONUtil.tradeNotesJsonAnalytic(res);
                        oldTradeNotesBean.getRows().addAll(tradeNotesBean.getRows());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = e.toString();
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (tradeNotesBean.getRows().size() == 0) {
                handler.sendEmptyMessage(2);
                return;
            }
            handler.sendEmptyMessage(1);
        }
    }
}
