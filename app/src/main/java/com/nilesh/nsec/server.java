/*

    package com.nilesh.nsec;

    import android.app.AlarmManager;
    import android.app.PendingIntent;
    import android.app.Service;
    import android.content.Context;
    import android.content.Intent;
    import android.os.IBinder;
    import java.util.Calendar;
    import java.util.Date;



    public class server extends Service {

        int time,hr,min;

        final class myThreadClass implements Runnable
        {
            int service_id;
            myThreadClass(int service_id)
            {
                this.service_id=service_id;
            }

            @Override
            public void run() {


                while(true)
                {
                    checkTime();
                    registerAlarm();
                }


            }

        }


        @Override
        public void onCreate() {

            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            Thread thread=new Thread(new myThreadClass(startId));
            thread.start();
            return START_STICKY;
        }

        @Override
        public void onDestroy() {


        }


        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }



        public void checkTime() {
            Date date = new Date();
            String a = date.toString();//Mon Sep 28 20:15:56 IST 2015
            String Shour = a.substring(11, 13);
            String Smin = a.substring(14, 16);
            int hour = Integer.parseInt(Shour);
            int minute = Integer.parseInt(Smin);
            time = hour * 100 + minute;
        }



        public void registerAlarm()
        {

            if(time==928) {
                hr=9;
                min=30;
                alarm();
            }
            else if(time==1018) {
                hr=10;
                min=20;
                alarm();
            }
            else if(time==1108) {
                hr=11;
                min=10;
                alarm();
            }
            else if(time==1158) {
                hr=12;
                min=0;
                alarm();
            }
            else if(time==1248) {
                hr=12;
                min=59;
                alarm();
            }
            else if(time==1318) {
                hr=13;
                min=20;
                alarm();
            }
            else if(time==1408) {
                hr=14;
                min=10;
                alarm();
            }
            else if(time==1457) {
                hr=15;
                min=0;
                alarm();
            }
            else if(time==1548) {
                hr=15;
                min=59;
                alarm();
            }


        }
        public void alarm()
        {

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
            notificationIntent.addCategory("android.intent.category.DEFAULT");

            PendingIntent broadcast = PendingIntent.getBroadcast(this, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, min);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, broadcast);
        }



    }
*/