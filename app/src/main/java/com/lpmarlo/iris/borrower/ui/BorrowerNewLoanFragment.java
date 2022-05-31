package com.lpmarlo.iris.borrower.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.commons.firebase.FirebaseManager;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class BorrowerNewLoanFragment extends Fragment {

    EditText amountNewLoanEditText;
    EditText descriptionNewLoanEditText;
    Button createNewLoanButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_borrower_new_loan, container, false);
        amountNewLoanEditText = rootView.findViewById(R.id.amountNewLoanEditText);
        descriptionNewLoanEditText = rootView.findViewById(R.id.descriptionNewLoanEditText);
        createNewLoanButton = rootView.findViewById(R.id.createNewLoanButton);

        createNewLoanButton.setOnClickListener(v -> {
            String amount = amountNewLoanEditText.getText().toString();
            String description = descriptionNewLoanEditText.getText().toString();

            if (TextUtils.isEmpty(amount)) {
                amountNewLoanEditText.setError("Required");
            } else if (TextUtils.isEmpty(description)) {
                descriptionNewLoanEditText.setError("Required");
            } else if (Double.parseDouble(amount) < 0) {
                amountNewLoanEditText.setError("Amount must be greater than 0");
            } else {
                FirebaseManager fbm = FirebaseManager.getInstance();
                fbm.createLoan(amount, description, getContext());
            }


            amountNewLoanEditText.setText("");
            descriptionNewLoanEditText.setText("");
        });

        return rootView;
    }
}