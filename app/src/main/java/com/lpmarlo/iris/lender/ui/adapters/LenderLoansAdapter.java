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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.ui.viewHolders.LoansViewHolder;
import com.lpmarlo.iris.commons.models.Loan;
import com.lpmarlo.iris.commons.models.Payment;
import com.lpmarlo.iris.lender.ui.viewHolders.LenderLoansViewHolder;

import java.util.Date;
import java.util.Objects;

public class LenderLoansAdapter extends FirestoreRecyclerAdapter<Loan, LenderLoansViewHolder> {

    public LenderLoansAdapter(@NonNull FirestoreRecyclerOptions<Loan> options) {
        super(options);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onBindViewHolder(@NonNull LenderLoansViewHolder holder, int position, @NonNull Loan model) {
        FirebaseFirestore.getInstance().collection("borrowers")
                .document(model.getBorrowerId())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        holder.borrowerNameLenderLoanTextView.setText(String.format("%s",
                                documentSnapshot.getString("name")));
                        holder.profilePictureLenderLoanImageView.setImageResource(R.drawable.ic_baseline_person_24);
                    } else {
                        holder.borrowerNameLenderLoanTextView.setText("");
                        holder.profilePictureLenderLoanImageView.setImageResource(R.drawable.ic_baseline_person_24);
                    }
                });
        holder.amountLenderLoanTextView.setText(String.format("%.0f", model.getBorrowedAmount()) + "€ / " + String.format("%.0f", model.getRequestedAmount()) + "€");
        holder.descriptionLenderLoanTextView.setText(model.getDescription());
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
                            if (TextUtils.isEmpty(amount) || amount.equals("0")) {
                                Toast.makeText(holder.itemView.getContext(), "Please enter an amount", Toast.LENGTH_SHORT).show();
                            } else if (Double.parseDouble(amount) > (model.getRequestedAmount() - model.getBorrowedAmount())) {
                                Toast.makeText(holder.itemView.getContext(), "Amount exceeds the amount requested", Toast.LENGTH_SHORT).show();
                            } else if (Double.parseDouble(amount) <= (model.getRequestedAmount() - model.getBorrowedAmount())) {
                                String lenderId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                FirebaseFirestore.getInstance().collection("payments")
                                        .add(new Payment(lenderId, model.getId(), amount));

                                FirebaseFirestore.getInstance().collection("loans")
                                        .document(model.getId())
                                        .update("status", "PAID");

                                FirebaseFirestore.getInstance().collection("loans")
                                        .document(model.getId())
                                        .update("borrowedAmount", model.getBorrowedAmount() + Double.parseDouble(amount));
                            }
                            dialog.dismiss();
                        }
                );
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
