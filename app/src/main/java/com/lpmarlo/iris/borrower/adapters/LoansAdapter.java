package com.lpmarlo.iris.borrower.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.commons.TemplateViewHolder;
import com.lpmarlo.iris.commons.models.Loan;

public class LoansAdapter extends FirestoreRecyclerAdapter<Loan, TemplateViewHolder> {

    public LoansAdapter(@NonNull FirestoreRecyclerOptions<Loan> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull TemplateViewHolder holder, int position, @NonNull Loan model) {
        holder.titleTextView.setText(model.getStatus());
        holder.subtitleTextView.setText(model.getBorrowedAmount() + " " + model.getRequestedAmount());
        holder.dateTextView.setText(model.getCreateDate().toString());
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_template, parent, false);
        return new TemplateViewHolder(view);
    }
}
