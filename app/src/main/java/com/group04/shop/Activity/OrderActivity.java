package com.group04.shop.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group04.shop.Adapter.OrderListAdapter;
import com.group04.shop.Domain.Order;
import com.group04.shop.R;
import com.group04.shop.databinding.ActivityMainBinding;
import com.group04.shop.databinding.ActivityOrderBinding;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private ActivityOrderBinding binding;
    ListView listViewOrders;
    OrderListAdapter orderListAdapter;
    List<Order> orderList;

    DatabaseReference databaseOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ListView
        listViewOrders = findViewById(R.id.listViewOrders);

        // Initialize Firebase database reference
        databaseOrders = FirebaseDatabase.getInstance("https://shopping-56bed-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("orders");

        // Initialize orderList
        orderList = new ArrayList<>();

        // Set adapter
        orderListAdapter = new OrderListAdapter(this, orderList);
        listViewOrders.setAdapter(orderListAdapter);

        // Load orders from Firebase Realtime Database
        loadOrders();

        topNavigation();
        bottomNavigation();
    }

    private void topNavigation() {
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void bottomNavigation() {
        binding.btnHome.setOnClickListener(v -> {
            startActivity(new Intent(OrderActivity.this, MainActivity.class));
            finish();
        });
        binding.btnMap.setOnClickListener(v -> {
            startActivity(new Intent(OrderActivity.this, MapActivity.class));
        });
        binding.btnChat.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.me/261327180406689")));
        });
        binding.btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(OrderActivity.this, ProfileActivity.class));
        });
    }

    private void loadOrders() {
        databaseOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    orderList.add(order);
                }
                orderListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}