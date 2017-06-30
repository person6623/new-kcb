package com.kcb360.newkcb.seal;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by xinshichao on 2017/5/17.
 * <p>
 * 加载网络图片封装(volley)
 */

public class ImageLoaderSeal {

    // 请求
    private static RequestQueue requestQueue;
    // imageloader
    private static ImageLoader imageLoader;

    public static ImageLoader getNetMipmap(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        if (imageLoader != null) {
            return imageLoader;
        }
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        return imageLoader;
    }
}
