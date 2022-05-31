package com.lpmarlo.iris.commons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.lpmarlo.iris.R;
import com.lpmarlo.iris.commons.auth.LoginActivity;

public class ChoiceProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_profile);
        if(getSupportActionBar() != null) getSupportActionBar().hide(); // hide the title bar
    }

    public void lenderOption(View view) {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userType", "lender");
        editor.apply();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void borrowerOption(View view) {
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userType", "borrower");
        editor.apply();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}