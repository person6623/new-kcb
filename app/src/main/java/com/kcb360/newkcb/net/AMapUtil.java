package com.kcb360.newkcb.net;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinshichao on 2017/6/12.
 * <p>
 * 高德地图功能
 */

public class AMapUtil {

    // 路线规划相关
    private RouteSearch routeSearch;
    // 司机路线查找
    private RouteSearch.DriveRouteQuery driveRouteQuery;
    // fromAndTo
    private RouteSearch.FromAndTo fromAndTo;
    // 起点坐标, 途径坐标, 终点坐标
    private LatLonPoint stLlp, edLlp;
    // 途径点
    private List<LatLonPoint> wayPoint;

    /**
     * 使用此方法时points务必按照 起点--终点--途径点传入
     */
    public void routeSearchMethod(Context context, List<String> points, final RSMethodListener rsMethodListener) {
        routeSearch = new RouteSearch(context);
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                rsMethodListener.rsMethod(i, driveRouteResult);
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
        Log.i("The Points-->", points.toString());
        wayPoint = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            if (i == 0) {
                stLlp = new LatLonPoint(Double.parseDouble(points.get(0).split("\\|")[1]),
                        Double.parseDouble(points.get(0).split("\\|")[0]));
            } else if (i == 1) {
                edLlp = new LatLonPoint(Double.parseDouble(points.get(1).split("\\|")[1]),
                        Double.parseDouble(points.get(1).split("\\|")[0]));
            } else {
                wayPoint.add(new LatLonPoint(Double.parseDouble(points.get(i).split("\\|")[1]),
                        Double.parseDouble(points.get(i).split("\\|")[0])));
            }
        }
        fromAndTo = new RouteSearch.FromAndTo(stLlp, edLlp);
        // 默认距离最短
        driveRouteQuery = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DRIVING_SINGLE_SHORTEST, wayPoint, null, null);
        routeSearch.calculateDriveRouteAsyn(driveRouteQuery);
    }

    public void reRouteSearch() {
        routeSearch.calculateDriveRouteAsyn(driveRouteQuery);
    }


    /***********相关接口**********
     *
     */
    public interface RSMethodListener {
        void rsMethod(int requestCode, DriveRouteResult driveRouteResult);
    }

}
