package com.example.womensafetyapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    ArrayList<String> contacts;
    Context context;

    ContactsAdapter(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        holder.textView.setText(contacts.get(position));
        holder.remove.setOnClickListener(view -> removeContact(position));
    }

    private void removeContact(int position) {
        contacts.remove(position);
        notifyItemRemoved(position);
        SharedPreferences pref = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ENUM", contacts.toString().replace("[", "").replace("]", ""));
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView remove;
        public ViewHolder(View view) {
            super(view);
             textView = view.findViewById(R.id.textview);
             remove = view.findViewById(R.id.remove);
        }
    }
}
