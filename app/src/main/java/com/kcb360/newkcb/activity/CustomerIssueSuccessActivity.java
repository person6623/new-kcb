package com.kcb360.newkcb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;

/**
 * Created by xinshichao on 2017/6/13.
 * <p>
 * 用户发布订单成功
 */

public class CustomerIssueSuccessActivity extends BaseActivity {

    // 布局相关
    // 返回
    private LinearLayout backLl;
    // 我的订单
    private Button myOrderBtn;

    @Override
    protected int setLayout() {
        return R.layout.activity_customer_issue_success;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backLl = findView(R.id.customer_issue_success_back_ll);
        myOrderBtn = findView(R.id.customer_issue_success_my_order_btn);

        backLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerIssueSuccessActivity.this, CustomerOrderListAcvity.class));
                finish();
            }
        });
    }
}
