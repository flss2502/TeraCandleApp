package com.group04.shop.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.group04.shop.R;
import com.group04.shop.databinding.ActivityMainBinding;
import com.group04.shop.databinding.ActivityMapBinding;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    ActivityMapBinding binding;
    GoogleMap gMap;
    FrameLayout map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        map = findViewById(R.id.map);

        if (savedInstanceState == null) {
            SupportMapFragment mapFragment = new SupportMapFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.map, mapFragment)
                    .commit();
            mapFragment.getMapAsync(this);
        }
        bottomNavigation();

    }

    private void bottomNavigation() {
        binding.btnHome.setOnClickListener(v -> {
            startActivity(new Intent(MapActivity.this, MainActivity.class));
            finish();
        });
        binding.btnChat.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.me/261327180406689")));
            finish();
        });
        binding.btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(MapActivity.this, ProfileActivity.class));
            finish();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LatLng latLng = new LatLng(10.838196251368094, 106.83006332451907);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

        MarkerOptions options = new MarkerOptions().position(latLng).title("Tera Candle");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        this.gMap.addMarker(options);

        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setCompassEnabled(true);
    }
}