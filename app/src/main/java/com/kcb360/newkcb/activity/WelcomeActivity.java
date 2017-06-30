package com.kcb360.newkcb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;

/**
 * Created by xinshichao on 2017/5/15.
 * <p>
 * 欢迎页
 */

public class WelcomeActivity extends BaseActivity {

    // 布局相关
    // 背景图
    private ImageView bgIv;
    // 倒计时, 跳过
    private TextView timerTv, jumpTv;
    // 倒计时工具
    private CountDownTimer countDownTimer;

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bgIv = findView(R.id.welcome_bg_iv);
        timerTv = findView(R.id.welcome_timer_tv);
        jumpTv = findView(R.id.welcome_jump_tv);

        jumpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
                finish();
            }
        });

        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTv.setText((millisUntilFinished / 1000) + "秒后");
            }

            @Override
            public void onFinish() {
                timerTv.setText("0秒后");
                startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
                finish();
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
