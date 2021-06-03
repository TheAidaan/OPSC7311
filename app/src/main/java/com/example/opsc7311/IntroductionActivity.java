package com.example.opsc7311;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroductionActivity extends AppCompatActivity {
    private Button _signInWithEmail;
    private Button _signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        _signInWithEmail = findViewById((R.id.btnSignInWithEmail_Introduction));
        _signUp = findViewById((R.id.btnSignUp_Introduction));

        _signInWithEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SignInWithEmail();
            }
        });

        _signUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SignUp();
            }
        });


    }
    void SignInWithEmail() {
        Intent intent = new Intent(IntroductionActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    void SignUp() {
        Intent intent = new Intent(IntroductionActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}