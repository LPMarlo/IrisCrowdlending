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
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.ui.adapters.LoansAdapter;
import com.lpmarlo.iris.commons.models.Loan;

import java.util.Objects;

public class BorrowerHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_borrower_home, container, false);

        FirebaseFirestore.getInstance().collection("loans")
                .whereEqualTo("borrowerId", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .orderBy("createDate", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty())
                        Log.e("BorrowerHomeFragment", "No loans found");
                    else {
                        Log.e("BorrowerHomeFragment", "Loans found");
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Loan loan = documentSnapshot.toObject(Loan.class);

                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("loan", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("loanId", loan.getId());
                            editor.apply();
                        }
                    }
                });

        Query query = FirebaseFirestore.getInstance().collection("loans")
                .whereEqualTo("borrowerId", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
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