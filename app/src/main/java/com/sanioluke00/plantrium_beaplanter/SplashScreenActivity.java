package com.sanioluke00.plantrium_beaplanter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    private Functions functions = new Functions();
    Boolean login_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        login_status = functions.getSharedPrefsValue(getApplicationContext(), "account_data", "login_status", "boolean", false);

        new Handler().postDelayed(() -> {
            Class activityClass = login_status ? DashboardActivity.class : LoginPageActivity.class;
            startActivity(new Intent(getApplicationContext(), activityClass));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 2000);
    }
}