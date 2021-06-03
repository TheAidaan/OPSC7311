package com.example.opsc7311;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {
    private EditText _email;
    private EditText _password;
    private Button _login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        _email = findViewById((R.id.edtEmailAdress_SignIn));
        _password = findViewById((R.id.edtPassword_SignIn));
        _login = findViewById((R.id.btnLogin_SignIn));

        _login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate(_email.getText().toString(), _password.getText().toString());
            }
        });

    }

    void Validate(String email, String password){
        if ((email.equals("admin" )) && ( password .equals("admin"))){
            Intent intent= new Intent(SignInActivity.this,HomeActivity.class);
            startActivity(intent);
        }else
        {
            //highlight everything in red and put text under password saying"incorrect password or email"
        }

    }
}