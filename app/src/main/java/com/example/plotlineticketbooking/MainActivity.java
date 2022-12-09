package com.example.plotlineticketbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.plotlineticketbooking.BookingPage.BookingFragment;
import com.example.plotlineticketbooking.HomePage.HomeFragment;
import com.example.plotlineticketbooking.ProfilePage.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, selectedFragment).commit();
                        return true;
                    case R.id.booking:
                        selectedFragment = new BookingFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, selectedFragment).commit();
                        return true;
                    case R.id.profile:
                        selectedFragment = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, selectedFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private void initializeViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }
}