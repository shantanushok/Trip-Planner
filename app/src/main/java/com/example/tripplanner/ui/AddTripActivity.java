package com.example.tripplanner.ui;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.tripplanner.R;
import com.example.tripplanner.data.Trip;
import com.example.tripplanner.viewmodel.TripViewModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {
    private EditText edtStartLocation, edtDestination, edtStartDate, edtEndDate, edtBudget;
    private TripViewModel tripViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        edtStartLocation = findViewById(R.id.edtStartLocation);
        edtDestination = findViewById(R.id.edtDestination);
        edtStartDate = findViewById(R.id.edtStartDate);
        edtEndDate = findViewById(R.id.edtEndDate);
        edtBudget = findViewById(R.id.edtBudget);
        Button btnSaveTrip = findViewById(R.id.btnSaveTrip);

        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        btnSaveTrip.setOnClickListener(v -> saveTrip());
    }

    private void saveTrip() {
        String startLocation = edtStartLocation.getText().toString().trim();
        String destination = edtDestination.getText().toString().trim();
        String startDate = edtStartDate.getText().toString().trim();
        String endDate = edtEndDate.getText().toString().trim();
        String budgetStr = edtBudget.getText().toString().trim();

        if (TextUtils.isEmpty(startLocation) || TextUtils.isEmpty(destination) ||
                TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate) ||
                TextUtils.isEmpty(budgetStr)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidDate(startDate) || !isValidDate(endDate)) {
            Toast.makeText(this, "Invalid date format! Use yyyy-MM-dd", Toast.LENGTH_SHORT).show();
            return;
        }

        double budget;
        try {
            budget = Double.parseDouble(budgetStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid budget! Enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> startAddresses = geocoder.getFromLocationName(startLocation, 1);
            List<Address> destinationAddresses = geocoder.getFromLocationName(destination, 1);

            if (startAddresses.isEmpty() || destinationAddresses.isEmpty()) {
                Toast.makeText(this, "Unable to geocode addresses", Toast.LENGTH_SHORT).show();
                return;
            }

            Address startAddress = startAddresses.get(0);
            Address destinationAddress = destinationAddresses.get(0);

            double startLat = startAddress.getLatitude();
            double startLon = startAddress.getLongitude();
            double destLat = destinationAddress.getLatitude();
            double destLon = destinationAddress.getLongitude();

            Trip newTrip = new Trip(destination, startDate, endDate, budget, startLocation, startLat, startLon, destLat, destLon);
            tripViewModel.insert(newTrip);

            Toast.makeText(this, "Trip added successfully!", Toast.LENGTH_SHORT).show();

            edtStartLocation.setText("");
            edtDestination.setText("");
            edtStartDate.setText("");
            edtEndDate.setText("");
            edtBudget.setText("");

        } catch (Exception e) {
            Toast.makeText(this, "Geocoding failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

