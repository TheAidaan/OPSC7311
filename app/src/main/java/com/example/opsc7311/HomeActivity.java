package com.example.opsc7311;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;

public class HomeActivity extends MainLayout {

    Content content;
    List<Content> testContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SetUpConstants(findViewById((R.id.btnProfile_home)),findViewById((R.id.btnHome_home)),findViewById((R.id.btnDiscover_home)));

        //content = new Content((R.drawable.five),"","")
        LoadContent();
    }

   void LoadContent(){
       /*String uri = "@drawable/myresource";  // where myresource (without the extension) is the file

       int imageResource = getResources().getIdentifier(uri, null, getPackageName());

       imageview = (ImageView)findViewById(R.id.imageView);
       Drawable res = getResources().getDrawable(imageResource);
       imageView.setImageDrawable(res);*/

       ConstraintLayout Mylayout = (ConstraintLayout) findViewById(R.id.mylayout);
       ImageButton button1=new ImageButton(this);
       button1.setImageResource(R.drawable.five);

       Mylayout.addView(button1);

       ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)button1.getLayoutParams();
       params.height = 900;
       params.width = 726;

       params.gravity = Gravity.CENTER
       button1.setBackgroundColor(517782);
       button1.setLayoutParams(params);
       button1.setScaleType(ImageView.ScaleType.FIT_XY);


    }
}