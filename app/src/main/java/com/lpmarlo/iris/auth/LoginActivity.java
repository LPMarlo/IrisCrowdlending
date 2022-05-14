package com.lpmarlo.iris.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.BorrowerMainActivity;
import com.lpmarlo.iris.lender.LenderMainActivity;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText emailLoginEditText;
    EditText passwordLoginEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(getSupportActionBar() != null) getSupportActionBar().hide(); // hide the title bar

        setupActivityLink();

        loginButton = findViewById(R.id.loginButton);
        emailLoginEditText = findViewById(R.id.emailLoginEditText);
        passwordLoginEditText = findViewById(R.id.passwordLoginEditText);
    }

    private void setupActivityLink() {
        TextView linkToSignUpTextView = findViewById(R.id.linkToSignUpTextView);
        linkToSignUpTextView.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
    }

    public void login(View view) {
        String email = emailLoginEditText.getText().toString();
        String password = passwordLoginEditText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        } else if (sharedPreferences.getString("email", "").equals(email) && sharedPreferences.getString("password", "").equals(password)) {
            if (sharedPreferences.getString("userType", "").equals("borrower")) {
                startActivity(new Intent(LoginActivity.this, BorrowerMainActivity.class));
            } else if (sharedPreferences.getString("userType", "").equals("lender")) {
                startActivity(new Intent(LoginActivity.this, LenderMainActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, ChoiceProfileActivity.class));
            }
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}