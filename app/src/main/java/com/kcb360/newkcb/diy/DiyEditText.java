package com.kcb360.newkcb.diy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by xinshichao on 2017/5/8.
 * <p>
 * 自定义密码输入框
 */

public class DiyEditText extends EditText {

    // 密码画笔, 密码框画笔
    private Paint passPaint, framePaint;
    // 获取edittext宽高, 框宽高
    private float width, height, fWidth, fheight;
    // 框间距
    private final float FRAME_SPAGING = 15;
    // 密码圆大小
    private float srcSize;
    // 实时密码长度
    private int nowTextLenfth;
    // 满位监听
    private OnTextLengMaxListener onTextLengMaxListener;

    public DiyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        passPaint = new Paint();
        passPaint.setColor(Color.BLACK);
        passPaint.setStyle(Paint.Style.FILL);
        passPaint.setAntiAlias(true);

        framePaint = new Paint();
        framePaint.setColor(Color.LTGRAY);
        framePaint.setStrokeWidth(1.50f);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();

        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, width, height, bgPaint);

        fWidth = (width - FRAME_SPAGING * (6 - 1)) / 6;
        for (int i = 0; i < 6; i++) {
            float left = (fWidth + FRAME_SPAGING) * i;
            float top = 0;
            float right = left + fWidth;
            float bottom = height + 4;
            canvas.drawRect(left, top, right, bottom, framePaint);
        }

        if (fWidth > height + 4) {
            srcSize = height / 4;
        } else {
            srcSize = (fWidth - 2) / 4;
        }

        for (int i = 0; i < nowTextLenfth; i++) {
            float cx = fWidth / 2 + (fWidth + FRAME_SPAGING) * i;
            float cy = (height + 4) / 2;
            canvas.drawCircle(cx, cy, srcSize, passPaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        nowTextLenfth = text.length();
        if (onTextLengMaxListener != null) {
            onTextLengMaxListener.onTextLengMaxListener(text);
        }
    }

    public interface OnTextLengMaxListener {
        void onTextLengMaxListener(CharSequence text);
    }

    public void setOnTextLengMaxListener(OnTextLengMaxListener onTextLengMaxListener) {
        this.onTextLengMaxListener = onTextLengMaxListener;
    }
}
