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

    public void updateBorrower(Borrower borrower) {
        db.collection("borrowers").document(borrower.getEmail()).set(borrower);
    }

    public void updateLender(Lender lender) {
        db.collection("lenders").document(lender.getEmail()).set(lender);
    }

    public void deleteBorrower(Borrower borrower) {
        db.collection("borrowers").document(borrower.getEmail()).delete();
    }

    public void deleteLender(Lender lender) {
        db.collection("lenders").document(lender.getEmail()).delete();
    }

    // Get a borrower by email
    public Borrower getBorrowerByEmailAndByPassword(String email, String password) {
        AtomicReference<Borrower> borrower = new AtomicReference<>();
        db.collection("borrowers").document(email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Borrower borrower1 = task.getResult().toObject(Borrower.class);
                if (borrower1.getPassword().equals(password)) {
                    borrower.set(borrower1);
                }
            }
        });
        return borrower.get();
    }

    // Get a lender by email and pass
    public Lender getLenderByEmailAndByPassword(String email, String password) {
        AtomicReference<Lender> lender = new AtomicReference<>();
        db.collection("lenders").document(email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Lender lender1 = task.getResult().toObject(Lender.class);
                if (lender1.getPassword().equals(password)) {
                    lender.set(lender1);
                }
            }
        });
        return lender.get();
    }

    // Get if a borrower exists by email
    public boolean borrowerExists(String email) {
        AtomicReference<Boolean> exists = new AtomicReference<>();
        db.collection("borrowers").document(email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                exists.set(task.getResult().exists());
            }
        });
        return exists.get();
    }

    // Get if a lender exists by email
    public boolean lenderExists(String email) {
        AtomicReference<Boolean> exists = new AtomicReference<>();
        db.collection("lenders").document(email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                exists.set(task.getResult().exists());
            }
        });
        return exists.get();
    }

    // Get a lender by email
    public Lender getLender(String email) {
        AtomicReference<Lender> lender = new AtomicReference<>(new Lender());
        db.collection("lenders").document(email).get().addOnSuccessListener(documentSnapshot -> {
            lender.set(documentSnapshot.toObject(Lender.class));
        });
        return lender.get();
    }


    public void insertPayment(String lenderId, String loanId, String amount, String date) {
        Map<String, Object> payment = new HashMap<>();
        payment.put("lenderId", lenderId);
        payment.put("loanId", loanId);
        payment.put("amount", amount);
        payment.put("date", date);
        db.collection("payments")
                .add(payment);
    }

    public void deletePayment(String lenderId, String loanId, String amount, String date) {
        Map<String, Object> payment = new HashMap<>();
        payment.put("lenderId", lenderId);
        payment.put("loanId", loanId);
        payment.put("amount", amount);
        payment.put("date", date);
        db.collection("payments")
                .document(lenderId)
                .delete();
    }

    public void insertFee(String lenderId, String borrowerId, String amount, String date) {
        Map<String, Object> fee = new HashMap<>();
        fee.put("lenderId", lenderId);
        fee.put("borrowerId", borrowerId);
        fee.put("amount", amount);
        fee.put("date", date);
        db.collection("fees")
                .add(fee);
    }

    public void deleteFee(String lenderId, String borrowerId, String amount, String date) {
        Map<String, Object> fee = new HashMap<>();
        fee.put("lenderId", lenderId);
        fee.put("borrowerId", borrowerId);
        fee.put("amount", amount);
        fee.put("date", date);
        db.collection("fees")
                .document(lenderId)
                .delete();
    }

    public void insertLoan(String lenderId, String borrowerId, String amount, String date) {
        Map<String, Object> loan = new HashMap<>();
        loan.put("lenderId", lenderId);
        loan.put("borrowerId", borrowerId);
        loan.put("amount", amount);
        loan.put("date", date);
        db.collection("loans")
                .add(loan);
    }

    public void deleteLoan(String lenderId, String borrowerId, String amount, String date) {
        Map<String, Object> loan = new HashMap<>();
        loan.put("lenderId", lenderId);
        loan.put("borrowerId", borrowerId);
        loan.put("amount", amount);
        loan.put("date", date);
        db.collection("loans")
                .document(lenderId)
                .delete();
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

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("loans")
                .whereEqualTo("borrowerId", userId)
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
                            if ((loan.get("status")).equals("COMPLETED")) {
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
