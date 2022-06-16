package com.lpmarlo.iris.borrower.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.ui.viewHolders.LoansViewHolder;
import com.lpmarlo.iris.commons.models.Loan;

public class LoansAdapter extends FirestoreRecyclerAdapter<Loan, LoansViewHolder> {

    public LoansAdapter(@NonNull FirestoreRecyclerOptions<Loan> options) {
        super(options);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onBindViewHolder(@NonNull LoansViewHolder holder, int position, @NonNull Loan model) {
        long minutes = (System.currentTimeMillis() - (model.getCreateDate().getSeconds() * 1000)) / (60 * 1000);
        if (minutes > 60) holder.dateTextView.setText(String.format("%d", minutes / 60) + "h");
        else holder.dateTextView.setText(String.format("%d", minutes) + "m");

        holder.statusTextView.setText(model.getStatus());
        holder.amountTextView.setText(String.format("%.0f", model.getBorrowedAmount()) + " / " + String.format("%.0f", model.getRequestedAmount()));
        holder.descriptionTextView.setText(model.getDescription());
    }

    @NonNull
    @Override
    public LoansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loans, parent, false);
        return new LoansViewHolder(view);
    }
}
