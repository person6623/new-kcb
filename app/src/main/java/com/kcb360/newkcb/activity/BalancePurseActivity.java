package com.kcb360.newkcb.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.diy.DiyEditText;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.entity.BankCardListBean;
import com.kcb360.newkcb.entity.LoginBean;
import com.kcb360.newkcb.entity.UserPurseBean;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/5/8.
 * <p>
 * 余额转出
 */

public class BalancePurseActivity extends BaseActivity {

    // 布局相关
    // 返回, 银行logo
    private ImageView backIv, bankLogoIv;
    // 背景
    private LinearLayout allLl;
    // 银行名称, 预留手机, 银行卡号, 可提款数
    private TextView bankNameTv, bindPhoneTv, bankCardNumTv, possibleNumTv;
    // 提款数
    private EditText balanceNumEt;
    // 提款
    private Button balanceBtn;

    // 数据相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 钱包数据线程
    private UserPurseDataThread userPurseDataThread;
    // 余额数据线程
    private BalanceThread balanceThread;
    // 余额信息map
    private Map<String, String> possibleBalanceMap;
    // 提取余额map
    private Map<String, String> balanceMap;
    // 银行卡数据
    private BankCardListBean.BankListBean bankListBean;
    // 余额数据存储
    private UserPurseBean userPurseBean;
    // 提款数据存储
    private LoginBean balanceBean;
    // progressDialog
    private Dialog dg;
    // 获取银行对应行号
    private String[] bankCode;


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dg != null && dg.isShowing()) {
                dg.dismiss();
            }
            switch (msg.what) {
                case 0:
                    DiyDialog.loginDialog(BalancePurseActivity.this, msg.obj.toString());
                    break;
                case 1:
                    if (userPurseBean == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BalancePurseActivity.this);
                        builder.setMessage("未能获取余额信息");
                        builder.setPositiveButton("再次获取", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                initData();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    } else {
                        if (userPurseBean.getCode() == 1) {
                            stuffData();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(BalancePurseActivity.this);
                            builder.setMessage(userPurseBean.getContext());
                            builder.setPositiveButton("再次获取", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    initData();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create().show();
                        }
                    }
                    break;
                case 2:
                    if (balanceBean.getCode().equals("1")) {
                        Toast.makeText(BalancePurseActivity.this, balanceBean.getContext(), Toast.LENGTH_SHORT).show();
                        initData();
                    } else {
                        DiyDialog.loginDialog(BalancePurseActivity.this, balanceBean.getContext());
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public int setLayout() {
        return R.layout.activity_balance_purse;
    }

    @Override
    protected void initData() {
        dg = ProgressDialog.show(BalancePurseActivity.this, "", "获取可用余额中...");
        dg.setCanceledOnTouchOutside(false);
        dg.show();
        possibleBalanceMap = new HashMap<>();
        userPurseDataThread = new UserPurseDataThread();
        es.execute(userPurseDataThread);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        bankListBean = getIntent().getParcelableExtra("bankCard");

        bankCode = getResources().getStringArray(R.array.bank_code);

        backIv = findView(R.id.banlace_purse_back_iv);
        bankLogoIv = findView(R.id.banlace_purse_bank_logo_iv);
        allLl = findView(R.id.banlace_purse_all_ll);
        bankNameTv = findView(R.id.banlace_purse_bank_name_tv);
        bindPhoneTv = findView(R.id.banlace_purse_bind_phone_tv);
        bankCardNumTv = findView(R.id.banlace_purse_bank_card_num_tv);
        possibleNumTv = findView(R.id.banlace_purse_possible_num_tv);
        balanceNumEt = findView(R.id.banlace_purse_balance_num_et);
        balanceBtn = findView(R.id.banlace_purse_balance_btn);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        balanceNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (userPurseBean == null) {
                    return;
                }
                if (s.length() == 0) {
                    return;
                }
                if (Float.parseFloat(s.toString()) >
                        (userPurseBean.getMemberWallet().getCashBalance() -
                                userPurseBean.getMemberWallet().getNotFinish())) {
                    balanceNumEt.setText(String.valueOf(userPurseBean.getMemberWallet().getCashBalance() -
                            userPurseBean.getMemberWallet().getNotFinish()));
                }
            }
        });

        balanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doCheck()) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(BalancePurseActivity.this);
                View view = LayoutInflater.from(BalancePurseActivity.this).inflate(R.layout.dialog_balance_password, null);
                final DiyEditText passEt = (DiyEditText) view.findViewById(R.id.dialog_balance_password_cet);
                passEt.setOnTextLengMaxListener(new DiyEditText.OnTextLengMaxListener() {
                    @Override
                    public void onTextLengMaxListener(CharSequence text) {
                        if (text.length() == 6) {
                            dg = ProgressDialog.show(BalancePurseActivity.this, "", "提款中...");
                            dg.setCanceledOnTouchOutside(false);
                            dg.show();
                            balanceMap = new HashMap<>();
                            balanceMap.put("accountNumber", bankListBean.getAccountNumber());
                            balanceMap.put("drawMoney", balanceNumEt.getText().toString());
                            balanceMap.put("payPwd", passEt.getText().toString());
                            balanceThread = new BalanceThread();
                            es.execute(balanceThread);
                        }
                    }

                });
                builder.setView(view);
                builder.create().show();
            }
        });
    }

    public void stuffData() {
        if (bankListBean.getRecBankNo().equals(bankCode[1])) {
            allLl.setBackgroundResource(R.mipmap.bank_cbc_bg);
            bankLogoIv.setBackgroundResource(R.mipmap.bank_cbc_icon);
        } else if (bankListBean.getRecBankNo().equals(bankCode[2])) {
            allLl.setBackgroundResource(R.mipmap.bank_icbc_bg);
            bankLogoIv.setBackgroundResource(R.mipmap.bank_icbc_icon);
        } else if (bankListBean.getRecBankNo().equals(bankCode[3])) {
            allLl.setBackgroundResource(R.mipmap.bank_abc_bg);
            bankLogoIv.setBackgroundResource(R.mipmap.bank_abc_icon);
        } else if (bankListBean.getRecBankNo().equals(bankCode[4])) {
            allLl.setBackgroundResource(R.mipmap.bank_ctb_bg);
            bankLogoIv.setBackgroundResource(R.mipmap.bank_ctb_icon);
        } else if (bankListBean.getRecBankNo().equals(bankCode[5])) {
            allLl.setBackgroundResource(R.mipmap.bank_psbc_bg);
            bankLogoIv.setBackgroundResource(R.mipmap.bank_psbc_icon);
        } else {
            allLl.setBackgroundColor(0xff66CCFF);
            bankLogoIv.setVisibility(View.GONE);
        }
        bankNameTv.setText(bankListBean.getBankName());
        bindPhoneTv.setText(getPhoneX(bankListBean.getBindPhone()));
        bankCardNumTv.setText(getBankCardX(bankListBean.getAccountNumber().toString()));
        possibleNumTv.setText(String.valueOf(userPurseBean.getMemberWallet().getCashBalance() -
                userPurseBean.getMemberWallet().getNotFinish()));
    }

    private boolean doCheck() {
        if (balanceNumEt.getText().equals("") || balanceNumEt.getText().equals("请输入提现金额")) {
            DiyDialog.loginDialog(BalancePurseActivity.this, "请输入提款金额");
            return false;
        }
        return true;
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

    class UserPurseDataThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(possibleBalanceMap, UrlUtil.MEMBER_WALLET);
                if (res.length() < 3) {
                    msg.what = 0;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    return;
                }
                userPurseBean = JSONUtil.userPurseJsonAnalytic(res);
            } catch (IOException e) {
                e.printStackTrace();
                msg.what = 0;
                msg.obj = e.toString();
                handler.sendMessage(msg);
            }
            handler.sendEmptyMessage(1);
        }
    }

    class BalanceThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(balanceMap, UrlUtil.SUB_DRAW_CASH);
                if (res.length() < 4) {
                    msg.obj = res;
                    msg.what = 0;
                    handler.sendMessage(msg);
                } else {
                    balanceBean = JSONUtil.LoginJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = e.toString();
                msg.what = 0;
                handler.sendMessage(msg);
            }
            handler.sendEmptyMessage(2);
        }
    }
}
