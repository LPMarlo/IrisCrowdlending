package com.lpmarlo.iris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.lpmarlo.iris.borrower.BorrowerMainActivity;
import com.lpmarlo.iris.commons.ChoiceProfileActivity;
import com.lpmarlo.iris.lender.LenderMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        checkLogin();
        finish();
    }

    private void checkLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String userType = sharedPreferences.getString("userType", "");
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");

        if(sharedPreferences.getBoolean("isLoggedIn", false)) {
            if(userType.equals("lender")) {
                FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email, password);
                startActivity(new Intent(MainActivity.this, LenderMainActivity.class));
            } else if(userType.equals("borrower")) {
                FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email, password);
                startActivity(new Intent(MainActivity.this, BorrowerMainActivity.class));
            }
            else startActivity(new Intent(MainActivity.this, ChoiceProfileActivity.class));
        }
        else startActivity(new Intent(MainActivity.this, ChoiceProfileActivity.class));
    }
}