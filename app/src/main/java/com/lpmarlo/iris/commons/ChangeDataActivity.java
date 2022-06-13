package com.lpmarlo.iris.commons;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.lpmarlo.iris.R;


public class ChangeDataActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_data);

        EditText nameChangeDataEditText = findViewById(R.id.nameChangeDataEditText);
        EditText birthdayChangeDataEditText = findViewById(R.id.birthdayChangeDataEditText);
        EditText surnamesChangeDataEditText = findViewById(R.id.surnamesChangeDataEditText);
    }
}