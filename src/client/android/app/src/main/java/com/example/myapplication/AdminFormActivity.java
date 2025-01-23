package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.ui.viewmodel.AdminFormViewModel;
import com.example.myapplication.ui.viewmodel.SignupViewModel;

public class AdminFormActivity extends AppCompatActivity {
    private LinearLayout categoryForm;
    private LinearLayout movieForm;
    private Button createCategoryButton;
    private Button cancelCategoryButton;
    private Button createMovieButton;
    private Button cancelMovieButton;
    private AdminFormViewModel adminFormViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_form);
        adminFormViewModel = new ViewModelProvider(this).get(AdminFormViewModel.class);

        categoryForm = findViewById(R.id.categoryForm);
        movieForm = findViewById(R.id.movieForm);
        createCategoryButton = findViewById(R.id.createCategoryButton);
        cancelCategoryButton = findViewById(R.id.cancelCategoryButton);
        createMovieButton = findViewById(R.id.createMovieButton);
        cancelMovieButton = findViewById(R.id.cancelMovieButton);
        Intent intent = getIntent();
         String select = intent.getExtras().getString("select");
         Boolean isEditing = Boolean.valueOf(intent.getExtras().getString("isEditing"));

        assert select != null;
        if (select.equals("category")) {
            categoryForm.setVisibility(View.VISIBLE);  // Show Category Form
            movieForm.setVisibility(View.GONE);  // Hide Movie Form
        } else {
            movieForm.setVisibility(View.VISIBLE);  // Show Movie Form
            categoryForm.setVisibility(View.GONE);  // Hide Category Form
        }
        if(isEditing){
           // String itemId = intent.getExtras("itemId");
        }


        adminFormViewModel.getReqStatus().observe(this, status -> {
            // Show toast message based on signup status
            Toast.makeText(AdminFormActivity.this, status, Toast.LENGTH_SHORT).show();
        });

        EditText categoryNameEditText = findViewById(R.id.categoryName);
        CheckBox promotedCheckBox= findViewById(R.id.promoted);
        EditText movieTitleEditText = findViewById(R.id.movieTitle);
        EditText movieLoglineEditText = findViewById(R.id.movieLogline);
        EditText movieImageEditText = findViewById(R.id.movieImage);
        EditText movieCategoriesEditText = findViewById(R.id.movieCategories);

        createCategoryButton.setOnClickListener(v -> {
            String name = categoryNameEditText.getText().toString();
            boolean promoted =  promotedCheckBox.isActivated();
            adminFormViewModel.createCategory(name,promoted);
            adminFormViewModel.createCategory(name,promoted);
            Intent intentBack = new Intent(this, AdminActivity.class);
            intentBack.putExtra("select", select);
            startActivity(intentBack);
        });
        cancelCategoryButton.setOnClickListener(v -> {
            Intent intentBack = new Intent(this, AdminActivity.class);
            intentBack.putExtra("select", select);
            startActivity(intentBack);
        });
        createMovieButton.setOnClickListener(v -> {
            String title = movieTitleEditText.getText().toString();
            String logline = movieLoglineEditText.getText().toString();
            String image = movieImageEditText.getText().toString();
            String categories =  movieCategoriesEditText.getText().toString();
            adminFormViewModel.createMovie(title,logline,image,categories);
            Intent intentBack = new Intent(this, AdminActivity.class);
            intentBack.putExtra("select", select);
            startActivity(intentBack);
        });
        cancelMovieButton.setOnClickListener(v -> {
            Intent intentBack = new Intent(this, AdminActivity.class);
            intentBack.putExtra("select", select);
            startActivity(intentBack);
        });

    }
}
