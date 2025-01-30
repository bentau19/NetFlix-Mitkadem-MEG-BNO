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
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Set up Parent RecyclerView
        RecyclerView parentRecyclerView = binding.parentRecyclerView;
        setupParentRecyclerView(parentRecyclerView);

        return root;
    }

    // Setup the RecyclerView with the current data
    private void setupParentRecyclerView(RecyclerView parentRecyclerView) {
        // Create initial data
        generateParentItemList( updatedList -> {
            List<Category> parentItemList =updatedList;
            parentAdapter = new ParentAdapter(parentItemList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            parentRecyclerView.setLayoutManager(layoutManager);
            parentRecyclerView.setAdapter(parentAdapter);
                    // Handle the updated list here
            Log.d("UpdatedList", "Parent Item List size: " + updatedList.size());
        });

        // Set up the ParentAdapter

    }

    // Generate the data for the Parent RecyclerView based on the counter value
    private void generateParentItemList( Consumer<List<Category>> callback) {
        List<Category> parentItemList = new ArrayList<>();
        homeViewModel.userRecommends();

        homeViewModel.getReqStatus().observe(getViewLifecycleOwner(), status -> {
            Log.d("HomeFragment", "Status: " + status);

            if (Objects.equals(status, "fetch successful!")) {
                homeViewModel.getMovieListLiveData().observe(getViewLifecycleOwner(), categoryList -> {
                    if (categoryList != null) {
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
