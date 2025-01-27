package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.ui.viewmodel.AdminFormViewModel;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AdminFormActivity extends AppCompatActivity {
    private LinearLayout categoryForm, movieForm;
    private Button createCategoryButton, cancelCategoryButton, createMovieButton, cancelMovieButton, selectImageButton;
    private EditText categoryNameEditText, movieTitleEditText, movieLoglineEditText, movieCategoriesEditText;
    private ImageView movieImageView;
    private CheckBox promotedCheckBox;
    private Uri imageUri;
    private AdminFormViewModel adminFormViewModel;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_form);

        // Initialize Views
        categoryForm = findViewById(R.id.categoryForm);
        movieForm = findViewById(R.id.movieForm);
        createCategoryButton = findViewById(R.id.createCategoryButton);
        cancelCategoryButton = findViewById(R.id.cancelCategoryButton);
        createMovieButton = findViewById(R.id.createMovieButton);
        cancelMovieButton = findViewById(R.id.cancelMovieButton);
        selectImageButton = findViewById(R.id.selectImageButton);
        movieImageView = findViewById(R.id.movieImageView);
        categoryNameEditText = findViewById(R.id.categoryName);
        promotedCheckBox = findViewById(R.id.promoted);
        movieTitleEditText = findViewById(R.id.movieTitle);
        movieLoglineEditText = findViewById(R.id.movieLogline);
        movieCategoriesEditText = findViewById(R.id.movieCategories);

        // Initialize ViewModel
        adminFormViewModel = new ViewModelProvider(this).get(AdminFormViewModel.class);

        // Get Intent Data
        Intent intent = getIntent();
        String select = intent.getStringExtra("type");
        boolean isEditing = Boolean.parseBoolean(intent.getStringExtra("isEditing"));
        String id = intent.getStringExtra("id");

        // Show relevant form based on type (Category or Movie)
        if ("Category".equals(select)) {
            categoryForm.setVisibility(View.VISIBLE);
            movieForm.setVisibility(View.GONE);
            if (isEditing) {
                adminFormViewModel.getCategory(id).observe(this, category -> {
                    if (category != null) {
                        categoryNameEditText.setText(category.getName());
                        promotedCheckBox.setChecked(category.isPromoted());
                    }
                });
            }
        } else {
            movieForm.setVisibility(View.VISIBLE);
            categoryForm.setVisibility(View.GONE);
            if (isEditing) {
                adminFormViewModel.getMovie(id).observe(this, movie -> {
                    if (movie != null) {
                        movieTitleEditText.setText(movie.getTitle());
                        movieLoglineEditText.setText(movie.getLogline());
                        movieCategoriesEditText.setText(TextUtils.join(", ", movie.getCategories()));

                        // Display current image if available
                        if (movie.getImage() != null && !movie.getImage().isEmpty()) {
                            byte[] decodedString = Base64.decode(movie.getImage(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            movieImageView.setImageBitmap(decodedByte);
                        }
                    }
                });
            }
        }

        // Enable AutoFill for newer Android versions
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_YES);
        }

        // Select Image Button
        selectImageButton.setOnClickListener(v -> openImagePicker());

        // Create Category Button
        createCategoryButton.setOnClickListener(v -> {
            adminFormViewModel.createCategory(categoryNameEditText.getText().toString(), promotedCheckBox.isChecked());
            navigateToAdmin(select);
        });

        // Create Movie Button
        createMovieButton.setOnClickListener(v -> {
            String title = movieTitleEditText.getText().toString();
            String logline = movieLoglineEditText.getText().toString();
            String categories = movieCategoriesEditText.getText().toString();

            // Convert image to Base64 if available
            String imageBase64 = null;
            if (imageUri != null) {
                try {
                    imageBase64 = encodeImageToBase64(imageUri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (isEditing) {
                // Update existing movie
                adminFormViewModel.updateMovie(id, title, logline, imageBase64, categories);
            } else {
                // Create new movie
                adminFormViewModel.createMovie(title, logline, imageBase64, categories);
            }
            navigateToAdmin(select);
        });

        // Cancel Buttons
        cancelCategoryButton.setOnClickListener(v -> navigateToAdmin(select));
        cancelMovieButton.setOnClickListener(v -> navigateToAdmin(select));
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            // Show the selected image in the ImageView
            movieImageView.setImageURI(imageUri);
        }
    }

    private String encodeImageToBase64(Uri imageUri) throws Exception {
        InputStream inputStream = getContentResolver().openInputStream(imageUri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void navigateToAdmin(String select) {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.putExtra("select", select);
        startActivity(intent);
    }
}
