package com.kcb360.newkcb.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.maps2d.model.LatLng;

/**
 * Created by xinshichao on 2017/5/19.
 * <p>
 * 地点选择返回
 */

public class InMapSelectResultBean implements Parcelable {

    private String title;
    private String details;
    private LatLng latLng;

    public InMapSelectResultBean() {
    }

    protected InMapSelectResultBean(Parcel in) {
        title = in.readString();
        details = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<InMapSelectResultBean> CREATOR = new Creator<InMapSelectResultBean>() {
        @Override
        public InMapSelectResultBean createFromParcel(Parcel in) {
            return new InMapSelectResultBean(in);
        }

        @Override
        public InMapSelectResultBean[] newArray(int size) {
            return new InMapSelectResultBean[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(details);
        dest.writeParcelable(latLng, flags);
    }
}
