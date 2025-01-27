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

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies = new ArrayList<>();
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEditClicked(Movie movie);
        void onDeleteClicked(Movie movie);
    }

    public void setMovies(List<Movie> newMovies) {
        movies.clear();
        movies.addAll(newMovies);
        notifyDataSetChanged();
    }

    public void setOnItemActionListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, loglineText, categoriesText , idText;
        ImageView imageView;
        Button editButton, deleteButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.item_title);
            idText = itemView.findViewById(R.id.item_id);
            loglineText = itemView.findViewById(R.id.item_logline);
            imageView = itemView.findViewById(R.id.item_image);
            categoriesText = itemView.findViewById(R.id.categories_text);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        public void bind(Movie movie) {
            titleText.setText(movie.getTitle());
            loglineText.setText("logline: " + movie.getLogline());
            idText.setText("id: " + movie.getId());

            // Convert and set image
            if (movie.getImage() != null) {
                imageView.setImageBitmap(ImageUtils.hexToImage(movie.getImage()));
            } else {
                imageView.setImageResource(android.R.drawable.ic_menu_gallery);
            }

            // Set categories
            if (movie.getCategories() != null && !movie.getCategories().isEmpty()) {
                StringBuilder categories = new StringBuilder();
                for (int i = 0; i < movie.getCategories().size(); i++) {
                    categories.append(movie.getCategories().get(i));
                    if (i < movie.getCategories().size() - 1) {
                        categories.append(", "); // Add comma only between elements
                    }
                }
                categoriesText.setText("categotirs: " + categories.toString());
            } else {
                categoriesText.setText("No Categories");
            }


            // Set button listeners
            editButton.setOnClickListener(v -> listener.onEditClicked(movie));
            deleteButton.setOnClickListener(v -> listener.onDeleteClicked(movie));
        }
    }
}
