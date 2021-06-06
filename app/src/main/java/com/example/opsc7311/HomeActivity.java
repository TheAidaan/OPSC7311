package com.example.opsc7311;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    String Test[] = {"one,two,three,four"};

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
        Content Testcontent1 = new Content("Arobranch","A place in stellenbosch to climb a tree",R.mipmap.test2);
        Content Testcontent2 = new Content("CampsBay","A place in africa to commit tax fraud", R.mipmap.test2);
        Content Testcontent3 = new Content("KoelBay","A place on earth to get eaten by a shark", R.mipmap.test3);
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
    void ShowViewPopUp(View v){
        ImageButton btnClose;
        ImageView image;
        TextView description;
        TextView title;

        _dialog.setContentView(R.layout.view_popup_menu);

        btnClose = _dialog.findViewById(R.id.btnClose_view_popup_menu);
        image = _dialog.findViewById(R.id.imgView_view_popup_menu);
        title = _dialog.findViewById(R.id.txtTitle_view_popup_menu);
        description = _dialog.findViewById(R.id.txtDescription_view_popup_menu);

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

    void ShowSavePopUp(View v, String Todo){
        ImageButton btnClose;
        ImageView image;
        TextView title,todo;

        _dialog.setContentView(R.layout.save_popup_menu);

        btnClose = _dialog.findViewById(R.id.btnClose_save_popup_menu);
        image = _dialog.findViewById(R.id.imgView_save_popup_menu);
        title = _dialog.findViewById(R.id.txtTitle_save_popup_menu);
        todo = _dialog.findViewById(R.id.txtTodo_save_popup_menu);

        recyclerView = _dialog.findViewById(R.id.rclOptions_savePopup);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, Test);
        recyclerView.setAdapter(adapter);

        image.setImageResource(_currentContent.imageID);
        title.setText(_currentContent.name);
        todo.setText("Select the " + Todo + " you would like to add this to");


        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });
        _dialog.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1://save to category
                ShowSavePopUp(item.getActionView(),"category");
                return true;
            case R.id.item2://add to goal
                ShowSavePopUp(item.getActionView(),"goal");
                return true;
            default:
                ShowViewPopUp(item.getActionView());
                return false;

        }
    }

}
