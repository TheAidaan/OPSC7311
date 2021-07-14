package com.example.opsc7311;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends MainLayout implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;

    String Test[] = {"one,two,three,four"};

    ContentUploadHelperClass _currentContent;
    ImageView _btnCamera;
    TableLayout lay;
    Dialog _dialog;

    List<Content> testContents = new ArrayList<Content>();
    ActivityResultLauncher<Intent> activityResultLauncher;

    StorageReference _storageReference;
    DatabaseReference _databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SetUpConstants(findViewById((R.id.btnProfile_home)), findViewById((R.id.btnHome_home)), findViewById((R.id.btnDiscover_home)));

        _dialog = new Dialog(this);
        _storageReference = FirebaseStorage.getInstance().getReference("uploads");
        _databaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        ImageView image = findViewById(R.id.imgTest);

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA)!=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }

        _btnCamera = findViewById(R.id.btn_close_view_popup_menu);
        _btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultLauncher.launch(intent);
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle test = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap)test.get("data");
                    //Uri uri =result.getData().getData().normalizeScheme();

                    //image.setImageURI(uri);
                    //image.setImageBitmap(bitmap);

                    ByteArrayOutputStream bytes =new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,bytes);
                    String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),bitmap,"val",null);
                    Uri uri = Uri.parse(path);
                    image.setImageURI(uri);

                    //Picasso.get().load(uri).into(image);
                    //Toast.makeText(HomeActivity.this, "Done", Toast.LENGTH_SHORT).show();


                    //Bundle bundle = result.getData().getExtras();
                    //Bitmap bitmap  = (Bitmap) bundle.get("data");
                   // CapturePicture(bitmap);
                }
            } // Camera
        });

        lay = findViewById(R.id.tblScroll_home);

        LoadContent();

    }
    // upload a captured image
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
                    Content newContent = new Content(title, description, bitmap);
                    testContents.add(newContent);
                    _dialog.dismiss();
                    LoadContent();
                }
            }
        });

        _dialog.show();

    }
    void UploadFile(String name, String description, Uri UploadUri){
        StorageReference fileReference = _storageReference.child(System.currentTimeMillis() + "."+ getFileExtension(UploadUri));
        fileReference.putFile(UploadUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String uploadID = _databaseReference.push().getKey();
                        ContentUploadHelperClass upload = new ContentUploadHelperClass(uploadID,name, description,uri.toString(),false);
                        _databaseReference.child(uploadID).setValue(upload);

                        Toast.makeText(HomeActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, "Upload was Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //la la la la
            }
        });

    }
    String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    //end of uploading a picture from captured image

    //load content
    void LoadContent(){
        List<ContentUploadHelperClass> uploads = new ArrayList<ContentUploadHelperClass>();
        _databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot postSnapchat : snapshot.getChildren() ){
                    ContentUploadHelperClass upload = postSnapchat.getValue(ContentUploadHelperClass.class);
                    uploads.add(upload);
                }
                ArrangeContents(uploads);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void ArrangeContents(List<ContentUploadHelperClass> contents) {

        lay.removeAllViews();
        TableRow row = new TableRow(this);
        int i=0;
        for (ContentUploadHelperClass content: contents
             ) {
            i++;
            SetContent(row, content);

            if (i%2==0){
                lay.addView(row);
                row = new TableRow(this);
            }else {
                if (i==contents.size()){
                    lay.addView(row);
                }
            }
        }

    }

    TableRow SetContent(TableRow row, ContentUploadHelperClass content) {
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

        Picasso.get().load(content.getImageUrl()).into(button);

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

    //end of load content

    //clicking on a content
    void ShowSlider(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.slider_down_menu);
        popup.show();
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

    //end of clicking on content


    //popups
    void ShowViewPopUp() {

        _dialog.dismiss();

        _dialog.setContentView(R.layout.view_popup_menu);

        ImageView btnClose = _dialog.findViewById(R.id.btn_close_view_popup_menu);
        ImageView image = _dialog.findViewById(R.id.imgView_view_popup_menu);
        ImageView btnAddToCat = _dialog.findViewById(R.id.btnAddToCat_view_popup_menu);
        ImageView btnAddToGoal = _dialog.findViewById(R.id.btnAddToGoal_view_popup_menu);
        TextView title = _dialog.findViewById(R.id.txtTitle_view_popup_menu);
        TextView description = _dialog.findViewById(R.id.txtDescription_view_popup_menu);

        Picasso.get().load(_currentContent.getImageUrl()).into(image);
        title.setText(_currentContent.getName());
        description.setText(_currentContent.getDescription());

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

            if(Profile.getInstance().getCategories().size()==0)
                txtTodo.setText("Please create a category first");
            else
                txtTodo.setText("Select a " + action);


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

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _dialog.dismiss();
                    AddGoalForm();
                }
            });

            for (Goal goal : Profile.getInstance().goals
            ) {
                Button button = new Button(_dialog.getContext());
                button.setText(goal.name);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "Added to " + action, Toast.LENGTH_SHORT).show();
                        //goal.contents.add(_currentContent);
                    }
                });
                scroller.addView(button);
            }
        } else {
            if(Profile.getInstance().getCategories().size()==0)
                txtTodo.setText("Please create a category first");
            else
                txtTodo.setText("Select a " + action);

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
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _dialog.dismiss();
                    AddCategoryForm();

                }
            });

            for (CategoryHelperClass category : Profile.getInstance().getCategories()
            ) {
                Button button = new Button(_dialog.getContext());
                button.setText(category.getName());
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Profile.getInstance().getReference().child("categories").child(category.getCategoryID()).child("contents").child(_currentContent.getContentID()).setValue(_currentContent.getContentID());

                        Toast.makeText(HomeActivity.this, "Added to "+category.getName(), Toast.LENGTH_SHORT).show();

                    }
                });
                scroller.addView(button);
            }

        }


        Picasso.get().load(_currentContent.imageUrl).into(imgImage);

        txtDescription.setText(_currentContent.description);
        txtContentName.setText(_currentContent.name);


        txtTitle.setText("Add to " + action);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });


        _dialog.show();
    }

    //end of popups

    void AddCategoryForm() {
        Dialog dialog = new Dialog(this);
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
                String name = edtName.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();
                if (name.equals("")) {
                    Toast.makeText(HomeActivity.this, "enter a name ", Toast.LENGTH_SHORT).show();
                } else if (description.equals("")) {
                    Toast.makeText(HomeActivity.this, "enter a description ", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(Profile.getInstance().username);
                    String categoryID = databaseReference.push().getKey();

                    CategoryHelperClass newCategory = new CategoryHelperClass(categoryID,name,description);
                    Profile.getInstance().getReference().child("categories").child(categoryID).setValue(newCategory);

                    Profile.getInstance().getReference().child("categories").child(categoryID).child("contents").child(_currentContent.contentID).setValue(_currentContent.contentID);


                    Toast.makeText(HomeActivity.this, "Added to new Category", Toast.LENGTH_SHORT).show();

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
                    //Category category = new Category(name, description, Color.valueOf(0x7FFF62), R.mipmap.rocket);
                    //Profile.getInstance().categories.add(category);
                    _dialog.dismiss();
                }
            }
        });

        _dialog.show();

    }




}
