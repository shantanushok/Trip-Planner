package com.example.tripplanner.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tripplanner.R;
import com.example.tripplanner.data.Expense;
import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {
    private List<Expense> expenses = new ArrayList<>(); // ✅ Initialize as empty list

    public void setExpenses(List<Expense> expenses) { // ✅ Update data dynamically
        this.expenses = expenses;
        notifyDataSetChanged(); // ✅ Refresh RecyclerView when data updates
    }

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.txtCategory.setText(expense.getCategory() != null ? expense.getCategory() : "Unknown");
        holder.txtAmount.setText("$" + String.format("%.2f", expense.getAmount())); // ✅ Format amount properly
    }

    @Override
    public int getItemCount() { return expenses.size(); } // ✅ Prevents NullPointerException

    static class ExpenseHolder extends RecyclerView.ViewHolder {
        TextView txtCategory, txtAmount;

        public ExpenseHolder(View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }
    }
}
