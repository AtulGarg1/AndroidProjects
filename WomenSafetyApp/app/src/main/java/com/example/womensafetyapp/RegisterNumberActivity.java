package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class RegisterNumberActivity extends AppCompatActivity {

    TextInputEditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_number);

        number = findViewById(R.id.numberEdit);
    }

    public void saveNumber(View view) {
        String numberString = Objects.requireNonNull(number.getText()).toString();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String contacts = sharedPreferences.getString("ENUM", "");

        if(numberString.length() == 10) {
            if(!contacts.contains(numberString)) {
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                if(contacts.equals("")) {
                    contacts = numberString;
                }
                else {
                    contacts = contacts + "," + numberString;
                }

                myEdit.putString("ENUM", contacts);
                myEdit.apply();
                RegisterNumberActivity.this.finish();
            } else {
                Toast.makeText(this, "Number Already Exists!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter Valid Number!", Toast.LENGTH_SHORT).show();
        }
    }
}