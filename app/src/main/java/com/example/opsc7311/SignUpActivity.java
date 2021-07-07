package com.example.opsc7311;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    String _name, _username, _email, _password;
    TextInputLayout _txtLEmail,_txtLName, _txtLUsername, _txtLPassword, _txtLPasswordConfirmation;
    Boolean _validDetails = false;

    Boolean _validUsername= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView btnBack = findViewById((R.id.btnBack_SignUp));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, IntroductionActivity.class);
                startActivity(intent);
            }
        });

        _txtLName = findViewById((R.id.txtIName_SignUp));
        _txtLUsername = findViewById((R.id.txtIUsername_SignUp));
        _txtLEmail = findViewById((R.id.txtIEmailAddress_SignUp));
        _txtLPassword = findViewById((R.id.txtIPassword_SignUp));

        _txtLPasswordConfirmation = findViewById((R.id.txtIPasswordConfirmation_SignUp));

        Button btnSignUp = findViewById((R.id.btnSignUp_SignUp));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _email="k";
                _name = _txtLName.getEditText().getText().toString().trim();
                _username = _txtLUsername.getEditText().getText().toString().trim();
                _email = _txtLEmail.getEditText().getText().toString().trim();
                _password = _txtLPassword.getEditText().getText().toString().trim();

                if (ValidInputs()) {

                    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                    DatabaseReference reference = rootNode.getReference("users");
                    reference.child(_username).setValue(CreateHelperClass());

                    Profile.getInstance().setName(_name);
                    Profile.getInstance().setUsername(_name);
                    Profile.getInstance().setEmail(_email);
                    Profile.getInstance().setPassword(_password);

                    Toast myToast = Toast.makeText(SignUpActivity.this, "I'm a toast!", Toast.LENGTH_LONG);
                    myToast.show();

                    Intent intent= new Intent(SignUpActivity.this,HomeActivity.class);
                    startActivity(intent);

                }
            }
        });
    }

    Boolean ValidInputs() {

        Boolean validName, validEmail, validPassword;
        if (_name.isEmpty()) {
            _txtLName.setError("Required field");
            validName = false;
        } else {
            _txtLPassword.setError(null);
            validName = true;
        }

        if (_username.isEmpty()) {
            _txtLUsername.setError("Required field");
            _validUsername = false;
        } else {
            _txtLPassword.setError(null);
            ValidateUsername();
        }

        if (_email.isEmpty()) {
            _txtLEmail.setError("Required field");
            validEmail = false;
        } else {
            _txtLPassword.setError(null);
            validEmail = ValidEmail();
        }

        if (_password.isEmpty()) {
            _txtLPassword.setError("Required field");
            validPassword = false;
        } else {
            _txtLPassword.setError(null);

            String passwordPattern = "^" + ".{6,}"+"$";
            if (_password.matches(passwordPattern))
                validPassword = ValidPassword();
            else
            {
                _txtLPassword.setError("Password must be at least 6 characters long");
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
                    _txtLUsername.setError("Username is already taken");
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
            _txtLEmail.setError("Invalid email address");
            return false;
        } else {
            _txtLEmail.setError(null);
            _validDetails = true;
            return true;
        }

    }

    Boolean ValidPassword() {

        String passwordConfirmation = _txtLPassword.getEditText().getText().toString().trim();
        if (_password.equals(passwordConfirmation)) {
            return true;
        }
        else {
            _txtLPasswordConfirmation.setError("Passwords do not match");
            return false;
        }

    }

}