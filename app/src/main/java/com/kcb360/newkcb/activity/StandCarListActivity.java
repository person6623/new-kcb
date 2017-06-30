package com.kcb360.newkcb.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.BaseActivity;
import com.kcb360.newkcb.xlistviewaliter.XListView;

/**
 * Created by xinshichao on 2017/5/25.
 * <p>
 * 空车列表
 */

public class StandCarListActivity extends BaseActivity {

    // 布局相关
    // 返回
    private LinearLayout backLl;
    // RadioGroup
    private RadioGroup standCarRg;
    // 空车车队, 空车司机
    private RadioButton teamRb, driverRb;
    // xlistview
    private XListView xlv;
    // 当前页所用数据控制
    private int isDataState = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_stand_car_list;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        backLl = findView(R.id.stand_stand_car_back_ll);
        standCarRg = findView(R.id.stand_stand_car_rg);
        teamRb = findView(R.id.stand_stand_car_team_rb);
        driverRb = findView(R.id.stand_stand_car_driver_rb);
        xlv = findView(R.id.stand_stand_car_xl);

        backLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        standCarRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.stand_stand_car_team_rb:
                        teamRb.setBackgroundResource(R.drawable.sc_black_bg);
                        driverRb.setBackgroundResource(R.drawable.white_bg);
                        isDataState = 0;
                        break;
                    case R.id.stand_stand_car_driver_rb:
                        teamRb.setBackgroundResource(R.drawable.white_bg);
                        driverRb.setBackgroundResource(R.drawable.sc_black_bg);
                        isDataState = 1;
                        break;
                }
            }
        });

        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    class StandCarListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            StandCarListViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(StandCarListActivity.this).inflate(R.layout.item_stand_car_list,
                        parent, false);
                vh = new StandCarListViewHolder();
                convertView.setTag(vh);
            } else {
                vh = (StandCarListViewHolder) convertView.getTag();
            }

            return convertView;
        }

        class StandCarListViewHolder {
            private TextView plateTv;
            private TextView scopeTv;
            private TextView seatsTv;
            private RatingBar markRtb;
            private TextView markTv;
            private TextView callDriverTv;
            private TextView selectCarTv;
        }
    }
}
