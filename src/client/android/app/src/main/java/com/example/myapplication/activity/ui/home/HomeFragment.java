package com.example.myapplication.activity.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ParentAdapter;

import com.example.myapplication.dataModel.Movie;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // TextView binding example
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Parent RecyclerView setup
        RecyclerView parentRecyclerView = binding.parentRecyclerView; // Ensure your XML has a RecyclerView with the ID 'parentRecyclerView'
        setupParentRecyclerView(parentRecyclerView);

        return root;
    }

    private void setupParentRecyclerView(RecyclerView parentRecyclerView) {
        // Create a list of lists for the parent RecyclerView
        List<List<Movie>> parentItemList = new ArrayList<>();
        for (int i = 0; i < 20; i++) { // 5 rows of horizontal RecyclerViews
            List<Movie> childItemList = new ArrayList<>();
            for (int j = 1; j <= 20; j++) { // 20 items per row
                childItemList.add(new Movie("Item " + j));
            }
            parentItemList.add(childItemList);
        }

        // Set up Parent RecyclerView with vertical LinearLayoutManager and Adapter
        ParentAdapter parentAdapter = new ParentAdapter(parentItemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        parentRecyclerView.setLayoutManager(layoutManager);
        parentRecyclerView.setAdapter(parentAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}