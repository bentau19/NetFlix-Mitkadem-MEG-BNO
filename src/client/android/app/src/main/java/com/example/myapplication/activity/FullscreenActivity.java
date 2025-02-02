package com.example.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.MotionEvent;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityFullscreenBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

public class FullscreenActivity extends AppCompatActivity {
    private ActivityFullscreenBinding binding;
    private PlayerView playerView;
    private ExoPlayer player;
    private boolean mVisible;
    private int movieId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFullscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mVisible = true;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            movieId = intent.getIntExtra("id", -1);
        } else {
            movieId = -1;
        }
        playerView = binding.videoPlayerView;

        String videoUrl = "http://10.0.2.2:5000/api/movies/" + movieId + "/play";
        setupPlayer(videoUrl);

        binding.fullscreenContentControls.setVisibility(View.VISIBLE);

        playerView.setOnClickListener(view -> toggle());

        Button btnGoToMain = findViewById(R.id.dummyButton);

        btnGoToMain.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            finish();
        });
    }

    private void setupPlayer(String videoUrl) {

        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    private void toggle() {
        if (mVisible) {
            binding.fullscreenContentControls.setVisibility(View.GONE);
            mVisible = false;
        } else {
            binding.fullscreenContentControls.setVisibility(View.VISIBLE);
            mVisible = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}
