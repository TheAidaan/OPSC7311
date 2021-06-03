package com.example.opsc7311;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DiscoverActivity extends MainLayout {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        SetUpConstants(findViewById((R.id.btnProfile_discover)),findViewById((R.id.btnHome_discover)),findViewById((R.id.btnDiscover_discover)));
    }


}