package com.nilesh.nsec;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class menu extends AppCompatActivity {

    Switch sw;
    Boolean Checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sw = (Switch) findViewById(R.id.switch1);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/zekton.ttf");
        sw.setTypeface(font);
        /*setSwitch();



        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sw.isChecked()) {
                    Toast.makeText(getBaseContext(), "Notification Turned ON", Toast.LENGTH_SHORT).show();

                    Intent in = new Intent(getBaseContext(), server.class);
                    startService(in);

                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isChecked", true);
                    editor.apply();
                    setSwitch();
                } else {
                    Toast.makeText(getBaseContext(), "Notification Turned OFF", Toast.LENGTH_SHORT).show();


                    Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                    notificationIntent.addCategory("android.intent.category.DEFAULT");
                    PendingIntent broadcast = PendingIntent.getBroadcast(getBaseContext(), 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(broadcast);

                    Intent in1 = new Intent(getBaseContext(), server.class);
                    stopService(in1);

                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isChecked", false);
                    editor.apply();
                    setSwitch();
                }

            }

        });


    }



        public void setSwitch() {
            SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
            Checked = sp.getBoolean("isChecked", true);

            if (Checked == true) {
                sw.setChecked(true);

            }
            else {
                sw.setChecked(false);

            }*/
        }
}


