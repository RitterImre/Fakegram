package com.mobilalk.fakegram;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mobilalk.fakegram.Fragments.HomeFragment;
import com.mobilalk.fakegram.Fragments.NotificationFragment;
import com.mobilalk.fakegram.Fragments.ProfileFragment;
import com.mobilalk.fakegram.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniMain();
    }

    private void iniMain() {
        bottomNavigationView = findViewById(R.id.bottom_navitaion_bar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.nav_addbox:
                        fragment = null;
                        startActivity(new Intent(MainActivity.this, PostActivity.class));
                        finish();
                        break;
                    case R.id.nav_favorite:
                        fragment = new NotificationFragment();
                        break;
                    case R.id.nav_person:
                        fragment = new ProfileFragment();
                        break;
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }

                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }
}