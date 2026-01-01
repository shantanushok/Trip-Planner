package com.example.tripplanner.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.tripplanner.data.Expense;
import com.example.tripplanner.repository.ExpenseRepository;
import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository repository;

    public ExpenseViewModel(Application application) {
        super(application);
        repository = new ExpenseRepository(application);
    }

    public void insert(Expense expense) {
        repository.insert(expense);
    }

    public LiveData<List<Expense>> getExpensesForTrip(int tripId) {
        return repository.getExpensesForTrip(tripId);
    }

    public LiveData<Double> getTotalExpenseForTrip(int tripId) {
        return repository.getTotalExpenseForTrip(tripId);
    }
}
