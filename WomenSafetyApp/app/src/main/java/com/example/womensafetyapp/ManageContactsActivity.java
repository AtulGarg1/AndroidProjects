package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class ManageContactsActivity extends AppCompatActivity {

    RecyclerView contactsRV;
    ContactsAdapter adapter;
    FloatingActionButton addContent;
    ArrayList<String> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_contacts);

        contactsRV = findViewById(R.id.contacts_rv);
        addContent = findViewById(R.id.add_contact);

        addContent.setOnClickListener(view -> startActivity(new Intent(this, RegisterNumberActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        contacts = getContacts();
        adapter = new ContactsAdapter(contacts);
        contactsRV.setAdapter(adapter);
        contactsRV.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyItemInserted(contacts.size() - 1);
    }

    private ArrayList<String> getContacts() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String contacts = sharedPreferences.getString("ENUM", "NONE");
        return new ArrayList<>(Arrays.asList(contacts.split(",")));
    }
}