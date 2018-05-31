package com.nilesh.nsec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.solver.SolverVariable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import jxl.*;

public class Routine extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String section;
    char ch;

    ListView lv;
    NavigationView navigationView;
    String Sday;
    DatabaseHelper myDB;
    ArrayList<HashMap<String, String>> DataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        action_bar();
        myDB = new DatabaseHelper(this);
        DataList = new ArrayList<>();
        lv=(ListView) findViewById(R.id.lv1);
        geel();
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        section = sp.getString("section", null);
        ch=section.charAt(0);
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(parentLayout, "Section: "+section, Snackbar.LENGTH_LONG)
                .setAction("Register Here", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Routine.this, login.class);
                        startActivity(i);
                    }
                });

        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();

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
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.routine, menu);

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
        }else if (id == R.id.share) {
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
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

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


    public void geel() {
        Cursor res = myDB.getAllData();
        if (res.getCount() == 0) {
            // show message
            //showMessage("Error","Nothing found");
           // Toast.makeText(this, "Nothing found", Toast.LENGTH_LONG).show();
        }
        else{

           switch(Sday)
           {
               case "Mon":
                   res.moveToFirst();
                   do {
                       String subject=res.getString(3);
                       String pos =res.getString(1);
                       String time=res.getString(2);
                       insertToList(subject,time,pos);
                   }while(res.moveToNext());
                   break;
               case "Tue":
                   res.moveToFirst();
                   do {
                       String subject=res.getString(4);
                       String pos =res.getString(1);
                       String time=res.getString(2);
                       insertToList(subject,time,pos);
                   }while(res.moveToNext());
                   break;
               case "Wed":
                   res.moveToFirst();
                   do {
                       String subject=res.getString(5);
                       String pos =res.getString(1);
                       String time=res.getString(2);
                       insertToList(subject,time,pos);
                   }while(res.moveToNext());
                   break;
               case "Thu":
                   res.moveToFirst();
                   do {
                       String subject=res.getString(6);
                       String pos =res.getString(1);
                       String time=res.getString(2);
                       insertToList(subject,time,pos);
                   }while(res.moveToNext());
                   break;
               case "Fri":
                   res.moveToFirst();
                   do {
                       String subject=res.getString(7);
                       String pos =res.getString(1);
                       String time=res.getString(2);
                       insertToList(subject,time,pos);
                   }while(res.moveToNext());
                   break;
               case "Sat":
                   res.moveToFirst();
                   do {
                       String subject=res.getString(8);
                       String pos =res.getString(1);
                       String time=res.getString(2);
                       insertToList(subject,time,pos);
                   }while(res.moveToNext());
                   break;
               default:


            }
        }
        ListAdapter adapter = new SimpleAdapter(Routine.this, DataList, R.layout.list_item, new String[]{"subject","time","position"}, new int[]{R.id.subject,R.id.time,R.id.magTxt});
        // updating listview
        lv.setAdapter(adapter);
    }



    public void insertToList(String subject,String time,String pos){
    HashMap<String, String> map = new HashMap<String, String>();
    // adding each child node to HashMap key => value
    map.put("subject", subject);
    map.put("time", time);
    map.put("position", pos);



    // adding HashList to ArrayList
    DataList.add(map);
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

    public void  action_bar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2B1C27")));

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.titleview, null);

        TextView actionbar_title = (TextView)v.findViewById(R.id.actionbar_title);

        Sday= getIntent().getStringExtra("day");

        if(Sday.equals("Mon")) {
            actionbar_title.setText("MONDAY");
        }
        else if(Sday.equals("Tue")) {
            actionbar_title.setText("TUESDAY");
        }
        else if(Sday.equals("Wed")) {
            actionbar_title.setText("WEDNESDAY");
        }
        else if(Sday.equals("Thu")) {
            actionbar_title.setText("THURSDAY");
        }
        else if(Sday.equals("Fri")) {
            actionbar_title.setText("FRIDAY");
        }
        if(Sday.equals("Sat")) {
            actionbar_title.setText("SATURDAY");

        }
        else if(Sday.equals("Sun")) {
            actionbar_title.setText("SUNDAY");

        }

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chip.ttf");
        actionbar_title.setTypeface(font);

        actionBar.setCustomView(v);
    }





}
