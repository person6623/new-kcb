package com.kcb360.newkcb.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;

/**
 * Created by xinshichao on 2017/5/15.
 * <p>
 * activity基类
 */

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(setLayout());

        initView(savedInstanceState);

        initData();
    }

    protected abstract int setLayout();

    protected abstract void initData();

    protected abstract void initView(Bundle savedInstanceState);

    protected <T extends View> T findView(int view) {
        return (T) findViewById(view);
    }
}
