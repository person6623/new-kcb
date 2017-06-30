package com.kcb360.newkcb.net;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.kcb360.newkcb.activity.LoginActivity;
import com.kcb360.newkcb.entity.VersionBean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.*;
import okhttp3.Request;

/**
 * Created by xinshichao on 2017/6/9.
 * <p>
 * 通用工具
 */

public class NormalUtil {

    // 获取版本信息
    private static PackageInfo pi = null;
    // 版本更新内容
    private static VersionBean versionBean;

    public static String timeToDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(time));
    }

    public static boolean loginTimeRatio(Context context) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        if (sp.getString("login_time", "0").equals("0") || sp.getString("_cName", "").equals("") ||
                (System.currentTimeMillis() - Float.parseFloat(sp.getString("login_time", "0")) >
                        15 * 24 * 60 * 60 * 1000)) {
            Toast.makeText(context, "登录过期或未登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("source", 1);
            context.startActivity(intent);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param （DisplayMetrics类中属性density）
     * @return
     */
    public static int dipToPx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static void versionUpdate(final Context context) {
        final ProgressDialog dg = ProgressDialog.show(context, "", "获取更新信息");
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (dg.isShowing()) {
                    dg.dismiss();
                }
                switch (msg.what) {
                    case 0:
                        Toast.makeText(context, "未能获取更新信息", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        try {
                            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        Log.i("The Version-->", pi.versionCode + "/" + versionBean.getVerCode());
                        if (pi.versionCode >= Integer.valueOf(versionBean.getVerCode())) {
                            break;
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("最新版本:" + versionBean.getVerName());
                            builder.setMessage(versionBean.getVerInfo());
                            builder.setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri content_url = Uri.parse(UrlUtil.VERSION_UPDATE + ".apk");
                                    intent.setData(content_url);
                                    context.startActivity(intent);
                                }
                            }).setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setCancelable(true).create().show();

                        }
                        break;
                    case 2:
                        Toast.makeText(context, "未能获取最新版本信息", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });


        dg.setCanceledOnTouchOutside(false);
        dg.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                okhttp3.Request request = new Request.Builder().url(UrlUtil.VERSION_UPDATE + "kcbjsj_ver.txt").build();
                new OkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handler.sendEmptyMessage(2);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        Log.i("The downloadResponse-->", res);
                        if (res != null) {
                            versionBean = JSONUtil.versionJsonAnalytic(res);
                            handler.sendEmptyMessage(1);
                        } else {
                            handler.sendEmptyMessage(2);
                        }
                    }
                });

            }
        }).start();
    }
}
