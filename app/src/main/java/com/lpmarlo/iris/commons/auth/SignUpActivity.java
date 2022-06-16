package com.lpmarlo.iris.commons.auth;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.commons.firebase.FirebaseManager;
import com.lpmarlo.iris.commons.models.Borrower;
import com.lpmarlo.iris.commons.models.Lender;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    EditText nameSignUpEditText;
    EditText surnamesSignUpEditText;
    EditText emailSignUpEditText;
    EditText phoneNumberSignUpEditText;
    EditText passwordSignUpEditText;
    EditText birthdaySignUpEditText;
    CheckBox termsAndConditionsSignUpCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        nameSignUpEditText = findViewById(R.id.nameSignUpEditText);
        surnamesSignUpEditText = findViewById(R.id.surnamesSignUpEditText);
        emailSignUpEditText = findViewById(R.id.emailSignUpEditText);
        phoneNumberSignUpEditText = findViewById(R.id.phoneNumberSignUpEditText);
        passwordSignUpEditText = findViewById(R.id.passwordSignUpEditText);
        birthdaySignUpEditText = findViewById(R.id.birthdaySignUpEditText);
        termsAndConditionsSignUpCheckBox = findViewById(R.id.termsAndConditionsSignUpCheckBox);

        findViewById(R.id.linkToLoginTextView).setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    public void signUp(View view) {
        String name = nameSignUpEditText.getText().toString();
        String surnames = surnamesSignUpEditText.getText().toString();
        String email = emailSignUpEditText.getText().toString();
        String phoneNumber = phoneNumberSignUpEditText.getText().toString();
        String password = passwordSignUpEditText.getText().toString();
        String birthday = birthdaySignUpEditText.getText().toString();
        boolean termsAndConditions = termsAndConditionsSignUpCheckBox.isChecked();

        if (validate(name, surnames, email, phoneNumber, password, birthday, termsAndConditions)) {
            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", name);
                            editor.putString("surnames", surnames);
                            editor.putString("email", email);
                            editor.putString("phoneNumber", phoneNumber);
                            editor.putString("password", password);
                            editor.putString("birthday", birthday);
                            String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                            editor.putString("id", id);
                            editor.apply();

                            FirebaseManager fm = FirebaseManager.getInstance();
                            fm.insertLender(new Lender(id, name, surnames, email, phoneNumber, password, birthday));
                            fm.insertBorrower(new Borrower(id, name, surnames, email, phoneNumber, password, birthday));

                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(SignUpActivity.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public boolean validate(String name, String surnames, String email, String phoneNumber, String password, String birthday, boolean termsAndConditions) {
        boolean valid = false;

        if (TextUtils.isEmpty(name)) {
            nameSignUpEditText.setError("Required.");
        } else if (TextUtils.isEmpty(surnames)) {
            surnamesSignUpEditText.setError("Required.");
        } else if (TextUtils.isEmpty(email)) {
            emailSignUpEditText.setError("Required.");
        } else if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            emailSignUpEditText.setError("Invalid email.");
        } else if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberSignUpEditText.setError("Required.");
        } else if (!phoneNumber.matches("^[0-9]{9}$")) {
            phoneNumberSignUpEditText.setError("Invalid phone number.");
        } else if (TextUtils.isEmpty(password)) {
            passwordSignUpEditText.setError("Required.");
        } else if (password.length() < 6) {
            passwordSignUpEditText.setError("At least 6 characters.");
        } else if (TextUtils.isEmpty(birthday)) {
            birthdaySignUpEditText.setError("Required.");
        } else if (!birthday.matches("^[0-9]{2}/[0-9]{2}/[0-9]{4}$")) {
            birthdaySignUpEditText.setError("Invalid birthday.");
        } else if (!termsAndConditions) {
            Toast.makeText(this, "Accept terms and conditions", Toast.LENGTH_SHORT).show();
        } else {
            valid = true;
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}