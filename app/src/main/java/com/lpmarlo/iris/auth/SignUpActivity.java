package com.lpmarlo.iris.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lpmarlo.iris.R;

public class SignUpActivity extends AppCompatActivity {

    EditText nameSignUpEditText;
    EditText surnamesSignUpEditText;
    EditText emailSignUpEditText;
    EditText phoneNumberSignUpEditText;
    EditText passwordSignUpEditText;
    CheckBox termsAndConditionsSignUpCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if(getSupportActionBar() != null) getSupportActionBar().hide(); // hide the title bar

        setupActivityLink();

        nameSignUpEditText = findViewById(R.id.nameSignUpEditText);
        surnamesSignUpEditText = findViewById(R.id.surnamesSignUpEditText);
        emailSignUpEditText = findViewById(R.id.emailSignUpEditText);
        phoneNumberSignUpEditText = findViewById(R.id.phoneNumberSignUpEditText);
        passwordSignUpEditText = findViewById(R.id.passwordSignUpEditText);
        termsAndConditionsSignUpCheckBox = findViewById(R.id.termsAndConditionsSignUpCheckBox);
    }

    private void setupActivityLink() {
        TextView linkToLoginTextView = findViewById(R.id.linkToLoginTextView);
        linkToLoginTextView.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    public void signUp(View view) {
        String name = nameSignUpEditText.getText().toString();
        String surnames = surnamesSignUpEditText.getText().toString();
        String email = emailSignUpEditText.getText().toString();
        String phoneNumber = phoneNumberSignUpEditText.getText().toString();
        String password = passwordSignUpEditText.getText().toString();
        boolean termsAndConditions = termsAndConditionsSignUpCheckBox.isChecked();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String phoneNumberPattern = "^[0-9]{10}$";

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surnames) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password) || !termsAndConditions) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        } else if (!phoneNumber.matches(phoneNumberPattern)) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putString("surnames", surnames);
            editor.putString("email", email);
            editor.putString("phoneNumber", phoneNumber);
            editor.putString("password", password);
            editor.putBoolean("termsAndConditions", true);
            editor.apply();

            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        }
    }
}