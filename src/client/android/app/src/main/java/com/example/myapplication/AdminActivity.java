package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.activity.LogInActivity;
import com.example.myapplication.activity.loggedMain;
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

public class AdminActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;

    private AdminViewModel adminViewModel;
    private EditText searchInput;
    private Button btnSearch;
    private Button btnToMain;
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
        btnToMain = findViewById(R.id.backToMain);
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
        btnToMain.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, loggedMain.class);
            startActivity(intent);
        });
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
                new AlertDialog.Builder(AdminActivity.this)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this movie?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            adminViewModel.deleteMovie(String.valueOf(movie.getId()));
                            Toast.makeText(AdminActivity.this, "Movie deleted", Toast.LENGTH_SHORT).show();
                            // Remove specific item from adapter using int ID
                            movieAdapter.removeItem(movie.getId());
                        })
                        .setNegativeButton("No", null)
                        .show();
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
                new AlertDialog.Builder(AdminActivity.this)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure you want to delete this category?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            adminViewModel.deleteCategory(String.valueOf(category.getId()));
                            Toast.makeText(AdminActivity.this, "Movie deleted", Toast.LENGTH_SHORT).show();
                            // Remove specific item from adapter using int ID
                            categoryAdapter.removeItem(category.getId());
                        })
                        .setNegativeButton("No", null)
                        .show();
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




    private void observeLiveData() {
        // Set up observers once
        adminViewModel.getMovieListLiveData().observe(this, movies -> {
            if (movies != null) {
                movieAdapter.setMovies(movies);
                recyclerView.setAdapter(movieAdapter);
            }
        });

        adminViewModel.getCategoryListLiveData().observe(this, categories -> {
            if (categories != null) {
                categoryAdapter.setCategories(categories);
                recyclerView.setAdapter(categoryAdapter);
            }
        });
    }

    public void displayMovies(String query) {
        adminViewModel.fetchMovies(query);
    }

    public void displayCategories(String query) {
        adminViewModel.fetchCategories(query);
    }

}



