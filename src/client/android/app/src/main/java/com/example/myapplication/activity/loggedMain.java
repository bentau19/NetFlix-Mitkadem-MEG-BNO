package com.example.myapplication.activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Category;
import com.example.myapplication.adapter.ImageUtils;
import com.example.myapplication.adapter.User;
import com.example.myapplication.data.Rooms.DB.AppDatabase;
import com.example.myapplication.data.Rooms.dao.TokenDao;
import com.example.myapplication.data.ThemeManager;
import com.example.myapplication.data.repository.CategoryRepository;
import com.example.myapplication.data.repository.MovieRepository;
import com.example.myapplication.data.repository.UserRepository;
import com.example.myapplication.dataModel.SharedViewModel;
import com.example.myapplication.server.api.ApiResponseCallback;
import com.example.myapplication.ui.viewmodel.AdminViewModel;
import com.example.myapplication.ui.viewmodel.HomeViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityLoggedMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executors;

public class loggedMain extends AppCompatActivity {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRep;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityLoggedMainBinding binding;
    private final MutableLiveData<List<Category>> categoryListLiveData = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();;
    private final MutableLiveData<String> reqStatus;
    public loggedMain() {
        this.userRepository = new UserRepository(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
//                Log.d(TAG, "onSuccess: "+response);
                user.setValue((User) response);
            }
            @Override
            public void onError(String error) {

            }
        });
        reqStatus = new MutableLiveData<>();
        this.categoryRep = new CategoryRepository(new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                reqStatus.setValue("Get cat successs");
            }
            @Override
            public void onError(String error) {
                reqStatus.setValue("Get cat failed: " + error);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityLoggedMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavigationView navigationView = binding.navView;
        // Fetch user data and update UI
        userRepository.getUser();
        String currentTheme = ThemeManager.getTheme(this);
        Menu menu = navigationView.getMenu();
        MenuItem themeTitle = menu.findItem(R.id.ThemeMode);
        if (currentTheme.equals("dark")) {
            themeTitle.setTitle("ThemeMode - dark");
        } else {
            themeTitle.setTitle("ThemeMode - light");
        }
        user.observe(this, user -> {
            if (user != null) {
                // Update user image if available
                if (user.getImage() != null) {
                    ImageView imageView = binding.navView.getHeaderView(0).findViewById(R.id.imageView2);
                    imageView.setImageBitmap(ImageUtils.hexToImage(user.getImage()));
                }

                // Update user name if available
                if (user.getName() != null) {
                    TextView userNameTextView = binding.navView.getHeaderView(0).findViewById(R.id.userName);
                    userNameTextView.setText(user.getName());
                }

                MenuItem adminItem = menu.findItem(R.id.Admin);
                adminItem.setVisible(user.getAdmin());

            }
        });

        // Set up the toolbar
        setSupportActionBar(binding.appBarLoggedMain.toolbar);

        // Set up navigation components
        DrawerLayout drawer = binding.drawerLayout;


        // Define top-level destinations
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.SignOut, R.id.Admin)
                .setOpenableLayout(drawer)
                .build();

        // Set up NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_logged_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        // Handle navigation item clicks
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (item.getItemId() == R.id.ThemeMode) {
                // Handle the ThemeMode click event

                // Get the current theme from preferences
//                String currentTheme = ThemeManager.getTheme(this);

                // Toggle between light and dark theme
                if (currentTheme.equals("dark")) {
                    themeTitle.setTitle("ThemeMode - light");
                    ThemeManager.saveThemeToPreferences(this, "light"); // Change to light theme
                } else {
                    themeTitle.setTitle("ThemeMode - dark");
                    ThemeManager.saveThemeToPreferences(this, "dark"); // Change to dark theme
                }

                // Show a Toast to indicate the theme change
                Toast.makeText(this, "Theme Mode Changed", Toast.LENGTH_SHORT).show();

                // Recreate the activity to apply the new theme
                recreate(); // This will recreate the activity and apply the new theme

                return true;
            }
             else if (id == R.id.SignOut) {
                Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase db= AppDatabase.getInstance();
                TokenDao tokenDao = db.tokenDao();
                tokenDao.deleteAllTokens();
                });
                // Handle SignOut: Navigate to LoginActivity and clear back stack
                Intent intent = new Intent(this, LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Close the current activity
                return true;
            } else if (id == R.id.Admin) {
                // Handle Admin: Navigate to AdminFragment
                navController.navigate(R.id.Admin);
                return true;
            } else if (id == R.id.nav_home) {
                // Handle Home: Navigate to HomeFragment
                navController.navigate(R.id.nav_home);
                return true;
            }

            // Let the NavController handle other navigation items
            return NavigationUI.onNavDestinationSelected(item, navController);
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logged_main, menu);
        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        // Fetch categories
        fetchCategories("");
// Set up the "Main" menu item
        MenuItem mainItem = menu.findItem(R.id.main);
        mainItem.setOnMenuItemClickListener(item -> {
            // Update the catId in SharedViewModel to indicate "Main"
            sharedViewModel.setCatId("main");
            return true;
        });
        // Observe the request status
        reqStatus.observe(this, status -> {
            if (status != null && status.equals("fetch successful!")) {
                // Observe the category list
                categoryListLiveData.observe(this, categories -> {
                    if (categories != null) {
                        // Add each category as a menu item
                        for (Category category : categories) {
                            MenuItem menuItem = menu.add(Menu.NONE, Menu.FIRST + (int) Float.parseFloat(category.getId()), Menu.NONE, category.getName());

                            // Set a click listener for the menu item
                            menuItem.setOnMenuItemClickListener(item -> {
                                // Update the catId in SharedViewModel
                                sharedViewModel.setCatId(category.getName());
                                return true;
                            });
                        }

                        // Add the "All" option
                        MenuItem allItem = menu.add(Menu.NONE, Menu.FIRST + categories.size(), Menu.NONE, "All");

                        // Set a click listener for the "All" option
                        allItem.setOnMenuItemClickListener(item -> {
                            // Update the catId in SharedViewModel to indicate "All"
                            sharedViewModel.setCatId("all");
                            return true;
                        });
                    } else {
                        // If no categories are available, add the "All" option
                        MenuItem allItem = menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "All");

                        // Set a click listener for the "All" option
                        allItem.setOnMenuItemClickListener(item -> {
                            // Update the catId in SharedViewModel to indicate "All"
                            sharedViewModel.setCatId("all");
                            return true;
                        });
                    }
                });
            }
        });

        // Set up the SearchView
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(@NonNull MenuItem item) {
                return true; // Allow the action to expand
            }

            @Override
            public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {
                return true; // Allow the action to collapse
            }
        };

        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.setOnActionExpandListener(onActionExpandListener);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search movie here...");

        // Get the SharedViewModel


        // Set up the SearchView query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sharedViewModel.setSearchQuery(query);
                return false; // Let the SearchView handle the submission
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sharedViewModel.setSearchQuery(newText);
                return false; // Let the SearchView handle the text change
            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_logged_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void fetchCategories(String query) {
        categoryRep.fetchCategories(query, new ApiResponseCallback() {
            @Override
            public void onSuccess(Object response) {
                Gson gson = new Gson();
                String json = gson.toJson(response);
                List<Category> categories = gson.fromJson(json, new TypeToken<List<Category>>() {}.getType());
                categoryListLiveData.setValue(categories);
                reqStatus.setValue("fetch successful!");
            }

            @Override
            public void onError(String error) {
                reqStatus.setValue(error);
            }
        });
    }
}