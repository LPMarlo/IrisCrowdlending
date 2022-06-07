package com.lpmarlo.iris.borrower.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.ui.viewHolders.LoansViewHolder;
import com.lpmarlo.iris.commons.models.Lender;
import com.lpmarlo.iris.commons.models.Loan;
import com.lpmarlo.iris.commons.models.Notification;
import com.lpmarlo.iris.commons.models.Payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BorrowerNotificationsFragment extends Fragment {

    RecyclerView notificationsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_borrower_notifications, container, false);
        notificationsRecyclerView = rootView.findViewById(R.id.notificationsRecyclerView);

        return rootView;
    }
}