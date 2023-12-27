package com.example.project136.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project136.Adapters.CategoryAdapter;
import com.example.project136.Adapters.PopularAdapter;
import com.example.project136.Domains.CategoryDomain;
import com.example.project136.Domains.PopularDomain;
import com.example.project136.R;

import com.example.project136.models.User;
import com.example.project136.settings.changeInfor;
import com.example.project136.settings.changePassword;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private RecyclerView.Adapter  adapterCat;
    private PopularAdapter adapterPopular;
    private RecyclerView recyclerViewPopular, recyclerViewCategory;
    private SearchView searchView;
    public ArrayList<PopularDomain> items;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout headerMain= findViewById(R.id.header_main);
        TextView tvUserMain = headerMain.findViewById(R.id.tvHeaderUser);
        String fullnameMain = User.getInstance().getFirstname().toString() + " " + User.getInstance().getLastname().toString();
        tvUserMain.setText(fullnameMain);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
//        drawerLayout.openDrawer(GravityCompat.START);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                // Handle each item click as needed
                if (id == R.id.navigation_setting) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
            }
        });
        NavigationView navigationView = findViewById(R.id.navigation_viewV);
        View headerView = navigationView.getHeaderView(0);
        TextView tvUser = headerView.findViewById(R.id.tvHeaderUser);
        TextView tvEmail = headerView.findViewById(R.id.tvHeaderEmail);
        // Đặt text
        String fullname = User.getInstance().getFirstname().toString() + " " + User.getInstance().getLastname().toString();
        tvUser.setText(fullname);
        tvEmail.setText(User.getInstance().getEmail().toString());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_changeInfo)
                {
                    Intent intent = new Intent(MainActivity.this, changeInfor.class);
                    startActivity(intent);
                }
                if (id == R.id.nav_ChangePassword)
                {
                    Intent intent = new Intent(MainActivity.this, changePassword.class);
                    startActivity(intent);
                }
                if (id == R.id.nav_LogOut)
                {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("Mar caible, avendia lago", "Miami Beach", "This 2 bed /1 bath home boasts an enormous," +
                " open-living plan, accented by striking " +
                "architectural features and high-end finishes." +
                " Feel inspired by open sight lines that" +
                " embrace the outdoors, crowned by stunning" +
                " coffered ceilings. ", 2, true, 4.8, "pic1", true, 1000));
        items.add(new PopularDomain("Passo Rolle, TN", "Hawaii Beach", "This 2 bed /1 bath home boasts an enormous," +
                " open-living plan, accented by striking " +
                "architectural features and high-end finishes." +
                " Feel inspired by open sight lines that" +
                " embrace the outdoors, crowned by stunning" +
                " coffered ceilings. ", 1, false, 5, "pic2", false, 2500));
        items.add(new PopularDomain("Mar caible, avendia lago", "Miami Beach", "This 2 bed /1 bath home boasts an enormous," +
                " open-living plan, accented by striking " +
                "architectural features and high-end finishes." +
                " Feel inspired by open sight lines that" +
                " embrace the outdoors, crowned by stunning" +
                " coffered ceilings. ", 3, true, 4.3, "pic3", true, 30000));
        recyclerViewPopular = findViewById(R.id.view_pop);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterPopular = new PopularAdapter(items);
        recyclerViewPopular.setAdapter(adapterPopular);

        ArrayList<CategoryDomain> catsList = new ArrayList<>();
        catsList.add(new CategoryDomain("Beaches", "cat1"));
        catsList.add(new CategoryDomain("Camps", "cat2"));
        catsList.add(new CategoryDomain("Forest", "cat3"));
        catsList.add(new CategoryDomain("Desert", "cat4"));
        catsList.add(new CategoryDomain("Mountain", "cat5"));



        recyclerViewCategory = findViewById(R.id.view_cat);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterCat = new CategoryAdapter(catsList);
        recyclerViewCategory.setAdapter(adapterCat);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                String searchText = newText.trim().toLowerCase();
                ArrayList<PopularDomain> filteredList = new ArrayList<>();
                for (PopularDomain item : items) {
                    String location = item.getLocation().toLowerCase();
                    if (location.contains(searchText)) {
                        filteredList.add(item);
                    }
                }

                if (filteredList.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No items found", Toast.LENGTH_SHORT).show();
                } else {
                    // Update the adapter with the filtered list
                    adapterPopular.setItems(filteredList);

                }

                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}