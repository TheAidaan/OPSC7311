package com.example.opsc7311;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    TextView _txtName, _txtUsername, _txtEmail, _txtPassword, _txtPasswordConfirmation;
    String _name, _username, _email, _password;

    Boolean _validDetails = false;

    Boolean _validUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView btnBack = findViewById((R.id.btnBack_signUp));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, IntroductionActivity.class);
                startActivity(intent);
            }
        });

        _txtName = findViewById((R.id.edtName_signup));
        _txtUsername = findViewById((R.id.edtUsername_signup));
        _txtEmail = findViewById((R.id.edtEmailAdress_signup));
        _txtPassword = findViewById((R.id.edtPassword_signup));

        _txtPasswordConfirmation = findViewById((R.id.edtPasswordConfirmation_signup));

        Button btnSignUp = findViewById((R.id.btnSignUp_signup));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _name = _txtName.getText().toString();
                _username = _txtUsername.getText().toString();
                _email = _txtEmail.getText().toString();
                _password = _txtPassword.getText().toString();

                if (ValidInputs()) {
                    {
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("users");
                        reference.child(_username).setValue(CreateHelperClass());
                    }
                }
            }
        });
    }

    Boolean ValidInputs() {

        Boolean validName, validEmail, validPassword;
        if (_name.isEmpty()) {
            _txtName.setBackgroundResource(R.drawable.incorrect_input);
            _txtName.setError("Required field");
            validName = false;
        } else {
            _txtName.setBackgroundResource(R.drawable.edit_box);
            _txtPassword.setError(null);
            validName = true;
        }

        if (_username.isEmpty()) {
            _txtUsername.setBackgroundResource(R.drawable.incorrect_input);
            _txtUsername.setError("Required field");
            _validUsername = false;
        } else {
            _txtUsername.setBackgroundResource(R.drawable.edit_box);
            _txtPassword.setError(null);
            ValidateUsername();
        }

        if (_email.isEmpty()) {
            _txtEmail.setBackgroundResource(R.drawable.incorrect_input);
            _txtEmail.setError("Required field");
            validEmail = false;
        } else {
            _txtEmail.setBackgroundResource(R.drawable.edit_box);
            _txtPassword.setError(null);
            validEmail = ValidEmail();
        }

        if (_password.isEmpty()) {
            _txtPassword.setBackgroundResource(R.drawable.incorrect_input);
            _txtPassword.setError("Required field");
            validPassword = false;
        } else {
            _txtPassword.setBackgroundResource(R.drawable.edit_box);
            _txtPassword.setError(null);

            String passwordPattern = "^" + ".{6,}"+"$";
            if (_password.matches(passwordPattern))
                validPassword = ValidPassword();
            else
            {
                _txtPassword.setError("Password must be at least 6 characters long");
                validPassword = false;

            }


        }

        if (validEmail && validName && validPassword && _validUsername)
            return true;

        return false;
    }


    UserHelperClass CreateHelperClass() {

        UserHelperClass helperClass = new UserHelperClass(_name, _username, _email, _password);
        return helperClass;

    }

    void ValidateUsername() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(_username);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                _validUsername = true;
                if(snapshot.exists()){
                    _txtUsername.setError("Username is already taken");
                    _validUsername = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    Boolean ValidEmail() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
        if (!_email.matches(emailPattern)) {
            _txtPassword.setBackgroundResource(R.drawable.incorrect_input);
            _txtEmail.setError("Invalid email address");
            return false;
        } else {
            _txtEmail.setError(null);
            _validDetails = true;
            return true;
        }

    }

    Boolean ValidPassword() {

        String passwordConfirmation = _txtPasswordConfirmation.getText().toString();
        if (_password.equals(passwordConfirmation)) {
            return true;
        }
        else {
            _txtPasswordConfirmation.setError("Passwords do not match");
            return false;
        }

    }

}