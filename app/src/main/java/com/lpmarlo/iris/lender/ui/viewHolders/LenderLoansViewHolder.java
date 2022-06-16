package com.lpmarlo.iris.lender.ui.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lpmarlo.iris.R;

public class LenderLoansViewHolder extends RecyclerView.ViewHolder {

    public TextView borrowerNameLenderLoansTextView;
    public TextView statusLenderLoansTextView;
    public ImageView profilePictureLenderLoansImageView;
    public TextView dateLenderLoansTextView;

    public LenderLoansViewHolder(@NonNull View itemView) {
        super(itemView);
        borrowerNameLenderLoansTextView = itemView.findViewById(R.id.borrowerNameLenderLoansTextView);
        statusLenderLoansTextView = itemView.findViewById(R.id.statusLenderLoansTextView);
        profilePictureLenderLoansImageView = itemView.findViewById(R.id.profilePictureLenderLoansImageView);
        dateLenderLoansTextView = itemView.findViewById(R.id.dateLenderLoansTextView);
    }
}