package com.sanioluke00.plantrium_beaplanter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class LoginPageActivity extends AppCompatActivity {

    TextInputLayout loginpage_emailid, loginpage_password;
    TextView loginpage_forgotpass, loginpage_loginbtn, loginpage_signup_btn;
    LinearLayout loginpage_mainlay;
    private Functions functions = new Functions();


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        loginpage_emailid = findViewById(R.id.loginpage_emailid);
        loginpage_password = findViewById(R.id.loginpage_password);
        loginpage_forgotpass = findViewById(R.id.loginpage_forgotpass);
        loginpage_loginbtn = findViewById(R.id.loginpage_loginbtn);
        loginpage_mainlay = findViewById(R.id.loginpage_mainlay);
        loginpage_signup_btn = findViewById(R.id.loginpage_signup_btn);

        loginpage_loginbtn.setEnabled(false);
        loginpage_emailid.setError("Please enter your Email ID !!");
        loginpage_emailid.requestFocus();
        loginpage_loginbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.lightgrey)));

        loginpage_signup_btn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });

        loginpage_emailid.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateLoginButton(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        loginpage_forgotpass.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(LoginPageActivity.this);
            dialog.setContentView(R.layout.forgot_pass_dialog);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            EditText forgot_pass_edittext = dialog.findViewById(R.id.forgot_pass_edittext);
            Button forgot_pass_getpassbtn = dialog.findViewById(R.id.forgot_pass_getpassbtn);

            forgot_pass_getpassbtn.setOnClickListener(v1 -> {
                String forgot_pass_edittext_txt = forgot_pass_edittext.getText().toString();

                Log.e("emailid", forgot_pass_edittext_txt);
                UserDBHelper dbHelper = new UserDBHelper(getApplicationContext());
                Cursor data = dbHelper.retrievePasswordPlantrium(forgot_pass_edittext_txt);
                if (data.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No entry Exist", Toast.LENGTH_LONG).show();
                } else {
                    dialog.dismiss();
                    StringBuffer buffer = new StringBuffer();
                    if (data.moveToNext()) {
                        buffer.append("Password : " + data.getString(0) + "\n");
                    } else {
                        buffer.append("Unable to find password !! Please try again !!");
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginPageActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Your Password");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
            });

            dialog.show();
        });

        loginpage_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateLoginButton(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loginpage_loginbtn.setOnClickListener(v -> {
            String emailid = loginpage_emailid.getEditText().getText().toString();
            String password = loginpage_password.getEditText().getText().toString();

            UserDBHelper dbHelper = new UserDBHelper(getApplicationContext());
            Cursor data = dbHelper.loginIntoPlantrium(emailid, password);
            if (data.getCount() == 0) {
                Snackbar.make(loginpage_mainlay, "No User Exists with this Email ID and password !!", Snackbar.LENGTH_SHORT).show();
            } else {
                if (data.moveToNext()) {
                    String user_fullname = data.getString(0);
                    String user_emailid = data.getString(1);
                    Snackbar.make(loginpage_mainlay, "Log In Successfully. Proceeding to dashboard....", Snackbar.LENGTH_SHORT).show();
                    functions.putSharedPrefsValue(getApplicationContext(), "account_data", "user_fullname", "string", user_fullname);
                    functions.putSharedPrefsValue(getApplicationContext(), "account_data", "user_emailid", "string", user_emailid);
                    functions.putSharedPrefsValue(getApplicationContext(), "account_data", "login_status", "boolean", true);
                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    }, 1500);
                } else {
                    Snackbar.make(loginpage_mainlay, "Log In Failed !! Please try again !!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkEmailEditText(boolean isemail) {
        String email_regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailid = loginpage_emailid.getEditText().getText().toString();

        if (!emailid.matches(email_regex)) {
            if (isemail) {
                loginpage_emailid.setError("Please enter a valid Email ID !!");
                loginpage_emailid.requestFocus();
            }
            return false;
        } else {
            if (isemail) {
                loginpage_emailid.setError(null);
            }
            return true;
        }
    }

    private boolean checkPasswordEditText(boolean ispass) {
        String password_regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        String password = loginpage_password.getEditText().getText().toString();

        if (!password.matches(password_regex)) {
            if (ispass) {
                loginpage_password.setError("Please enter a valid Password !!\nPlease check if the password contains - " +
                        "\nAtleast one digit, Atleast one lowercase letter, Atleast one uppercase letter," +
                        "\nAtleast one special characters from @ # $ % ^ & + =\n password size should be atleast 4");
                loginpage_password.requestFocus();
            }
            return false;
        } else {
            if (ispass) {
                loginpage_password.setError(null);
            }
            return true;
        }
    }

    private void updateLoginButton(boolean flag) {
        if (!checkEmailEditText(flag) || !checkPasswordEditText(!flag)) {
            loginpage_loginbtn.setEnabled(false);
            loginpage_loginbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.lightgrey)));
        } else {
            loginpage_loginbtn.setEnabled(true);
            loginpage_loginbtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.themecolor)));
        }
    }

}