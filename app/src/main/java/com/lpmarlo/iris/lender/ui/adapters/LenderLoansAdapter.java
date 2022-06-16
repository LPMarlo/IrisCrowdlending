package com.lpmarlo.iris.lender.ui.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.commons.models.Loan;
import com.lpmarlo.iris.commons.models.Payment;
import com.lpmarlo.iris.lender.ui.viewHolders.LenderHomeViewHolder;
import com.lpmarlo.iris.lender.ui.viewHolders.LenderLoansViewHolder;

import java.util.Objects;

public class LenderLoansAdapter extends FirestoreRecyclerAdapter<Loan, LenderLoansViewHolder> {

    public LenderLoansAdapter(@NonNull FirestoreRecyclerOptions<Loan> options) {
        super(options);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onBindViewHolder(@NonNull LenderLoansViewHolder holder, int position, @NonNull Loan model) {
        long minutes = (System.currentTimeMillis() - (model.getCreateDate().getSeconds() * 1000)) / (60 * 1000);
        if (minutes > 60)
            holder.dateLenderLoansTextView.setText(String.format("%d", minutes / 60) + "h");
        else holder.dateLenderLoansTextView.setText(String.format("%d", minutes) + "m");

        holder.profilePictureLenderLoansImageView.setImageResource(R.drawable.ic_baseline_person_24);
        holder.statusLenderLoansTextView.setText(model.getStatus());

        FirebaseFirestore.getInstance().collection("borrowers")
                .document(model.getBorrowerId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists())
                        holder.borrowerNameLenderLoansTextView.setText(String.format("%s", documentSnapshot.getString("name")));
                    else {
                        holder.borrowerNameLenderLoansTextView.setText("");
                        Log.e("LenderLoansAdapter", "No such document");
                    }
                });
    }

    @NonNull
    @Override
    public LenderLoansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lender_loans, parent, false);
        return new LenderLoansViewHolder(view);
    }
}
