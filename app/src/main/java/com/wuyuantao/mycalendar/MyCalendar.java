package com.wuyuantao.mycalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wuyuantao on 2017/6/8.
 */

public class MyCalendar extends LinearLayout {

    private ImageView ivPrevious;
    private ImageView ivNext;
    private TextView tvDate;
    private GridView mGvDate;

    private Calendar curDate = Calendar.getInstance();

    private String dateFormat;

    public MyCalendarListener listener;

    public MyCalendar(Context context) {
        super(context);
//        initControl(context);
    }

    public MyCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public MyCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    private void initControl(Context context, @Nullable AttributeSet attrs) {
        bindControl(context);
        bindControlEvent();

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MyCalendar);
        try {
            String format = ta.getString(R.styleable.MyCalendar_dateFormat);
            dateFormat = format;
            if (dateFormat == null) {
                dateFormat = "MMM yyy"; //set default value
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
        renderCalendar();
    }

    private void bindControl(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_view, this);
        ivPrevious = (ImageView) findViewById(R.id.iv_previous);
        ivNext = (ImageView) findViewById(R.id.iv_next);
        tvDate = (TextView) findViewById(R.id.tv_date);
        mGvDate = (GridView) findViewById(R.id.gv_calendar);
        mGvDate.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener == null) {
                    return false;
                } else {
                    listener.onItemLongPress((Date) parent.getItemAtPosition(position));
                    return true;
                }
            }
        });
    }

    private void bindControlEvent() {
        ivPrevious.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, -1);
                renderCalendar();
            }
        });

        ivNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, 1);
                renderCalendar();
            }
        });
    }

    /**
     * render calendar
     */
    private void renderCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat(/*"MMM yyy"*/dateFormat);
        tvDate.setText(sdf.format(curDate.getTime()));

        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) curDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int previousDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -previousDays);

        int maxCellCount = 6 * 7;
        while (cells.size() < maxCellCount) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        mGvDate.setAdapter(new CalendarAdapter(getContext(), cells));
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {

        LayoutInflater inflater;

        public CalendarAdapter(@NonNull Context context, ArrayList<Date> days) {
            super(context, R.layout.calendar_text_day, days);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Date date = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.calendar_text_day, parent, false);
            }
            int day = date.getDate();
            ((TextView) convertView).setText(String.valueOf(day));


            Date currentDate = new Date();
            boolean isTheSameMonth = false;
            if (date.getMonth() == currentDate.getMonth()) {
                isTheSameMonth = true;
            }
            if (isTheSameMonth) {
                ((TextView) convertView).setTextColor(Color.parseColor("#000000"));
            } else {
                ((TextView) convertView).setTextColor(Color.parseColor("#55000000"));
            }
            //判断日期是否是当天
            if (currentDate.getDate() == date.getDate() && currentDate.getMonth() == date.getMonth() && currentDate.getYear() == date.getYear()) {
                ((TextView) convertView).setTextColor(Color.parseColor("#ff0000"));
                ((CalendarDayTextView) convertView).isToday = true;
            }
            return convertView;
        }
    }

    public interface MyCalendarListener {
        void onItemLongPress(Date day);
    }

}
