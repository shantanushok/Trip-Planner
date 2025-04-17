package com.example.tripplanner.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.R;
import com.example.tripplanner.data.Trip;

public class TripAdapter extends ListAdapter<Trip, TripAdapter.TripViewHolder> {
    private final OnTripClickListener onTripClickListener;

    public interface OnTripClickListener {
        void onTripClick(Trip trip);
        void onTripDelete(int tripId);
        void onTripMapClick(Trip trip); // New method
    }

    public TripAdapter(OnTripClickListener onTripClickListener) {
        super(DIFF_CALLBACK);
        this.onTripClickListener = onTripClickListener;
    }

    private static final DiffUtil.ItemCallback<Trip> DIFF_CALLBACK = new DiffUtil.ItemCallback<Trip>() {
        @Override
        public boolean areItemsTheSame(@NonNull Trip oldItem, @NonNull Trip newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Trip oldItem, @NonNull Trip newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = getItem(position);
        holder.txtDestination.setText(trip.getDestination());
        holder.txtDates.setText(trip.getStartDate() + " - " + trip.getEndDate());
        holder.txtBudget.setText("Budget: $" + trip.getBudget());

        holder.itemView.setOnClickListener(v -> onTripClickListener.onTripClick(trip));
        holder.btnDeleteTrip.setOnClickListener(v -> onTripClickListener.onTripDelete(trip.getId()));
        holder.btnMapTrip.setOnClickListener(v -> onTripClickListener.onTripMapClick(trip)); // New map button
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView txtDestination, txtDates, txtBudget;
        ImageButton btnDeleteTrip, btnMapTrip;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDestination = itemView.findViewById(R.id.txtDestination);
            txtDates = itemView.findViewById(R.id.txtDates);
            txtBudget = itemView.findViewById(R.id.txtBudget);
            btnDeleteTrip = itemView.findViewById(R.id.btnDeleteTrip);
            btnMapTrip = itemView.findViewById(R.id.btnMapTrip); // New
        }
    }
}

