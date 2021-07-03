package com.example.opsc7311;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    EditText _edtUsername,_edtPassword;
    Button _btnLogin;
    TextView _txtIncorrectInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin_in);


        _edtUsername = findViewById((R.id.edtEmailAdress_SignIn));
        _edtPassword = findViewById((R.id.edtPassword_SignIn));
        _btnLogin = findViewById((R.id.btnLogin_SignIn));
        _txtIncorrectInput = findViewById(R.id.txtIncorrectInput_SignIn);

        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidateInput(_edtUsername.getText().toString(), _edtPassword.getText().toString());
            }
        });

        TextView btnBack = findViewById((R.id.txtBack_SignIn));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SignInActivity.this,IntroductionActivity.class);
                startActivity(intent);
            }
        });


    }
    void ValidateInput(String enteredUsername,String enteredPassword){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(enteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String databasePassword = snapshot.child(enteredUsername).child("password").getValue(String.class);

                    if (databasePassword.equals(enteredPassword)){
                        String databaseName = snapshot.child("username").child("name").getValue(String.class);
                        String databaseUsername = snapshot.child("username").child("username").getValue(String.class);
                        String databaseEmail = snapshot.child("username").child("email").getValue(String.class);

                        Profile.getInstance().setName(databaseName);
                        Profile.getInstance().setName(databaseUsername);
                        Profile.getInstance().setName(databaseEmail);
                        Profile.getInstance().setName(enteredPassword);

                        CreateTestValues();

                        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                        startActivity(intent);

                    }else
                    {
                        _edtPassword.setFocusable(true);
                        _edtPassword.setFocusableInTouchMode(true);///add this line
                        _edtPassword.requestFocus();

                        _edtPassword.setBackgroundResource(R.drawable.incorrect_input);

                        _txtIncorrectInput.setText("Incorrect Password");

                    }
                }else{

                    _edtUsername.setFocusable(true);
                    _edtUsername.setFocusableInTouchMode(true);
                    _edtUsername.requestFocus();

                    _edtUsername.setBackgroundResource(R.drawable.incorrect_input);

                    _txtIncorrectInput.setText("Invalid Username");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void CreateTestValues(){

            // test values
            Profile.getInstance().name = "Triangle";
            Profile.getInstance().username = "theShape";
            Drawable drawable = getResources().getDrawable(R.drawable.test1);
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            Content Testcontent4 = new Content("Injury", "I got hurt here", bitmap);
            drawable = getResources().getDrawable(R.drawable.test4);
            bitmap = ((BitmapDrawable)drawable).getBitmap();
            Content Testcontent5 = new Content("N1", "driver", bitmap);

            Goal goal = new Goal("eastSide", Color.valueOf(0xFFAB1E));
            goal.contents.add(Testcontent4);
            Category category = new Category("cape town", "place to go when i see it", Color.valueOf(0xBC62FF), R.mipmap.sun);
            category.contents.add(Testcontent5);

            Profile.getInstance().goals.add(goal);
            Profile.getInstance().categories.add(category);
            //

    }
}