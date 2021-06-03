package com.example.opsc7311;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public abstract class MainLayout extends AppCompatActivity {

    public void SetUpConstants(ImageButton _btnProfile,ImageButton _btnHome,ImageButton _btnDiscover){

        _btnProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GoToProfileLayout();
            }
        });
        _btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GoToHomeLayout();
            }
        });
        _btnDiscover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GoToDiscoverLayout();
            }
        });

    }
    public void GoToProfileLayout()
    {
        Intent intent = new Intent(MainLayout.this, ProfileActivity.class);
        startActivity(intent);
    }
    public void GoToHomeLayout()
    {
        Intent intent = new Intent(MainLayout.this, HomeActivity.class);
        startActivity(intent);
    }
    public void GoToDiscoverLayout()
    {
        Intent intent = new Intent(MainLayout.this, DiscoverActivity.class);
        startActivity(intent);
    }
}
