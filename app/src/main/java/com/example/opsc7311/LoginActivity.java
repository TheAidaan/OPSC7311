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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout _txtLUsername,_txtLPassword;
    Button _btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        _txtLUsername = findViewById((R.id.txtLUsername_SignIn));
        _txtLPassword = findViewById((R.id.txtLPassword_SignIn));
        _btnLogin = findViewById((R.id.btnLogin_SignIn));

        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String enteredUsername = _txtLUsername.getEditText().getText().toString().trim();
                String enteredPassword = _txtLPassword.getEditText().getText().toString().trim();

                if(!enteredUsername.isEmpty())
                    ValidateInput(enteredUsername, enteredPassword);
                else
                    _txtLUsername.setError("Field can not be empty");



            }
        });

        TextView btnBack = findViewById((R.id.txtBack_SignIn));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this,IntroductionActivity.class);
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
                    _txtLUsername.setError(null);
                    String databasePassword = snapshot.child(enteredUsername).child("password").getValue(String.class);

                    if (databasePassword.equals(enteredPassword)){
                        String databaseName = snapshot.child(enteredUsername).child("name").getValue(String.class);
                        String databaseUsername = snapshot.child(enteredUsername).child("username").getValue(String.class);
                        String databaseEmail = snapshot.child(enteredUsername).child("email").getValue(String.class);


                        Profile.getInstance().setName(databaseName);
                        Profile.getInstance().setUsername(databaseUsername);
                        Profile.getInstance().setEmail(databaseEmail);
                        Profile.getInstance().setPassword(enteredPassword);
                        Profile.getInstance().setReference(FirebaseDatabase.getInstance().getReference("users").child(enteredUsername));


                        Profile.getInstance().getReference().child("categories").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Profile.getInstance().getCategories().clear();
                                for( DataSnapshot postSnapchat : snapshot.getChildren() ){
                                    CategoryHelperClass category = postSnapchat.getValue(CategoryHelperClass.class);
                                    Profile.getInstance().getCategories().add(category);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                        Profile.getInstance().getReference().child("goals").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                /*Profile.getInstance().getCategories().clear();
                                for( DataSnapshot postSnapchat : snapshot.getChildren() ){
                                    CategoryHelperClass category = postSnapchat.getValue(CategoryHelperClass.class);
                                    Profile.getInstance().getCategories().add(category);
                                }*/

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);

                        _txtLUsername.setError(null);

                    }else
                    {
                        _txtLPassword.setError("Incorrect Password");

                    }
                }else{

                    _txtLUsername.setError("Invalid Username");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}