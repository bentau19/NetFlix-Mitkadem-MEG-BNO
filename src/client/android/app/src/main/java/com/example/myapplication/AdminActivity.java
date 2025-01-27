package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.adapter.MovieAdapter;
import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.example.myapplication.ui.viewmodel.AdminViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements MovieAdapter.OnItemActionListener , CategoryAdapter.OnItemActionListener  {

    private RecyclerView recyclerView;

    private AdminViewModel adminViewModel;
    private EditText searchInput;
    private Button btnSearch;
    private MovieAdapter movieAdapter; // Custom RecyclerView Adapter
    private CategoryAdapter categoryAdapter; // Custom RecyclerView Adapter
    private Spinner movieCategorySelect;
    private boolean isSpinnerInitialized = false; // Prevent initial trigger
     private String type = "Movie";
    private Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        // Initialize adapters
        movieAdapter = new MovieAdapter();
        categoryAdapter = new CategoryAdapter();

        recyclerView = findViewById(R.id.item_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieCategorySelect = findViewById(R.id.movieCategorySelect);
        btnAdd = findViewById(R.id.addButton);
        btnSearch = findViewById(R.id.searchButton);
        searchInput = findViewById(R.id.searchInput);

        adminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        // Observe LiveData for movies and categories
        observeLiveData();

        // Set default type
        adminViewModel.fetchMovies(" ");  // Initially fetch movies when activity loads

        setupSpinnerListener();
        setupSearchListener();

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, AdminFormActivity.class);
            intent.putExtra("type", type);
            intent.putExtra("isEditing", false);
            startActivity(intent);
        });


        // Set the listener for MovieAdapter
        movieAdapter.setOnItemActionListener(new MovieAdapter.OnItemActionListener() {
            @Override
            public void onEditClicked(Movie movie) {
                // Handle edit button click
                Intent intent = new Intent(AdminActivity.this, AdminFormActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("isEditing", true);
                intent.putExtra("id", String.valueOf(movie.getId())); // Pass the movie ID to edit
                startActivity(intent);
                // Handle edit action
            }

            @Override
            public void onDeleteClicked(Movie movie) {
                // Handle delete action for movie
                adminViewModel.deleteMovie(String.valueOf(movie.getId()));
                Toast.makeText(AdminActivity.this, "Movie deleted", Toast.LENGTH_SHORT).show();
                displayMovies("");

            }
        });

// Set the listener for CategoryAdapter
        categoryAdapter.setOnItemActionListener(new CategoryAdapter.OnItemActionListener() {
            @Override
            public void onEditClicked(Category category) {
                Intent intent = new Intent(AdminActivity.this, AdminFormActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("isEditing", true);
                intent.putExtra("id", String.valueOf(category.getId())); // Pass the movie ID to edit
                startActivity(intent);
                // Handle edit action for category
            }

            @Override
            public void onDeleteClicked(Category category) {
                // Handle delete action for category
                adminViewModel.deleteCategory(String.valueOf(category.getId()));
                Toast.makeText(AdminActivity.this, "Category deleted", Toast.LENGTH_SHORT).show();
                displayCategories("");

            }
        });

    }

    private void observeLiveData() {
        // Observe the movie list LiveData
        adminViewModel.getMovieListLiveData().observe(this, movies -> {
            if (movies != null) {
                movieAdapter.setMovies(movies);
                recyclerView.setAdapter(movieAdapter);
            }
        });

        // Observe the category list LiveData
        adminViewModel.getCategoryListLiveData().observe(this, categories -> {
            if (categories != null) {
                categoryAdapter.setCategories(categories);
                recyclerView.setAdapter(categoryAdapter);
            }
        });
    }


    private void setupSearchListener() {
        btnSearch.setOnClickListener(view -> {
            if (type.equals("Category")) {
                displayCategories(searchInput.getText().toString());
            } else {
               displayMovies(searchInput.getText().toString());
            }
        });
    }

    private void setupSpinnerListener() {
        movieCategorySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!isSpinnerInitialized) {
                    isSpinnerInitialized = true;
                    return;
                }

                type = movieCategorySelect.getSelectedItem().toString();

                if (type.equals("Category")) {
                    displayCategories("");
                    // Fetch all categories
                } else {
                    displayMovies("");
// Fetch all movies
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(AdminActivity.this, "Please select a category", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onEditClicked(Movie movie) {
        // Handle edit button click
        Intent intent = new Intent(AdminActivity.this, AdminFormActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("isEditing", true);
        intent.putExtra("id", String.valueOf(movie.getId())); // Pass the movie ID to edit
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(Movie movie) {
        // Handle delete button click
        adminViewModel.deleteMovie(String.valueOf(movie.getId()));
        Toast.makeText(this, "Movie deleted2", Toast.LENGTH_SHORT).show();
        displayMovies("");
    }


    @Override
    public void onEditClicked(Category category) {
        // Handle edit button click
        Intent intent = new Intent(AdminActivity.this, AdminFormActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("isEditing", true);
        intent.putExtra("id", String.valueOf(category.getId())); // Pass the movie ID to edit
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(Category category) {
        // Handle edit button click
        Intent intent = new Intent(AdminActivity.this, AdminFormActivity.class);
        intent.putExtra("type", "Movie");
        intent.putExtra("movieId", String.valueOf(category.getId())); // Pass the movie ID to edit
        Toast.makeText(this, "Movie deleted2", Toast.LENGTH_SHORT).show();

        startActivity(intent);

    }

    public void displayCategories(String query){
        adminViewModel.fetchCategories(query);
        adminViewModel.getReqStatus().observe(this, status -> {
            if (status != null) {
                // Compare the actual value of status (which is a String) here
                if (status.equals("fetch successful!")) {
                    adminViewModel.getCategoryListLiveData().observe(this, categories -> {
                        if (categories != null) {
                            categoryAdapter.setCategories(categories);
                            recyclerView.setAdapter(categoryAdapter);
                        }
                    });
                    // Handle success case
                }
            }
        });

    }
    public void displayMovies(String query){
        adminViewModel.fetchMovies(searchInput.getText().toString());
        adminViewModel.getReqStatus().observe(this, status -> {
            if (status != null) {
                // Compare the actual value of status (which is a String) here
                if (status.equals("fetch successful!")) {
                    adminViewModel.getMovieListLiveData().observe(this, movies -> {
                        if (movies != null) {
                            movieAdapter.setMovies(movies);
                            recyclerView.setAdapter(movieAdapter);
                        }
                    });
                    // Handle success case
                }
            }
        });

    }
}



