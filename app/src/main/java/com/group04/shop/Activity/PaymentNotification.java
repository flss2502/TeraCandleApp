package com.group04.shop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.group04.shop.R;

public class PaymentNotification extends AppCompatActivity {
    TextView tvNotify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_notification);

        tvNotify = (TextView) findViewById(R.id.tvNotify);
        Intent intent = getIntent();

        tvNotify.setText(intent.getStringExtra("result"));
    }
}