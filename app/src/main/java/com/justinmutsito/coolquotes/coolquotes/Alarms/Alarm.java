package com.justinmutsito.coolquotes.coolquotes.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.justinmutsito.coolquotes.coolquotes.receivers.AlarmReceiver;

import java.util.Calendar;

/**
 * Created by justin on 8/18/16.
 */
public class Alarm {

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    public Alarm(Context context) {

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void setAlarm(int hour,int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);


        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

    }


    public void cancel() {
        alarmManager.cancel(alarmIntent);
    }

}
