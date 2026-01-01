package com.example.tripplanner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.R;
import com.example.tripplanner.data.Trip;
import com.example.tripplanner.viewmodel.TripViewModel;

public class MainActivity extends AppCompatActivity {
    private TripViewModel tripViewModel;
    private TripAdapter tripAdapter;
    private Button btnAddTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTrip = findViewById(R.id.btnAddTrip);
        RecyclerView recyclerViewTrips = findViewById(R.id.recyclerViewTrips);
        recyclerViewTrips.setLayoutManager(new LinearLayoutManager(this));

        tripAdapter = new TripAdapter(new TripAdapter.OnTripClickListener() {
            @Override
            public void onTripClick(Trip trip) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                intent.putExtra("trip_id", trip.getId());
                startActivity(intent);
            }

            @Override
            public void onTripDelete(int tripId) {
                tripViewModel.deleteTripById(tripId);
                Toast.makeText(MainActivity.this, "Trip deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTripMapClick(Trip trip) {
                Intent intent = new Intent(MainActivity.this, RouteActivity.class);
                intent.putExtra("startLat", trip.getStartLat());
                intent.putExtra("startLon", trip.getStartLon());
                intent.putExtra("endLat", trip.getDestinationLat());
                intent.putExtra("endLon", trip.getDestinationLon());
                startActivity(intent);
            }
        });

        recyclerViewTrips.setAdapter(tripAdapter);

        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripViewModel.getAllTrips().observe(this, trips -> tripAdapter.submitList(trips));

        btnAddTrip.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
            startActivity(intent);
        });
    }
}

