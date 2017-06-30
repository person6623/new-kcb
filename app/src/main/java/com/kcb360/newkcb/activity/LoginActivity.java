package com.kcb360.newkcb.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.diy.DiyDialog;
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
 * Created by xinshichao on 2017/5/31.
 * <p>
 * 登录界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    // 注册,忘记密码,车队登录,个人登录
    private TextView registTv, forgetPasswordTv, teamUserTv, personTv;
    // 账号,密码
    private EditText userNameEt, userPasswordEt;
    // 登录
    private Button loginBtn;
    // dialog
    private Dialog dg;
    // Login Bean
    private LoginBean loginBean;
    // 登录
    private final Map loginMessage = new HashMap();

    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 车队用户&&个人用户
    private TeamUserLoginThread teamUserLoginThread;


    private Handler mhandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dg.isShowing()) {
                dg.dismiss();
            }
            switch (msg.what) {

                case 0:
                    DiyDialog.loginDialog(LoginActivity.this, msg.obj.toString());
                    break;
                // 登录信息判断
                case 1:
                    if (loginBean.getCode().equals("1")) {
                        // 登录信息记录
                        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("_cName", loginMessage.get("_cName").toString());
                        editor.putString("_cPwd", loginMessage.get("_cPwd").toString());
                        if (loginBean.getTeamInfo() != null) {
                            editor.putString("teamInfo", loginBean.getTeamInfo().getTeamName());
                        }
                        editor.commit();
                        if (getIntent() != null && getIntent().getIntExtra("source", 0) == 1) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                            finish();
                        }
                    } else {
                        DiyDialog.loginDialog(LoginActivity.this, loginBean.getContext());
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 未过期登录判断
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        if (!sp.getString("_cName", "").toString().equals("")) {
            startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
            finish();
        }
        registTv = findView(R.id.login_regist_tv);
        forgetPasswordTv = findView(R.id.login_forget_regist_tv);
//        teamUserTv = findView(R.id.login_team_user_tv);
//        personTv = findView(R.id.login_person_user_tv);
        userNameEt = findView(R.id.login_user_name_et);
        userPasswordEt = findView(R.id.login_user_password_et);
        loginBtn = findView(R.id.login_login_btn);
        registTv.setOnClickListener(this);
        forgetPasswordTv.setOnClickListener(this);
//        teamUserTv.setOnClickListener(this);
//        personTv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_regist_tv:
                Intent intent = new Intent(this, RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.login_forget_regist_tv:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
//            case R.id.login_team_user_tv:
//                teamUserTv.setBackgroundResource(R.drawable.custom_orange_bg_arc_frame);
//                teamUserTv.setTextColor(0xffFFFFFF);
//                personTv.setBackgroundResource(R.drawable.custom_gray_arc_frame);
////                Log.i("The CurrentColor-->", Integer.toHexString(personTv.getCurrentTextColor()) + "");
//                personTv.setTextColor(0x8a000000);
//                break;
//            case R.id.login_person_user_tv:
//                teamUserTv.setBackgroundResource(R.drawable.custom_gray_arc_frame);
//                teamUserTv.setTextColor(0x8a000000);
//                personTv.setBackgroundResource(R.drawable.custom_orange_bg_arc_frame);
////                Log.i("The CurrentColor-->", Integer.toHexString(personTv.getCurrentTextColor()) + "");
//                personTv.setTextColor(0xffFFFFFF);
//                break;
            case R.id.login_login_btn:
                if (userNameEt.getText().toString().equals("")) {
                    DiyDialog.loginDialog(this, "请输入用户名");
                    return;
                }
                if (userPasswordEt.getText().toString().equals("")) {
                    DiyDialog.loginDialog(this, "请输入密码");
                    return;
                }

                loginMessage.clear();
                loginMessage.put("phoneId", "," + getPhoneId());
                loginMessage.put("_cName", userNameEt.getText().toString());
                loginMessage.put("_cPwd", userPasswordEt.getText().toString());

                dg = ProgressDialog.show(LoginActivity.this, "", "正在登陆,请稍后...");
                dg.setCanceledOnTouchOutside(false);
                dg.show();
                teamUserLoginThread = new TeamUserLoginThread();
                es.execute(teamUserLoginThread);

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String res;
//                        Message msg = new Message();
//                        try {
//                            res = new NetworkRequest().NoCookieRequest(loginMessage, UrlUtil.APP_LOGIN);
//                            if (!res.substring(0, 1).equals("{")) {
//                                msg.what = 0;
//                                msg.obj = res;
//                                mhandler.sendMessage(msg);
//                                return;
//                            } else {
//                                loginBean = JSONUtil.LoginJsonAnalytic(res);
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            msg.what = 0;
//                            msg.obj = e.toString();
//                            mhandler.sendMessage(msg);
//                        }
//                        if (loginBean != null) {
//                            mhandler.sendEmptyMessage(1);
//                        }
//                    }
//                }).start();
                break;
        }
    }

    // 获取手机ID
    private String getPhoneId() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        Log.i("phoneId-->", JPushInterface.getRegistrationID(this));
//        return JPushInterface.getRegistrationID(this);
        return tm.getDeviceId();
    }

    class TeamUserLoginThread implements Runnable {

        @Override
        public void run() {
            String res;
            Message msg = new Message();
            try {
                res = new Request().NoCookieRequest(loginMessage, UrlUtil.APP_LOGIN);
                if (!res.substring(0, 1).equals("{")) {
                    msg.what = 0;
                    msg.obj = res;
                    mhandler.sendMessage(msg);
                    return;
                } else {
                    loginBean = JSONUtil.LoginJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.what = 0;
                msg.obj = e.toString();
                mhandler.sendMessage(msg);
            }
            if (loginBean != null) {
                mhandler.sendEmptyMessage(1);
            }
        }
    }
}
