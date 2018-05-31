package com.nilesh.nsec;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tv1, tv2, period, nextPeriod, dayString;
    Button todayRoutine;
    TextClock tc;
    int time, count, hour, minute, show;
    String section, collegeOver = "College Over", Sday = "",grp,branch;
    String arr[] = new String[9];
    Vibrator myVib;
    AlertDialog alertDialog;
    ProgressDialog pDialog;
    DatabaseHelper myDB;
    private static final String REGISTER_URL = "http://eugenicspharma.in/nerd_app/json.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        action_bar();
        myDB = new DatabaseHelper(this);
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        volleyJsonArrayRequest();
        day();
        geel();
        //startNotify();
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        section = sp.getString("section", null);
        count = sp.getInt("counter", 0);


        if (count == 0) {
            Intent i = new Intent(this, login.class);
            startActivity(i);
        }

        tv1 = (TextView) findViewById(R.id.P);
        tv2 = (TextView) findViewById(R.id.NP);
        period = (TextView) findViewById(R.id.period);
        nextPeriod = (TextView) findViewById(R.id.nextPeriod);
        dayString = (TextView) findViewById(R.id.day);
        todayRoutine = (Button) findViewById(R.id.bToday);
        tc = (TextClock) findViewById(R.id.linearLayout);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chip.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/zekton.ttf");
        dayString.setTypeface(font);
        tv1.setTypeface(font);
        tv2.setTypeface(font);
        period.setTypeface(font2);
        nextPeriod.setTypeface(font2);
        todayRoutine.setTypeface(font2);
        tc.setTypeface(font);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
                                checkTime();
                                getDay();
                                geel();
                                assignPeriod();


                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
       //volleyJsonArrayRequest();
       geel();


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater mnuInflater = getMenuInflater();
        mnuInflater.inflate(R.menu.main, menu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView depart = (TextView)hView.findViewById(R.id.department);


        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String department = sp.getString("branch", null);

        depart.setText("Department: "+department);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {

            Intent in1 = new Intent(this, menu.class);
            startActivity(in1);
            return true;

        } else if (id == R.id.aboutUs) {
            Intent in1 = new Intent(this, about.class);
            startActivity(in1);
            return true;
        } else if (id == R.id.share) {
            shareApplication();

        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.register) {
            Intent i = new Intent(this, login.class);
            startActivity(i);
        } else if (id == R.id.home) {


        } else if (id == R.id.mon) {
            Intent intent = new Intent(getBaseContext(), Routine.class);
            intent.putExtra("day", "Mon");
            startActivity(intent);


        } else if (id == R.id.tue) {
            Intent intent = new Intent(getBaseContext(), Routine.class);
            intent.putExtra("day", "Tue");
            startActivity(intent);

        } else if (id == R.id.wed) {
            Intent intent = new Intent(getBaseContext(), Routine.class);
            intent.putExtra("day", "Wed");
            startActivity(intent);

        } else if (id == R.id.thu) {
            Intent intent = new Intent(getBaseContext(), Routine.class);
            intent.putExtra("day", "Thu");
            startActivity(intent);

        } else if (id == R.id.fri) {
            Intent intent = new Intent(getBaseContext(), Routine.class);
            intent.putExtra("day", "Fri");
            startActivity(intent);

        } else if (id == R.id.abt) {
            Intent i = new Intent(this, about.class);
            startActivity(i);

        } else if (id == R.id.share)
            shareApplication();

        else if (id == R.id.settings) {
            Intent i = new Intent(this, menu.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getDay() {
        day();


        switch (Sday) {

            case "Mon":
                dayString.setText("MONDAY");
                break;

            case "Tue":
                dayString.setText("TUESDAY");
                break;

            case "Wed":
                dayString.setText("WEDNESDAY");
                break;

            case "Thu":
                dayString.setText("THURSDAY");
                break;

            case "Fri":
                dayString.setText("FRIDAY");
                break;

            case "Sat":
                dayString.setText("SATURDAY");

                break;

            case "Sun":
                dayString.setText("SUNDAY");



        }
    }

    public void checkTime() {
        Date date = new Date();
        String a = date.toString();//Mon Sep 28 20:15:56 IST 2015
        String Shour = a.substring(11, 13);
        String Smin = a.substring(14, 16);
        hour = Integer.parseInt(Shour);
        minute = Integer.parseInt(Smin);
        time = hour * 100 + minute;

    }

    public void day() {
        Date date = new Date();
        String a = date.toString();//Mon Sep 28 20:15:56 IST 2015
        Sday = a.substring(0, 3);
    }


    public void assignPeriod() {

        if (Sday.equalsIgnoreCase("Sun")) {
            period.setText("Holiday");
            nextPeriod.setText("Holiday");
        } else {

            if (time >= 100 && time < 430) {
                period.setText("Are You Are Drunk?\nGo Home");
                nextPeriod.setText("Are You Are Drunk?\nGo Home");
            } else if (time >= 500 && time < 900) {
                period.setText("College Has Not\nStarted Yet");
                nextPeriod.setText("College Has Not\nStarted Yet");
            } else if (time >= 900 && time < 950) {
                period.setText("College About\nTo Start");
                nextPeriod.setText("College About\nTo Start");
            } else if (time >= 1000 && time < 1050) {
                period.setText(arr[0]);
                nextPeriod.setText(arr[1]);
            } else if (time >= 1050 && time < 1140) {
                period.setText(arr[1]);
                nextPeriod.setText(arr[2]);
            } else if (time >= 1140 && time < 1230) {
                period.setText(arr[2]);
                nextPeriod.setText(arr[3]);
            } else if (time >= 1230 && time < 1320) {
                period.setText(arr[3]);
                nextPeriod.setText(arr[4]);
            } else if (time >= 1320 && time < 1350) {
                period.setText(arr[4]);
                nextPeriod.setText(arr[5]);
            } else if (time >= 1350 && time < 1440) {
                period.setText(arr[5]);
                nextPeriod.setText(arr[6]);
            } else if (time >= 1440 && time < 1530) {
                period.setText(arr[6]);
                nextPeriod.setText(arr[7]);
            } else if (time >= 1530 && time < 1620) {
                period.setText(arr[7]);
                nextPeriod.setText(arr[8]);
            } else if (time >= 1620 && time < 1730) {
                period.setText(arr[8]);
                nextPeriod.setText(collegeOver);
            } else {
                period.setText(collegeOver);
                nextPeriod.setText(collegeOver);
            }

        }
    }

    public void today(View v) {
        myVib.vibrate(20);
        Date date = new Date();
        String a = date.toString();//Mon Sep 28 20:15:56 IST 2015
        Sday = a.substring(0, 3);

        Intent intent = new Intent(getBaseContext(), Routine.class);
        intent.putExtra("day", Sday);
        startActivity(intent);
    }

    public void countdown(View v) {
        myVib.vibrate(20);
        if (!(period.getText().toString()).equals("College Over")) {


            mem();

            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Time Remaining");


                alertDialog.setMessage(show + " Minutes Left");
                new CountDownTimer(999999999, 1000) {
                    @Override
                    public void onTick(long m) {
                        if(show!=-1) {
                            alertDialog.setMessage(show + " Minutes Left");
                        }else
                        {
                            alertDialog.setMessage("No Class Right Now");
                        }
                        mem();

                    }

                    @Override
                    public void onFinish() {

                        alertDialog.dismiss();
                    }
                }.start();


            alertDialog.show();
        } else {
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Time Remaining");
            alertDialog.setMessage("No Class Right Now");
            alertDialog.show();
        }


    }

    public void mem() {
        checkTime();
        if (time >= 1000 && time < 1050)
            show = 50 - minute ;
        else if (time >= 1050 && time < 1100)
            show = 50 - (minute -50);
        else if (time >= 1100 && time < 1140)
            show = 50 - (minute + 10);
        else if (time >= 1140 && time < 1200)
            show = 50 - (minute - 40);
        else if (time >= 1200 && time < 1230)
            show = 50 - (minute + 20);
        else if (time >= 1230 && time < 1300)
            show = 50 - (minute - 30);
        else if (time >= 1300 && time < 1320)
            show = 50 - (minute + 30);
        else if (time >= 1320 && time < 1350)
            show = 30 - (minute - 20);

        else if (time >= 1350 && time < 1400)
            show = 50 - (minute - 50);
        else if (time >= 1400 && time < 1440)
            show = 50 - (minute + 10);
        else if (time >= 1440 && time < 1500)
            show = 50 - (minute - 40);
        else if (time >= 1500 && time < 1530)
            show = 50 - (minute + 20);
        else if (time >= 1530 && time < 1600)
            show = 50 - (minute - 30);
        else if (time >= 1600 && time < 1620)
            show = 50 - (minute + 30);
        else if (time >= 1620 && time < 1700)
            show = 50 - (minute - 20);
        else if (time >= 1700 && time < 1730)
            show = 50 - (minute + 20);
        else
            show=-1;

    }


    public void call(View v) {
        myVib.vibrate(20);
        Intent un = new Intent(this, Routine.class);
        startActivity(un);
    }

    public void shareApplication() {
        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");


        // Append file and send Intent
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        startActivity(Intent.createChooser(intent, "Share app via"));
    }



   /* public void startNotify() {

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        Boolean Checked = sp.getBoolean("isChecked", true);

        if (Checked) {
            Intent in = new Intent(this, server.class);
            startService(in);
        } else {

            Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
            notificationIntent.addCategory("android.intent.category.DEFAULT");
            PendingIntent broadcast = PendingIntent.getBroadcast(this, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(broadcast);
            Intent in1 = new Intent(this, server.class);
            stopService(in1);
        }
    }*/


    public void volleyJsonArrayRequest() {
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        grp = sp.getString("section", null);
        branch = sp.getString("branch", null);
        //Log.d("MYLOG",branch);
        //Log.d("MYLOG",grp);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       //Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                        jsonParser(response);
                        pDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("table", branch+"_"+grp);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void jsonParser(String data) {
        SharedPreferences sp = getSharedPreferences("request", Context.MODE_PRIVATE);
        int toInsert = sp.getInt("toInsert", 0);
        JSONArray jArray = null;


        try {
            jArray = new JSONArray(data);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject c = jArray.getJSONObject(i);

                int id = c.getInt("id");

                String pos = c.getString("position");
                String tym = c.getString("TIME");
                String MON = c.getString("MONDAY");
                String TUE = c.getString("TUESDAY");
                String WED = c.getString("WEDNESDAY");
                String THUR = c.getString("THURSDAY");
                String FRI = c.getString("FRIDAY");
                String SAT = c.getString("SATURDAY");



                if (toInsert == 0) {
                    sp = getSharedPreferences("request", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("toInsert", 1);
                    editor.apply();
                   myDB.insertData(Integer.toString(id),pos,tym,MON,TUE,WED,THUR,FRI,SAT);



                } else {
                    myDB.updateData(Integer.toString(id),pos,tym,MON,TUE,WED,THUR,FRI,SAT);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, , Toast.LENGTH_LONG).show();
    }

    public void geel() {
        Cursor res = myDB.getAllData();
        if (res.getCount() == 0) {
            // show message
            //showMessage("Error","Nothing found");
            //Toast.makeText(this, "Nothing found", Toast.LENGTH_LONG).show();
        }
        else{
            int i;
            switch(Sday)
            {
                case "Mon":
                    res.moveToFirst();
                    i=0;
                    do {
                        arr[i++]=res.getString(3);

                        //Log.d("MYLOG",arr[i]);
                    }while(res.moveToNext());
                    break;
                case "Tue":
                    res.moveToFirst();
                    i=0;
                    do {
                        arr[i++]=res.getString(4);
                    }while(res.moveToNext());
                    break;
                case "Wed":
                    res.moveToFirst();
                    i=0;
                    do {
                        arr[i++]=res.getString(5);
                    }while(res.moveToNext());
                    break;
                case "Thu":
                    res.moveToFirst();
                    i=0;
                    do {
                        arr[i++]=res.getString(6);
                    }while(res.moveToNext());
                    break;
                case "Fri":
                    res.moveToFirst();
                    i=0;
                    do {
                        arr[i++]=res.getString(7);
                    }while(res.moveToNext());
                    break;
                case "Sat":
                    res.moveToFirst();
                    i=0;
                    do {
                        arr[i++]=res.getString(8);
                    }while(res.moveToNext());
                    break;
                default:

            }
        }

    }

    public void  action_bar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2B1C27")));

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.titleview, null);

        TextView actionbar_title = (TextView)v.findViewById(R.id.actionbar_title);
        actionbar_title.setText("Nerd");
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chip.ttf");
        actionbar_title.setTypeface(font);

        actionBar.setCustomView(v);
    }
}









