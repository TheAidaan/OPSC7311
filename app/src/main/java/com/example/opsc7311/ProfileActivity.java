package com.example.opsc7311;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ProfileActivity extends MainLayout {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SetUpConstants(findViewById((R.id.btnProfile_profile)),findViewById((R.id.btnHome_profile)),findViewById((R.id.btnDiscover_profile)));
    }


}