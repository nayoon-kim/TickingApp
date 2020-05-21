package com.project.mobpro.time;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Alarm_Receiver extends BroadcastReceiver {
    Context context;
    private final static int NOTIFICATION_ID = 222;

    @Override
    public void onReceive(Context context, Intent intent){
        this.context =context;
        String get_you_string = intent.getExtras().getString("state");

        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        service_intent.putExtra("state", get_you_string);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "AlarmPush")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Alarm")
                .setContentText("Wake up")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
