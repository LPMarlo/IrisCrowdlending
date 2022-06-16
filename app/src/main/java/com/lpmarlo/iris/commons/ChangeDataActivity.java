package com.lpmarlo.iris.commons;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.commons.firebase.FirebaseManager;
import com.lpmarlo.iris.commons.models.Borrower;
import com.lpmarlo.iris.commons.models.Lender;

import java.util.Objects;


public class ChangeDataActivity extends AppCompatActivity {

    EditText nameChangeDataEditText;
    EditText surnamesChangeDataEditText;
    EditText birthdayChangeDataEditText;
    EditText phoneNumberChangeDataEditText;
    EditText ibanChangeDataEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_data);

        nameChangeDataEditText = findViewById(R.id.nameChangeDataEditText);
        surnamesChangeDataEditText = findViewById(R.id.surnamesChangeDataEditText);
        birthdayChangeDataEditText = findViewById(R.id.birthdayChangeDataEditText);
        phoneNumberChangeDataEditText = findViewById(R.id.phoneNumberChangeDataEditText);
        ibanChangeDataEditText = findViewById(R.id.ibanChangeDataEditText);

        setData(nameChangeDataEditText, surnamesChangeDataEditText, birthdayChangeDataEditText, phoneNumberChangeDataEditText, ibanChangeDataEditText);
    }

    private void setData(EditText nameChangeDataEditText, EditText surnamesChangeDataEditText, EditText birthdayChangeDataEditText, EditText phoneNumberChangeDataEditText, EditText ibanChangeDataEditText) {
        FirebaseFirestore.getInstance().collection("lenders")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Borrower borrower = documentSnapshot.toObject(Borrower.class);
                    if (borrower != null) {
                        nameChangeDataEditText.setText(borrower.getName());
                        surnamesChangeDataEditText.setText(borrower.getSurnames());
                        birthdayChangeDataEditText.setText(borrower.getBirthday());
                        phoneNumberChangeDataEditText.setText(borrower.getPhoneNumber());
                        ibanChangeDataEditText.setText(borrower.getIban());
                    } else {
                        Log.d("ChangeDataActivity", "No such document");
                    }
                });
    }

    public void changeData(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        boolean isCorrect = true;

        db.collection("lenders")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .update("name", nameChangeDataEditText.getText().toString());

        db.collection("borrowers")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("name", nameChangeDataEditText.getText().toString());

        db.collection("lenders")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("surnames", surnamesChangeDataEditText.getText().toString());

        db.collection("borrowers")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("surnames", surnamesChangeDataEditText.getText().toString());

        String birthday = birthdayChangeDataEditText.getText().toString();
        if (birthday.matches("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$")) {
            db.collection("lenders")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("birthday", birthdayChangeDataEditText.getText().toString());
            db.collection("borrowers")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("birthday", birthdayChangeDataEditText.getText().toString());
        } else {
            birthdayChangeDataEditText.setError("Incorrect date format");
            isCorrect = false;
        }

        String phoneNumber = phoneNumberChangeDataEditText.getText().toString();
        if (phoneNumber.matches("^[0-9]{9}$")) {
            db.collection("lenders")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("phoneNumber", phoneNumberChangeDataEditText.getText().toString());
            db.collection("borrowers")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("phoneNumber", phoneNumberChangeDataEditText.getText().toString());
        } else {
            phoneNumberChangeDataEditText.setError("Incorrect phone number format");
            isCorrect = false;
        }

        String iban = ibanChangeDataEditText.getText().toString();
        if (iban.matches("^ES\\d{22}$") || TextUtils.isEmpty(iban)) {
            db.collection("lenders")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("iban", ibanChangeDataEditText.getText().toString());
            db.collection("borrowers")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("iban", ibanChangeDataEditText.getText().toString());
        } else {
            ibanChangeDataEditText.setError("Incorrect IBAN format");
            isCorrect = false;
        }

        if (isCorrect) {
            Toast.makeText(this, "Data changed", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}