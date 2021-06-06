package com.example.opsc7311;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends MainLayout {

    LinearLayout _layout;
    Dialog _dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SetUpConstants(findViewById((R.id.btnProfile_profile)),findViewById((R.id.btnHome_profile)),findViewById((R.id.btnDiscover_profile)));

        _layout = findViewById(R.id.llScroll_profile);
        _dialog = new Dialog(this);

        LoadCategories();
    }
    void LoadCategories(){

        for (Category category: Profile.getInstance().categories
             ) {
            View view = CreateCategoryFolder();

            TextView txtTitle = view.findViewById(R.id.txtLabel_category_folder);
            TextView txtProgress = view.findViewById(R.id.txtProgress_category_folder);
            ImageView imgIcon = view.findViewById(R.id.imgIcon_category_folder);

            ConstraintLayout folder = view.findViewById(R.id.category_folder);

            txtTitle.setText(category.name);
            imgIcon.setImageResource(category.iconId);
            txtProgress.setText("0/"+category.contents.size());

            folder.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProfileActivity.this,"clacked",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    View CreateCategoryFolder(){
        ViewStub stub = new ViewStub(this);
        _layout.addView(stub);

        ViewGroup.LayoutParams params = stub.getLayoutParams();
        params.height = 485;
        params.width = 535;

        stub.setPadding(100,100,100,100);


        stub.setLayoutResource(R.layout.category_folder);
        View inflated = stub.inflate();

        return inflated;
    }

 }