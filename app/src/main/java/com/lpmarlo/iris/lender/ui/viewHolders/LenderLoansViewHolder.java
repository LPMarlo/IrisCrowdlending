package com.lpmarlo.iris.lender.ui.viewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lpmarlo.iris.R;

public class LenderLoansViewHolder extends RecyclerView.ViewHolder {

    public TextView borrowerNameLenderLoanTextView;
    public TextView amountLenderLoanTextView;
    public TextView descriptionLenderLoanTextView;
    public ImageView profilePictureLenderLoanImageView;
    public ImageButton paymentImageButton;

    public LenderLoansViewHolder(@NonNull View itemView) {
        super(itemView);
        borrowerNameLenderLoanTextView = itemView.findViewById(R.id.borrowerNameLenderLoanTextView);
        amountLenderLoanTextView = itemView.findViewById(R.id.amountLenderLoanTextView);
        descriptionLenderLoanTextView = itemView.findViewById(R.id.descriptionLenderLoanTextView);
        profilePictureLenderLoanImageView = itemView.findViewById(R.id.profilePictureLenderLoanImageView);
        paymentImageButton = itemView.findViewById(R.id.paymentImageButton);
    }
}
