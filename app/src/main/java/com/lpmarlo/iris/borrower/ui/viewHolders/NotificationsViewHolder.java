package com.lpmarlo.iris.borrower.ui.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lpmarlo.iris.R;

public class NotificationsViewHolder extends RecyclerView.ViewHolder {

    public TextView lenderNamePaymentTextView;
    public TextView amountPaymentTextView;
    public TextView createDatePaymentTextView;
    public ImageView profileLenderPaymentImageView;

    public NotificationsViewHolder(@NonNull View itemView) {
        super(itemView);
        lenderNamePaymentTextView = itemView.findViewById(R.id.lenderNamePaymentTextView);
        amountPaymentTextView = itemView.findViewById(R.id.amountPaymentTextView);
        createDatePaymentTextView = itemView.findViewById(R.id.createDatePaymentTextView);
        profileLenderPaymentImageView = itemView.findViewById(R.id.profileLenderPaymentImageView);
    }
}
