package com.example.opsc7311;


import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends MainLayout implements PopupMenu.OnMenuItemClickListener  {

    Content _currentContent;
    LinearLayout _layout;
    Dialog _dialog;

    List<Content> testContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SetUpConstants(findViewById((R.id.btnProfile_home)),findViewById((R.id.btnHome_home)),findViewById((R.id.btnDiscover_home)));
        _dialog = new Dialog(this);
        Content Testcontent1 = new Content("Arobranch","A place in stellenbosch to climb a tree", R.drawable.five);
        Content Testcontent2 = new Content("CampsBay","A place in africa to commit tax fraud", R.drawable.four);
        Content Testcontent3 = new Content("KoelBay","A place on earth to get eaten by a shark", R.drawable.two);
        _layout = findViewById(R.id.llScroll_home);
        LoadContent(Testcontent1);
        LoadContent(Testcontent2);
        LoadContent(Testcontent3);
    }

   void LoadContent(Content content){
       ConstraintLayout Mylayout = (ConstraintLayout) findViewById(R.id.mylayout);
       ImageButton button1=new ImageButton(this);

       button1.setImageResource(content.imageID);

       _layout.addView(button1);

       LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)button1.getLayoutParams();
       params.height = 900;
       params.width = 726;

       button1.setBackgroundColor(517782);
       button1.setLayoutParams(params);
       button1.setScaleType(ImageView.ScaleType.FIT_XY);

       button1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              _currentContent = content;
              ShowSlider(v);
          }
      });

       /*TextView text = new TextView(this);
       text.setText("Added tv");
       _layout.addView(text);*/
    }

    void ShowSlider(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.slider_down_menu);
        popup.show();
    }
    void ShowDisplayPopUp(View v){
        ImageButton btnClose;
        ImageView image;
        TextView description;
        TextView title;
        _dialog.setContentView(R.layout.display__popup_menu);
        btnClose = _dialog.findViewById(R.id.btnClose_display__popup_menu);
        image = _dialog.findViewById(R.id.imgView_display__popup_menu);
        title = _dialog.findViewById(R.id.edtTitle_display__popup_menu);
        description = _dialog.findViewById(R.id.txtDescription_display__popup_menu);

        image.setImageResource(_currentContent.imageID);
        title.setText(_currentContent.name);
        description.setText(_currentContent.description);

        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });
        _dialog.show();

    }
    void ShowCategoryPopUp(View v){

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1://save to category
                Toast.makeText(this,"Item 1 clicked" + _currentContent.name, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2://add to goal
                Toast.makeText(this,"Item 2 clicked"+ _currentContent.name, Toast.LENGTH_SHORT).show();
                return true;
            default:
                ShowDisplayPopUp(item.getActionView());
                return false;

        }
    }

}
