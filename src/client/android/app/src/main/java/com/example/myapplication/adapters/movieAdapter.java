package com.example.myapplication.adapters;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ImageUtils;
import com.example.myapplication.adapter.Movie;

import java.util.List;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.ItemViewHolder> {

    private List<Movie> items;


    public movieAdapter(List<Movie> items) {
        this.items = items;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Movie item = items.get(position);
        holder.textView.setText(item.getTitle());
        if (item.getImage() != null && !item.getImage().isEmpty()) {
            Bitmap selectedImageBitmap = ImageUtils.hexToImage(item.getImage());
            holder.imageView.setImageBitmap(selectedImageBitmap);
        }

        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {

                Log.d("movieAdapter", "Item clicked: " + item.getTitle());

        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.itemTextView);
            imageView = itemView.findViewById(R.id.imageView4);
        }
    }
}
