package com.example.opsc7311;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {

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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        TextView txtUsername = findViewById((R.id.txtUsername_Settings));
        txtUsername.setText("@" + Profile.getInstance().username);

        TextView txtName = findViewById((R.id.txtName_Settings));
        txtName.setText(Profile.getInstance().name);

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


                TextInputLayout name = _dialog.findViewById((R.id.txtIName_EditProfile));
                name.getEditText().setHint(Profile.getInstance().name);

                Button btnSave = _dialog.findViewById((R.id.btnSave_UpdateEmail));
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String newName = name.getEditText().getText().toString();

                        if (NameChanged(newName)) {
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
                _dialog = new Dialog(SettingsActivity.this);
                _dialog.setContentView(R.layout.update_password);

                TextView btnBack = _dialog.findViewById((R.id.txtBack_UpdatePassword));
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _dialog.dismiss();
                    }
                });

                TextInputLayout txtLCurrentPassword = _dialog.findViewById((R.id.txtLOldPassword_UpdatePassword));
                TextInputLayout txtLNewPassword = _dialog.findViewById((R.id.txtLPassword_UpdatePassword));
                TextInputLayout txtLNewPasswordConfirmation = _dialog.findViewById((R.id.txtLPasswordConfirmation_UpdatePassword));


                TextView btnSave = _dialog.findViewById((R.id.btnSave_UpdatePassword));
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String currentPassword = txtLCurrentPassword.getEditText().getText().toString();

                        if (currentPassword.equals(Profile.getInstance().password)) {
                            txtLCurrentPassword.setError(null);
                            if (ValidPassword(txtLNewPassword, txtLNewPasswordConfirmation)) {
                                _reference.child(Profile.getInstance().username).child("password").setValue(txtLNewPassword.getEditText().getText().toString().trim());
                                _dialog.dismiss();

                                Toast myToast = Toast.makeText(SettingsActivity.this, "Password has been updated", Toast.LENGTH_LONG);
                                myToast.show();
                            }
                        } else
                            txtLCurrentPassword.setError("Incorrect password");

                    }
                });
                _dialog.show();
            }
        });

//Change Email
        CardView btnUpdateEmail = findViewById((R.id.cdvEmailAddress_UpdateAccount));
        btnUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog = new Dialog(SettingsActivity.this);
                _dialog.setContentView(R.layout.update_email);

                TextView btnBack = _dialog.findViewById((R.id.txtBack_UpdateEmail));
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _dialog.dismiss();
                    }
                });

                TextInputLayout txtLEmailAddress = _dialog.findViewById((R.id.txtLEmail_UpdateEmail));
                TextInputLayout txtLEmailAddressConfirmation = _dialog.findViewById((R.id.txtLConfirmEmail_UpdateEmail));

                TextView btnSave = _dialog.findViewById((R.id.btnSave_UpdateEmail));
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ValidEmail(txtLEmailAddress, txtLEmailAddressConfirmation)) {
                            _reference.child(Profile.getInstance().username).child("email").setValue(txtLEmailAddress.getEditText().getText().toString().trim());
                            _dialog.dismiss();

                            Toast myToast = Toast.makeText(SettingsActivity.this, "Email address has been updated", Toast.LENGTH_LONG);
                            myToast.show();
                        }
                    }
                });
                _dialog.show();
            }
        });

        Button btnDeleteAccount = findViewById((R.id.btnDeleteAccount_Settings));
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _reference.child(Profile.getInstance().username).removeValue();
                Intent intent = new Intent(SettingsActivity.this, IntroductionActivity.class);
                startActivity(intent);
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

    Boolean NameChanged(String name){

        if(!name.isEmpty()&& !name.equals(Profile.getInstance().name)){
            _reference.child(Profile.getInstance().username).child("name").setValue(name);
            return true;
        }
        return false;
    }

    Boolean ValidEmail(TextInputLayout txtLEmail, TextInputLayout txtLEmailConfirmation)
    {
        String email = txtLEmail.getEditText().getText().toString();
        String emailConfirmation = txtLEmailConfirmation.getEditText().getText().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
        if (email.matches(emailPattern))
        {
            if(email.equals(emailConfirmation))
                return true;
            else{
                txtLEmailConfirmation.setError("Email confirmation does not match");
                return false;
            }
        }else {
            txtLEmail.setError("Email address is not valid");
            return false;
        }
    }

    Boolean ValidPassword(TextInputLayout txtPassword, TextInputLayout txtPasswordConfirmation)
    {
        String password = txtPassword.getEditText().getText().toString();
        String passwordConfirmation = txtPasswordConfirmation.getEditText().getText().toString();

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