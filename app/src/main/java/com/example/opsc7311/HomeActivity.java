package com.example.opsc7311;


import androidx.cardview.widget.CardView;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends MainLayout implements PopupMenu.OnMenuItemClickListener  {

    RecyclerView recyclerView;

    String Test[] = {"one,two,three,four"};

    Content _currentContent;
    //LinearLayout _layout;
    TableLayout lay;
    Dialog _dialog;

    List<Content> testContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SetUpConstants(findViewById((R.id.btnProfile_home)),findViewById((R.id.btnHome_home)),findViewById((R.id.btnDiscover_home)));

        _dialog = new Dialog(this);
        lay = findViewById(R.id.tblScroll_home);

        DisplayContent();

    }
    void DisplayContent(){
        Content Testcontent1 = new Content("Arobranch","A place in stellenbosch to climb a tree",R.mipmap.test5);
        Content Testcontent2 = new Content("CampsBay","A place in africa to commit tax fraud", R.mipmap.test2);
        Content Testcontent3 = new Content("KoelBay","A place on earth to get eaten by a shark", R.mipmap.test3);
        TableRow row = SetRow(Testcontent1,Testcontent2);

        lay.addView(row);


    }
    TableRow SetRow(Content content1, Content content2){
       TableRow row = new TableRow(this);

       SetContent(row,content1);
       SetContent(row,content2);

       return row;
    }

    CardView SetContent(TableRow row, Content content){
        CardView card = new CardView(this);
        row.addView(card);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)card.getLayoutParams();
        params.height = 900;
        params.width = 726;

        card.setRadius(30);
        params.setMargins(15,15,15,15);

        card.setLayoutParams(params);

        ImageView button = new ImageView(this);
        card.addView(button);
        button.setImageResource(content.imageID);

        button.setBackgroundColor(517782);

        button.setScaleType(ImageView.ScaleType.CENTER_CROP);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _currentContent = content;
                ShowSlider(v);
            }
        });

        return card;
    }

    void SetButton(ImageButton button,Content content){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)button.getLayoutParams();
        params.height = 900;
        params.width = 726;
        params.setMargins(15,15,15,15);


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
                        goal.contents.add(_currentContent);
                    }
                });
                scroller.addView(button);

            }

            btnAdd.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    _dialog.dismiss();
                    AddGoalForm();

                }
            });
        }else
        {
            for (Category category: Profile.getInstance().categories
            ) {
                Button button = new Button(_dialog.getContext());
                button.setText(category.name);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this,"added to " + txtTodo,Toast.LENGTH_SHORT).show();
                        category.contents.add(_currentContent);
                    }
                });
                scroller.addView(button);

            }
            btnAdd.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    _dialog.dismiss();
                    AddCategoryForm();

                }
            });
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
                    Category category = new Category(name,description, Color.valueOf(0x7FFF62),R.mipmap.rocket);
                    category.contents.add(_currentContent);
                    Profile.getInstance().categories.add(category);
                   _dialog.dismiss();
               }
            }
        });

        _dialog.show();

    }
    void AddGoalForm(){
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
