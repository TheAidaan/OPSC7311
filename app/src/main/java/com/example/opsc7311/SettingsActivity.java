package com.example.opsc7311;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    Dialog _dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView btnBack = findViewById((R.id.txtBack_Settings));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        Button btnProfileSettings = findViewById((R.id.btnEditProfile_Settings));
        btnProfileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog = new Dialog(SettingsActivity.this);
                _dialog.setContentView(R.layout.edit_profile);

                TextView btnBack = _dialog.findViewById((R.id.txtBack_ProfileSettings));
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _dialog.dismiss();
                    }
                });
            }
        });

        Button btnLogOut = findViewById((R.id.btnLogOut_Settings));
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, IntroductionActivity.class);
                startActivity(intent);
                }
            });

    }
}