package com.nilesh.nsec;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


public class Notification_receiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        /*SharedPreferences sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        Boolean Checked = sp.getBoolean("isChecked", true);
        if (Checked == true) {

            if ((intent.getAction() != null) && (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))) {
                Intent startServiceIntent = new Intent(context, server.class);
                context.startService(startServiceIntent);
            }

        }*/


    }
}
