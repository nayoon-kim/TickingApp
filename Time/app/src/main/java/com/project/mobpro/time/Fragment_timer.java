package com.project.mobpro.time;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class Fragment_timer extends Fragment {
    private static final int MESSAGE_TIMER_START=100;
    private static final int MESSAGE_TIMER_REPEAT=101;
    private static final int MESSAGE_TIMER_STOP=102;

//    private TimerHandler timerHandler = null;
    private Thread thread;

    private Context context;

    private Button btn_start;
    private Button btn_cancel;
    private Button btn_pause;
    private Button btn_continue;

    private NumberPicker numberPicker_Hour;
    private NumberPicker numberPicker_Minute;
    private NumberPicker numberPicker_Second;

    private TextView timer_time;
    private Boolean isRunning = true;
    private SharedPreferences sharedPreferences;

    public Fragment_timer(Context context){
        this.context = context;
    }

    private int time = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        numberPicker_Hour = (NumberPicker) view.findViewById(R.id.numpicker_hours);
        numberPicker_Minute = (NumberPicker) view.findViewById(R.id.numpicker_minutes);
        numberPicker_Second = (NumberPicker) view.findViewById(R.id.numpicker_seconds);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //timer_timepicker = (TimePicker) view.findViewById(R.id.timer_picker);
        timer_time = (TextView) view.findViewById(R.id.timer_time);
//        timerHandler = new TimerHandler();
        numberPicker_Hour.setMaxValue(23);
        numberPicker_Hour.setValue(sharedPreferences.getInt("Hours",0));
        numberPicker_Minute.setMaxValue(59);
        numberPicker_Minute.setValue(sharedPreferences.getInt("Minutes",0));
        numberPicker_Second.setMaxValue(59);
        numberPicker_Second.setValue(sharedPreferences.getInt("Seconds",0));

        btn_start = view.findViewById(R.id.timer_start);
        btn_pause = view.findViewById(R.id.timer_pause);
        btn_continue = view.findViewById(R.id.timer_continue);
        btn_cancel = view.findViewById(R.id.timer_cancel);

        btn_start.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                time += numberPicker_Hour.getValue()*60*60*1000;
                time += numberPicker_Minute.getValue()*60*100;
                time += numberPicker_Second.getValue()*1000;

                //timer_time.setText(numberPicker_Hour.getValue()+":"+numberPicker_Minute.getValue()+":"+numberPicker_Second.getValue());
                timer_time.setText(String.format("%1$d:%2$02d:%3$02d", numberPicker_Hour.getValue(), numberPicker_Minute.getValue(), numberPicker_Second.getValue()));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("Hours", numberPicker_Hour.getValue());
                editor.putInt("Minutes", numberPicker_Minute.getValue());
                editor.putInt("Seconds", numberPicker_Second.getValue());
                editor.apply();

                thread = new Thread(new timeThread());
                thread.start();
                timer_time.setVisibility(View.VISIBLE);

                numberPicker_Minute.setVisibility(View.GONE);
                numberPicker_Hour.setVisibility(View.GONE);
                numberPicker_Second.setVisibility(View.GONE);

                btn_start.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.VISIBLE);
            }
        });
        btn_pause.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                isRunning = !isRunning;
                btn_continue.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.GONE);
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                isRunning = !isRunning;
                btn_pause.setVisibility(View.VISIBLE);
                btn_continue.setVisibility(View.GONE);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                thread.interrupt();

                timer_time.setText("00:00:00");

                timer_time.setVisibility(View.GONE);
                numberPicker_Minute.setVisibility(View.VISIBLE);
                numberPicker_Hour.setVisibility(View.VISIBLE);
                numberPicker_Second.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.GONE);
                btn_continue.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
            }
        });

        return view;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            int sec = (msg.arg1 / 100 ) % 60;
            int min = (msg.arg1 / 100 ) / 60;
            int hour = (msg.arg1 % 3600) % 24;
            String result = String.format("00:%02d:%02d:%02d",min, sec,hour);
            timer_time.setText(result);
        }
    };

    class timeThread implements Runnable{
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void run(){
            int i = time;
            while(true){
                while(isRunning){

                    Message msg = new Message();
                    msg.arg1 = i--;
                    handler.sendMessage(msg);
                    try{
                        Thread.sleep(10);
                    }catch(InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }
}
