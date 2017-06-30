package com.kcb360.newkcb.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.diy.DiyDialog;
import com.kcb360.newkcb.entity.CityAndSiteBean;
import com.kcb360.newkcb.entity.ProvinceBean;
import com.kcb360.newkcb.net.ExceptionUtil;
import com.kcb360.newkcb.net.JSONUtil;
import com.kcb360.newkcb.net.NormalUtil;
import com.kcb360.newkcb.net.Request;
import com.kcb360.newkcb.net.SingletonInstance;
import com.kcb360.newkcb.net.UrlUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by xinshichao on 2017/6/15.
 * <p>
 * 可跑区域
 */

public class PossibleRegionActivity extends BaseActivity {

    // 布局相关
    // 屏幕宽度获取
    private int screenWidth;
    // 返回, 全体布局(范围除外)
    private LinearLayout backLl, noGoAllLl;
    // radioGroup
    private RadioGroup scopeAllRg;
    // radioButton
    private RadioButton countryRb, provinceRb, cityRb;
    // 不去区域, 保存
    private TextView notGoTv, subTv;
    //    // recycleView
//    private RecyclerView noGoRv, noGoSpotRv;
    // gridlayout
    private GridLayout regionGl, spotGl, selectGl;
    // 更多
    private TextView noGoMoreTv, noGoSpotMoreTv;
    // adapter
//    private PossibleRegionAdapter noGoAdapter;
//    private PossibleRegionAdapter noGoSpotAdapter;

    // 高德地图
    // client
    private AMapLocationClient client;
    // option
    private AMapLocationClientOption option;

    // 数据获取
    // 线程池
    private ExecutorService es = SingletonInstance.getInstanceCachePool();
    // 获取城市线程
    private GetCityAndSiteThread getCityAndSiteThread;
    // 获取城市传参
    private Map<String, String> cityMap;
    // 页数控制
    private int page = 1;
    // 获取城市/景点(暂存)
    private CityAndSiteBean cityAndSiteBean;
    // 获取城市/景点(存储)
    private CityAndSiteBean oldCityAndSiteBean;
    // 获取省份线程
    private GetProvinceThread getProvinceThread;
    // 获取省份传参
    private Map<String, String> provinceMap;
    // 获取省份
    private List<ProvinceBean> provinceBeans;

    // 已选择存储(不去 城市&&景点)
    private String selectStr = "";
    // 可跑范围
    private String possibleStr = "1";

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(PossibleRegionActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    break;
                case 2:
                    if (provinceBeans == null || provinceBeans.size() == 0) {
                        Toast.makeText(PossibleRegionActivity.this, "未能获取省份数据, 重新尝试获取", Toast.LENGTH_SHORT).show();
                        getProvince();
                    } else {
                        provinceListShow();
                    }
                    break;
                case 3:
                    if (cityAndSiteBean.getRows().size() < 5) {
                        noGoMoreTv.setVisibility(View.GONE);
                        noGoSpotMoreTv.setVisibility(View.GONE);
                    } else {
                        page++;
                        noGoMoreTv.setVisibility(View.VISIBLE);
                        noGoSpotMoreTv.setVisibility(View.VISIBLE);
                    }
                    udView(oldCityAndSiteBean);
                    break;
                case 4:
                    // 无数据并不做处理
                    Toast.makeText(PossibleRegionActivity.this, "当前地区无更多可用数据", Toast.LENGTH_SHORT).show();
                    udView(oldCityAndSiteBean);
                    break;
            }
            return false;
        }
    });


    @Override
    protected int setLayout() {
        return R.layout.activity_possible_region;
    }

    private void getProvince() {
        if (provinceMap == null) {
            provinceMap = new HashMap<>();
        }
        if (getProvinceThread == null) {
            getProvinceThread = new GetProvinceThread();
        }
        es.execute(getProvinceThread);
    }

    @Override
    protected void initData() {
        cityMap.put("page", page + "");
        cityMap.put("rows", "5");
        getCityAndSite();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        cityMap = new HashMap<>();

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;

        backLl = findView(R.id.possible_region_back_ll);
        noGoAllLl = findView(R.id.possible_region_not_go_all_ll);
        scopeAllRg = findView(R.id.possible_region_scope_all_rg);
//        countryRb = findView(R.id.);
        provinceRb = findView(R.id.possible_region_province_rg);
//        cityRb = findView(R.id.);
//        notGoTv = findView(R.id.possible_region_not_go_tv);
        subTv = findView(R.id.possible_region_sub_tv);

        regionGl = findView(R.id.possible_region_not_go_gl);
        spotGl = findView(R.id.possible_region_not_go_spot_gl);
        selectGl = findView(R.id.possible_region_not_go_and_spot_gl);

        noGoMoreTv = findView(R.id.possible_region_not_go_more_tv);
        noGoSpotMoreTv = findView(R.id.possible_region_not_go_spot_more_tv);

        backLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scopeAllRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.possible_region_country_rg:
                        possibleStr = "全国";
                        provinceRb.setText("全省范围");
                        noGoAllLl.setVisibility(View.VISIBLE);
                        oldCityAndSiteBean = null;
                        page = 1;
                        cityMap.clear();
                        selectStr = "";
                        possibleStr = "1";
                        udSelectView();
                        initData();
                        break;
                    case R.id.possible_region_province_rg:

                        break;
                    case R.id.possible_region_city_rg:
                        possibleStr = "车牌所在地";
                        provinceRb.setText("全省范围");
                        noGoAllLl.setVisibility(View.GONE);
                        selectStr = "";
                        possibleStr = "3";
                        udSelectView();
                        break;
                }
            }
        });

        provinceRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noGoAllLl.setVisibility(View.VISIBLE);
                oldCityAndSiteBean = null;
                page = 1;
                cityMap.clear();
                selectStr = "";
                possibleStr = "2";
                udSelectView();
                getProvince();
            }
        });


        subTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (possibleStr.equals("1") || possibleStr.equals("2")) {
                    String result = "";
                    if (possibleStr.equals("1")) {
                        result = "全国范围";
                    } else if (possibleStr.equals("2")) {
                        result = "全省范围";
                    } else {
                        result = "市内周边";
                    }
                    String city = "";
                    String spot = "";
                    for (int i = 0; i < selectStr.split(",").length; i++) {
                        for (int l = 0; l < cityAndSiteBean.getRows().size(); l++) {
                            if (selectStr.split(",")[i].equals(cityAndSiteBean.getRows().get(l).getAreaName())) {
                                if (cityAndSiteBean.getRows().get(l).getAreaType() == 0) {
                                    if (city.equals("")) {
                                        city = selectStr.split(",")[i];
                                    } else {
                                        city = city + "," + selectStr.split(",")[i];
                                    }
                                } else {
                                    if (spot.equals("")) {
                                        spot = selectStr.split(",")[i];
                                    } else {
                                        spot = spot + "," + selectStr.split(",")[i];
                                    }
                                }
                                break;
                            }
                        }
                    }
                    Toast.makeText(PossibleRegionActivity.this, "已选择:" + selectStr, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("scope", possibleStr);
                    if (city.equals("")) {
                        city = "无";
                    }
                    if (spot.equals("")) {
                        spot = "无";
                    }
                    intent.putExtra("select", result + "=" + city + "=" + spot);
                    setResult(2, intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("scope", possibleStr);
                    intent.putExtra("select", "市内周边=无=无");
                    setResult(2, intent);
                    finish();
                }
            }
        });


    }

    private void udView(final CityAndSiteBean bean) {
        regionGl.removeAllViews();
        spotGl.removeAllViews();
        if (bean == null || bean.getRows() == null) {
            Toast.makeText(PossibleRegionActivity.this, "未能获取数据", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < bean.getRows().size(); i++) {
            final TextView tv = new TextView(PossibleRegionActivity.this);
            if (bean.getRows().get(i).getAreaName().length() > 4) {
                tv.setWidth(screenWidth / 2 - NormalUtil.dipToPx(PossibleRegionActivity.this, 10) - 10);
            } else {
                tv.setWidth(screenWidth / 4 - NormalUtil.dipToPx(PossibleRegionActivity.this, 10));
            }
            tv.setHeight(NormalUtil.dipToPx(PossibleRegionActivity.this, 35));
            if (selectStr.contains(tv.getText().toString())) {
                tv.setBackgroundResource(R.drawable.tv_orange_frame_white_bg);
            } else {
                tv.setBackgroundResource(R.drawable.et_arc_gray_frame_white_bg);
            }
            tv.setText(bean.getRows().get(i).getAreaName());
            tv.setGravity(Gravity.CENTER);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!selectStr.contains(tv.getText().toString())) {
                        tv.setBackgroundResource(R.drawable.tv_orange_frame_white_bg);
                        if (selectStr.equals("")) {
                            selectStr = tv.getText().toString();
                        } else {
                            selectStr = selectStr + "," + tv.getText().toString();
                        }
                        udSelectView();
                    }

                    Log.i("The SelectStr-->", selectStr);
                }
            });
            GridLayout.Spec row = GridLayout.spec(GridLayout.UNDEFINED);
            GridLayout.Spec col = null;
            if (bean.getRows().get(i).getAreaName().length() > 4) {
                col = GridLayout.spec(GridLayout.UNDEFINED, 2);
            } else {
                col = GridLayout.spec(GridLayout.UNDEFINED);
            }
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row, col);
            lp.setMargins(5, 5, 5, 5);
            lp.setGravity(Gravity.LEFT);
            if (bean.getRows().get(i).getAreaType() == 0) {
                regionGl.addView(tv, lp);
            } else {
                spotGl.addView(tv, lp);
            }
        }
        if (regionGl.getChildCount() == 0) {
            TextView nullTv = new TextView(PossibleRegionActivity.this);
            nullTv.setWidth(screenWidth);
            nullTv.setHeight(40);
            nullTv.setText("暂无数据");
            nullTv.setBackgroundResource(R.drawable.sc_black_bg);
            nullTv.setGravity(Gravity.CENTER);
            regionGl.addView(nullTv
                    , new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED)
                            , GridLayout.spec(GridLayout.UNDEFINED, 4)));
        }
        if (spotGl.getChildCount() == 0) {
            TextView nullTv = new TextView(PossibleRegionActivity.this);
            nullTv.setWidth(screenWidth);
            nullTv.setHeight(40);
            nullTv.setText("暂无数据");
            nullTv.setBackgroundResource(R.drawable.sc_black_bg);
            nullTv.setGravity(Gravity.CENTER);
            spotGl.addView(nullTv
                    , new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED)
                            , GridLayout.spec(GridLayout.UNDEFINED, 4)));
        }

    }

    private void udSelectView() {
        selectGl.removeAllViews();
        if (selectStr.equals("")) {
            return;
        }
        String[] selectArr = selectStr.split(",");
        for (int i = 0; i < selectArr.length; i++) {
            final TextView tv = new TextView(PossibleRegionActivity.this);
            if (selectArr[i].length() > 4) {
                tv.setWidth(screenWidth / 2 - NormalUtil.dipToPx(PossibleRegionActivity.this, 10));
            } else {
                tv.setWidth(screenWidth / 4 - NormalUtil.dipToPx(PossibleRegionActivity.this, 10));
            }
            tv.setHeight(NormalUtil.dipToPx(PossibleRegionActivity.this, 35));
            tv.setBackgroundResource(R.drawable.tv_orange_frame_white_bg);
            tv.setText(selectArr[i]);
            tv.setGravity(Gravity.CENTER);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectStr = selectStr.replace(tv.getText(), "");
                    selectStr = selectStr.replace(",,", ",");
                    if (selectStr.indexOf(",") == 0) {
                        selectStr = selectStr.substring(1, selectStr.length());
                    }
                    tv.setVisibility(View.GONE);
                    udView(oldCityAndSiteBean);
                }
            });
            GridLayout.Spec row = GridLayout.spec(GridLayout.UNDEFINED);
            GridLayout.Spec col = null;
            if (selectArr[i].length() > 4) {
                col = GridLayout.spec(GridLayout.UNDEFINED, 2);
            } else {
                col = GridLayout.spec(GridLayout.UNDEFINED);
            }
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row, col);
            lp.setGravity(Gravity.LEFT);
            lp.setMargins(5, 5, 5, 5);
            selectGl.addView(tv, lp);
        }
    }

    private void provinceListShow() {
        String provinceStr = "";
        for (int i = 0; i < provinceBeans.size(); i++) {
            if (i == 0) {
                provinceStr = provinceBeans.get(i).getProvinceName();
            } else {
                provinceStr = provinceStr + "," + provinceBeans.get(i).getProvinceName();
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(PossibleRegionActivity.this);
        final String[] provinceArr = provinceStr.split(",");
        builder.setTitle("请选择省份").setItems(provinceArr
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cityMap == null) {
                            cityMap = new HashMap<>();
                        } else {
                            cityMap.clear();
                        }
                        provinceRb.setText(provinceArr[which]);
                        possibleStr = provinceArr[which];
                        cityMap.put("provinceCode", provinceBeans.get(which).getProvinceCode());
                        initData();
                    }
                }).create().setCanceledOnTouchOutside(false);
        builder.show();
    }


//    private void location() {
//        if (client == null) {
//            client = new AMapLocationClient(PossibleRegionActivity.this);
//        }
//        option = new AMapLocationClientOption();
//        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        option.setOnceLocation(true);
//        client.setLocationOption(option);
//        client.setLocationListener(new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//                if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
//                    int position = 9999999;
//                    for (int i = 0; i < provinceBeans.size(); i++) {
//                        if (provinceBeans.get(i).getProvinceName().contains(aMapLocation.getProvince())) {
//                            position = i;
//                        }
//                    }
//                    if (position == 9999999) {
//                        // 当未能查找到时,默认四川
//                        if (cityMap == null) {
//                            cityMap = new HashMap<>();
//                        } else {
//                            cityMap.clear();
//                        }
//                        String provinceCode = "";
//                        for (int i = 0; i < provinceBeans.size(); i++) {
//                            if (provinceBeans.get(i).getProvinceName().contains("四川")) {
//                                provinceCode = provinceBeans.get(i).getProvinceCode();
//                            }
//                        }
//                        cityMap.put("provinceCode", provinceCode);
//                        getCityAndSite();
//                    } else {
//                        if (cityMap == null) {
//                            cityMap = new HashMap<>();
//                        } else {
//                            cityMap.clear();
//                        }
//                        cityMap.put("provinceCode", provinceBeans.get(position).getProvinceCode());
//                        getCityAndSite();
//                    }
//                } else {
//                    Toast.makeText(PossibleRegionActivity.this, "未能获取省份信息, 默认四川省", Toast.LENGTH_SHORT).show();
//                    if (cityMap == null) {
//                        cityMap = new HashMap<>();
//                    } else {
//                        cityMap.clear();
//                    }
//                    String provinceCode = "";
//                    for (int i = 0; i < provinceBeans.size(); i++) {
//                        if (provinceBeans.get(i).getProvinceName().contains("四川")) {
//                            provinceCode = provinceBeans.get(i).getProvinceCode();
//                        }
//                    }
//                    cityMap.put("provinceCode", provinceCode);
//                    getCityAndSite();
//                }
//            }
//        });
//        client.startLocation();
//    }

    private void getCityAndSite() {
        if (getCityAndSiteThread == null) {
            getCityAndSiteThread = new GetCityAndSiteThread();
        }
        es.execute(getCityAndSiteThread);
    }

//    class PossibleRegionAdapter extends RecyclerView.Adapter<PossibleRegionAdapter.PossibleRegionViewHolder> {
//
//        // 控制刷新视图:0.市级视图1.地区视图
//        private int source;
//
//        private CityAndSiteBean bean;
//
//        public PossibleRegionAdapter(int source) {
//            this.source = source;
//        }
//
//        public void setBean(CityAndSiteBean bean) {
//            this.bean = bean;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public PossibleRegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(PossibleRegionActivity.this).inflate(R.layout.item_possible_region, parent, false);
//            PossibleRegionViewHolder vh = new PossibleRegionViewHolder(view);
//            return vh;
//        }
//
//        @Override
//        public void onBindViewHolder(final PossibleRegionViewHolder holder, final int position) {
//
////            holder.siteTv.getLayoutParams().width = (screenWidth / 4 - NormalUtil.dipToPx(PossibleRegionActivity.this, 10));
//            holder.siteTv.getLayoutParams().height = (NormalUtil.dipToPx(PossibleRegionActivity.this, 35));
//            if (source == 0) {
//                if (bean.getSpeList().get(position).getCityName().length() > 4) {
//                    holder.siteTv.getLayoutParams().width = (screenWidth / 2 - NormalUtil.dipToPx(PossibleRegionActivity.this, 10));
//                } else {
//                    holder.siteTv.getLayoutParams().width = (screenWidth / 4 - NormalUtil.dipToPx(PossibleRegionActivity.this, 10));
//                }
//                holder.siteTv.setText(bean.getSpeList().get(position).getCityName());
//                holder.siteTv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (selectStr.contains(bean.getSpeList().get(position).getCityName())) {
//                            holder.siteTv.setBackgroundResource(R.drawable.possible_region_no_check);
//                            selectStr = selectStr.replace(bean.getSpeList().get(position).getCityName(), "");
//                            selectStr = selectStr.replace(",,", ",");
//                        } else {
//                            holder.siteTv.setBackgroundResource(R.drawable.possible_region_check);
//                            if (selectStr.equals("")) {
//                                selectStr = bean.getSpeList().get(position).getCityName();
//                            } else {
//                                selectStr = selectStr + "," + bean.getSpeList().get(position).getCityName();
//                            }
//                        }
//                        Log.i("The SelectStr-->", selectStr);
//                    }
//                });
//            } else {
//                if (bean.getSpeArea().get(position).getScenicName().length() > 4) {
//                    holder.siteTv.getLayoutParams().width = (screenWidth / 2 - NormalUtil.dipToPx(PossibleRegionActivity.this, 10));
//                } else {
//                    holder.siteTv.getLayoutParams().width = (screenWidth / 4 - NormalUtil.dipToPx(PossibleRegionActivity.this, 10));
//                }
//                holder.siteTv.setText(bean.getSpeArea().get(position).getScenicName());
//                holder.siteTv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (selectStr.contains(bean.getSpeArea().get(position).getScenicName())) {
//                            holder.siteTv.setBackgroundResource(R.drawable.possible_region_no_check);
//                            selectStr = selectStr.replace(bean.getSpeArea().get(position).getScenicName(), ",");
//                            selectStr = selectStr.replace(",,", ",");
//                        } else {
//                            holder.siteTv.setBackgroundResource(R.drawable.possible_region_check);
//                            if (selectStr.equals("")) {
//                                selectStr = bean.getSpeArea().get(position).getScenicName();
//                            } else {
//                                selectStr = selectStr + "," + bean.getSpeArea().get(position).getScenicName();
//                            }
//                        }
//                        Log.i("The SelectStr-->", selectStr);
//                    }
//                });
//            }
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            if (source == 0) {
//                if (bean.getSpeList().get(position).getCityName().length() > 4) {
//                    return 1;
//                } else {
//                    return 0;
//                }
//            } else {
//                if (bean.getSpeArea().get(position).getScenicName().length() > 4) {
//                    return 1;
//                } else {
//                    return 0;
//                }
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            if (source == 0) {
//                return bean.getSpeList() != null && bean.getSpeList().size() != 0 ? bean.getSpeList().size() : 0;
//            } else {
//                return bean.getSpeArea() != null && bean.getSpeArea().size() != 0 ? bean.getSpeArea().size() : 0;
//            }
//        }
//
//        class PossibleRegionViewHolder extends RecyclerView.ViewHolder {
//
//            private TextView siteTv;
//
//            public PossibleRegionViewHolder(View itemView) {
//                super(itemView);
//                siteTv = (TextView) itemView.findViewById(R.id.item_possible_region_site_tv);
//            }
//        }
//    }

    class GetCityAndSiteThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().cookieRequest(cityMap, UrlUtil.NGA_LIST);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    cityAndSiteBean = JSONUtil.cityAndSiteJsonAnalytic(res);
                    if (oldCityAndSiteBean == null) {
                        oldCityAndSiteBean = cityAndSiteBean;
                    } else {
                        oldCityAndSiteBean.getRows().addAll(cityAndSiteBean.getRows());
                        oldCityAndSiteBean.setTotal(cityAndSiteBean.getTotal());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            if (cityAndSiteBean == null || cityAndSiteBean.getRows() == null) {
                handler.sendEmptyMessage(4);
            } else {
                handler.sendEmptyMessage(3);
            }
        }
    }

    class GetProvinceThread implements Runnable {

        @Override
        public void run() {
            Message msg = new Message();
            try {
                String res = new Request().NoCookieRequest(provinceMap, UrlUtil.GET_PROVINCE);
                if (res.length() < 4) {
                    msg.obj = ExceptionUtil.exceptionSwitch(res);
                    msg.what = 0;
                    handler.sendMessage(msg);
                    return;
                } else {
                    provinceBeans = JSONUtil.provincesListJsonAnalytic(res);
                }
            } catch (IOException e) {
                e.printStackTrace();
                msg.obj = ExceptionUtil.exceptionSwitch(e.toString());
                msg.what = 0;
                handler.sendMessage(msg);
            }
            handler.sendEmptyMessage(2);
        }
    }
}
