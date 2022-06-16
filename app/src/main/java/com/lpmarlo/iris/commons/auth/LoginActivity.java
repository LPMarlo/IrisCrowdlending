package com.lpmarlo.iris.commons.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.BorrowerMainActivity;
import com.lpmarlo.iris.commons.ChoiceProfileActivity;
import com.lpmarlo.iris.lender.LenderMainActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText emailLoginEditText;
    EditText passwordLoginEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        loginButton = findViewById(R.id.loginButton);
        emailLoginEditText = findViewById(R.id.emailLoginEditText);
        passwordLoginEditText = findViewById(R.id.passwordLoginEditText);

        findViewById(R.id.linkToSignUpTextView).setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
    }

    public void login(View view) {
        String email = emailLoginEditText.getText().toString();
        String password = passwordLoginEditText.getText().toString();

        SharedPreferences sharedPref = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            Toast.makeText(this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
        else if (!email.matches("^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+$"))
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        else {
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putString("userId", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();

                            if (sharedPref.getString("userType", "").equals("lender")) {
                                startActivity(new Intent(LoginActivity.this, LenderMainActivity.class));
                                finish();
                            } else if (sharedPref.getString("userType", "").equals("borrower")) {
                                startActivity(new Intent(LoginActivity.this, BorrowerMainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(this, "Please select a user type", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, ChoiceProfileActivity.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}