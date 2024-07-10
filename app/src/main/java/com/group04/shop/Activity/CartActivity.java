package com.group04.shop.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.group04.shop.Adapter.CartAdapter;
import com.group04.shop.Api.CreateOrder;
import com.group04.shop.Helper.ChangeNumberItemsListener;
import com.group04.shop.Helper.ManagmentCart;
import com.group04.shop.databinding.ActivityCartBinding;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    private double tax;
    private ManagmentCart managmentCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavigation();
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(2553, Environment.SANDBOX);

        managmentCart = new ManagmentCart(this);
        calculatorCart();
        setVariable();
        initCartList();

        double total = Double.parseDouble(extractNumber(binding.tvTotal.getText().toString()));
        String totalString = String.format("%.0f", total);

        binding.btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(totalString);

                    String code = data.getString("return_code");


                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(CartActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Intent intent = new Intent(CartActivity.this, PaymentNotification.class);
                                intent.putExtra("result", "Thanh toán thành công");
                                startActivity(intent);
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent = new Intent(CartActivity.this, PaymentNotification.class);
                                intent.putExtra("result", "Đã hủy thanh toán");
                                startActivity(intent);
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent = new Intent(CartActivity.this, PaymentNotification.class);
                                intent.putExtra("result", "Thanh toán không thành công");
                                startActivity(intent);
                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void bottomNavigation() {
        binding.btnHome.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, MainActivity.class));
            finish();
        });
        binding.btnMap.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, MapActivity.class));
            finish();
        });
        binding.btnChat.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.me/261327180406689")));
            finish();
        });
        binding.btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, ProfileActivity.class));
            finish();
        });
    }

    private void initCartList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        } else {
            binding.tvEmpty.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
        }

        binding.cartView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.cartView.setAdapter(new CartAdapter(managmentCart.getListCart(), this, () -> calculatorCart()));
    }

    private void setVariable() {
        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void calculatorCart() {
        double percentTax = 0.02;
        double delivery = 10;

        tax = Math.round((managmentCart.getTotalFee() * percentTax * 100.0)) / 100.0;

        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100.0) / 100.0;

        binding.tvTotalFee.setText(itemTotal + "VNĐ");
        binding.tvTax.setText(tax + "VNĐ");
        binding.tvDelivery.setText(delivery + "VNĐ");
        binding.tvTotal.setText(total + "VNĐ");

    }

    private String extractNumber(String text) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}