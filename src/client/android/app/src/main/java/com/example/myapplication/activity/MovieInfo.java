package com.example.myapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ImageUtils;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.adapters.movieAdapter;
import com.example.myapplication.ui.viewmodel.MovieInfoViewModel;

import java.util.List;

public class MovieInfo extends AppCompatActivity {

    private int movieId;
    private RecyclerView recommendedMoviesRecyclerView;
    private movieAdapter movieAdapter;
    private List<Movie> recommendedMoviesList;
    private MovieInfoViewModel movieInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        // Initialize ViewModel
        movieInfoViewModel = new ViewModelProvider(this).get(MovieInfoViewModel.class);

        // Initialize views
        ImageView movieImageView = findViewById(R.id.movieImageView);
        TextView titleTextView = findViewById(R.id.movieTitleTextView);
        TextView loglineTextView = findViewById(R.id.movieLoglineTextView);
        Button playButton = findViewById(R.id.playButton);
        recommendedMoviesRecyclerView = findViewById(R.id.recommendedMoviesRecyclerView);
        recommendedMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get data from intent
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("MOVIE_TITLE");
            String logline = intent.getStringExtra("MOVIE_LOGLINE");
            String imageHex = intent.getStringExtra("MOVIE_IMAGE");
            movieId = intent.getIntExtra("id", -1);

            titleTextView.setText(title);
            loglineTextView.setText(logline);
            if (imageHex != null && !imageHex.isEmpty()) {
                Bitmap bitmap = ImageUtils.hexToImage(imageHex);
                movieImageView.setImageBitmap(bitmap);
            }
        }

        // Set up Play button click listener
        playButton.setOnClickListener(v -> {
            movieInfoViewModel.watched(String.valueOf(movieId));
            Intent intent2 = new Intent(this, FullscreenActivity.class);
            intent2.putExtra("id", movieId);
            startActivity(intent2);
        });

        // Observe LiveData for movie details
        movieInfoViewModel.getMovieLiveData().observe(this, movie -> {
            if (movie != null) {
                titleTextView.setText(movie.getTitle());
                loglineTextView.setText(movie.getLogline());
                if (movie.getImage() != null && !movie.getImage().isEmpty()) {
                    Bitmap bitmap = ImageUtils.hexToImage(movie.getImage());
                    movieImageView.setImageBitmap(bitmap);
                }
            }
        });
        movieInfoViewModel.fetchRecommendedMovies(String.valueOf(movieId));        // Observe LiveData for recommended movies
        movieInfoViewModel.getRecommendedMoviesLiveData().observe(this, recommendedMovies -> {
            if (recommendedMovies != null) {
                movieAdapter = new movieAdapter(recommendedMovies);
                recommendedMoviesRecyclerView.setAdapter(movieAdapter);
            }
        });
        Button btnGoToMain = findViewById(R.id.button4);

        btnGoToMain.setOnClickListener(view -> {
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            finish();
        });
    }
}