package com.project.mobpro.time;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class Fragment_alarm extends Fragment {

    private RecyclerView mRecyclerView;
    private Context context;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<ItemAlarm> items= new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    private AlarmManager alarm_manager;
    private TimePicker alarm_timepicker;
    private PendingIntent pendingIntent;
    private Button alarm_add;

    public Fragment_alarm(Context context)
    {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_alarm);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        alarm_manager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarm_timepicker = (TimePicker)view.findViewById(R.id.time_picker);

        final Calendar calendar = Calendar.getInstance();
        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);

        Button alarm_on = view.findViewById(R.id.btn_start);
        alarm_on.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v){
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();
                Toast.makeText(context, "Alarm 예정 "+hour+"시 "+minute+"분", Toast.LENGTH_SHORT).show();

                my_intent.putExtra("state", "alarm_on");

                pendingIntent = PendingIntent.getBroadcast(context, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
        });

        Button alarm_off = view.findViewById(R.id.btn_finish);
        alarm_off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, "Alarm 종료", Toast.LENGTH_SHORT).show();

                alarm_manager.cancel(pendingIntent);
                my_intent.putExtra("state","alarm off");

                context.sendBroadcast(my_intent);
            }
        });

        return view;
    }
}
