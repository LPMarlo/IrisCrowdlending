package com.lpmarlo.iris.lender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.BorrowerMainActivity;
import com.lpmarlo.iris.commons.ChoiceProfileActivity;
import com.lpmarlo.iris.commons.firebase.FirebaseManager;
import com.lpmarlo.iris.commons.models.Lender;

public class LenderSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lender_settings);
    }

    public void logoutLender(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(LenderSettingsActivity.this, ChoiceProfileActivity.class));
        finish();
    }
}