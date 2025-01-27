package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.ImageUtils;
import com.example.myapplication.adapter.Movie;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.BaseViewHolder> {
    private List<Object> items = new ArrayList<>();
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEditClicked(Object item);
        void onDeleteClicked(Object item);
    }

    public void setItems(List<Object> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void setOnItemActionListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = items.get(position);
        if (item instanceof Movie) return 0;
        if (item instanceof Category) return 1;
        return -1;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_layout, parent, false);

        switch (viewType) {
            case 0: return new MovieViewHolder(view);
            case 1: return new CategoryViewHolder(view);
            default: return new BaseViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Object item = items.get(position);

        if (holder instanceof MovieViewHolder && item instanceof Movie) {
            ((MovieViewHolder) holder).bind((Movie) item);
        } else if (holder instanceof CategoryViewHolder && item instanceof Category) {
            ((CategoryViewHolder) holder).bind((Category) item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MovieViewHolder extends BaseViewHolder {
        TextView titleText, loglineText, categoriesText;
        ImageView imageView;
        Button editButton, deleteButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.item_title);
            loglineText = itemView.findViewById(R.id.item_description);
            imageView = itemView.findViewById(R.id.item_image);
            categoriesText = itemView.findViewById(R.id.categories_text);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        public void bind(Movie movie) {
            titleText.setText(movie.getTitle());
            loglineText.setText(movie.getLogline());

            // Convert and set image
            if (movie.getImage() != null) {
                imageView.setImageBitmap(ImageUtils.hexToImage(movie.getImage()));
            } else {
                imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            }

            // Set categories
            if (movie.getCategories() != null && !movie.getCategories().isEmpty()) {
                categoriesText.setText(String.join(", ", movie.getCategories()));
            } else {
                categoriesText.setText("No Categories");
            }

            // Set button listeners
            editButton.setOnClickListener(v -> listener.onEditClicked(movie));
            deleteButton.setOnClickListener(v -> listener.onDeleteClicked(movie));
        }
    }

    public class CategoryViewHolder extends BaseViewHolder {
        TextView titleText, promotedText;
        Button editButton, deleteButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.item_title);
            promotedText = itemView.findViewById(R.id.item_description);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        public void bind(Category category) {
            titleText.setText(category.getDisplayName());
            promotedText.setText(category.isPromoted() ? "Promoted: Yes" : "Promoted: No");

            editButton.setOnClickListener(v -> listener.onEditClicked(category));
            deleteButton.setOnClickListener(v -> listener.onDeleteClicked(category));
        }
    }
}