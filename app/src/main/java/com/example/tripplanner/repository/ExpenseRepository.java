package com.example.tripplanner.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.tripplanner.data.Expense;
import com.example.tripplanner.data.ExpenseDao;
import com.example.tripplanner.data.TripDatabase;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExpenseRepository {
    private final ExpenseDao expenseDao;
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor(); // ✅ Reused executor

    public ExpenseRepository(Application application) {
        TripDatabase db = TripDatabase.getInstance(application); // ✅ Fixed method name
        expenseDao = db.expenseDao();
    }

    public void insert(Expense expense) {
        executorService.execute(() -> expenseDao.insert(expense));
    }

    public void update(Expense expense) { // ✅ Added update method
        executorService.execute(() -> expenseDao.update(expense));
    }

    public void delete(Expense expense) { // ✅ Added delete method
        executorService.execute(() -> expenseDao.delete(expense));
    }

    public LiveData<List<Expense>> getExpensesForTrip(int tripId) {
        return expenseDao.getExpensesForTrip(tripId);
    }

    public LiveData<Double> getTotalExpenseForTrip(int tripId) {
        return expenseDao.getTotalExpenseForTrip(tripId);
    }
}

