package com.lpmarlo.iris.borrower.ui.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.ui.viewHolders.LoansViewHolder;
import com.lpmarlo.iris.borrower.ui.viewHolders.NotificationsViewHolder;
import com.lpmarlo.iris.commons.models.Loan;
import com.lpmarlo.iris.commons.models.Payment;

public class NotificationsAdapter extends FirestoreRecyclerAdapter<Payment, NotificationsViewHolder> {

    public NotificationsAdapter(@NonNull FirestoreRecyclerOptions<Payment> options) {
        super(options);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position, @NonNull Payment model) {
        holder.amountPaymentTextView.setText("You have borrowed " + model.getAmount() + "€");
        long minutes = (System.currentTimeMillis() - model.getDate().getSeconds() * 1000) / (60 * 1000);
        if (minutes > 60) {
            holder.createDatePaymentTextView.setText(String.format("%d", minutes / 60) + "h");
        } else {
            holder.createDatePaymentTextView.setText(String.format("%d", minutes) + "m");
        }
        FirebaseFirestore.getInstance()
                .collection("lenders")
                .document(model.getLenderId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        holder.lenderNamePaymentTextView.setText(documentSnapshot.getString("name"));
                        holder.profileLenderPaymentImageView.setImageResource(R.drawable.ic_baseline_person_24);
                    } else {
                        holder.profileLenderPaymentImageView.setImageResource(R.drawable.ic_baseline_person_24);
                    }
                });
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_borrower_notifications, parent, false);
        return new NotificationsViewHolder(view);
    }
}
