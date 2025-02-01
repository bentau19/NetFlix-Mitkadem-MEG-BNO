package com.example.myapplication.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.movieAdapter;

import java.util.List;
public class ParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Category> parentItemList;

    public ParentAdapter(List<Category> parentItemList) {
        this.parentItemList = parentItemList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER; // First item is the header
        }
        return TYPE_ITEM; // Other items are regular items
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            // Inflate the header layout
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_header, parent, false);
            return new HeaderViewHolder(headerView);
        } else {
            // Inflate the regular parent item layout
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_recyclerview, parent, false);
            return new ParentViewHolder(view);
        }
    }
    public void setData(List<Category> newList) {
        Log.d("ParentAdapter", "Setting data with size: " + newList.size());
        this.parentItemList.clear();
        this.parentItemList.addAll(newList);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            // Bind header data if needed
            ((HeaderViewHolder) holder).bind();
        } else {
            // Adjust position for header
            Category category = parentItemList.get(position - 1); // Get the category
            ((ParentViewHolder) holder).bind(category); // Pass category to bind method
        }
    }

    @Override
    public int getItemCount() {
        return parentItemList.size() + 1; // +1 for the header
    }

    public void updateData(List<Category> newParentItemList) {
        this.parentItemList = newParentItemList;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    // Header ViewHolder
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private VideoView videoView;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
//            videoView = itemView.findViewById(R.id.videoView);
        }

        void bind() {
            // Set up the VideoView (e.g., load video, start playback)
            // Example:
            // videoView.setVideoPath("https://example.com/video.mp4");
            // videoView.start();
        }
    }

    // Parent ViewHolder
    static class ParentViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView childRecyclerView;
        private TextView categoryTitle;  // Reference to category title

        ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);  // Initialize title reference
        }

        public void bind(Category category) {
            // Set category name to the title TextView
            categoryTitle.setText(category.getName());

            // Set up child RecyclerView
            movieAdapter childAdapter = new movieAdapter(category.getMovies());
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            childRecyclerView.setLayoutManager(layoutManager);
            childRecyclerView.setAdapter(childAdapter);
        }
    }
}
