package com.lpmarlo.iris.lender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lpmarlo.iris.R;
import com.lpmarlo.iris.borrower.BorrowerSettingsActivity;
import com.lpmarlo.iris.databinding.ActivityLenderMainBinding;

public class LenderMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        ActivityLenderMainBinding binding = ActivityLenderMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_lender_home, R.id.navigation_lender_loans, R.id.navigation_lender_retributions)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.lender_nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.lenderNavView, navController);
    }

    public void onLenderSettingsClick(View view) {
        startActivity(new Intent(this, LenderSettingsActivity.class));
    }
}