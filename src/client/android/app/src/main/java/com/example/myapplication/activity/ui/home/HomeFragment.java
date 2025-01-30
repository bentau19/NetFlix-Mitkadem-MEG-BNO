package com.example.myapplication.activity.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.Movie;
import com.example.myapplication.adapter.ParentAdapter;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.ui.viewmodel.AdminViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private int counter = 3;  // Initial counter value
    private ParentAdapter parentAdapter;
    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout using ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Set up Parent RecyclerView
        RecyclerView parentRecyclerView = binding.parentRecyclerView;
        setupParentRecyclerView(parentRecyclerView);

        // Button click listener to dynamically update counter and refresh RecyclerView
        Button button = binding.getRoot().findViewById(R.id.button3);
        button.setOnClickListener(v -> {
            counter++;  // Increment the counter
            // Dynamically update the list and notify the adapter to refresh
            List<List<Movie>> updatedParentItemList = generateParentItemList(counter);
            parentAdapter.updateData(updatedParentItemList);
        });

        return root;
    }

    // Setup the RecyclerView with the current data
    private void setupParentRecyclerView(RecyclerView parentRecyclerView) {
        // Create initial data
        List<List<Movie>> parentItemList = generateParentItemList(counter);

        // Set up the ParentAdapter
        parentAdapter = new ParentAdapter(parentItemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        parentRecyclerView.setLayoutManager(layoutManager);
        parentRecyclerView.setAdapter(parentAdapter);
    }

    // Generate the data for the Parent RecyclerView based on the counter value
    private List<List<Movie>> generateParentItemList(int rows) {
        List<List<Movie>> parentItemList = new ArrayList<>();
        homeViewModel.userRecommends();
        homeViewModel.getReqStatus().observe(getViewLifecycleOwner(), status -> {
            Log.d("HomeFragment", "Status: " + status);
            if(Objects.equals(status, "fetch successful!")){
                homeViewModel.getMovieListLiveData().observe(getViewLifecycleOwner(), categoryList -> {
                    if (categoryList != null) {

                        for (Category category : categoryList) {
                            List<Movie> childItemList = new ArrayList<>();
                            Log.d("Category", "Category Name: " + category.getName());
                            for (Movie movie : category.getMovies()) {
                                childItemList.add(new Movie("Item " ));
                                Log.d("Movie", "Movie Title: " + movie.getTitle());
                            }
                            parentItemList.add(childItemList);
                        }

                    }

            });
        }});
        return parentItemList;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
