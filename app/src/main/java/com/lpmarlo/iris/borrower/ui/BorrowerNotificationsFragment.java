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
import com.lpmarlo.iris.commons.TemplateViewHolder;
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
        // Mostrar FirestoreRecyclerViewAdapter
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        List<Notification> notifications = new ArrayList<>(0);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("loans")
                .whereEqualTo("borrowerId", userId)
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1);
        query.addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Log.i("Loans", "There are no loans");
                    } else {
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            Loan loan = queryDocumentSnapshot.toObject(Loan.class);
                            // Obtener todos los pagos de este prestamo
                            db.collection("payments")
                                    .whereEqualTo("loanId", loan.getId())
                                    .get()
                                    .addOnSuccessListener(queryDocumentSnapshots1 -> {
                                        if (queryDocumentSnapshots1.isEmpty()) {
                                            Log.i("Payments", "There are no payments");
                                        } else {
                                            for (QueryDocumentSnapshot queryDocumentSnapshot1 : queryDocumentSnapshots1) {
                                                Payment payment = queryDocumentSnapshot1.toObject(Payment.class);
                                                // Obtener el prestamista
                                                db.collection("lenders")
                                                        .whereEqualTo("lenderId", payment.getLenderId())
                                                        .get()
                                                        .addOnSuccessListener(queryDocumentSnapshots2 -> {
                                                            if (queryDocumentSnapshots2.isEmpty()) {
                                                                Log.i("Lenders", "There are no lenders");
                                                            } else {
                                                                for (QueryDocumentSnapshot queryDocumentSnapshot2 : queryDocumentSnapshots2) {
                                                                    Lender lender = queryDocumentSnapshot2.toObject(Lender.class);
                                                                    notifications.add(new Notification(lender.getName(), payment.getAmount(), payment.getDate()));
                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });
                        }
                    }
                }
        );
        FirestoreRecyclerOptions<Notification> options = new FirestoreRecyclerOptions.Builder<Notification>()
                .setQuery(query, Notification.class)
                .build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Notification, TemplateViewHolder>(options) {
            @Override
            protected void onBindViewHolder(TemplateViewHolder holder, int position, Notification model) {
                holder.titleTextView.setText(model.getLenderName());
                holder.subtitleTextView.setText(model.getAmount());
                holder.subtitleTextView.setText(model.getDate());
            }

            @Override
            public TemplateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_template, parent, false);
                return new TemplateViewHolder(view);
            }
        };
        notificationsRecyclerView.setAdapter(adapter);
        return rootView;
    }
}