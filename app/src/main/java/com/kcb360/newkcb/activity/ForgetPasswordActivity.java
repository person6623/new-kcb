package com.kcb360.newkcb.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.entity.RegistBean;
import com.kcb360.newkcb.entity.VerifyBean;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.UrlUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinshichao on 2017/5/31.
 * <p>
 * 忘记密码
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    // 手机,密码,确认密码,验证码
    private EditText phoneEt, passwordEt, passwordSecondEt, verifyEt;
    // 修改密码
    private Button alterPasswordBtn;
    // 获取验证码
    private TextView getVerifyTv;
    // 返回
    private ImageView backIv;
    // 数据记录map(验证码,修改密码)
    private Map<String, String> verifyMap = new HashMap<>();
    private Map<String, String> forgetPasswordMap = new HashMap<>();
    // 实体类修改密码用
    private RegistBean registBean;
    // 实体类获取验证码用
    private VerifyBean verifyBean;
    // dialog
    private Dialog dg;
    // 计时器
    private CountDownTimer timer = null;

    private Handler mhandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (dg.isShowing()) {
                dg.dismiss();
            }
            switch (msg.what) {
                case 0:
                    DiyDialog.loginDialog(ForgetPasswordActivity.this, msg.obj.toString());
                    break;
                case 1:
                    if (verifyBean.getCode().equals("1")) {
                        timer = new CountDownTimer(1000, 60000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                getVerifyTv.setClickable(false);
                                getVerifyTv.setBackgroundColor(0xFFD2D2D2);
                                getVerifyTv.setText((millisUntilFinished / 1000) + "秒后可再次发送");
                            }

                            @Override
                            public void onFinish() {
                                getVerifyTv.setClickable(true);
                                getVerifyTv.setBackgroundColor(0xFFFF961B);
                            }
                        }.start();
                        DiyDialog.loginDialog(ForgetPasswordActivity.this, verifyBean.getContext());
                    } else {
                        DiyDialog.loginDialog(ForgetPasswordActivity.this, verifyBean.getContext());
                    }
                    break;
                case 2:
                    if (registBean.getCode().equals("1")) {
                        DiyDialog.loginDialog(ForgetPasswordActivity.this, registBean.getContext());
                        Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        DiyDialog.loginDialog(ForgetPasswordActivity.this, registBean.getContext());
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public int setLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        phoneEt = findView(R.id.forget_password_phone_et);
        passwordEt = findView(R.id.forget_password_passwrod_et);
        passwordSecondEt = findView(R.id.forget_password_passwrod_second_et);
        verifyEt = findView(R.id.forget_password_verify_et);
        getVerifyTv = findView(R.id.forget_password_get_verify_tv);
        alterPasswordBtn = findView(R.id.forget_password_alter_btn);
        backIv = findView(R.id.forget_password_back_iv);

        getVerifyTv.setOnClickListener(this);
        alterPasswordBtn.setOnClickListener(this);
        backIv.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_password_get_verify_tv:
                if (phoneEt.getText().toString().equals("") ||
                        phoneEt.getText().toString().length() < 11 ||
                        !phoneEt.getText().toString().substring(0, 1).equals("1")) {
                    DiyDialog.loginDialog(ForgetPasswordActivity.this, "请填写正确的手机号码");
                    return;
                }
                verifyMap.clear();
                verifyMap.put("phone", phoneEt.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            String res = new Request().NoCookieRequest(verifyMap, UrlUtil.GET_PHONE_CODE);
                            if (!res.substring(0, 1).equals("{")) {
                                msg.obj = res;
                                msg.what = 0;
                                mhandler.sendMessage(msg);
                                return;
                            } else {
                                verifyBean = JSONUtil.VerifyJsonAnalytic(res);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            msg.obj = e.toString();
                            msg.what = 0;
                            mhandler.sendMessage(msg);
                        }
                        if (verifyBean != null) {
                            mhandler.sendEmptyMessage(1);
                        }
                    }
                }).start();
                break;
            case R.id.forget_password_alter_btn:
                if (!checkMessage()) {
                    return;
                }
                forgetPasswordMap.clear();
                forgetPasswordMap.put("_phone", phoneEt.getText().toString());
                forgetPasswordMap.put("_newPwd", passwordEt.getText().toString());
                forgetPasswordMap.put("backPwdCode", verifyEt.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            String res = new Request().NoCookieRequest(forgetPasswordMap, UrlUtil.BACK_PWD);
                            if (!res.substring(0, 1).equals("{")) {
                                msg.obj = res;
                                msg.what = 0;
                                mhandler.sendMessage(msg);
                            } else {
                                registBean = JSONUtil.RegistJsonAnalytic(res);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            msg.obj = e.toString();
                            msg.what = 0;
                            mhandler.sendMessage(msg);
                        }
                        if (registBean != null) {
                            mhandler.sendEmptyMessage(2);
                        }
                    }
                }).start();
                break;
            case R.id.forget_password_back_iv:
                finish();
                break;
        }
    }

    private boolean checkMessage() {
        if (phoneEt.getText().toString().equals("") ||
                phoneEt.getText().toString().length() < 11 ||
                !phoneEt.getText().toString().substring(0, 1).equals("1")) {
            DiyDialog.loginDialog(ForgetPasswordActivity.this, "请填写正确的手机号码");
            return false;
        }
        if (passwordEt.getText().toString().equals("") ||
                passwordEt.getText().toString().length() < 6) {
            DiyDialog.loginDialog(ForgetPasswordActivity.this, "密码有误,请重新输入");
            return false;
        }
        if (!passwordEt.getText().toString().equals(passwordSecondEt.getText().toString())) {
            DiyDialog.loginDialog(ForgetPasswordActivity.this, "两次输入的密码不同,请重新输入");
            return false;
        }
        if (verifyEt.getText().toString().equals("")) {
            DiyDialog.loginDialog(ForgetPasswordActivity.this, "请输入验证码");
            return false;
        }
        return true;
    }
}
