package com.kcb360.newkcb.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by xinshichao on 2017/5/15.
 * <p>
 * 应用周期
 */

public class MyApplication extends Application {

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();

        
    }
}
