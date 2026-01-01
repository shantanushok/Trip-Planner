package com.example.tripplanner.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.tripplanner.data.Trip;
import com.example.tripplanner.data.TripDao;
import com.example.tripplanner.data.TripDatabase;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TripRepository {
    private final TripDao tripDao;
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor(); // ✅ Reused executor

    public TripRepository(Application application) {
        TripDatabase db = TripDatabase.getInstance(application); // ✅ Fixed method name
        tripDao = db.tripDao();
    }

    public void insert(Trip trip) {
        executorService.execute(() -> tripDao.insert(trip));
    }

    public void update(Trip trip) { // ✅ Added update method
        executorService.execute(() -> tripDao.update(trip));
    }

    public void delete(Trip trip) { // ✅ Added delete method
        executorService.execute(() -> tripDao.delete(trip));
    }
    public void deleteTripById(int tripId) { // ✅ Now using ExecutorService
        executorService.execute(() -> tripDao.deleteTripById(tripId));
    }
    public LiveData<List<Trip>> getAllTrips() {
        return tripDao.getAllTrips();
    }
}

