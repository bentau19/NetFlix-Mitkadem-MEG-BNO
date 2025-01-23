package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    private Spinner movieCategorySelect;
    private Button addButton;
    private String select;
    private boolean isEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        movieCategorySelect = findViewById(R.id.movieCategorySelect);
        addButton = findViewById(R.id.addButton);

        // Handle Spinner Selection
        movieCategorySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCategory = movieCategorySelect.getSelectedItem().toString();

                // Show the corresponding form based on the selection
                if (selectedCategory.equals("Category")) {
                    select = "category"; // Hide Movie Form
                } else {
                    select = "movie";  // Hide Category Form
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Optional: Handle the case when nothing is selected
            }
        });


        // Add Button Logic to Open Forms
        addButton.setOnClickListener(v -> {
            isEditing = false;
            Intent intent = new Intent(this, AdminFormActivity.class);
            intent.putExtra("select", select);
            intent.putExtra("isEditing", isEditing);
            startActivity(intent);
        });
    }
}
