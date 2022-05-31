package com.lpmarlo.iris.borrower.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.adapters.LoansAdapter;
import com.lpmarlo.iris.commons.TemplateViewHolder;
import com.lpmarlo.iris.commons.models.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BorrowerHomeFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView borrowerHomeRecycleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_borrower_home, container, false);

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection("loans")
                .whereEqualTo("borrowerId", userId);

        FirestoreRecyclerOptions<Loan> options = new FirestoreRecyclerOptions.Builder<Loan>()
                .setQuery(query, Loan.class)
                .build();

        borrowerHomeRecycleView = rootView.findViewById(R.id.borrowerHomeRecycleView);
        borrowerHomeRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        LoansAdapter adapter = new LoansAdapter(options);
        borrowerHomeRecycleView.setAdapter(adapter);

        return rootView;
    }
}