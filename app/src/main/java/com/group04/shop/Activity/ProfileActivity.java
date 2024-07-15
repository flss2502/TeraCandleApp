package com.group04.shop.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.group04.shop.R;
import com.group04.shop.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    LinearLayout btnLogout, btnOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btnLogout = findViewById(R.id.btnLogout);
        btnOrder = findViewById(R.id.btnOrder);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();
            }
        });

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