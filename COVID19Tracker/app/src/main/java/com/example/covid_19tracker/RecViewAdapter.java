package com.example.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19tracker.classes.State;

import java.util.ArrayList;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.ViewHolder> {
    private final ArrayList<State> states;
    private final Context context;

    public RecViewAdapter(ArrayList<State> states, Context context) {
        this.states = states;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        State state = states.get(position);
        String stateNameAndCode = state.getStateName() + " (" + state.getStatecode() + ')';
        holder.tvStateName.setText(stateNameAndCode);

        // set up its adapter
        holder.rvDistrict.setAdapter(new DistrictAdapter(state.getDistrict()));
        holder.rvDistrict.setLayoutManager(new LinearLayoutManager(context));
        holder.rvDistrict.setHasFixedSize(false);
        holder.rvDistrict.setVerticalScrollBarEnabled(true);
        holder.rvDistrict.setVisibility(state.getOpen() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStateName;
        RecyclerView rvDistrict;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStateName = itemView.findViewById(R.id.tvStateName);
            rvDistrict = itemView.findViewById(R.id.rvDistrict);

            tvStateName.setOnClickListener(view -> {
                State state = states.get(getAdapterPosition());
                state.setOpen(!state.getOpen());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
