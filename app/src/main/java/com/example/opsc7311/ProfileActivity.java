package com.example.opsc7311;


import androidx.constraintlayout.widget.ConstraintLayout;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.StackView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileActivity extends MainLayout {

    TableLayout _layout;
    Dialog _dialog;
    StackView _stackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SetUpConstants(findViewById((R.id.btnProfile_profile)),findViewById((R.id.btnHome_profile)),findViewById((R.id.btnDiscover_profile)));

        _layout = findViewById(R.id.tblScroll_profile);
        _dialog = new Dialog(this);
        _stackView = findViewById(R.id.stkView_profile);



        TextView helloUser = findViewById(R.id.txtHelloUser_Profile);
        helloUser.setText("Hello \n" + Profile.getInstance().name);

        ImageButton btnSettings = findViewById(R.id.btnSettings_ProfilePage);
        btnSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ProfileActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });


        LoadGoalStack();
        LoadCategories();
    }
    void LoadGoalStack(){
        GoalStackAdapter adapter = new GoalStackAdapter(Profile.getInstance().goals,
        R.layout.goal_stack,ProfileActivity.this);
        _stackView.setAdapter(adapter);

    }
    void LoadCategories(){

        TableRow row = new TableRow(this);
        int i=0;
        for (Category category: Profile.getInstance().categories
             )
        {
            i++;

            View view = CreateCategoryFolder(row);

            TextView txtTitle = view.findViewById(R.id.txtLabel_category_folder);
            TextView txtProgress = view.findViewById(R.id.txtProgress_category_folder);
            ImageView imgIcon = view.findViewById(R.id.imgIcon_category_folder);

            //imgIcon.setBackgroundColor();

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
            if (i%2==0)
            {
                _layout.addView(row);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
                params.setMargins(60,0,30,5);
                row  = new TableRow(this);
            }else{
                if (Profile.getInstance().categories.size() == i){
                    _layout.addView(row);
                }else{
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
                    params.setMargins(30,0,60,5);
                }
            }

        }
    }
    View CreateCategoryFolder(TableRow row){
        ViewStub stub = new ViewStub(this);

        row.addView(stub);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)stub.getLayoutParams();
        params.height = 485;
        params.width = 535;

        stub.setLayoutResource(R.layout.category_folder);
        View inflated = stub.inflate();

        inflated.setElevation(10);

        return inflated;
    }

    void OpenCategoryFolder(Category category){
        _dialog.setContentView(R.layout.open_category_folder);
        LinearLayout layout =_dialog.findViewById(R.id.llContents_open_category_popup_menu);

        for (Content content:
                category.contents
             ) {
            ImageView image = new ImageView(this);
            image.setImageBitmap(content.image);
            layout.addView(image);
            image.setLayoutParams(new LinearLayout.LayoutParams(1120, 850));
            Toast.makeText(this, "done" , Toast.LENGTH_SHORT).show();
        }
        TextView txtTitle = _dialog.findViewById(R.id.txtTitle_open_category_popup_menu);
        TextView txtDescription = _dialog.findViewById(R.id.txtDescription_open_category_popup_menu);
        txtDescription.setText(category.description);

        txtTitle.setText(category.name);
         ImageView btnClose = _dialog.findViewById(R.id.btn_close_open_category_popup_menu);
        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();

            }
        });

        _dialog.show();
    }



 }