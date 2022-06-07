package com.lpmarlo.iris.lender.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.commons.models.Loan;
import com.lpmarlo.iris.lender.ui.adapters.LenderLoansAdapter;

public class LenderHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_lender_home, container, false);

        Query query = FirebaseFirestore.getInstance().collection("loans")
                .orderBy("createDate", Query.Direction.DESCENDING)
                .limit(7);

        FirestoreRecyclerOptions<Loan> options = new FirestoreRecyclerOptions.Builder<Loan>()
                .setQuery(query, Loan.class)
                .build();

        RecyclerView lenderHomeRecycleView = rootView.findViewById(R.id.lenderHomeRecycleView);
        lenderHomeRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));

        LenderLoansAdapter adapter = new LenderLoansAdapter(options);
        adapter.startListening();
        lenderHomeRecycleView.setAdapter(adapter);

        return rootView;
    }
}