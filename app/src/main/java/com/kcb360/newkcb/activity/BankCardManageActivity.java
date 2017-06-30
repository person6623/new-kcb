package com.kcb360.newkcb.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.entity.BankCardListBean;
import com.kcb360.newkcb.entity.LoginBean;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/4/28.
 * <p>
 * 银行卡管理
 */

public class BankCardManageActivity extends BaseActivity implements View.OnClickListener {

    // 布局相关
    // 返回
    private ImageView backIv;
    // 未设置银行卡布局
    private LinearLayout noCardLl;
    // 未设置银行卡添加
    private LinearLayout noBankCardLl;
    // 银行卡footview
    private View footView;
    // 银行卡footer
    private LinearLayout addCardLl;
    // listview
    private ListView lv;
    // adapter
    private BankCardManageAdapter adapter;
    // 获取银行对应行号
    private String[] bankCode;

    // 数据获取
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 线程
    private GetBankListThread getBankListThread;
    // 删卡线程
    private DelBankCardThread delBankCardThread;
    // 卡列表传参map
    private Map<String, String> map;
    // 删卡传参map
    private Map<String, String> delCardMap;
    // progessdialog
    private Dialog dg;
    // 存储数据
    private BankCardListBean bankCardListBean;
    // 删卡存储数据
    private LoginBean delCardBean;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dg != null && dg.isShowing()) {
                dg.dismiss();
            }
            switch (msg.what) {
                case 0:
                    DiyDialog.loginDialog(BankCardManageActivity.this, msg.obj.toString());
                    break;
                case 1:
                    noCardLl.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    adapter.setBean(bankCardListBean);
                    lv.setAdapter(adapter);
                    break;
                case 2:
                    noCardLl.setVisibility(View.VISIBLE);
                    lv.setVisibility(View.GONE);
                    break;
                case 3:
                    if (delCardBean.getCode().equals("1")) {
                        Toast.makeText(BankCardManageActivity.this, delCardBean.getContext(), Toast.LENGTH_SHORT).show();
                        initData();
                    } else {
                        DiyDialog.loginDialog(BankCardManageActivity.this, delCardBean.getContext());
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public int setLayout() {
        return R.layout.activity_bank_card_manage;
    }

    @Override
    protected void initData() {
        dg = ProgressDialog.show(BankCardManageActivity.this, "", "获取银行卡数据中...");
        dg.setCanceledOnTouchOutside(false);
        dg.show();
        map = new HashMap<>();
        getBankListThread = new GetBankListThread();
        es.execute(getBankListThread);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backIv = findView(R.id.bank_card_manage_back_iv);
        noCardLl = (LinearLayout) findViewById(R.id.bank_card_manage_no_card_ll);
        footView = LayoutInflater.from(BankCardManageActivity.this).inflate(R.layout.item_bank_card_footer, null);
        noBankCardLl = findView(R.id.bank_card_manage_add_bank_carde_ll);
        addCardLl = (LinearLayout) footView.findViewById(R.id.item_bank_card_manage_add_card_ll);
        lv = (ListView) findViewById(R.id.bank_card_manage_lv);
        lv.addFooterView(footView);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        noBankCardLl.setOnClickListener(this);
        addCardLl.setOnClickListener(this);

        adapter = new BankCardManageAdapter();
        bankCode = getResources().getStringArray(R.array.bank_code);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bank_card_manage_add_bank_carde_ll ||
                v.getId() == R.id.item_bank_card_manage_add_card_ll) {
            startActivityForResult(new Intent(BankCardManageActivity.this, AddBankCardActivity.class), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 1) {
            initData();
        }
    }

    class BankCardManageAdapter extends BaseAdapter {

        private BankCardListBean bean;

        public void setBean(BankCardListBean bean) {
            this.bean = bean;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return bean != null && bean.getBankList().size() > 0 ? bean.getBankList().size() : 0;
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
            BankCardManageViewHloder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(BankCardManageActivity.this).inflate(R.layout.list_item_bank_manage, parent, false);
                vh = new BankCardManageViewHloder();
                vh.allLl = (LinearLayout) convertView.findViewById(R.id.list_item_bank_manage_all_ll);
                vh.bankIconIv = (ImageView) convertView.findViewById(R.id.list_item_bank_manage_bank_logo_iv);
                vh.bankNameTv = (TextView) convertView.findViewById(R.id.list_item_bank_manage_bank_name_tv);
                vh.bindPhoneTv = (TextView) convertView.findViewById(R.id.list_item_bank_manage_bind_phone_tv);
                vh.accountNumberTv = (TextView) convertView.findViewById(R.id.list_item_bank_manage_bank_card_num_tv);
                convertView.setTag(vh);
            } else {
                vh = (BankCardManageViewHloder) convertView.getTag();
            }
            vh.allLl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BankCardManageActivity.this);
                    builder.setTitle("操作提示");
                    builder.setMessage("是否删除此银行卡?");
                    builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dg = ProgressDialog.show(BankCardManageActivity.this, "", "删除信息中...");
                            dg.setCanceledOnTouchOutside(false);
                            dg.show();
                            delCardMap = new HashMap<>();
                            delCardMap.put("delId", bean.getBankList().get(position).getId() + "");
                            delBankCardThread = new DelBankCardThread();
                            es.execute(delBankCardThread);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                    return false;
                }
            });
            vh.allLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BankCardManageActivity.this, BalancePurseActivity.class);
//                    for (Iterator iterator = getIntent().getExtras().keySet().iterator(); iterator.hasNext(); ) {
//                        if (iterator.next().toString().equals("balance")) {
//                            if (getIntent().getStringExtra("balance").equals("")) {
//
//                            } else if (Float.parseFloat(getIntent().getStringExtra("balance")) <= 0) {
//                                DiyDialog.loginDialog(BankCardManageActivity.this, "余额不足");
//                                return;
//                            }
//                            break;
//                        }
//                    }
                    intent.putExtra("bankCard", bean.getBankList().get(position));
                    startActivity(intent);
                }
            });
            vh.bankIconIv.setVisibility(View.VISIBLE);
            if (bean.getBankList().get(position).getRecBankNo().equals(bankCode[1])) {
                vh.allLl.setBackgroundResource(R.mipmap.bank_cbc_bg);
                vh.bankIconIv.setBackgroundResource(R.mipmap.bank_cbc_icon);
            } else if (bean.getBankList().get(position).getRecBankNo().equals(bankCode[2])) {
                vh.allLl.setBackgroundResource(R.mipmap.bank_icbc_bg);
                vh.bankIconIv.setBackgroundResource(R.mipmap.bank_icbc_icon);
            } else if (bean.getBankList().get(position).getRecBankNo().equals(bankCode[3])) {
                vh.allLl.setBackgroundResource(R.mipmap.bank_abc_bg);
                vh.bankIconIv.setBackgroundResource(R.mipmap.bank_abc_icon);
            } else if (bean.getBankList().get(position).getRecBankNo().equals(bankCode[4])) {
                vh.allLl.setBackgroundResource(R.mipmap.bank_ctb_bg);
                vh.bankIconIv.setBackgroundResource(R.mipmap.bank_ctb_icon);
            } else if (bean.getBankList().get(position).getRecBankNo().equals(bankCode[5])) {
                vh.allLl.setBackgroundResource(R.mipmap.bank_psbc_bg);
                vh.bankIconIv.setBackgroundResource(R.mipmap.bank_psbc_icon);
            } else {
                vh.allLl.setBackgroundColor(0xff66CCFF);
                vh.bankIconIv.setVisibility(View.GONE);
            }
            vh.bankNameTv.setText(bean.getBankList().get(position).getBankName());
            vh.bindPhoneTv.setText(getPhoneX(bean.getBankList().get(position).getBindPhone()));
            vh.accountNumberTv.setText(getBankCardX(bean.getBankList().get(position).getAccountNumber().toString()));
            return convertView;
        }

        class BankCardManageViewHloder {
            private LinearLayout allLl;
            private ImageView bankIconIv;
            private TextView accountNumberTv;
            private TextView bankNameTv;
            private TextView bindPhoneTv;
        }
    }

    public String getPhoneX(String phone) {
        if (phone.length() < 4) {
            return phone;
        }
        return "手机尾号 " + phone.substring(phone.length() - 4, phone.length());
    }

    public String getBankCardX(String bankCardNum) {
        if (bankCardNum.length() < 12) {
            return bankCardNum;
        }
        if (bankCardNum.length() < 17) {
            return bankCardNum.substring(0, 4) + "   ****   ****   " + bankCardNum.substring(13, bankCardNum.length());
        }
        return bankCardNum.substring(0, 4) + "   ****   ****   " + bankCardNum.substring(13, 17) +
                bankCardNum.substring(17, bankCardNum.length());
    }

    class GetBankListThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(map, UrlUtil.GET_BANK_LIST);
                if (res.length() <= 4) {
                    msg.obj = res;
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    bankCardListBean = JSONUtil.bankCardListJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.what = 0;
                msg.obj = e.toString();
                handler.sendMessage(msg);
            }
            if (bankCardListBean.getBankList().size() == 0) {
                handler.sendEmptyMessage(2);
            }
            handler.sendEmptyMessage(1);
        }
    }

    class DelBankCardThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(delCardMap, UrlUtil.DEL_BANK_CARD);
                if (res.length() < 4) {
                    msg.obj = res;
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    delCardBean = JSONUtil.LoginJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = e.toString();
                msg.what = 0;
                handler.sendMessage(msg);
            }
            handler.sendEmptyMessage(3);
        }
    }
}
