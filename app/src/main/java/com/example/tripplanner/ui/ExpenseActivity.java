package com.example.tripplanner.ui;



import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tripplanner.R;
import com.example.tripplanner.data.Expense;
import com.example.tripplanner.viewmodel.ExpenseViewModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExpenseActivity extends AppCompatActivity {
    private ExpenseViewModel expenseViewModel;
    private EditText editCategory, editAmount;
    private Button btnAddExpense;
    private RecyclerView recyclerExpenses;
    private TextView txtTotalExpense;
    private ExpenseAdapter expenseAdapter; // ✅ Keep a single instance of adapter
    private int tripId; // ✅ Get trip ID dynamically

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        editCategory = findViewById(R.id.editCategory);
        editAmount = findViewById(R.id.editAmount);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        recyclerExpenses = findViewById(R.id.recyclerExpenses);
        txtTotalExpense = findViewById(R.id.txtTotalExpense);

        recyclerExpenses.setLayoutManager(new LinearLayoutManager(this));
        recyclerExpenses.setHasFixedSize(true);

        expenseAdapter = new ExpenseAdapter(); // ✅ Initialize adapter
        recyclerExpenses.setAdapter(expenseAdapter); // ✅ Set adapter once

        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);

        // ✅ Get the trip ID from Intent (Prevent hardcoded ID)
        tripId = getIntent().getIntExtra("trip_id", -1);
        if (tripId == -1) {
            Toast.makeText(this, "Error: No trip selected!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        btnAddExpense.setOnClickListener(v -> addExpense());

        // ✅ Observe and update total expenses
        expenseViewModel.getTotalExpenseForTrip(tripId).observe(this, total -> {
            txtTotalExpense.setText("Total: $" + (total != null ? total : 0));
        });

        // ✅ Observe and update RecyclerView data
        expenseViewModel.getExpensesForTrip(tripId).observe(this, expenses -> {
            expenseAdapter.setExpenses(expenses); // ✅ Update adapter instead of recreating
        });
    }

    private void addExpense() {
        String category = editCategory.getText().toString().trim();
        String amountStr = editAmount.getText().toString().trim();

        // ✅ Validate input fields
        if (TextUtils.isEmpty(category) || TextUtils.isEmpty(amountStr)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) { // ✅ Prevent crash for non-numeric input
            Toast.makeText(this, "Invalid amount! Enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Expense expense = new Expense(tripId, category, amount, date);
        expenseViewModel.insert(expense);

        // ✅ Clear input fields after successful entry
        editCategory.setText("");
        editAmount.setText("");
    }
}
