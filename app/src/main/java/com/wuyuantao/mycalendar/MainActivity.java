package com.wuyuantao.mycalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements MyCalendar.MyCalendarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyCalendar calendar = (MyCalendar) findViewById(R.id.MyCalendar);
        calendar.listener = this;
    }

    @Override
    public void onItemLongPress(Date day) {
        DateFormat df = SimpleDateFormat.getDateInstance();
        Toast.makeText(this, df.format(day), Toast.LENGTH_SHORT).show();
    }
}
