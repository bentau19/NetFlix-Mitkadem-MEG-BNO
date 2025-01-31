package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.server.api.APIRequest;
import com.example.myapplication.server.api.ApiResponseCallback;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminForm2Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Bitmap selectedImageBitmap;
    private APIRequest apiRequest;
    private EditText titleEditText, loglineEditText, categoriesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminform2);

        imageView = findViewById(R.id.imageView);
        titleEditText = findViewById(R.id.titleEditText);
        loglineEditText = findViewById(R.id.loglineEditText);
        categoriesEditText = findViewById(R.id.categoriesEditText);
        Button selectImageButton = findViewById(R.id.selectImageButton);
        Button submitButton = findViewById(R.id.submitButton);

        // Open image picker when the user clicks the "Select Image" button
        selectImageButton.setOnClickListener(v -> openImageChooser());

        // Submit the form with image and other fields
        submitButton.setOnClickListener(v -> {
            if (selectedImageBitmap != null) {
                // Convert the image to Hex and get the form data
                String imageHex = convertImageToHex(selectedImageBitmap);
                String title = titleEditText.getText().toString();
                String logline = loglineEditText.getText().toString();
                String categories = categoriesEditText.getText().toString();

                // Send the data to the server
                sendImageData(title, logline, categories, imageHex);
            } else {
                showToast("Please select an image first");
            }
        });
    }

    // Open the image picker
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(selectedImageBitmap);  // Display the selected image
            } catch (IOException e) {
                e.printStackTrace();
                showToast("Failed to load image");
            }
        }
    }

    // Convert the image bitmap to hexadecimal string
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
    // Convert categories string into an array of non-empty, trimmed categories
// Convert categories string into an array of numbers
    private List<Integer> processCategories(String categories) {
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }

        // Split categories by commas, trim spaces, and remove empty values
        String[] categoriesArray = categories.split(",");
        List<Integer> processedCategories = new ArrayList<>();

        for (String category : categoriesArray) {
            String trimmedCategory = category.trim();
            if (!trimmedCategory.isEmpty()) {
                try {
                    processedCategories.add(Integer.parseInt(trimmedCategory)); // Convert to integer
                } catch (NumberFormatException e) {
                    // Handle invalid category ID
                    showToast("Invalid category ID: " + trimmedCategory);
                }
            }
        }

        return processedCategories;
    }


    // Send the image data along with other fields (title, logline, categories)
    private void sendImageData(String title, String logline, String categories, String imageHex) {
        String endpoint = "movies/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("token", "your-jwt-token-here");

        // Create a FormData object with the image hex and other form fields
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("title", title);
        jsonBody.put("logline", logline);
        jsonBody.put("image", imageHex);

        // Process categories into a List<Integer>
        List<Integer> processedCategories = processCategories(categories);

        // Convert the List<Integer> into a JSON array string
        String categoriesJsonString = new JSONArray(processedCategories).toString();

        // Put the categories as a string in the JSON body
        jsonBody.put("categories", categoriesJsonString);

        // Create and send the API request
        apiRequest = new APIRequest(endpoint, headers, jsonBody);

        // Make the POST request
        apiRequest.post(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                showToast("Form submitted successfully");
            }

            @Override
            public void onError(String error) {
                showToast("Error: " + error);
            }
        });
    }


    // Utility method to show toast messages
    private void showToast(String message) {
        Toast.makeText(AdminForm2Activity.this, message, message.length()).show();
    }



    // Assuming FormData holds the image hex and other form fields

}
