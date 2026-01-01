package com.example.tripplanner.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.tripplanner.data.Trip;
import com.example.tripplanner.repository.TripRepository;
import java.util.List;

public class TripViewModel extends AndroidViewModel {
    private final TripRepository repository;
    private final LiveData<List<Trip>> allTrips; // ✅ Store LiveData reference

    public TripViewModel(Application application) {
        super(application);
        repository = new TripRepository(application);
        allTrips = repository.getAllTrips(); // ✅ Initialize once
    }

    public void insert(Trip trip) {
        repository.insert(trip);
    }

    public void update(Trip trip) { // ✅ Added update method
        repository.update(trip);
    }

    public void delete(Trip trip) { // ✅ Delete by Trip object
        repository.delete(trip);
    }

    public void deleteTripById(int tripId) { // ✅ Added delete method by ID
        repository.deleteTripById(tripId);
    }

    public LiveData<List<Trip>> getAllTrips() {
        return allTrips;
    }
}
