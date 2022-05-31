package com.lpmarlo.iris.commons;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lpmarlo.iris.R;

public class TemplateViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public TextView subtitleTextView;
    public TextView dateTextView;

    public TemplateViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        subtitleTextView = itemView.findViewById(R.id.subtitleTextView);
        dateTextView = itemView.findViewById(R.id.dateTextView);
    }
}
