package com.example.tripplanner.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insert(Expense expense);

    @Update // ✅ Added update method
    void update(Expense expense);

    @Delete // ✅ Added delete method
    void delete(Expense expense);

    @Query("SELECT * FROM expenses WHERE tripId = :tripId")
    LiveData<List<Expense>> getExpensesForTrip(int tripId);

    @Query("SELECT SUM(amount) FROM expenses WHERE tripId = :tripId")
    LiveData<Double> getTotalExpenseForTrip(int tripId);
}
