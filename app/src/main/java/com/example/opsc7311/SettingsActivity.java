package com.example.opsc7311;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    Boolean _validUsername,_usernameValidationCompleted;
    Dialog _dialog;

    DatabaseReference _reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        _dialog = new Dialog(this);

        _reference = FirebaseDatabase.getInstance().getReference("users");

//Go Back
        TextView btnBack = findViewById((R.id.txtBack_Settings));
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SettingsActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
//Profile Settings Button
        Button btnProfileSettings = findViewById((R.id.btnEditProfile_Settings));
        btnProfileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog = new Dialog(SettingsActivity.this);
                _dialog.setContentView(R.layout.edit_profile);



                TextView btnBack = _dialog.findViewById((R.id.txtBack_ProfileSettings));
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _dialog.dismiss();
                    }
                });

                TextView username = _dialog.findViewById((R.id.edtUsername_ProfileSettings));
                username.setHint(Profile.getInstance().username);

                TextView name = _dialog.findViewById((R.id.edtName_ProfileSettings));
                name.setHint(Profile.getInstance().name);

                Button btnSave = _dialog.findViewById((R.id.btnSave_UpdateAccount));
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(UsernameChanged(username))
                        {

                                Toast myToast = Toast.makeText(SettingsActivity.this, "Username has been updated", Toast.LENGTH_LONG);
                                myToast.show();
                        }

                        String newName = name.getText().toString();

                        if(NameChanged(newName))
                        {
                            Toast myToast = Toast.makeText(SettingsActivity.this, "Name has been updated", Toast.LENGTH_LONG);
                            myToast.show();

                        }

                    }
                });
                _dialog.show();
            }
        });

//Change Password
        CardView btnUpdatePassword = findViewById((R.id.cdvChangePassword_Settings));
        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupUpdateAccountDialog(true);

            }
        });

//Change Email
        CardView btnUpdateEmail = findViewById((R.id.cdvEmailAddress_UpdateAccount));
        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupUpdateAccountDialog(false);
            }
        });

        Button btnLogOut = findViewById((R.id.btnLogOut_Settings));
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, IntroductionActivity.class);
                startActivity(intent);
                }
            });


    }

    Boolean UsernameChanged(TextView txtUsername){

        String username = txtUsername.getText().toString();

        if(!username.isEmpty() && !username.equals(Profile.getInstance().username)){

            /*_usernameValidationCompleted = false;
            ValidateUsername(txtUsername);

            do{}while(!_usernameValidationCompleted);

            if(_validUsername){
                _reference.child(Profile.getInstance().username).child("username").setValue(username);
                return true;
            }*/

        }
        return false;
    }
    void ValidateUsername(TextView txtUsername) {

        String username = txtUsername.getText().toString();

        Query checkUser = _reference.orderByChild("username").equalTo(username);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                _validUsername = true;
                if(snapshot.exists()){
                    txtUsername.setError("Username is already taken");
                    _validUsername = false;
                }

                _usernameValidationCompleted = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    Boolean NameChanged(String name){

        if(!name.isEmpty()&& !name.equals(Profile.getInstance().name)){
            _reference.child(Profile.getInstance().username).child("name").setValue(name);
            return true;
        }
        return false;
    }

    void SetupUpdateAccountDialog(Boolean updatePassword){

        String required;
        if (updatePassword)
            required = "Password";
        else
            required = "Email";

        _dialog = new Dialog(SettingsActivity.this);
        _dialog.setContentView(R.layout.update_account);

        TextView txtHeading = _dialog.findViewById(R.id.txtHeading_UpdateAccount);
        txtHeading.setText("Change "+required);

        TextView btnBack = _dialog.findViewById((R.id.txtBack_UpdateAccount));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });

        TextView editBox_one = _dialog.findViewById((R.id.editBox_one_UpdateAccount));
        TextView editBox_two = _dialog.findViewById((R.id.editBox_two_UpdateAccount));

        editBox_one.setHint("Enter new " + required);
        editBox_two.setHint("Confirm new " + required);

        TextView btnSave = _dialog.findViewById((R.id.btnSave_UpdateAccount));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(updatePassword)
                {
                    if (ValidPassword(editBox_one, editBox_two)){
                        _reference.child(Profile.getInstance().username).child("password").setValue(editBox_one.getText().toString());
                        _dialog.dismiss();

                        Toast myToast = Toast.makeText(SettingsActivity.this, "Password has been updated", Toast.LENGTH_LONG);
                        myToast.show();
                    }

                }else
                {
                    if (ValidEmail(editBox_one, editBox_two)){
                        _reference.child(Profile.getInstance().username).child("email").setValue(editBox_one.getText().toString());
                        _dialog.dismiss();

                        Toast myToast = Toast.makeText(SettingsActivity.this, "Email address has been updated", Toast.LENGTH_LONG);
                        myToast.show();
                    }

                }
            }
        });
        _dialog.show();
    }

    Boolean ValidEmail(TextView txtEmail, TextView txtEmailConfirmation)
    {
        String email = txtEmail.getText().toString();
        String emailConfirmation = txtEmailConfirmation.getText().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
        if (email.matches(emailPattern))
        {
            if(email.equals(emailConfirmation))
                return true;
            else{
                txtEmailConfirmation.setError("Email confirmation does not match");
                return false;
            }
        }else {
            txtEmail.setError("Email address is not valid");
            return false;
        }
    }

    Boolean ValidPassword(TextView txtPassword, TextView txtPasswordConfirmation)
    {
        String password = txtPassword.getText().toString();
        String passwordConfirmation = txtPasswordConfirmation.getText().toString();

        String passwordRequirement = "^" + ".{6,}"+"$";
        if (password.matches(passwordRequirement))
        {
            if(password.equals(passwordConfirmation))
                return true;
            else{
                txtPasswordConfirmation.setError("Password confirmation does not match");
                return false;
            }
        }else {
            txtPassword.setError("Password must be at least 6 characters long");
            return false;
        }
    }


}