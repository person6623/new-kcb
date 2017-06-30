package com.kcb360.newkcb.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.fragment.AskOrderFrontFragment;
import com.kcb360.newkcb.fragment.FormalOrderFrontFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinshichao on 2017/5/18.
 * <p>
 * 用户发布订单(framelayout)
 */

public class CustomerIssueOrderActivity extends BaseActivity {

    // 布局相关
    // 返回
    private LinearLayout backLl;
    // 大标题, 分享
    private TextView allTitleTv, shareTv;
    // 小标题(radiogroup)
    private RadioGroup subheadingRg;
    // 小标题内容(询价订单, 发布订单)
    private RadioButton askRb, formalRb;

    // fragment存储
    public static Map<String, Fragment> fragments;

    @Override
    protected int setLayout() {
        return R.layout.activity_customer_issue_order;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backLl = findView(R.id.customer_issue_order_back_ll);
        allTitleTv = findView(R.id.customer_issue_order_all_title_tv);
        shareTv = findView(R.id.customer_issue_order_share_tv);
        askRb = findView(R.id.customer_issue_order_ask_rb);
        formalRb = findView(R.id.customer_issue_order_formal_rb);
        subheadingRg = findView(R.id.customer_issue_order_subheading_rg);

        backLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragments.clear();
                finish();
            }
        });

        fragments = new HashMap<>();
        subheadingRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Log.i("The CheckId-->", checkedId + "");
                FragmentManager fm = getFragmentManager();
                switch (checkedId) {
                    case R.id.customer_issue_order_ask_rb:
                        allTitleTv.setText("询价订单");
                        shareTv.setVisibility(View.VISIBLE);
                        askRb.setBackgroundColor(0xffD2D2D2);
                        formalRb.setBackgroundColor(0xffFFFFFF);
                        fragments.put("askFront", new AskOrderFrontFragment());
                        fm.beginTransaction().replace(R.id.customer_issue_order_content_fl,
                                fragments.get("askFront")).commit();
                        break;
                    case R.id.customer_issue_order_formal_rb:
                        allTitleTv.setText("发布订单");
                        shareTv.setVisibility(View.GONE);
                        formalRb.setBackgroundColor(0xffD2D2D2);
                        askRb.setBackgroundColor(0xffFFFFFF);
                        fragments.put("formalFront", new FormalOrderFrontFragment());
                        fm.beginTransaction().replace(R.id.customer_issue_order_content_fl,
                                fragments.get("formalFront")).commit();
                        break;
                }
            }
        });
        //默认触发
        subheadingRg.check(R.id.customer_issue_order_ask_rb);
    }
}
