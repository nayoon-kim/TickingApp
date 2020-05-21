package com.project.mobpro.time;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import androidx.fragment.app.Fragment;

public class Fragment_stopwatch extends Fragment {
    private Button button_Start;
    private Button button_Stop;
    private Button button_Record;
    private Button button_Pause;
    private Button button_Continue;

    private TextView time;

    private String record = "";
    private TextView recordView;

    private Thread thread = null;

    private Context context;
    private Boolean isRunning = true;

    public Fragment_stopwatch(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        //textview
        time = (TextView) view.findViewById(R.id.time);
        recordView = (TextView) view.findViewById(R.id.stopwatch_recordView);
        recordView.setMovementMethod(new ScrollingMovementMethod());
        recordView.setText("");

        //Button
        button_Start = (Button) view.findViewById(R.id.button_start);
        button_Pause = (Button) view.findViewById(R.id.button_pause);
        button_Record = (Button) view.findViewById(R.id.button_record);
        button_Stop = (Button) view.findViewById(R.id.button_stop);
        button_Continue = (Button) view.findViewById(R.id.button_continue);

        //button_control
        button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_Start.setVisibility(View.GONE);
                button_Pause.setVisibility(View.VISIBLE);
                button_Record.setVisibility(View.VISIBLE);

                thread = new Thread(new timeThread());
                thread.start();
            }
        });
        button_Stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                button_Continue.setVisibility(View.GONE);
                button_Stop.setVisibility(View.GONE);
                button_Pause.setVisibility(View.GONE);
                button_Record.setVisibility(View.GONE);

                button_Start.setVisibility(View.VISIBLE);
                recordView.setVisibility(View.GONE);

                thread.interrupt();

                isRunning = true;
                time.setText("00:00:00");
                record = "";
                recordView.post(new Runnable(){

                    @Override
                    public void run() {
                        recordView.setText(record);
                    }
                });
            }
        });
        button_Record.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                recordView.setVisibility(View.VISIBLE);
                record+=time.getText().toString()+"\n";

                recordView.post(new Runnable(){

                    @Override
                    public void run() {
                        recordView.setText(record);
                    }
                });
            }
        });
        button_Pause.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                isRunning = !isRunning;
                button_Continue.setVisibility(View.VISIBLE);
                button_Stop.setVisibility(View.VISIBLE);
                button_Pause.setVisibility(View.GONE);
                button_Record.setVisibility(View.GONE);
            }
        });
        button_Continue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                isRunning = !isRunning;
                button_Continue.setVisibility(View.GONE);
                button_Stop.setVisibility(View.GONE);
                button_Pause.setVisibility(View.VISIBLE);
                button_Record.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 % 3600) % 24;
            String result = String.format("%02d:%02d:%02d", min, sec, mSec);
            time.setText(result);
        }
    };
    class timeThread implements Runnable{

        @Override
        public void run() {
            int i = 0;

            while(true){
                while(isRunning){
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try{
                        Thread.sleep(10);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                        return;
                    }
                }
            }
        }
    }
}
