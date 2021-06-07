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
import android.widget.StackView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends MainLayout {

    LinearLayout _layout;
    Dialog _dialog;
    StackView _stackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SetUpConstants(findViewById((R.id.btnProfile_profile)),findViewById((R.id.btnHome_profile)),findViewById((R.id.btnDiscover_profile)));

        _layout = findViewById(R.id.llScroll_profile);
        _dialog = new Dialog(this);
        _stackView = findViewById(R.id.stkView_profile);


        LoadGoalStack();
        LoadCategories();
    }
    void LoadGoalStack(){
        GoalStackAdapter adapter = new GoalStackAdapter(Profile.getInstance().goals,
        R.layout.goal_stack,ProfileActivity.this);
        _stackView.setAdapter(adapter);

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
                    OpenCategoryFolder(category);
                }
            });
        }
    }

    void OpenCategoryFolder(Category category){
        _dialog.setContentView(R.layout.open_folder);
        LinearLayout layout =_dialog.findViewById(R.id.llScroll_open_folder);

        for (Content content:
                category.contents
             ) {
            ImageView image = new ImageView(this);
            image.setImageResource(content.imageID);
            layout.addView(image);
        }

        TextView txtDescription = _dialog.findViewById(R.id.txtDescription_open_folder);
        txtDescription.setText(category.description);

        ImageButton btnClose = _dialog.findViewById(R.id.btnClose_open_folder);
        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();

            }
        });

        _dialog.show();
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