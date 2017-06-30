package com.kcb360.newkcb.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.entity.CouponBean;
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
 * Created by xinshichao on 2017/4/24.
 * <p>
 * 用户钱包
 */

public class UserPurseActivity extends BaseActivity {

    // 布局相关
    // 返回
    private ImageView backIv;
    // 总价(元), 总价(角分), 余额, 余额状态, 押金, 未完单定金, 提成, 积分, 优惠券
    private TextView allYuanTv, allFenTv, balanceTv, balanceStateTv, depositTv, noFinishTv, commissionTv, scoreTv, couponTv;
    // 点击事件布局: 余额, 押金, 未完单定金, 提成, 积分, 优惠券, 银行卡管理, 充值, 交易记录, 提现记录
    private LinearLayout balanceLl, depositLl, noFinishLl, commissionLl, scoreLl, couponLl, bankLl, topUpLl, tradeNotesLl, withDrawCashLl;

    // 数据相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 数据线程
    private UserPurseDataThread userPurseDataThread;
    // 优惠券线程
    private CouponNumThread couponNumThread;
    // 提取提成/押金/定金到余额线程
    private GetDepositNoFinishCommissionThread getDepositNoFinishCommissionThread;
    // 传参map
    private Map<String, String> map;
    // 提取提成/押金/定金到余额map
    private Map<String, String> getMap;
    // 优惠券传参
    private Map<String, String> couponMap;
    // 数据存储
    private UserPurseBean userPurseBean;
    // 优惠券数据暂存
    private CouponBean couponBean;
    // 提取提成/押金/定金到余额数据存储
    private LoginBean getDepositNoFinishCommissionBean;
    // progressdialog
    private Dialog dg;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dg != null && dg.isShowing()) {
                dg.dismiss();
            }
            switch (msg.what) {
                case 0:
                    DiyDialog.loginDialog(UserPurseActivity.this, msg.obj.toString());
                    break;
                case 1:
                    if (userPurseBean == null) {
                        DiyDialog.loginDialog(UserPurseActivity.this, "未能获取数据");
                    } else {
                        if (userPurseBean.getCode() == 1) {
                            reciveData();
                        } else {
                            DiyDialog.loginDialog(UserPurseActivity.this, userPurseBean.getContext());
                        }
                    }

                    break;
                case 2:
                    if (getDepositNoFinishCommissionBean.getCode().equals("1")) {
                        Toast.makeText(UserPurseActivity.this, getDepositNoFinishCommissionBean.getContext(),
                                Toast.LENGTH_SHORT).show();
                        initData();
                    } else {
                        DiyDialog.loginDialog(UserPurseActivity.this, getDepositNoFinishCommissionBean.getContext());
                    }
                    break;
                case 3:
                    if (couponBean != null) {
                        couponTv.setText(couponBean.getTotal() + "");
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public int setLayout() {
        return R.layout.activity_user_purse;
    }

    @Override
    protected void initData() {
        map = new HashMap<>();
        userPurseDataThread = new UserPurseDataThread();
        es.execute(userPurseDataThread);
        // 优惠券数据获取
        initCouponData();
    }

    private void initCouponData() {
//        dg = ProgressDialog.show(UserPurseActivity.this, "", "获取优惠券中...");
//        dg.setCanceledOnTouchOutside(false);
//        dg.show();
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        couponMap = new HashMap<>();
        couponMap.put("page", "1");
        couponMap.put("rows", "0");
        couponMap.put("receiptName", sp.getString("_cName", ""));
        couponMap.put("way", "0");
        couponNumThread = new CouponNumThread();
        es.execute(couponNumThread);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        backIv = findView(R.id.user_purse_back_iv);
        allYuanTv = findView(R.id.user_purse_all_yuan_tv);
        allFenTv = findView(R.id.user_purse_all_fen_tv);
        balanceTv = findView(R.id.user_purse_balance_tv);
        balanceStateTv = findView(R.id.user_purse_balance_state);
        depositTv = findView(R.id.user_purse_deposit_tv);
        noFinishTv = findView(R.id.user_purse_no_finish_tv);
        commissionTv = findView(R.id.user_purse_commission_tv);
        scoreTv = findView(R.id.user_purse_score_tv);
        couponTv = findView(R.id.user_purse_coupon_tv);
        balanceLl = findView(R.id.user_purse_balance_ll);
        depositLl = findView(R.id.user_purse_deposit_ll);
        noFinishLl = findView(R.id.user_purse_no_finish_ll);
        commissionLl = findView(R.id.user_purse_commission_ll);
        scoreLl = findView(R.id.user_purse_score_ll);
        couponLl = findView(R.id.user_purse_coupon_ll);
        bankLl = findView(R.id.user_purse_bank_ll);
        topUpLl = findView(R.id.user_purse_top_up_ll);
        tradeNotesLl = findView(R.id.user_purse_trade_notes_ll);
        withDrawCashLl = findView(R.id.user_purse_withdraw_cash_notes_ll);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        balanceLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userPurseBean == null) {
                    DiyDialog.loginDialog(UserPurseActivity.this, "未能获取钱包数据");
                    return;
                }
                if (userPurseBean.getMemberWallet().getCashBalance() <= 0) {
                    DiyDialog.loginDialog(UserPurseActivity.this, "余额不足");
                    return;
                }
                Intent intent = new Intent(UserPurseActivity.this, BankCardManageActivity.class);
                intent.putExtra("balance", userPurseBean.getMemberWallet().getCashBalance() -
                        userPurseBean.getMemberWallet().getNotFinish());
                startActivity(intent);
            }
        });

        depositLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userPurseBean.getMemberWallet().getCashPledge() == 0) {
                    DiyDialog.loginDialog(UserPurseActivity.this, "无可提取押金");
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(UserPurseActivity.this);
                builder.setTitle("提取押金");
                builder.setMessage("是否提取押金到余额?\n当前押金:" + userPurseBean.getMemberWallet().getCashPledge() + "元");
                builder.setPositiveButton("提取", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getMap == null) {
                            getMap = new HashMap<>();
                        } else {
                            getMap.clear();
                        }
                        dg = ProgressDialog.show(UserPurseActivity.this, "", "领取中...");
                        dg.setCanceledOnTouchOutside(false);
                        dg.show();
                        getMap.put("type", "2");
                        getDepositNoFinishCommissionThread = new GetDepositNoFinishCommissionThread();
                        es.execute(getDepositNoFinishCommissionThread);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        });

        noFinishLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserPurseActivity.this);
                builder.setTitle("提取已完团定金");
                builder.setMessage("是否提取已完团定金到余额?\n(提示:可领取金额为已完团定金,未完团定金将在完团后可领取)");
                builder.setPositiveButton("提取", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getMap == null) {
                            getMap = new HashMap<>();
                        } else {
                            getMap.clear();
                        }
                        dg = ProgressDialog.show(UserPurseActivity.this, "", "领取中...");
                        dg.setCanceledOnTouchOutside(false);
                        dg.show();
                        getMap.put("type", "3");
                        getDepositNoFinishCommissionThread = new GetDepositNoFinishCommissionThread();
                        es.execute(getDepositNoFinishCommissionThread);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        });

        commissionLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserPurseActivity.this);
                builder.setTitle("领取提成");
                builder.setMessage("是否领取提成?\n当前提成金额:" + userPurseBean.getMemberWallet().getRoyalty() + "元");
                builder.setPositiveButton("领取", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getMap == null) {
                            getMap = new HashMap<>();
                        } else {
                            getMap.clear();
                        }
                        dg = ProgressDialog.show(UserPurseActivity.this, "", "领取中...");
                        dg.setCanceledOnTouchOutside(false);
                        dg.show();
                        getMap.put("type", "1");
                        getDepositNoFinishCommissionThread = new GetDepositNoFinishCommissionThread();
                        es.execute(getDepositNoFinishCommissionThread);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
            }
        });

        scoreLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        couponLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPurseActivity.this, CouponActivity.class));
            }
        });

        bankLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPurseActivity.this, BankCardManageActivity.class);
                if (userPurseBean == null) {
                    intent.putExtra("balance", "");
                } else {
                    intent.putExtra("balance", (userPurseBean.getMemberWallet().getCashBalance() -
                            userPurseBean.getMemberWallet().getNotFinish()) + "");
                }
                startActivityForResult(intent, 1);
            }
        });

        tradeNotesLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserPurseActivity.this, TradeNotesAcitivity.class));
            }
        });

        withDrawCashLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UserPurseActivity.this, WithDrawCashActivity.class), 1);
            }
        });
    }

    public void reciveData() {
        if (userPurseBean.getMemberWallet().getCashBalance() % 1 == 0) {
            allYuanTv.setText(String.valueOf((int) userPurseBean.getMemberWallet().getCashBalance()));
        } else {
            String balanceStr = String.valueOf(userPurseBean.getMemberWallet().getCashBalance());
            allYuanTv.setText(balanceStr.substring(0, balanceStr.indexOf(".")));
            allFenTv.setText(balanceStr.substring(balanceStr.indexOf(".") + 1, balanceStr.length()));
        }

        if (userPurseBean.getMemberWallet().getCashBalance() -
                userPurseBean.getMemberWallet().getNotFinish() > 0) {
            balanceStateTv.setVisibility(View.VISIBLE);
        } else {
            balanceStateTv.setVisibility(View.GONE);
        }

        balanceTv.setText(String.valueOf(userPurseBean.getMemberWallet().getCashBalance() -
                userPurseBean.getMemberWallet().getNotFinish()));

        depositTv.setText(String.valueOf(userPurseBean.getMemberWallet().getCashPledge()));

        noFinishTv.setText(String.valueOf(userPurseBean.getMemberWallet().getNotFinish()));

        commissionTv.setText(String.valueOf(userPurseBean.getMemberWallet().getRoyalty()));

        scoreTv.setText(String.valueOf(userPurseBean.getMemberWallet().getPresentBalance()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            initData();
        }
    }

    class UserPurseDataThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(map, UrlUtil.MEMBER_WALLET);
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

    class GetDepositNoFinishCommissionThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(getMap, UrlUtil.TAKE_ROYALTY);
                if (res.length() < 3) {
                    msg.what = 0;
                    msg.obj = res;
                    handler.sendMessage(msg);
                    return;
                }
                getDepositNoFinishCommissionBean = JSONUtil.LoginJsonAnalytic(res);
            } catch (IOException e) {
                e.printStackTrace();
                e.printStackTrace();
                msg.what = 0;
                msg.obj = e.toString();
                handler.sendMessage(msg);
            }
            handler.sendEmptyMessage(2);
        }
    }

    class CouponNumThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(couponMap, UrlUtil.DISCOUNT_LIST);
                if (res.length() < 4) {
                    msg.obj = res;
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    couponBean = JSONUtil.couponJsonAnalytic(res);
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
