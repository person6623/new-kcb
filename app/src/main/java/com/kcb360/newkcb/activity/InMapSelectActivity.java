package com.kcb360.newkcb.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.base.StandardUtil;
import com.kcb360.newkcb.entity.InMapSelectResultBean;
import com.kcb360.newkcb.xlistviewaliter.XListView;

import java.util.ArrayList;

/**
 * Created by xinshichao on 2017/5/19.
 * <p>
 * 地图选点
 * <p>
 * // 使用规则
 * 1.marker的数量 参照StandardUtil(即intent带值传入)
 * 2.resultcode统一返回
 */

public class InMapSelectActivity extends BaseActivity {

    // 布局相关
    // 整体布局(监听用)
    private LinearLayout allLl;
    // 返回
    private LinearLayout backIv;
    // 提交, 删除marker
    private TextView subTv, delFmarkerTv, delSmarkerTv, delTmarkerTv;
    // 地址输入
    private EditText placeNameEt;
    // xlv
    private XListView xlv;
    // adapter
    private InMapSelectAdapter adapter;
    // mapview
    private MapView mv;

    // 获取数据存储
    // poiItem
    private ArrayList<PoiItem> resultPois;

    // 地图相关
    // 实例
    private AMap aMap;
    // 逆地理坐标查询
    private GeocodeSearch geocoderSearch;
    private RegeocodeQuery reGeocodeQuery;
    // 查询poi点
    private PoiSearch.Query poiQury;
    private PoiSearch poiSearch;
    private int page = 0;

    // marker(此处设计3个,是作为途经点使用的)
    private Marker fMarker, sMarker, tMarker;


    // 防止多次请求时间限制1s
    private Thread timeThread;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    page++;
                    adapter.setPoiItems(resultPois);
                    xlv.setAdapter(adapter);
                    break;
            }
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.activity_in_map_select;
    }

    @Override
    protected void initData() {
        // 设置初始缩放地
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.9622250000, 108.4270060000), 5));
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mv = findView(R.id.in_map_select_mv);
        mv.onCreate(savedInstanceState);
        aMap = mv.getMap();

        allLl = findView(R.id.in_map_select_all_ll);
        backIv = findView(R.id.in_map_select_back_ll);
        subTv = findView(R.id.in_map_select_place_sub_tv);
        placeNameEt = findView(R.id.in_map_select_place_name_et);
        delFmarkerTv = findView(R.id.in_map_select_del_fmarer_tv);
        delSmarkerTv = findView(R.id.in_map_select_del_smarer_tv);
        delTmarkerTv = findView(R.id.in_map_select_del_tmarer_tv);
        xlv = findView(R.id.in_map_select_xlv);

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                final Dialog dg = ProgressDialog.show(InMapSelectActivity.this, "", "查找地点中....");
                dg.setCanceledOnTouchOutside(false);
                dg.show();
                latlogSeekPlace(latLng, new OnLatLonSeekPlaceListener() {
                    @Override
                    public void onLatLonSeekPlaceListener(RegeocodeResult regeocodeResult) {
                        if (dg != null && dg.isShowing()) {
                            dg.dismiss();
                        }
                        addMarker(latLng, regeocodeResult.getRegeocodeAddress().getCity() + " " +
                                        regeocodeResult.getRegeocodeAddress().getAois().get(0).getAoiName(),
                                regeocodeResult.getRegeocodeAddress().getFormatAddress());
                    }
                });
            }
        });

        allLl.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != 0 && bottom != 0 &&
                        (oldBottom - bottom > getWindowManager().getDefaultDisplay().getHeight() / 3)) {
                    xlv.setVisibility(View.VISIBLE);
                } else if (oldBottom != 0 && bottom != 0 &&
                        (bottom - oldBottom > getWindowManager().getDefaultDisplay().getHeight() / 3)) {
                    xlv.setVisibility(View.GONE);
                }
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        subTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fMarker == null && sMarker == null && tMarker == null) {
                    Toast.makeText(InMapSelectActivity.this, "请添加地点", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<InMapSelectResultBean> beans = new ArrayList<>();
                if (fMarker != null) {
                    InMapSelectResultBean bean = new InMapSelectResultBean();
                    bean.setLatLng(fMarker.getPosition());
                    bean.setTitle(fMarker.getTitle());
                    bean.setDetails(fMarker.getSnippet());
                    beans.add(bean);
                }
                if (sMarker != null) {
                    InMapSelectResultBean bean = new InMapSelectResultBean();
                    bean.setLatLng(sMarker.getPosition());
                    bean.setTitle(sMarker.getTitle());
                    bean.setDetails(sMarker.getSnippet());
                    beans.add(bean);
                }
                if (tMarker != null) {
                    InMapSelectResultBean bean = new InMapSelectResultBean();
                    bean.setLatLng(tMarker.getPosition());
                    bean.setTitle(tMarker.getTitle());
                    bean.setDetails(tMarker.getSnippet());
                    beans.add(bean);
                }
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("mapSelect", beans);
                setResult(StandardUtil.MAP_SELECT_RESULT, intent);
                finish();
            }
        });

        placeNameEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xlv.setVisibility(View.VISIBLE);
            }
        });
        placeNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 2) {
                    return;
                }
//                if (timeThread != null) {
//                    if (!timeThread.isAlive()) {
//                        timeThread.run();
//                    }
//                }
                poiSeek(s.toString());
            }
        });

        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 0;
                poiSeek(placeNameEt.getText().toString());
            }

            @Override
            public void onLoadMore() {
                poiSeek(placeNameEt.getText().toString());
            }
        });

        delFmarkerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fMarker.remove();
                fMarker = null;
                delFmarkerTv.setVisibility(View.GONE);
            }
        });

        delSmarkerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sMarker.remove();
                sMarker = null;
                delSmarkerTv.setVisibility(View.GONE);
            }
        });

        delTmarkerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tMarker.remove();
                tMarker = null;
                delTmarkerTv.setVisibility(View.GONE);
            }
        });
        adapter = new InMapSelectAdapter();
    }

    class InMapSelectAdapter extends BaseAdapter {

        private ArrayList<PoiItem> poiItems;

        public void setPoiItems(ArrayList<PoiItem> poiItems) {
            this.poiItems = poiItems;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return poiItems != null && poiItems.size() > 0 ? poiItems.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            InMapSelectViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(InMapSelectActivity.this).inflate(R.layout.item_in_map_select, parent, false);
                vh = new InMapSelectViewHolder();
                vh.allLl = (LinearLayout) convertView.findViewById(R.id.item_in_map_select_all_ll);
                vh.titleTv = (TextView) convertView.findViewById(R.id.item_in_map_select_title_tv);
                vh.detailsTv = (TextView) convertView.findViewById(R.id.item_in_map_select_details_tv);
                convertView.setTag(vh);
            } else {
                vh = (InMapSelectViewHolder) convertView.getTag();
            }
            vh.allLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(InMapSelectActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                    addMarker(new LatLng(poiItems.get(position).getLatLonPoint().getLatitude(),
                                    poiItems.get(position).getLatLonPoint().getLongitude()),
                            poiItems.get(position).getCityName() + " " + poiItems.get(position).getTitle(),
                            poiItems.get(position).getSnippet());
                }
            });
            vh.titleTv.setText(poiItems.get(position).getTitle());
            vh.detailsTv.setText(poiItems.get(position).getSnippet());
            return convertView;
        }

        class InMapSelectViewHolder {
            private LinearLayout allLl;
            private TextView titleTv;
            private TextView detailsTv;
        }
    }

    // marker设置
    private void addMarker(LatLng latlng, String title, String details) {
        if (xlv.getVisibility() == View.VISIBLE) {
            xlv.setVisibility(View.GONE);
        }
        MarkerOptions mo;
        BitmapDescriptor bitmapDescriptor;
        switch (getIntent().getIntExtra(StandardUtil.MARKER_TYPE, 0)) {
            case StandardUtil.SINGULAR:
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10));
                mo = new MarkerOptions();
                mo.position(latlng);
                mo.visible(true);
                mo.title(title);
                mo.snippet(details);
                bitmapDescriptor = BitmapDescriptorFactory.
                        fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.in_map_select_fmarker));
                mo.icon(bitmapDescriptor);
                if (fMarker != null) {
                    fMarker.remove();
                }
                fMarker = aMap.addMarker(mo);
                fMarker.showInfoWindow();
                break;
            case StandardUtil.PLURAL:
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10));
                mo = new MarkerOptions();
                mo.position(latlng);
                mo.visible(true);
                mo.title(title);
                mo.snippet(details);
                bitmapDescriptor = BitmapDescriptorFactory.
                        fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.in_map_select_fmarker));
                mo.icon(bitmapDescriptor);
                if (fMarker == null) {
                    bitmapDescriptor = BitmapDescriptorFactory.
                            fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.in_map_select_fmarker));
                    mo.icon(bitmapDescriptor);
                    fMarker = aMap.addMarker(mo);
                    fMarker.showInfoWindow();
                    break;
                }
                if (sMarker == null) {
                    bitmapDescriptor = BitmapDescriptorFactory.
                            fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.in_map_select_smarker));
                    mo.icon(bitmapDescriptor);
                    sMarker = aMap.addMarker(mo);
                    sMarker.showInfoWindow();
                    break;
                }
                if (tMarker == null) {
                    bitmapDescriptor = BitmapDescriptorFactory.
                            fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.in_map_select_tmarker));
                    mo.icon(bitmapDescriptor);
                    tMarker = aMap.addMarker(mo);
                    tMarker.showInfoWindow();
                    break;
                }
                Toast.makeText(InMapSelectActivity.this, "可添加途径地已达到上限,您可以删除地点以重新选择", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        // 删除marker
        if (fMarker != null) {
            delFmarkerTv.setVisibility(View.VISIBLE);
        } else {
            delFmarkerTv.setVisibility(View.GONE);
        }
        if (sMarker != null) {
            delSmarkerTv.setVisibility(View.VISIBLE);
        } else {
            delSmarkerTv.setVisibility(View.GONE);
        }
        if (tMarker != null) {
            delTmarkerTv.setVisibility(View.VISIBLE);
        } else {
            delTmarkerTv.setVisibility(View.GONE);
        }
    }

    // 模糊查询poi点
    private void poiSeek(String s) {
        poiQury = new PoiSearch.Query(s, getResources().getString(R.string.poi_type), "");
        poiQury.setPageSize(12);
        poiQury.setPageNum(page);
        poiSearch = new PoiSearch(InMapSelectActivity.this, poiQury);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                if (i == 1000) {
                    resultPois = poiResult.getPois();
                    handler.sendEmptyMessage(1);
                } else {
                    Toast.makeText(InMapSelectActivity.this, "未能获取信息,请检查网络与GPS", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    // latlog查询地点
    private void latlogSeekPlace(LatLng latLng, final OnLatLonSeekPlaceListener onLatLonSeekPlaceListener) {
        if (geocoderSearch == null) {
            geocoderSearch = new GeocodeSearch(InMapSelectActivity.this);
        }
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                if (i == 1000) {
                    onLatLonSeekPlaceListener.onLatLonSeekPlaceListener(regeocodeResult);
                } else {
                    Toast.makeText(InMapSelectActivity.this, "未能获取地点信息,请检查网络与GPS", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        reGeocodeQuery = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 50, GeocodeSearch.AMAP);

        geocoderSearch.getFromLocationAsyn(reGeocodeQuery);
    }

    /**********接口******
     *
     */
    // 获取latlog地点查询返回
    interface OnLatLonSeekPlaceListener {
        void onLatLonSeekPlaceListener(RegeocodeResult regeocodeResult);
    }


}
