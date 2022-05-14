package com.lpmarlo.iris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lpmarlo.iris.borrower.BorrowerMainActivity;
import com.lpmarlo.iris.auth.ChoiceProfileActivity;
import com.lpmarlo.iris.auth.LoginActivity;
import com.lpmarlo.iris.lender.LenderMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null) getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_main);

        checkLogin();
        finish();
    }

    private void checkLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("isLogin", false)) {
            if (sharedPreferences.getString("userType", "").equals("borrower")) {
                startActivity(new Intent(MainActivity.this, BorrowerMainActivity.class));
            } else if (sharedPreferences.getString("userType", "").equals("lender")) {
                startActivity(new Intent(MainActivity.this, LenderMainActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, ChoiceProfileActivity.class));
            }
        } else  {
            startActivity(new Intent(MainActivity.this, ChoiceProfileActivity.class));
        }
        finish();
    }
}