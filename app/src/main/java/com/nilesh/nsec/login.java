package com.nilesh.nsec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    Spinner spin1, spin2;
    Button register;
    TextView textView1,textView2;
    ArrayAdapter<CharSequence> adapterIT,adapterCSE,adapterECE,adapterEE,adapterME,adapterBME,adapterAEIE,adapterCE;
    String str2 = "",branch="";
    int count=0;
    private Vibrator myVib;
    DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        myDB = new DatabaseHelper(this);
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        spin1 = (Spinner) findViewById(R.id.spinner);
        spin2 = (Spinner) findViewById(R.id.spinner2);
        register=(Button)findViewById(R.id.registerB);
        textView1=(TextView) findViewById(R.id.textView);
        textView2=(TextView) findViewById(R.id.textView2);
        adapterIT = ArrayAdapter.createFromResource(this, R.array.IT, android.R.layout.simple_spinner_item);
        adapterCSE = ArrayAdapter.createFromResource(this, R.array.CSE, android.R.layout.simple_spinner_item);
        adapterIT = ArrayAdapter.createFromResource(this, R.array.IT, android.R.layout.simple_spinner_item);
        adapterECE = ArrayAdapter.createFromResource(this, R.array.ECE, android.R.layout.simple_spinner_item);
        adapterEE = ArrayAdapter.createFromResource(this, R.array.EE, android.R.layout.simple_spinner_item);
        adapterME = ArrayAdapter.createFromResource(this, R.array.ME, android.R.layout.simple_spinner_item);
        adapterBME = ArrayAdapter.createFromResource(this, R.array.BME, android.R.layout.simple_spinner_item);
        adapterAEIE = ArrayAdapter.createFromResource(this, R.array.AEIE, android.R.layout.simple_spinner_item);
        adapterCE = ArrayAdapter.createFromResource(this, R.array.CE, android.R.layout.simple_spinner_item);



        adapterIT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCSE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterECE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterEE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterME.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterBME.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterAEIE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chip.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/zekton.ttf");
        register.setTypeface(font2);
        textView2.setTypeface(font);
        textView1.setTypeface(font);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(spin1.getItemAtPosition(i).toString())
                {
                    case "CSE(Computer Science)":
                        spin2.setAdapter(adapterCSE);
                        break;
                    case "IT(Information Technology)":
                        spin2.setAdapter(adapterIT);
                        break;
                    case "ECE(Electronics & Communication Eng.)":
                        spin2.setAdapter(adapterECE);
                        break;
                    case "EE(Electrical Eng.)":
                        spin2.setAdapter(adapterEE);
                        break;
                    case "ME(Mechanical Eng.)":
                        spin2.setAdapter(adapterME);
                        break;
                    case "BME(Bio Medical Eng.)":
                        spin2.setAdapter(adapterBME);
                        break;
                    case "AEIE(Applied Electronics &amp; Instru. Eng.)":
                        spin2.setAdapter(adapterAEIE);
                        break;
                    case "CE(Civil Eng.)":
                        spin2.setAdapter(adapterCE);
                        break;


                }

                branch = spin1.getItemAtPosition(i).toString();
                //Log.d("MYLOG",branch);
                for(i=0;i<branch.length();i++){
                    if(branch.charAt(i)=='(')
                    {
                        branch =branch.substring(0,i);
                        //Log.d("MYLOG",branch);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str2 = spin2.getItemAtPosition(i).toString();
                str2=str2.substring(6,str2.length());
               // Log.d("MYLOG",str2);
                //Toast.makeText(getBaseContext(),str2,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void register(View v) {

        myVib.vibrate(20);
        count++;
        myDB.deleteAll();
        SharedPreferences sp = getSharedPreferences("request", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("toInsert", 0);
        editor.apply();
        //Toast.makeText(getBaseContext(),str2,Toast.LENGTH_LONG).show();
        SharedPreferences sp1 = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sp1.edit();
        editor1.putString("section", str2);
        editor1.putString("branch",branch);
        editor1.putInt("counter",count);
        editor1.apply();

        Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show();

        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        
    }



}