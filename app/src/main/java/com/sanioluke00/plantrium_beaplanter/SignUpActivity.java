package com.sanioluke00.plantrium_beaplanter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout signuppage_fullname, signuppage_emailid, signuppage_password;
    TextView signuppage_signupbtn, signuppage_login_btn;
    LinearLayout signuppage_mainlay;

    Functions functions= new Functions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signuppage_fullname= findViewById(R.id.signuppage_fullname);
        signuppage_emailid= findViewById(R.id.signuppage_emailid);
        signuppage_password= findViewById(R.id.signuppage_password);
        signuppage_signupbtn= findViewById(R.id.signuppage_signupbtn);
        signuppage_login_btn= findViewById(R.id.signuppage_login_btn);
        signuppage_mainlay= findViewById(R.id.signuppage_mainlay);

        signuppage_signupbtn.setEnabled(false);
        signuppage_fullname.setError("Please enter your Fullname !!");
        signuppage_fullname.requestFocus();
        signuppage_signupbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.lightgrey)));

        signuppage_login_btn.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), LoginPageActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        signuppage_fullname.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateSignUpButton(1);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        signuppage_emailid.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateSignUpButton(2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signuppage_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateSignUpButton(3);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        signuppage_signupbtn.setOnClickListener(v->{
            String fullname= signuppage_fullname.getEditText().getText().toString();
            String emailid= signuppage_emailid.getEditText().getText().toString();
            String password= signuppage_password.getEditText().getText().toString();

            UserDBHelper dbHelper= new UserDBHelper(getApplicationContext());
            int check = dbHelper.signUpToPlantrium(fullname,emailid,password);
            if(check==1){
                Snackbar.make(signuppage_mainlay,"Registeration Successfully. Proceeding to dashboard....",Snackbar.LENGTH_SHORT).show();
                functions.putSharedPrefsValue(getApplicationContext(), "account_data", "user_fullname","string",fullname);
                functions.putSharedPrefsValue(getApplicationContext(), "account_data", "user_emailid","string",emailid);
                functions.putSharedPrefsValue(getApplicationContext(), "account_data", "login_status","boolean",true);
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                },1500);
            }
            else if(check==-1){
                Snackbar.make(signuppage_mainlay,"This Email ID already registered !! Please try with another Email ID !!",Snackbar.LENGTH_SHORT).show();
            }
            else{
                Snackbar.make(signuppage_mainlay,"Registeration Failed !! Please try again !!",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkFullNameEditText(int field){
        String fullname_regx = "^[a-zA-Z\\s]*$";
        String fullname= signuppage_fullname.getEditText().getText().toString();

        if(!fullname.matches(fullname_regx)){
            if(field==1){
                signuppage_fullname.setError("Please enter a valid Name !!");
                signuppage_fullname.requestFocus();
            }
            return false;
        }
        else{
            if(field==1){
                signuppage_fullname.setError(null);
            }
            return true;
        }
    }

    private boolean checkEmailEditText(int field){
        String email_regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailid= signuppage_emailid.getEditText().getText().toString();

        if(!emailid.matches(email_regex)){
            if(field==2){
                signuppage_emailid.setError("Please enter a valid Email ID !!");
                signuppage_emailid.requestFocus();
            }
            return false;
        }
        else{
            if(field==2){
                signuppage_emailid.setError(null);
            }
            return true;
        }
    }

    private boolean checkPasswordEditText(int field){
        String password_regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        String password= signuppage_password.getEditText().getText().toString();

        if(!password.matches(password_regex)){
            if(field==3){
                signuppage_password.setError("Please enter a valid Password !!\nPlease check if the password contains - " +
                        "\nAtleast one digit, Atleast one lowercase letter, Atleast one uppercase letter," +
                        "\nAtleast one special characters from @ # $ % ^ & + =\n password size should be atleast 4");
                signuppage_password.requestFocus();
            }
            return false;
        }
        else{
            if(field==3){
                signuppage_password.setError(null);
            }
            return true;
        }
    }

    private void updateSignUpButton(int field){
        if(!checkFullNameEditText(field) || !checkEmailEditText(field) || !checkPasswordEditText(field)){
            signuppage_signupbtn.setEnabled(false);
            signuppage_signupbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.lightgrey)));
        }
        else{
            signuppage_signupbtn.setEnabled(true);
            signuppage_signupbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.themecolor)));
        }
    }
}