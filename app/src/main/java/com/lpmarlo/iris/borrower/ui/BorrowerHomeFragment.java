package com.lpmarlo.iris.borrower.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.ui.adapters.LoansAdapter;
import com.lpmarlo.iris.commons.models.Loan;

import java.util.Objects;

public class BorrowerHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_borrower_home, container, false);

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        Query query = FirebaseFirestore.getInstance().collection("loans")
                .whereEqualTo("borrowerId", userId)
                .orderBy("createDate", Query.Direction.DESCENDING)
                .limit(7);

        FirestoreRecyclerOptions<Loan> options = new FirestoreRecyclerOptions.Builder<Loan>()
                .setQuery(query, Loan.class)
                .build();

        RecyclerView borrowerHomeRecyclerView = rootView.findViewById(R.id.borrowerHomeRecyclerView);
        borrowerHomeRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        LoansAdapter adapter = new LoansAdapter(options);
        adapter.startListening();
        borrowerHomeRecyclerView.setAdapter(adapter);

        return rootView;
    }
}