package com.lpmarlo.iris.borrower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.commons.ChoiceProfileActivity;
import com.lpmarlo.iris.lender.LenderSettingsActivity;

public class BorrowerSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null) getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_borrower_settings);
    }

    public void logoutBorrower(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(BorrowerSettingsActivity.this, ChoiceProfileActivity.class));
        finish();
    }
}