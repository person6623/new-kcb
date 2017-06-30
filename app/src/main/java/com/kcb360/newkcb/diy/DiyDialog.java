package com.kcb360.newkcb.diy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.kcb360.newkcb.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xinshichao on 2017/5/19.
 * <p>
 * 自定义dialog
 */

public class DiyDialog {

    // 日期&时间选择器
    public static void dateOrTimeSelectDialog(final Context context, final OnTimeDataListener onTimeDataListener) {
        // 自定义布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_date_or_time_select, null);
        final DatePicker dp = (DatePicker) view.findViewById(R.id.dialog_date_or_time_select_date_dp);
        final TimePicker tp = (TimePicker) view.findViewById(R.id.dialog_date_or_time_select_time_tp);
        // 获取当前时间
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String dateNowStr = sdf.format(date);
        String[] dateNowArr = dateNowStr.split("-");
        String dateSel = null;

        dp.setCalendarViewShown(false);
        dp.init(Integer.valueOf(dateNowArr[0]), Integer.valueOf(dateNowArr[1]) - 1, Integer.valueOf(Integer.valueOf(dateNowArr[2])), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });

        tp.setCurrentHour(Integer.valueOf(dateNowArr[3]));
        tp.setCurrentMinute(Integer.valueOf(dateNowArr[4]));
        tp.setIs24HourView(true);
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请选择时间");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String date = dp.getYear() + "-" + monthOrDaySimple((dp.getMonth() + 1)) + "-" +
                        monthOrDaySimple(dp.getDayOfMonth()) + " ";
                String time;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    time = timeSimple(tp.getHour(), tp.getMinute());
                } else {
                    time = timeSimple(tp.getCurrentHour(), tp.getCurrentMinute());
                }
                onTimeDataListener.onTimeDataListener(date + time);
            }
        }).create().show();
    }

    private static String monthOrDaySimple(int monthOrDay) {
        String monthOrDayStr = monthOrDay > 9 ? "" + monthOrDay : "0" + monthOrDay;
        return monthOrDayStr;
    }

    private static String timeSimple(int hour, int minute) {
        String hourStr = hour > 9 ? "" + hour : "0" + hour;
        String minuteStr = minute > 9 ? "" + minute : "0" + minute;
        return hourStr + ":" + minuteStr;
    }

    // 登录界面dialog
    public static void loginDialog(Context context, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示");
        builder.setMessage(content);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    // 跳转用dialog
    public static void fromToDialog(Context context, String content, final OnFromToListener onFromToListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(content);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onFromToListener.onFromToListener();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    // itemSelectDialog
    public static void itemSelDialog(final Context context, String title, final String[] arr
            , final OnItemSelectListener onItemSelectListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setItems(arr
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onItemSelectListener.onItemSelectListener(which,
                                arr[which]);
                    }
                }).create().show();
    }

    // edittextDialog
    public static void edittextDialog(Context context, String title
            , final OnEditChangeListener onEditChangeListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_text_decimal, null);
        final EditText et = (EditText) view.findViewById(R.id.edit_text_decimal_et);
        builder.setTitle(title).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onEditChangeListener.onEditChangeListener(et.getText().toString());
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }


    /***************************** 所用接口
     *
     */
    // 时间接口
    public interface OnTimeDataListener {
        void onTimeDataListener(String date);
    }

    // 跳转操作接口
    public interface OnFromToListener {
        void onFromToListener();
    }

    // item点击接口
    public interface OnItemSelectListener {
        void onItemSelectListener(int position, String content);
    }

    // edittext内容接口
    public interface OnEditChangeListener {
        void onEditChangeListener(String edit);
    }
}
