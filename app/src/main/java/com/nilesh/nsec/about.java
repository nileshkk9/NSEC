package com.nilesh.nsec;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class about extends AppCompatActivity {
    Vibrator myVib;
    TextView name,stream,number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/zekton.ttf");
        myVib=(Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        name=(TextView) findViewById(R.id.name);
        stream=(TextView) findViewById(R.id.stream);
        number=(TextView) findViewById(R.id.number);

        name.setTypeface(font2);
        stream.setTypeface(font2);
        number.setTypeface(font2);

    }

    public void onClickY(View v)
    {
        myVib.vibrate(20);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.youtube.com/channel/UCYoqyESCAZJJ2a1gKQW_2xw"));
        startActivity(intent);
    }

    public void onClickF(View v)
    {   myVib.vibrate(20);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.facebook.com/nileshkk9"));
        startActivity(intent);
    }

    public void onClickN(View v)
    {   myVib.vibrate(20);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.nsec.ac.in/"));
        startActivity(intent);
    }

    public void dial(View v)
    {    myVib.vibrate(20);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+918444865679"));
        startActivity(intent);
    }

    public void full(View v)
    {   myVib.vibrate(20);
        Toast.makeText(this,"Contact Me If Change In Routine Is Required Or Any Minor Bug Is Found;-)",Toast.LENGTH_LONG).show();
    }

}
