package com.wuyuantao.mycalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wuyuantao on 2017/6/8.
 */

public class CalendarDayTextView extends TextView {

    public boolean isToday = false;
    private Paint paint = new Paint();

    public CalendarDayTextView(Context context) {
        super(context);
    }

    public CalendarDayTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl();
    }

    public CalendarDayTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }

    private void initControl(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ff000000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isToday) {
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(0, 0, getWidth() / 2, paint);
        }
    }
}
