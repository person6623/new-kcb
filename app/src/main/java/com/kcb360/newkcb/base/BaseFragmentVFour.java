package com.kcb360.newkcb.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xinshichao on 2017/6/21.
 */

public abstract class BaseFragmentVFour extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(setLayout(), container, false);
        intiData();
        intiView(view, savedInstanceState);
        return view;
    }

    protected abstract int setLayout();

    protected abstract void intiData();

    protected abstract void intiView(View view, Bundle savedInstanceState);
}
