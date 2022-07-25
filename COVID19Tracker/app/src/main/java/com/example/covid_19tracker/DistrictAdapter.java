package com.example.covid_19tracker;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19tracker.classes.District;

import java.util.ArrayList;
import java.util.Objects;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.ViewHolder> {
    private final ArrayList<District> districts;

    public DistrictAdapter(ArrayList<District> districts) {
        this.districts = districts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.district_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        District district = districts.get(position);

        holder.tvDistrictName.setText(district.getDistrictName());
        holder.tvActive.setText(district.getDistrictData().getActive().toString());
        holder.tvConfirmed.setText(district.getDistrictData().getConfirmed().toString());
        holder.tvMigratedother.setText(district.getDistrictData().getMigratedother().toString());
        holder.tvDeceased.setText(district.getDistrictData().getDeceased().toString());
        holder.tvRecovered.setText(district.getDistrictData().getRecovered().toString());
        holder.tvDeltaConfirmed.setText(district.getDistrictData().getDelta().getConfirmed().toString());
        holder.tvDeltaDeceased.setText(district.getDistrictData().getDelta().getDeceased().toString());
        holder.tvDeltaRecovered.setText(district.getDistrictData().getDelta().getRecovered().toString());

        if(Objects.equals(district.getDistrictData().getNotes(), "")) holder.tvNotes.setVisibility(View.GONE);
        else {
            String note = '*' + district.getDistrictData().getNotes();
            holder.tvNotes.setText(note);
        }

        if(position == districts.size()-1) holder.divider.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return districts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDistrictName;
        TextView tvActive;
        TextView tvConfirmed;
        TextView tvMigratedother;
        TextView tvDeceased;
        TextView tvRecovered;
        TextView tvDeltaConfirmed;
        TextView tvDeltaDeceased;
        TextView tvDeltaRecovered;
        TextView tvNotes;
        View divider;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDistrictName = itemView.findViewById(R.id.tvDistrictName);
            tvActive = itemView.findViewById(R.id.tvActive);
            tvConfirmed = itemView.findViewById(R.id.tvConfirmed);
            tvMigratedother = itemView.findViewById(R.id.tvMigratedother);
            tvDeceased = itemView.findViewById(R.id.tvDeceased);
            tvRecovered = itemView.findViewById(R.id.tvRecovered);
            tvDeltaConfirmed = itemView.findViewById(R.id.tvDeltaConfirmed);
            tvDeltaDeceased = itemView.findViewById(R.id.tvDeltaDeceased);
            tvDeltaRecovered = itemView.findViewById(R.id.tvDeltaRecovered);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}
