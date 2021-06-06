package com.example.opsc7311;


import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends MainLayout implements PopupMenu.OnMenuItemClickListener  {

    RecyclerView recyclerView;

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
        Content Testcontent1 = new Content("Arobranch","A place in stellenbosch to climb a tree",R.mipmap.test5);
        Content Testcontent2 = new Content("CampsBay","A place in africa to commit tax fraud", R.mipmap.test2);
        Content Testcontent3 = new Content("KoelBay","A place on earth to get eaten by a shark", R.mipmap.test3);
        _layout = findViewById(R.id.llScroll_home);
        LoadContent(Testcontent1);
        LoadContent(Testcontent2);
        LoadContent(Testcontent3);

    }

   void LoadContent(Content content){

       ImageButton button = new ImageButton(this);

       button.setImageResource(content.imageID);

       _layout.addView(button);

       LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)button.getLayoutParams();
       params.height = 900;
       params.width = 726;

       button.setBackgroundColor(517782);
       button.setLayoutParams(params);
       button.setScaleType(ImageView.ScaleType.FIT_XY);

       button.setOnClickListener(new View.OnClickListener() {
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

    void ShowSavePopUp( String Todo)
    {
        _dialog.setContentView(R.layout.save_popup_menu);

        ImageButton btnClose    =   _dialog.findViewById(R.id.btnClose_save_popup_menu);
        ImageView imgImage    =   _dialog.findViewById(R.id.imgView_save_popup_menu);
        TextView txtTitle    =   _dialog.findViewById(R.id.txtTitle_save_popup_menu);
        TextView txtTodo     =   _dialog.findViewById(R.id.txtTodo_save_popup_menu);
        ImageButton btnAdd      =   _dialog.findViewById(R.id.btnAdd_save_popup_menu);

        LinearLayout scroller    =   _dialog.findViewById(R.id.llScroll_save_popup_menu);

        if (Todo.equals("goal"))
        {
            for (Goal goal: Profile.getInstance().goals
            ) {
                Button button = new Button(_dialog.getContext());
                button.setText(goal.name);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this,"added to " + txtTodo,Toast.LENGTH_SHORT).show();
                    }
                });
                scroller.addView(button);

            }
        }else
        {
            for (Category category: Profile.getInstance().categories
            ) {
                Button button = new Button(_dialog.getContext());
                button.setText(category.name);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this,"added to " + txtTodo,Toast.LENGTH_SHORT).show();
                    }
                });
                scroller.addView(button);

            }
        }


        imgImage.setImageResource(_currentContent.imageID);
        txtTitle.setText(_currentContent.name);
        txtTodo.setText("Select the " + Todo + " you would like to add this to");


        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
                AddCategoryForm();

            }
        });
        _dialog.show();
    }

    void AddCategoryForm(){
        //Dialog dialog = new Dialog(this);
        _dialog.setContentView(R.layout.add_category_form);


        EditText edtName = _dialog.findViewById(R.id.edtName_add_category_form);
        EditText edtDescription = _dialog.findViewById(R.id.edtName_add_category_form);
        Button btnAdd = _dialog.findViewById(R.id.btnAdd_add_category_form);
        ImageButton btnClose = _dialog.findViewById(R.id.btnClose_add_category_popup_menu);

        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();
               if (name.equals(""))
               {
                   Toast.makeText(HomeActivity.this,"enter a name ",Toast.LENGTH_SHORT).show();
                }else if(description.equals("")){
                   Toast.makeText(HomeActivity.this,"enter a description ",Toast.LENGTH_SHORT).show();
                }else{
                    Category category = new Category(name,description, Color.valueOf(0x7FFF62),R.mipmap.rocket);Profile.getInstance().categories.add(category);
                   _dialog.dismiss();
               }
            }
        });

        _dialog.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1://save to category
                ShowSavePopUp("category");
                return true;
            case R.id.item2://add to goal
                ShowSavePopUp("goal");
                return true;
            default:
                ShowViewPopUp(item.getActionView());
                return false;

        }
    }

}
