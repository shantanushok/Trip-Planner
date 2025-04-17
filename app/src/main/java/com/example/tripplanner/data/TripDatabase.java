package com.example.tripplanner.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Trip.class, Expense.class}, version = 5) // ✅ Added Expense.class
public abstract class TripDatabase extends RoomDatabase {

    private static volatile TripDatabase instance; // ✅ Added volatile for thread safety

    public abstract TripDao tripDao();
    public abstract ExpenseDao expenseDao(); // ✅ Added ExpenseDao

    public static synchronized TripDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TripDatabase.class, "trip_database")
                    .fallbackToDestructiveMigration()
                    // .allowMainThreadQueries() // ⚠️ Uncomment for testing, remove in production
                    .build();
        }
        return instance;
    }
}
