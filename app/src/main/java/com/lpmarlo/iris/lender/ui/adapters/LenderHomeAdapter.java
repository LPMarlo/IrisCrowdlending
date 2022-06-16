package com.lpmarlo.iris.lender.ui.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.Objects;

public class LenderHomeAdapter extends FirestoreRecyclerAdapter<Loan, LenderHomeViewHolder> {

    public LenderHomeAdapter(@NonNull FirestoreRecyclerOptions<Loan> options) {
        super(options);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onBindViewHolder(@NonNull LenderHomeViewHolder holder, int position, @NonNull Loan model) {
        holder.profilePictureLenderHomeImageView.setImageResource(R.drawable.ic_baseline_person_24);
        holder.amountLenderHomeTextView.setText(String.format("%.0f", model.getBorrowedAmount()) + " / " + String.format("%.0f", model.getRequestedAmount()));
        holder.descriptionLenderHomeTextView.setText(model.getDescription());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("borrowers")
                .document(model.getBorrowerId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        holder.borrowerNameLenderHomeTextView.setText(String.format("%s", documentSnapshot.getString("name")));
                    } else {
                        holder.borrowerNameLenderHomeTextView.setText("");
                        Log.e("LenderHomeAdapter", "No such document");
                    }
                });

        holder.paymentImageButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            View view = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.dialog_payment, null);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();

            View button = view.findViewById(R.id.dialog_payment_button);
            if (button != null) {
                button.setOnClickListener(
                        v1 -> {
                            String amount = ((EditText) view.findViewById(R.id.paymentEditText)).getText().toString();
                            if (TextUtils.isEmpty(amount) || amount.equals("0"))
                                Toast.makeText(holder.itemView.getContext(), "Please enter an amount", Toast.LENGTH_SHORT).show();

                            else if (Double.parseDouble(amount) > (model.getRequestedAmount() - model.getBorrowedAmount()))
                                Toast.makeText(holder.itemView.getContext(), "Amount exceeds the amount requested", Toast.LENGTH_SHORT).show();

                            else if (Double.parseDouble(amount) <= (model.getRequestedAmount() - model.getBorrowedAmount())) {
                                String lenderId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                FirebaseFirestore.getInstance().collection("payments")
                                        .add(new Payment(lenderId, model.getId(), amount));

                                if (Double.parseDouble(amount) == (model.getRequestedAmount() - model.getBorrowedAmount()))
                                    db.collection("loans")
                                            .document(model.getId())
                                            .update("status", "ACCEPTED");

                                db.collection("loans")
                                        .document(model.getId())
                                        .update("borrowedAmount", model.getBorrowedAmount() + Double.parseDouble(amount));
                            }
                            dialog.dismiss();
                        }
                );
            }
        });
        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("loanId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("paymentLoanId", model.getId());
        editor.apply();
    }

    @NonNull
    @Override
    public LenderHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lender_home, parent, false);
        return new LenderHomeViewHolder(view);
    }
}
