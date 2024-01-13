package com.example.fieldpreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

public class Homepage extends AppCompatActivity {

    Map<String, Object> result;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        result = (Map<String, Object>) getIntent().getSerializableExtra("user");
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home: return showCustomFragment("home");
                case R.id.nav_search: return showCustomFragment("search");
                case R.id.nav_profile: return showCustomFragment("profile");
                default:
                    Log.d("a", "Error");
                    return false;
            }
        });
    }

    /*protected Boolean showFragment(Class<? extends Fragment> theClass) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, theClass, null)
                .setReorderingAllowed(true)
                .commit();
        return true;
    }*/

    protected Boolean showCustomFragment(String ris) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (ris.equals("home")) {
            HomeFragment homeFragment = HomeFragment.newInstance(
                    result.get("email").toString()
            );
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .setReorderingAllowed(true)
                    .commit();
        }
        else if (ris.equals("search")) {
            SearchFragment searchFragment = SearchFragment.newInstance(
                    result.get("name").toString(),
                    result.get("surname").toString(),
                    result.get("email").toString(),
                    result.get("password").toString()
            );
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, searchFragment)
                    .setReorderingAllowed(true)
                    .commit();
        }
        else if (ris.equals("profile")) {
            ProfileFragment profileFragment = ProfileFragment.newInstance(
                    result.get("name").toString(),
                    result.get("surname").toString(),
                    result.get("email").toString(),
                    result.get("password").toString()
            );
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, profileFragment)
                    .setReorderingAllowed(true)
                    .commit();
        }
        return true;
    }
}