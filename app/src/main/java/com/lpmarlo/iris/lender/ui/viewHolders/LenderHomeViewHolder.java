package com.lpmarlo.iris.lender.ui.viewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lpmarlo.iris.R;

public class LenderHomeViewHolder extends RecyclerView.ViewHolder {

    public TextView borrowerNameLenderHomeTextView;
    public TextView amountLenderHomeTextView;
    public TextView descriptionLenderHomeTextView;
    public ImageView profilePictureLenderHomeImageView;
    public ImageButton paymentImageButton;

    public LenderHomeViewHolder(@NonNull View itemView) {
        super(itemView);
        borrowerNameLenderHomeTextView = itemView.findViewById(R.id.borrowerNameLenderHomeTextView);
        amountLenderHomeTextView = itemView.findViewById(R.id.amountLenderHomeTextView);
        descriptionLenderHomeTextView = itemView.findViewById(R.id.descriptionLenderHomeTextView);
        profilePictureLenderHomeImageView = itemView.findViewById(R.id.profilePictureLenderHomeImageView);
        paymentImageButton = itemView.findViewById(R.id.paymentImageButton);
    }
}