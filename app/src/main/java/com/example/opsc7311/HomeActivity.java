package com.example.opsc7311;


import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends MainLayout implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;

    String Test[] = {"one,two,three,four"};

    Content _currentContent;
    ImageView _btnCamera;
    TableLayout lay;
    Dialog _dialog;

    List<Content> testContents = new ArrayList<Content>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SetUpConstants(findViewById((R.id.btnProfile_home)), findViewById((R.id.btnHome_home)), findViewById((R.id.btnDiscover_home)));

        _dialog = new Dialog(this);
        lay = findViewById(R.id.tblScroll_home);
        _btnCamera = findViewById(R.id.btn_close_view_popup_menu);

        TextView helloUser = findViewById(R.id.txtHelloUser_ProfilePage);
        helloUser.setText("Hello \n" + Profile.getInstance().name);

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA)!=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }

        _btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });

        TestContent();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==100){
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");

        CapturePicture(bitmap);

        }
    }

    void CapturePicture(Bitmap bitmap){
        _dialog.dismiss();
        _dialog.setContentView(R.layout.capture_image);

        ImageView image = _dialog.findViewById(R.id.imgView_capture_image);
        EditText edtTitle = _dialog.findViewById(R.id.edtImageTitle_capture_image);
        EditText edtDescription = _dialog.findViewById(R.id.edtDescription_capture_image);

        image.setImageBitmap(bitmap);

        ImageView btnClose = _dialog.findViewById(R.id.btnClose_capture_image);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });

        ImageView btnAdd =_dialog.findViewById(R.id.btnAdd_capture_image);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String description = edtDescription.getText().toString();

                if (title.equals("") || description.equals("")){
                    Toast.makeText(HomeActivity.this, "Please provide a title and a description", Toast.LENGTH_SHORT).show();
                }else {
                    Content newContent = new Content(title, "A place in stellenbosch to climb a tree", bitmap);
                    testContents.add(newContent);
                    _dialog.dismiss();
                    LoadContent();
                }
            }
        });

        _dialog.show();

    }

    void TestContent() {
        Drawable drawable = getResources().getDrawable(R.drawable.test5);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        Content Testcontent1 = new Content("Arobranch", "A place in stellenbosch to climb a tree", bitmap);

        testContents.add(Testcontent1);

        drawable = getResources().getDrawable(R.drawable.test2);
         bitmap = ((BitmapDrawable)drawable).getBitmap();
        Content Testcontent2 = new Content("CampsBay", "A place in africa to commit tax fraud", bitmap);

        testContents.add(Testcontent2);

        drawable = getResources().getDrawable(R.drawable.test3);
        bitmap = ((BitmapDrawable)drawable).getBitmap();
        Content Testcontent3 = new Content("KoelBay", "A place on earth to get eaten by a shark", bitmap);

        testContents.add(Testcontent3);
        //TableRow row = SetRow(Testcontent1, Testcontent2);
        LoadContent();
        //lay.addView(row);


    }

    void LoadContent() {
        lay.removeAllViews();
        TableRow row = new TableRow(this);
        int i=0;
        for (Content content: testContents
             ) {
            i++;
            SetContent(row, content);

            if (i%2==0){
                lay.addView(row);
                row = new TableRow(this);
            }else {
                if (i==testContents.size()){
                    lay.addView(row);
                }
            }
        }
        //SetContent(row, content1);
        //SetContent(row, content2);

    }

    TableRow SetContent(TableRow row, Content content) {
        CardView card = new CardView(this);
        row.addView(card);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) card.getLayoutParams();
        params.height = 900;
        params.width = 700;

        card.setRadius(30);
        params.setMargins(15, 15, 15, 15);

        card.setLayoutParams(params);

        ImageView button = new ImageView(this);
        card.addView(button);
        button.setImageBitmap(content.image);
        //button.setImageResource(content.imageID);

        button.setBackgroundColor(517782);

        button.setScaleType(ImageView.ScaleType.CENTER_CROP);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _currentContent = content;
                ShowSlider(v);
            }
        });

        return row;
    }


    void ShowSlider(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.slider_down_menu);
        popup.show();
    }

    void ShowViewPopUp() {

        _dialog.dismiss();

        _dialog.setContentView(R.layout.view_popup_menu);

        ImageView btnClose = _dialog.findViewById(R.id.btn_close_view_popup_menu);
        ImageView image = _dialog.findViewById(R.id.imgView_view_popup_menu);
        ImageView btnAddToCat = _dialog.findViewById(R.id.btnAddToCat_view_popup_menu);
        ImageView btnAddToGoal = _dialog.findViewById(R.id.btnAddToGoal_view_popup_menu);
        TextView title = _dialog.findViewById(R.id.txtTitle_view_popup_menu);
        TextView description = _dialog.findViewById(R.id.txtDescription_view_popup_menu);

        image.setImageBitmap(_currentContent.image);
        title.setText(_currentContent.name);
        description.setText(_currentContent.description);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });

        btnAddToCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSavePopUp("Category");
            }
        });
        btnAddToGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSavePopUp("Goal");
            }
        });

        _dialog.show();

    }

    void ShowSavePopUp(String action) {
        _dialog.dismiss();
        _dialog.setContentView(R.layout.save_popup_menu);

        ImageView btnView;
        ImageView btnClose =  _dialog.findViewById(R.id.btnClose_save_popup_menu);

        ImageView imgImage = _dialog.findViewById(R.id.imgView_save_popup_menu);
        TextView txtContentName = _dialog.findViewById(R.id.txtContentName_save_popup_menu);
        TextView txtTitle = _dialog.findViewById(R.id.txtAction_save_popup_menu);
        TextView txtDescription = _dialog.findViewById(R.id.txtContentDescription_save_popup_menu);

        TextView txtTodo = _dialog.findViewById(R.id.txtRequiredAction_save_popup_menu);
        ImageView btnAdd = _dialog.findViewById(R.id.btnAdd_save_popup_menu);

        LinearLayout scroller = _dialog.findViewById(R.id.llScroll_save_popup_menu);

        if (action.equals("Goal")) {
            for (Goal goal : Profile.getInstance().goals
            ) {
                Button button = new Button(_dialog.getContext());
                button.setText(goal.name);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "Added to " + action, Toast.LENGTH_SHORT).show();
                        goal.contents.add(_currentContent);
                    }
                });
                btnView = _dialog.findViewById(R.id.btnAddToGoal_view_popup_menu);
                btnView.setImageResource(R.mipmap.eye);


                btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowViewPopUp();
                    }
                });

                ImageView btnCategory = _dialog.findViewById(R.id.btnAddToCat_view_popup_menu);
                btnCategory.setImageResource(R.mipmap.pushpin);
                btnCategory.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ShowSavePopUp("Category");
                    }
                });
                btnAdd.setImageResource(R.mipmap.target);
                scroller.addView(button);

            }

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _dialog.dismiss();
                    AddGoalForm();
                }
            });
        } else {
            for (Category category : Profile.getInstance().categories
            ) {
                Button button = new Button(_dialog.getContext());
                button.setText(category.name);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "Added to " + action, Toast.LENGTH_SHORT).show();
                        category.contents.add(_currentContent);
                    }
                });
                btnView = _dialog.findViewById(R.id.btnAddToCat_view_popup_menu);
                btnView.setImageResource(R.mipmap.eye);
                btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowViewPopUp();
                    }
                });

                btnAdd.setImageResource(R.mipmap.pushpin);
                ImageView btnGoal = _dialog.findViewById(R.id.btnAddToGoal_view_popup_menu);
                btnGoal.setImageResource(R.mipmap.target);
                btnGoal.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ShowSavePopUp("Goal");
                    }
                });
                scroller.addView(button);
            }

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _dialog.dismiss();
                    AddCategoryForm();

                }
            });
        }


        imgImage.setImageBitmap(_currentContent.image);

        txtDescription.setText(_currentContent.description);
        txtContentName.setText(_currentContent.name);

        txtTodo.setText("Select a " + action);
        txtTitle.setText("Add to " + action);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });


        _dialog.show();
    }

    void AddCategoryForm() {
        //Dialog dialog = new Dialog(this);
        _dialog.setContentView(R.layout.add_category_form);


        EditText edtName = _dialog.findViewById(R.id.edtName_add_category_form);
        EditText edtDescription = _dialog.findViewById(R.id.edtName_add_category_form);
        Button btnAdd = _dialog.findViewById(R.id.btnAdd_add_category_form);
        ImageButton btnClose = _dialog.findViewById(R.id.btnClose_add_category_popup_menu);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(HomeActivity.this, "enter a name ", Toast.LENGTH_SHORT).show();
                } else if (description.equals("")) {
                    Toast.makeText(HomeActivity.this, "enter a description ", Toast.LENGTH_SHORT).show();
                } else {
                    Category category = new Category(name, description, Color.valueOf(0x7FFF62), R.mipmap.rocket);
                    category.contents.add(_currentContent);
                    Profile.getInstance().categories.add(category);
                    _dialog.dismiss();
                }
            }
        });

        _dialog.show();

    }

    void AddGoalForm() {
        //Dialog dialog = new Dialog(this);
        _dialog.setContentView(R.layout.add_category_form);


        EditText edtName = _dialog.findViewById(R.id.edtName_add_category_form);
        EditText edtDescription = _dialog.findViewById(R.id.edtName_add_category_form);
        Button btnAdd = _dialog.findViewById(R.id.btnAdd_add_category_form);
        ImageButton btnClose = _dialog.findViewById(R.id.btnClose_add_category_popup_menu);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String description = edtDescription.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(HomeActivity.this, "enter a name ", Toast.LENGTH_SHORT).show();
                } else if (description.equals("")) {
                    Toast.makeText(HomeActivity.this, "enter a description ", Toast.LENGTH_SHORT).show();
                } else {
                    Category category = new Category(name, description, Color.valueOf(0x7FFF62), R.mipmap.rocket);
                    Profile.getInstance().categories.add(category);
                    _dialog.dismiss();
                }
            }
        });

        _dialog.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1://save to category
                ShowSavePopUp("Category");
                return true;
            case R.id.item2://add to goal
                ShowSavePopUp("Goal");
                return true;
            default:
                ShowViewPopUp();
                return false;

        }
    }

}
