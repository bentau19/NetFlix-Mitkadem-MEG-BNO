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

import com.example.myapplication.adapters.movieAdapter;
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
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // RecyclerView setup
        RecyclerView recyclerView = binding.recyclerView; // Ensure your XML has a RecyclerView with the ID 'recyclerView'
        setupRecyclerView(recyclerView);

        return root;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        // Create a dynamic list of items
        List<Movie> itemList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            itemList.add(new Movie("Item " + i));
        }

        // Set up RecyclerView with horizontal LinearLayoutManager and Adapter
        movieAdapter adapter = new movieAdapter(itemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
