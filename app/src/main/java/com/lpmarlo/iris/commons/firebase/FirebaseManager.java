package com.lpmarlo.iris.commons.firebase;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lpmarlo.iris.commons.models.Borrower;
import com.lpmarlo.iris.commons.models.Lender;
import com.lpmarlo.iris.commons.models.Loan;
import com.lpmarlo.iris.commons.models.Notification;
import com.lpmarlo.iris.commons.models.Payment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FirebaseManager {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static FirebaseManager getInstance() {
        return new FirebaseManager();
    }

    public void insertBorrower(Borrower borrower) {
        Log.i("FIREBASE", "Inserting borrower: " + borrower.getEmail());
        db.collection("borrowers").document(borrower.getId()).set(borrower);
        Log.i("FIREBASE", "Borrower inserted");
    }

    public void insertLender(Lender lender) {
        Log.i("FIREBASE", "Inserting lender: " + lender.getEmail());
        db.collection("lenders").document(lender.getId()).set(lender);
        Log.i("FIREBASE", "Lender inserted");
    }

    public void createNewLoan(String amount, String description) {
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("loans")
                .add(new Loan(amount, description, userId, new Date(), 0.05, 48, getDeadline()))
                .addOnSuccessListener(documentReference -> {
                    db.collection("loans")
                            .document(documentReference.getId())
                            .update("id", documentReference.getId());
                });
    }

    private Date getDeadline() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 4);
        return calendar.getTime();
    }

    public void createLoan(String amount, String description, Context context) {
        db.collection("loans")
                .whereEqualTo("borrowerId", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .orderBy("createDate", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        createNewLoan(amount, description);
                        Toast.makeText(context, "Loan created", Toast.LENGTH_SHORT).show();
                        Log.i("Loans", "There are no loans");
                    } else {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Map<String, Object> loan = documentSnapshot.getData();
                            if (Objects.equals(loan.get("status"), "COMPLETED")) {
                                createNewLoan(amount, description);
                                Toast.makeText(context, "Loan created", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "You already have a loan", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
