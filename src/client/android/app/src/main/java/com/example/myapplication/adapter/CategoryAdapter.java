package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<Category> categories = new ArrayList<>();
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEditClicked(Category category);
        void onDeleteClicked(Category category);
    }

    public void setCategories(List<Category> newCategories) {
        categories.clear();
        categories.addAll(newCategories);
        notifyDataSetChanged();
    }
    public void removeItem(String categoryId) {
        for (int i = 0; i < categories.size(); i++) {
            // Convert the movie ID to int before comparison
            if (categories.get(i).getId().equals(categoryId)) {
                categories.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }

    }
    public void setOnItemActionListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, promotedText, idText;
        Button editButton, deleteButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.item_title);
            promotedText = itemView.findViewById(R.id.item_description);
            idText = itemView.findViewById(R.id.item_id);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        public void bind(Category category) {
            titleText.setText(category.getName());
            promotedText.setText(category.isPromoted() ? "Promoted: Yes" : "Promoted: No");
            idText.setText("id: " + category.getId());
            editButton.setOnClickListener(v -> listener.onEditClicked(category));
            deleteButton.setOnClickListener(v -> listener.onDeleteClicked(category));
        }
    }
}
