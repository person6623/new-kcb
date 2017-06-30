package com.kcb360.newkcb.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.kcb360.newkcb.R;
import com.kcb360.newkcb.fragment.TradeNotesFragment;

import java.util.ArrayList;

/**
 * Created by xinshichao on 2017/5/4.
 * <p>
 * 交易记录(tablayout+viewpager)
 */

public class TradeNotesAcitivity extends FragmentActivity {

    // 布局相关
    // TableLayout
    private TabLayout tl;
    // viewpage
    private ViewPager vp;
    // fragmnetPagerAdapter
    private TradeNotesFragmentPagerAdapter adapter;
    // fragmentList
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_notes);
        initData();
        initView();
    }

    private void initData() {
        fragments = new ArrayList<>();
        TradeNotesFragment allFragment = new TradeNotesFragment();
        allFragment.setNotesType(TradeNotesFragment.ALL_NOTES);
        fragments.add(allFragment);
        TradeNotesFragment takeInFragment = new TradeNotesFragment();
        takeInFragment.setNotesType(TradeNotesFragment.TAKE_IN_NOTES);
        fragments.add(takeInFragment);
        TradeNotesFragment takeOutFragment = new TradeNotesFragment();
        takeOutFragment.setNotesType(TradeNotesFragment.TAKE_OUT_NOTES);
        fragments.add(takeOutFragment);
    }

    private void initView() {
        tl = (TabLayout) findViewById(R.id.trade_notes_tl);
        vp = (ViewPager) findViewById(R.id.trade_notes_vp);

        adapter = new TradeNotesFragmentPagerAdapter(getSupportFragmentManager());
        adapter.setList(fragments);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }

    class TradeNotesFragmentPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> list;
        private String[] title = new String[]{"全部记录", "收入记录", "支出记录"};

        public TradeNotesFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setList(ArrayList<Fragment> list) {
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list != null && list.size() != 0 ? list.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
