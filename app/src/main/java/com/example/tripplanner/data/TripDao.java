package com.example.tripplanner.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TripDao {
    @Insert
    void insert(Trip trip);

    @Update // ✅ Added update method
    void update(Trip trip);

    @Delete // ✅ Added delete method
    void delete(Trip trip);

    @Query("DELETE FROM trips WHERE id = :tripId") // ✅ Delete trip by ID
    void deleteTripById(int tripId);

    @Query("SELECT * FROM trips")
    LiveData<List<Trip>> getAllTrips();
}
