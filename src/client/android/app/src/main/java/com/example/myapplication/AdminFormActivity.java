package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.AdminActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ImageUtils;
import com.example.myapplication.ui.viewmodel.AdminFormViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class AdminFormActivity extends AppCompatActivity {
    private LinearLayout categoryForm, movieForm;
    private Button createCategoryButton, cancelCategoryButton, createMovieButton, cancelMovieButton, selectImageButton;
    private EditText categoryNameEditText, movieTitleEditText, movieLoglineEditText, movieCategoriesEditText;
    private ImageView movieImageView;
    private Bitmap selectedImageBitmap;

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
        boolean isEditing = intent.getBooleanExtra("isEditing", false);
        String id = intent.getStringExtra("id");
        assert select != null;
        Log.d("AdminFormActivity",  select);  // Verify the category name


        // Show relevant form based on type (Category or Movie)
        if ("Category".equals(select)) {
            categoryForm.setVisibility(View.VISIBLE);
            movieForm.setVisibility(View.GONE);
            if (isEditing) {
                createCategoryButton.setText("Edit Category");
                adminFormViewModel.getCategory(id).observe(this, category -> {
                    if (category != null) {
                        Log.d("AdminFormActivity", "Category name: " + category.getName());  // Verify the category name
                        categoryNameEditText.setText(category.getName());  // Ensure this is being set
                        promotedCheckBox.setChecked(category.isPromoted());
                    }
                });

            }
        } else {
            movieForm.setVisibility(View.VISIBLE);
            categoryForm.setVisibility(View.GONE);
            if (isEditing) {
                createCategoryButton.setText("Edit Movie");

                adminFormViewModel.getMovie(id).observe(this, movie -> {
                    if (movie != null) {
                        movieTitleEditText.setText(movie.getTitle());
                        movieLoglineEditText.setText(movie.getLogline());

                        // Convert the array of category IDs to comma-separated string
                        List<Integer> categories = movie.getCategories();
                        String categoryString = categories.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(", "));
                        movieCategoriesEditText.setText(categoryString);

                        selectedImageBitmap = ImageUtils.hexToImage(movie.getImage());
                        if (selectedImageBitmap != null) {
                            // Use the bitmap (e.g., set it to an ImageView)
                            movieImageView.setImageBitmap(selectedImageBitmap);
                        } else {
                            // Handle the case where image decoding failed
                            showToast("Failed to decode image");
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
            if(isEditing){
                adminFormViewModel.updateCategory(id,categoryNameEditText.getText().toString(), promotedCheckBox.isChecked());
            }
            else{
                adminFormViewModel.createCategory(categoryNameEditText.getText().toString(), promotedCheckBox.isChecked());

            }
            navigateToAdmin(select);
        });

        // Create Movie Button
        // Inside AdminFormActivity.java
        createMovieButton.setOnClickListener(v -> {
            String title = movieTitleEditText.getText().toString();
            String logline = movieLoglineEditText.getText().toString();
            String categories = movieCategoriesEditText.getText().toString();

            // Disable the button to prevent multiple submissions
            createMovieButton.setEnabled(false);

            // Convert image to Base64 if available
            String imageBase64 = null;
            if (selectedImageBitmap != null) {
                try {
                    imageBase64 = convertImageToHex(selectedImageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("Failed to encode image");
                    createMovieButton.setEnabled(true);
                    return;
                }
            }

            try {
                final String finalImageBase64 = imageBase64;

                // Remove any previous observers to prevent duplicate observations
                adminFormViewModel.getReqStatus().removeObservers(this);

                // Observe the request status
                adminFormViewModel.getReqStatus().observe(this, status -> {
                    if (status != null) {
                        if (status.equals("success")) {
                            showToast("Movie saved successfully");
                            // Wait for toast to show before navigating
                            new Handler().postDelayed(() -> {
                                navigateToAdmin(select);
                            }, 1000); // 1 second delay
                        } else {
                            showToast("Failed to save movie: " + status);
                            createMovieButton.setEnabled(true);
                        }
                    }
                });

                // Make the API call
                if (isEditing) {
                    adminFormViewModel.updateMovie(id, title, logline, finalImageBase64, categories);
                } else {
                    adminFormViewModel.createMovie(title, logline, finalImageBase64, categories);
                }

            } catch (Exception e) {
                e.printStackTrace();
                showToast("Failed to save movie: " + e.getMessage());
                createMovieButton.setEnabled(true);
            }
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
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                movieImageView.setImageBitmap(selectedImageBitmap);  // Display the selected image
            } catch (IOException e) {
                e.printStackTrace();
                showToast("Failed to load image");
            }
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

    // Show Toast message for error handling
    private void showToast(String message) {
        Toast toast = Toast.makeText(AdminFormActivity.this, message, Toast.LENGTH_LONG);
        toast.show();
    }
    private String convertImageToHex(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return bytesToHex(byteArray);
    }

    // Convert bytes to hexadecimal string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}