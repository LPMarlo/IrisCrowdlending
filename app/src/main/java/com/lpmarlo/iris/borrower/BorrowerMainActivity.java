package com.lpmarlo.iris.borrower;

import static com.google.android.material.internal.ViewUtils.dpToPx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.databinding.ActivityBorrowerMainBinding;
import com.lpmarlo.iris.databinding.ActivityLenderMainBinding;

public class BorrowerMainActivity extends AppCompatActivity {

    private ActivityBorrowerMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null) getSupportActionBar().hide(); // hide the title bar

        binding = ActivityBorrowerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.lender_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_borrower_home, R.id.navigation_borrower_new_loan, R.id.navigation_borrower_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.borrower_nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.borrowerNavView, navController);
    }

    public void onBorrowerSettingsClick(View view) {
        startActivity(new Intent(this, BorrowerSettingsActivity.class));
    }
}