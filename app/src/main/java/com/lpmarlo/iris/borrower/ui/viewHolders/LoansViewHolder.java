package com.lpmarlo.iris.borrower.ui.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lpmarlo.iris.R;

public class LoansViewHolder extends RecyclerView.ViewHolder {

    public TextView statusTextView;
    public TextView amountTextView;
    public TextView dateTextView;
    public TextView descriptionTextView;

    public LoansViewHolder(@NonNull View itemView) {
        super(itemView);
        statusTextView = itemView.findViewById(R.id.statusTextView);
        amountTextView = itemView.findViewById(R.id.amountTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
    }
}
