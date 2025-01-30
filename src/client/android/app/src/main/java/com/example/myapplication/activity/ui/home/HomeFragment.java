package com.example.myapplication.activity.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.ParentAdapter;
import com.example.myapplication.dataModel.SharedViewModel;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.ui.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ParentAdapter parentAdapter;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout using ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Set up Parent RecyclerView
        RecyclerView parentRecyclerView = binding.parentRecyclerView;
        setupParentRecyclerView(parentRecyclerView);

        // Observe movie list LiveData
        homeViewModel.getMovieListLiveData().observe(getViewLifecycleOwner(), newList -> {
            if (parentAdapter != null) {
                parentAdapter.setData(newList);  // Update the adapter with the new list
                parentAdapter.notifyDataSetChanged();  // Force UI refresh
            }
        });

        // Observe search query LiveData
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getCatId().observe(getViewLifecycleOwner(), catId -> {
            if (catId != null) {
                if (catId.equals("all")) {
                    // Handle the "All" case
                    homeViewModel.fetchCategories("");
                } else {
                    if (catId.equals("main")) {
                        // Handle the "All" case
                        generateParentItemList(updatedList -> {
                            parentAdapter.setData(updatedList); // Update the adapter with the default list
                            parentAdapter.notifyDataSetChanged(); // Refresh the UI
                        });
                    } else {
                        homeViewModel.findCategoryByName(catId);
                    // Handle specific category selection
                    Log.d("CatId", "Selected category ID: " + catId);
                }}
            }
        });

        sharedViewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            if (query == null || query.isEmpty()) {
                Log.d("SearchQuery", "Query is empty, generating default list");
                // If query is empty, generate the default parent item list
                generateParentItemList(updatedList -> {
                    parentAdapter.setData(updatedList); // Update the adapter with the default list
                    parentAdapter.notifyDataSetChanged(); // Refresh the UI
                });
            } else {
                Log.d("SearchQuery", "New query: " + query);
                // If query is not empty, fetch movies based on the query
                homeViewModel.fetchMovies(query);
            }
        });

        return root;
    }

    // Setup the RecyclerView with the current data
    private void setupParentRecyclerView(RecyclerView parentRecyclerView) {
        // Create initial data
        generateParentItemList(updatedList -> {
            parentAdapter = new ParentAdapter(updatedList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            parentRecyclerView.setLayoutManager(layoutManager);
            parentRecyclerView.setAdapter(parentAdapter);
            Log.d("UpdatedList", "Parent Item List size: " + updatedList.size());
        });
    }

    // Generate the data for the Parent RecyclerView based on the counter value
    private void generateParentItemList(Consumer<List<Category>> callback) {
        List<Category> parentItemList = new ArrayList<>();
        homeViewModel.userRecommends();

        homeViewModel.getReqStatus().observe(getViewLifecycleOwner(), status -> {
            Log.d("HomeFragment", "Status: " + status);

            if (Objects.equals(status, "fetch successful!")) {
                homeViewModel.getMovieListLiveData().observe(getViewLifecycleOwner(), categoryList -> {
                    if (categoryList != null) {
                        parentItemList.clear(); // Clear existing data before adding new data
                        parentItemList.addAll(categoryList);
                    }

                    // After data is processed, return the updated parentItemList through callback
                    callback.accept(parentItemList);
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}