package com.lpmarlo.iris.borrower.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.ui.adapters.LoansAdapter;
import com.lpmarlo.iris.borrower.ui.adapters.NotificationsAdapter;
import com.lpmarlo.iris.commons.models.Loan;
import com.lpmarlo.iris.commons.models.Payment;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class BorrowerNotificationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_borrower_notifications, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loan", Context.MODE_PRIVATE);
        String loanId = sharedPreferences.getString("loanId", "");

        Query queryPayment = db.collection("payments")
                .whereEqualTo("loanId", loanId)
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(7);

        FirestoreRecyclerOptions<Payment> optionsPayment = new FirestoreRecyclerOptions.Builder<Payment>()
                .setQuery(queryPayment, Payment.class)
                .build();

        RecyclerView notificationsRecyclerView = rootView.findViewById(R.id.notificationsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        NotificationsAdapter adapter = new NotificationsAdapter(optionsPayment);
        adapter.startListening();
        notificationsRecyclerView.setAdapter(adapter);

        return rootView;
    }
}