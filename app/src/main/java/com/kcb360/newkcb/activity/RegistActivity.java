package com.kcb360.newkcb.activity;

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
 * 注册
 */

public class RegistActivity extends BaseActivity implements View.OnClickListener {

    // 手机,密码,推荐人,验证码
    private EditText phoneEt, passwordEt, recommendEt, verifyEt;
    // 注册
    private Button registBtn;
    // 获取验证码
    private TextView getVerifyTv;
    // 返回
    private ImageView backIv;
    // 验证码实体类
    private VerifyBean verifyBean;
    // 注册实体类
    private RegistBean registBean;
    // 计时器
    private CountDownTimer timer = null;
    // 数据存储(注册,验证码)
    private final Map<String, String> registMap = new HashMap<>();
    private final Map<String, String> verifyMap = new HashMap<>();

    private Handler mhandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    DiyDialog.loginDialog(RegistActivity.this, msg.obj.toString());
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
                        DiyDialog.loginDialog(RegistActivity.this, verifyBean.getContext());
                    } else {
                        DiyDialog.loginDialog(RegistActivity.this, verifyBean.getContext());
                    }
                    break;
                case 2:
                    if (registBean.getCode().equals("1")) {
                        DiyDialog.loginDialog(RegistActivity.this, registBean.getContext());
//                        new DBTools(RegistActivity.this).addData(SQLiteHelper.USER_TAB, registMap);
                        // 此处缺少数据库插入
                    } else {
                        DiyDialog.loginDialog(RegistActivity.this, registBean.getContext());
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public int setLayout() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        phoneEt = findView(R.id.regist_phone_et);
        passwordEt = findView(R.id.regist_passwrod_et);
        recommendEt = findView(R.id.regist_recommend_et);
        verifyEt = findView(R.id.regist_verify_et);
        getVerifyTv = findView(R.id.regist_get_verify_tv);
        registBtn = findView(R.id.regist_regist_btn);
        backIv = findView(R.id.regist_back_iv);

        getVerifyTv.setOnClickListener(this);
        registBtn.setOnClickListener(this);
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
            case R.id.regist_get_verify_tv:
                if (phoneEt.getText().toString().equals("") ||
                        phoneEt.getText().toString().length() < 11 ||
                        !phoneEt.getText().toString().substring(0, 1).equals("1")) {
                    DiyDialog.loginDialog(RegistActivity.this, "请填写正确的手机号码");
                    return;
                }
                verifyMap.clear();
                verifyMap.put("phone", phoneEt.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            String res = new Request().NoCookieRequest(verifyMap, UrlUtil.GETVAL_CODE);
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
            case R.id.regist_regist_btn:
                if (!checkMessage()) {
                    return;
                }
                registMap.clear();
                registMap.put("actRole", "2");
                registMap.put("regWay", "app");
                registMap.put("phone", phoneEt.getText().toString());
                registMap.put("psw", passwordEt.getText().toString());
                registMap.put("recomName", recommendEt.getText().toString());
                registMap.put("validateCode", verifyEt.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        try {
                            String res = new Request().NoCookieRequest(registMap, UrlUtil.REG_CUSTOMER);
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
            case R.id.regist_back_iv:
                finish();
                break;
        }
    }

    // 必须参数确认
    private boolean checkMessage() {
        if (phoneEt.getText().toString().equals("") ||
                phoneEt.getText().toString().length() < 11 ||
                !phoneEt.getText().toString().substring(0, 1).equals("1")) {
            DiyDialog.loginDialog(RegistActivity.this, "请填写正确的手机号码");
            return false;
        }
        if (passwordEt.getText().toString().equals("") ||
                passwordEt.getText().toString().length() < 6) {
            DiyDialog.loginDialog(RegistActivity.this, "密码有误,请重新输入");
            return false;
        }
        if (recommendEt.getText().toString().equals("")) {
            DiyDialog.loginDialog(RegistActivity.this, "请输入推荐人");
            return false;
        }
        if (verifyEt.getText().toString().equals("")) {
            DiyDialog.loginDialog(RegistActivity.this, "请输入验证码");
            return false;
        }
        return true;
    }
}
