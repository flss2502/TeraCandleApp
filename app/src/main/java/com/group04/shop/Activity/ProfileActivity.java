package com.group04.shop.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group04.shop.R;
import com.group04.shop.databinding.ActivityMainBinding;
import com.group04.shop.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigation();
    }

    private void bottomNavigation() {
        binding.btnMap.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, MapActivity.class));
            finish();
        });
        binding.btnHome.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            finish();
        });
        binding.btnChat.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.me/261327180406689")));
            finish();
        });
        binding.btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
            finish();
        });
    }
}