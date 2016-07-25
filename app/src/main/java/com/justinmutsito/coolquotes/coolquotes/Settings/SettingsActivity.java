package com.justinmutsito.coolquotes.coolquotes.Settings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.justinmutsito.coolquotes.coolquotes.Notifications.AlarmReceiver;
import com.justinmutsito.coolquotes.coolquotes.R;
import com.justinmutsito.coolquotes.coolquotes.WelcomeActivity;

import java.util.Calendar;

import at.markushi.ui.CircleButton;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {
    private static final String THEME = "theme";
    private static final String TIME = "time";
    private SharedPreferences savedTheme;
    private SharedPreferences savedTime;
    public String mTheme;
    public int mTime;
    private Toolbar myToolbar;
    private PendingIntent pendingIntent;


    @Bind(R.id.themeLabel)
    TextView mThemeLabel;
    @Bind(R.id.notificationsLabel)
    TextView mNotificationLabel;
    @Bind(R.id.brownCheckbox)
    CircleButton mBrown;
    @Bind(R.id.blueCheckbox)
    CircleButton mBlue;
    @Bind(R.id.offCheckbox)
    CircleButton mOff;
    @Bind(R.id.morningCheckbox)
    CircleButton mMorning;
    @Bind(R.id.eveningCheckbox)
    CircleButton mEvening;
    @Bind(R.id.aboutButton)
    Button mAbout;
    @Bind(R.id.backgroundImage)
    ImageView mBackgroundImage;
    @Bind(R.id.fadedImage)
    ImageView mFadedImage;
    @Bind(R.id.zBrownLabel)
    TextView mThemeBrownLabel;
    @Bind(R.id.cBlueLabel)
    TextView mThemeBlueLabel;
    @Bind(R.id.offLabel)
    TextView mOffLabel;
    @Bind(R.id.morningLabel)
    TextView mMorningLabel;
    @Bind(R.id.eveningLabel)
    TextView mEveningLabel;
    @Bind(R.id.layout1)
    LinearLayout mLayout1;
    @Bind(R.id.layout2)
    LinearLayout mLayout2;
    @Bind(R.id.layout3)
    LinearLayout mLayout3;
    @Bind(R.id.layout4)
    LinearLayout mLayout4;
    @Bind(R.id.layout5)
    LinearLayout mLayout5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        savedTheme = getSharedPreferences(THEME, 0);
        mTheme = savedTheme.getString(getString(R.string.themeKey), "brown");
        if(mTheme.isEmpty()){
            mTheme="brown";
        }


        String leave = getIntent().getStringExtra(getString(R.string.intentKey));
        if (leave.equals("leave")) {
            Intent leaveIntent = new Intent(SettingsActivity.this, WelcomeActivity.class);
            leaveIntent.putExtra(getString(R.string.themeKey), mTheme);
            leaveIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            leaveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(leaveIntent);
        }

        savedTime = getSharedPreferences(TIME, 0);
        mTime = savedTime.getInt(getString(R.string.timeKey), 0);


        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(Color.parseColor("#212121"));
        setSupportActionBar(myToolbar);


        if (mTheme.equals("brown")) {
            brownTheme(); //Set the theme.
        } else {
            blueTheme();

        }

        if (mTime == 0) {
            setOff();//Set notification time.
        } else if (mTime == 9) {
            setMorning();
        } else {
            setEvening();
        }

        saveTheme(mTheme);
        saveTime(mTime);


        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SettingsActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (mTime != 0) {
            setNotificationTime(mTime);
        }

    }


    @OnClick(R.id.brownCheckbox)
    public void setBrown() {
        brownTheme();
        resetTheme();

    }

    private void brownTheme() {
        mTheme = "brown";
        mBlue.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_grey600_48dp);
        mBrown.setImageResource(R.drawable.ic_checkbox_marked_circle_grey600_48dp);
        setBrownTheme();
        setIcons();
        saveTheme(mTheme);
    }

    @OnClick(R.id.blueCheckbox)
    public void setBlue() {
        blueTheme();
        resetTheme();
    }

    private void blueTheme() {
        mTheme = "blue";
        mBrown.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_white_48dp);
        mBlue.setImageResource(R.drawable.ic_checkbox_marked_circle_white_48dp);
        setBlueTheme();
        setIcons();
        saveTheme(mTheme);
    }

    private void setIcons() {
        if (mTime == 0) {
            setOff();
        } else if (mTime == 9) {
            setMorning();
        } else {
            setEvening();
        }


    }


    @OnClick(R.id.offCheckbox)
    public void setOff() {
        mTime = 0;
        if (mTheme.equals("brown")) {

            mOff.setImageResource(R.drawable.ic_checkbox_marked_circle_grey600_48dp);
            mEvening.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_grey600_48dp);
            mMorning.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_grey600_48dp);
        } else {

            mOff.setImageResource(R.drawable.ic_checkbox_marked_circle_white_48dp);
            mEvening.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_white_48dp);
            mMorning.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_white_48dp);
        }
        saveTime(mTime);
        cancel();
    }

    @OnClick(R.id.morningCheckbox)
    public void setMorning() {
        mTime = 9;
        if (mTheme.equals("brown")) {
            mEvening.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_grey600_48dp);
            mMorning.setImageResource(R.drawable.ic_checkbox_marked_circle_grey600_48dp);
            mOff.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_grey600_48dp);
        } else {

            mEvening.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_white_48dp);
            mMorning.setImageResource(R.drawable.ic_checkbox_marked_circle_white_48dp);
            mOff.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_white_48dp);
        }
        saveTime(mTime);
        setNotificationTime(mTime);
    }


    @OnClick(R.id.eveningCheckbox)
    public void setEvening() {
        mTime = 21;
        if (mTheme.equals("brown")) {

            mMorning.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_grey600_48dp);
            mEvening.setImageResource(R.drawable.ic_checkbox_marked_circle_grey600_48dp);
            mOff.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_grey600_48dp);
        } else {

            mMorning.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_white_48dp);
            mEvening.setImageResource(R.drawable.ic_checkbox_marked_circle_white_48dp);
            mOff.setImageResource(R.drawable.ic_checkbox_blank_circle_outline_white_48dp);
        }
        saveTime(mTime);
        setNotificationTime(mTime);
    }


    @OnClick(R.id.aboutButton)
    public void about() {
        Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
        intent.putExtra(getString(R.string.themeKey), mTheme);
        startActivity(intent);
    }


    private void saveTheme(String theme) {
        savedTheme = getSharedPreferences(THEME, 0);
        SharedPreferences.Editor editorTheme = savedTheme.edit();
        editorTheme.putString(getString(R.string.themeKey), theme);
        editorTheme.commit();
    }

    private void saveTime(int time) {
        savedTime = getSharedPreferences(TIME, 0);
        SharedPreferences.Editor editorTime = savedTime.edit();
        editorTime.putInt(getString(R.string.timeKey), time);
        editorTime.commit();
    }

    private void setBlueTheme() {
        //Do not merge setBlueTheme and setBrownTheme to one method because in v2 the themes might not have any similar attributes.
        String white = "#ffffff";
        String lightBlue = "#80B3E5FC";

        mBackgroundImage.setImageResource(R.drawable.bg_blue);
        mFadedImage.setImageResource(R.color.blueFaded);
        mThemeLabel.setTextColor(Color.parseColor(white));
        mNotificationLabel.setTextColor(Color.parseColor(white));
        mAbout.setTextColor(Color.parseColor(white));
        mAbout.setBackground(getResources().getDrawable(R.drawable.bg_blue_circle_gradient));
        mThemeBrownLabel.setTextColor(Color.parseColor(white));
        mThemeBlueLabel.setTextColor(Color.parseColor(white));
        mOffLabel.setTextColor(Color.parseColor(white));
        mMorningLabel.setTextColor(Color.parseColor(white));
        mEveningLabel.setTextColor(Color.parseColor(white));
        mLayout1.setBackgroundColor(Color.parseColor(lightBlue));
        mLayout2.setBackgroundColor(Color.parseColor(lightBlue));
        mLayout3.setBackgroundColor(Color.parseColor(lightBlue));
        mLayout4.setBackgroundColor(Color.parseColor(lightBlue));
        mLayout5.setBackgroundColor(Color.parseColor(lightBlue));
        myToolbar.setTitleTextColor(Color.parseColor(white));

    }

    private void setBrownTheme() {
        //Do not merge setBlueTheme and setBrownTheme to one method because in v2 the themes might not have any similar attributes.
        String darkGrey = "#212121";
        String lightGrey = "#757575";
        String lightBrown = "#90D7CCC8";
        mBackgroundImage.setImageResource(R.drawable.bg_brown);
        mFadedImage.setImageResource(R.color.brownFaded);
        mThemeLabel.setTextColor(Color.parseColor(darkGrey));
        mNotificationLabel.setTextColor(Color.parseColor(darkGrey));
        mAbout.setTextColor(Color.parseColor(darkGrey));
        mAbout.setBackground(getResources().getDrawable(R.drawable.bg_brown_circle_gradient));
        mThemeBrownLabel.setTextColor(Color.parseColor(lightGrey));
        mThemeBlueLabel.setTextColor(Color.parseColor(lightGrey));
        mOffLabel.setTextColor(Color.parseColor(lightGrey));
        mMorningLabel.setTextColor(Color.parseColor(lightGrey));
        mEveningLabel.setTextColor(Color.parseColor(lightGrey));
        mLayout1.setBackgroundColor(Color.parseColor(lightBrown));
        mLayout2.setBackgroundColor(Color.parseColor(lightBrown));
        mLayout3.setBackgroundColor(Color.parseColor(lightBrown));
        mLayout4.setBackgroundColor(Color.parseColor(lightBrown));
        mLayout5.setBackgroundColor(Color.parseColor(lightBrown));
        myToolbar.setTitleTextColor(Color.parseColor(darkGrey));

    }

    private void resetTheme() {

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    //Intentionally left blank
                } finally {
                    Intent leaveIntent = new Intent(SettingsActivity.this, WelcomeActivity.class);
                    leaveIntent.putExtra(getString(R.string.themeKey), mTheme);
                    leaveIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    leaveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(leaveIntent);
                }

            }
        };
        timer.start();


    }


    private void setNotificationTime(int time) {

        AlarmManager manager = (AlarmManager) SettingsActivity.this.getSystemService(SettingsActivity.this.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, time);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);


        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }


    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }
}




