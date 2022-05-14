package com.lpmarlo.iris.borrower.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.BorrowerSettingsActivity;

public class BorrowerHomeFragment extends Fragment {

    Button borrowerSettingsButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        borrowerSettingsButton = getActivity().findViewById(R.id.borrowerSettingsButton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_borrower_home, container, false);
    }
}