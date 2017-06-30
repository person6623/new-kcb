package com.kcb360.newkcb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.kcb360.newkcb.R;
import com.kcb360.newkcb.base.MyApplication;
import com.kcb360.newkcb.seal.ImageLoaderSeal;

/**
 * Created by xinshichao on 2017/5/17.
 * <p>
 * 轮播fragment
 */

public class ImageTurnFragment extends Fragment {

    // 图
    private ImageView turnIv;
    // 图片url
    private String ivUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(MyApplication.context).inflate(R.layout.fragment_image_turn, container, false);
        turnIv = (ImageView) view.findViewById(R.id.image_turn_iv);
        ImageLoaderSeal.getNetMipmap(MyApplication.context).get(ivUrl,
                ImageLoader.getImageListener(turnIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        return view;
    }

    public void setIvUrl(String ivUrl) {
        this.ivUrl = ivUrl;
    }
}
