package com.lpmarlo.iris.lender.ui;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.commons.models.Loan;
import com.lpmarlo.iris.lender.ui.adapters.LenderHomeAdapter;
import com.lpmarlo.iris.lender.ui.adapters.LenderLoansAdapter;

public class LenderLoansFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_lender_loans, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loanId", Context.MODE_PRIVATE);
        String paymentLoanId = sharedPreferences.getString("paymentLoanId", "");

        Query query = FirebaseFirestore.getInstance().collection("loans")
                .whereEqualTo("id", paymentLoanId)
                .orderBy("createDate", Query.Direction.DESCENDING)
                .limit(7);

        FirestoreRecyclerOptions<Loan> options = new FirestoreRecyclerOptions.Builder<Loan>()
                .setQuery(query, Loan.class)
                .build();

        RecyclerView lenderLoansRecyclerView = rootView.findViewById(R.id.lenderLoansRecyclerView);
        lenderLoansRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        LenderLoansAdapter adapter = new LenderLoansAdapter(options);
        adapter.startListening();
        lenderLoansRecyclerView.setAdapter(adapter);

        return rootView;
    }
}