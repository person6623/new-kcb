package com.kcb360.newkcb.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.StandardUtil;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.entity.NormalBean;
import com.kcb360.newkcb.fragment.HomeAskOrderFragment;
import com.kcb360.newkcb.fragment.HomeNormalOrderFragment;
import com.kcb360.newkcb.fragment.HomeOnWayOrderFragment;
import com.kcb360.newkcb.fragment.HomeTakeAirOrderFragment;
import com.kcb360.newkcb.fragment.ImageTurnFragment;
import com.kcb360.newkcb.net.ExceptionUtil;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.NormalUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/5/15.
 * <p>
 * 主页
 */

public class HomePageActivity extends FragmentActivity {

    // 布局相关
    // 左上抽屉, 接送服务, 顺风中转, 旅游包车, 自家租赁, 注册成为司机, 注册成为乘客, 用户/司机订单列表, 抽屉, 抽屉登录整体布局
    private LinearLayout topDrawetLl, receiveSeeLl, transferLl, charterVehicleLl, driveByMyselfLl,
            registForDriverLl, registForCustomerLl, orderListLl, drawerControlLl, drawerLoginLl;
    // 抽屉左半
    private RelativeLayout drawerControlLeftRl;
    // 抽屉遮罩(点击取消功能)
    private FrameLayout drawerCancelFl;
    // vp(轮播图用)
    private ViewPager vp;
    // 首页订单radiogroup
    private RadioGroup orderRG;
    // 首页顺风车订单界面, 首页接送机订单界面, 首页询价订单界面, 首页成团订单
    private RadioButton onWayRb, meetAirRb, askRb, normalRb;
    // 站位布局
    private FrameLayout fl;
    // fragmentmanager
    private FragmentManager fm;
    // 乘客发布订单, 司机发布空车, 用户/乘客订单列表点击按钮
    private TextView customerIssueTv, driverIssueTv, allOrderTv;
    // 乘客订单列表/司机订单列表
    private TextView cusromerOrderListTv, driverOrderListTv;

    // 顶部抽屉
    // 登录按钮, 包车设置, 包车价格设置列表 钱包, 退出
    private TextView loginTv, charterSettingTv, charterSettingListTv, purseTv, logOutTv;

    // 线程相关
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 退出线程
    private LogoutThread logoutThread;
    // 传参
    private Map<String, String> logoutMap;
    // 返回
    private NormalBean logoutBean;

    // 首页获取订单相关数据 state : 0.顺风车 1.接送机 2.旅游包车
    private int orderListState = 0;

    // 屏幕宽度
    private int screenWidth;

    // 轮播图
    // 轮播fragmentadapter
    private TurnImageFragmentAdapter turnImageFragmentAdapter;
    // 轮播内容
    private List<String> imageUrl;
    // 轮播图刷新值(默认刷新)
    private boolean isTurn = true;
    // 轮播前页记录
    private int beforePage;
    // 轮播循环
    private Handler turnImageHnadler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    beforePage++;
                    Log.i("The handler_page-->", beforePage + "");
//                    if (beforePage > 2) {
                    vp.setCurrentItem(beforePage);
//                        beforePage = 0;
//                    } else {
//                        vp.setCurrentItem(beforePage);
//                    }
                    turnimage();
                    break;
            }
            return false;
        }
    });

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(HomePageActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(HomePageActivity.this, logoutBean.getContext(), Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                    upLogState();
                    sp.edit().clear().commit();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView(savedInstanceState);
        initData();
    }

    protected void initData() {
        NormalUtil.versionUpdate(HomePageActivity.this);
    }

    private void initImageData() {
        imageUrl = new ArrayList<>();
        imageUrl.add(UrlUtil.HOME_PAGE_IMAGE_ONE);
        imageUrl.add(UrlUtil.HOME_PAGE_IMAGE_TWO);
        imageUrl.add(UrlUtil.HOME_PAGE_IMAGE_THREE);
        turnImageFragmentAdapter = new TurnImageFragmentAdapter(getSupportFragmentManager());
        turnImageFragmentAdapter.setList(imageUrl);
        vp.setAdapter(turnImageFragmentAdapter);
        vp.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE % imageUrl.size());
        beforePage = vp.getCurrentItem();
        Log.i("The page-->", beforePage + "");
        turnimage();
    }

    private void turnimage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    turnImageHnadler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    protected void initView(Bundle savedInstanceState) {

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;

        topDrawetLl = (LinearLayout) findViewById(R.id.home_page_top_drawet_ll);
        receiveSeeLl = (LinearLayout) findViewById(R.id.home_page_receive_see_ll);
        transferLl = (LinearLayout) findViewById(R.id.home_page_transfer_ll);
        charterVehicleLl = (LinearLayout) findViewById(R.id.home_page_charter_vehicle_ll);
        driveByMyselfLl = (LinearLayout) findViewById(R.id.home_page_drive_by_myself_ll);
        registForDriverLl = (LinearLayout) findViewById(R.id.home_page_regist_for_driver_ll);
        registForCustomerLl = (LinearLayout) findViewById(R.id.home_page_regist_for_customer_ll);
        drawerControlLl = (LinearLayout) findViewById(R.id.home_page_drawer_control_ll);
        drawerControlLeftRl = (RelativeLayout) findViewById(R.id.home_page_drawer_control_left_rl);
        drawerLoginLl = (LinearLayout) findViewById(R.id.home_page_drawer_login_ll);
        drawerCancelFl = (FrameLayout) findViewById(R.id.home_page_drawer_cancel_fl);
        vp = (ViewPager) findViewById(R.id.home_page_vp);
        orderRG = (RadioGroup) findViewById(R.id.home_page_oder_list_rg);
        onWayRb = (RadioButton) findViewById(R.id.home_page_on_way_rb);
        meetAirRb = (RadioButton) findViewById(R.id.home_page_meet_air_rb);
        askRb = (RadioButton) findViewById(R.id.home_page_ask_rb);
        normalRb = (RadioButton) findViewById(R.id.home_page_normal_rb);
        fl = (FrameLayout) findViewById(R.id.home_page_fl);
        customerIssueTv = (TextView) findViewById(R.id.home_page_customer_issue_tv);
        driverIssueTv = (TextView) findViewById(R.id.home_page_driver_issue_tv);
        allOrderTv = (TextView) findViewById(R.id.home_page_order_tv);
        orderListLl = (LinearLayout) findViewById(R.id.home_page_order_list_ll);
        cusromerOrderListTv = (TextView) findViewById(R.id.home_page_customer_order_list_tv);
        driverOrderListTv = (TextView) findViewById(R.id.home_page_driver_order_list_tv);

        loginTv = (TextView) findViewById(R.id.home_page_drawer_login_tv);
        charterSettingTv = (TextView) findViewById(R.id.home_page_drawer_drive_charter_setting_tv);
        charterSettingListTv = (TextView) findViewById(R.id.home_page_drawer_drive_charter_setting_list_tv);
        purseTv = (TextView) findViewById(R.id.home_page_drawer_purse_tv);
        logOutTv = (TextView) findViewById(R.id.home_page_drawer_log_out_tv);

        topDrawetLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                drawerControlLl.setVisibility(View.VISIBLE);
                TranslateAnimation showAnimation = new TranslateAnimation(-screenWidth * 0.625f, 0, 0, 0);
                showAnimation.setDuration(500);
                showAnimation.setFillAfter(true);
                showAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        drawerCancelFl.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                drawerControlLeftRl.startAnimation(showAnimation);
            }
        });

        drawerCancelFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation showAnimation = new TranslateAnimation(0, -screenWidth * 0.625f, 0, 0);
                showAnimation.setDuration(500);
                showAnimation.setFillAfter(true);
                showAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        drawerCancelFl.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        drawerControlLl.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                drawerControlLeftRl.startAnimation(showAnimation);
            }
        });

//        drawerControlLl.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                float downX = 0;
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    downX = event.getX();
//                    Log.i("The start-->", downX + "");
//                }
//                float upX = 0;
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    upX = event.getX();
//                    Log.i("The end-->", upX + "");
//                }
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    if (upX - downX > 10) {
//                        drawerControlLl.setVisibility(View.GONE);
//                    }
//                }
//                return false;
//            }
//        });

        receiveSeeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        transferLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        charterVehicleLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        driveByMyselfLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        registForDriverLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, RegistActivity.class));
            }
        });

        registForCustomerLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, RegistActivity.class));
            }
        });

        // 默认顺风车显示
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.home_page_fl, new HomeOnWayOrderFragment()).commit();
        orderRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.home_page_on_way_rb:
                        onWayRb.setBackgroundColor(0xFFD2D2D2);
                        meetAirRb.setBackgroundColor(0xFFFFFFFF);
                        askRb.setBackgroundColor(0xFFFFFFFF);
                        normalRb.setBackgroundColor(0xFFFFFFFF);
                        orderListState = 0;
                        fm.beginTransaction().replace(R.id.home_page_fl, new HomeOnWayOrderFragment()).commit();
                        break;
                    case R.id.home_page_meet_air_rb:
                        onWayRb.setBackgroundColor(0xFFFFFFFF);
                        meetAirRb.setBackgroundColor(0xFFD2D2D2);
                        askRb.setBackgroundColor(0xFFFFFFFF);
                        normalRb.setBackgroundColor(0xFFFFFFFF);
                        orderListState = 1;
                        fm.beginTransaction().replace(R.id.home_page_fl, new HomeTakeAirOrderFragment()).commit();
                        break;
                    case R.id.home_page_ask_rb:
                        onWayRb.setBackgroundColor(0xFFFFFFFF);
                        meetAirRb.setBackgroundColor(0xFFFFFFFF);
                        askRb.setBackgroundColor(0xFFD2D2D2);
                        normalRb.setBackgroundColor(0xFFFFFFFF);
                        orderListState = 2;
                        fm.beginTransaction().replace(R.id.home_page_fl, new HomeAskOrderFragment()).commit();
                        break;
                    case R.id.home_page_normal_rb:
                        onWayRb.setBackgroundColor(0xFFFFFFFF);
                        meetAirRb.setBackgroundColor(0xFFFFFFFF);
                        askRb.setBackgroundColor(0xFFFFFFFF);
                        normalRb.setBackgroundColor(0xFFD2D2D2);
                        orderListState = 2;
                        fm.beginTransaction().replace(R.id.home_page_fl, new HomeNormalOrderFragment()).commit();
                        break;
                }
            }
        });


        customerIssueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, CustomerIssueOrderActivity.class));
            }
        });

        driverIssueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePageActivity.this, DriverIssueEmptyActivity.class));
            }
        });

        allOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderListLl.getVisibility() == View.VISIBLE) {
                    orderListLl.setVisibility(View.GONE);
                    allOrderTv.setBackgroundResource(R.drawable.home_page_order_click_before);
                } else {
                    orderListLl.setVisibility(View.VISIBLE);
                    allOrderTv.setBackgroundResource(R.drawable.home_page_order_click_after);
                }
            }
        });
        orderListLl.setVisibility(View.GONE);

        cusromerOrderListTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NormalUtil.loginTimeRatio(HomePageActivity.this)) {
                    return;
                }
                startActivity(new Intent(HomePageActivity.this, CustomerOrderListAcvity.class));
            }
        });

        driverOrderListTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NormalUtil.loginTimeRatio(HomePageActivity.this)) {
                    return;
                }
                SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                Intent intent = new Intent(HomePageActivity.this, DriverEmptyCarListActivity.class);
                intent.putExtra("role", StandardUtil.ROLE_DRIVER);
                intent.putExtra("driverPhone", sp.getString("_cName", ""));
                startActivity(intent);
            }
        });

        final SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        if (!sp.getString("_cName", "").equals("")) {
            loginTv.setText("当前登录:\n" + sp.getString("_cName", ""));
        }
        drawerLoginLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sp.getString("_cName", "").equals("")) {
                    DiyDialog.fromToDialog(HomePageActivity.this, "当前已登录,是否更换账号?", new DiyDialog.OnFromToListener() {
                        @Override
                        public void onFromToListener() {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear();
                            editor.commit();
                            startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                        }
                    });
                } else {
                    startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                }
            }
        });

        charterSettingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NormalUtil.loginTimeRatio(HomePageActivity.this)) {
                    return;
                }
                startActivity(new Intent(HomePageActivity.this, DriverCharterSettingActivity.class));
            }
        });

        charterSettingListTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NormalUtil.loginTimeRatio(HomePageActivity.this)) {
                    return;
                }
                startActivity(new Intent(HomePageActivity.this, DriverCharterSettingListActivity.class));
            }
        });

        purseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 功能待完善
                if (!NormalUtil.loginTimeRatio(HomePageActivity.this)) {
                    return;
                }
                startActivity(new Intent(HomePageActivity.this, UserPurseActivity.class));
            }
        });

        logOutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiyDialog.fromToDialog(HomePageActivity.this, "是否退出当前账号?", new DiyDialog.OnFromToListener() {
                    @Override
                    public void onFromToListener() {
                        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                        if (sp.getString("_cName", "").equals("")) {
                            Toast.makeText(HomePageActivity.this, "当前尚未登录", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        logoutThread = new LogoutThread();
                        es.execute(logoutThread);
//                        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.clear();
//                        editor.commit();
//                        startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
//                        finish();
                    }
                });
            }
        });

        initImageData();
    }

    private void upLogState() {
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        if (sp.getString("-cName", "").equals("")) {
            loginTv.setText("未登录");
        } else {
            loginTv.setText("当前登录:\n" + sp.getString("-cName", ""));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (drawerControlLl.getVisibility() == View.VISIBLE) {
            float downX = 0;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                downX = event.getX();
                Log.i("The start-->", downX + "");
            }
            float upX = 0;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                upX = event.getX();
                Log.i("The end-->", upX + "");
            }
            if (downX - upX > 150) {
                TranslateAnimation showAnimation = new TranslateAnimation(0, -screenWidth * 0.625f, 0, 0);
                showAnimation.setDuration(500);
                showAnimation.setFillAfter(true);
                showAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        drawerCancelFl.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        drawerControlLl.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                drawerControlLeftRl.startAnimation(showAnimation);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        upLogState();
        if (drawerControlLl.getVisibility() == View.VISIBLE) {
            drawerControlLl.setVisibility(View.GONE);
        }
    }

    class TurnImageFragmentAdapter extends FragmentPagerAdapter {

        private List<String> list;

        public TurnImageFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            ImageTurnFragment fragment = new ImageTurnFragment();
            fragment.setIvUrl(list.get(position % list.size()));
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
    }

    class LogoutThread implements Runnable {

        @Override
        public void run() {
            logoutMap = new HashMap<>();
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(logoutMap, UrlUtil.APP_QUIT);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.obj = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    logoutBean = JSONUtil.normalJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.obj = 0;
                handler.sendMessage(msg);
            }
            handler.sendEmptyMessage(1);
        }
    }
}
