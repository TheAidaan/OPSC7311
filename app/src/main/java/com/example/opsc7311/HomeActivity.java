package com.example.opsc7311;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends MainLayout {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SetUpConstants(findViewById((R.id.btnProfile_home)),findViewById((R.id.btnHome_home)),findViewById((R.id.btnDiscover_home)));

    }

   void LoadContent(){
        //take content and put on screen
    }
}