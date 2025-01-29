package com.example.myapplication.dataModel;

import android.view.View;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class VideoHeaderViewHolder extends RecyclerView.ViewHolder {
    VideoView videoView;

    public VideoHeaderViewHolder(View itemView) {
        super(itemView);
        videoView = itemView.findViewById(R.id.videoView);
    }
}