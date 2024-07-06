package com.group04.shop.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.group04.shop.Adapter.SliderAdapter;
import com.group04.shop.Domain.ItemsDomain;
import com.group04.shop.Domain.SliderItems;
import com.group04.shop.Helper.ManagmentCart;
import com.group04.shop.R;
import com.group04.shop.databinding.ActivityDetailBinding;
import com.group04.shop.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private ItemsDomain object;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;
    private Handler sliderHandle = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        getBundles();
        banners();
    }

    private void banners() {
        ArrayList<SliderItems> sliderItems = new ArrayList<>();
        for(int i = 0; i < object.getPicUrl().size(); i++){
            sliderItems.add(new SliderItems(object.getPicUrl().get(i)));
        }
        binding.viewpageSlider.setAdapter(new SliderAdapter(sliderItems, binding.viewpageSlider));
        binding.viewpageSlider.setClipToPadding(false);
        binding.viewpageSlider.setClipChildren(false);
        binding.viewpageSlider.setOffscreenPageLimit(3);
        binding.viewpageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void getBundles() {
        object = (ItemsDomain) getIntent().getSerializableExtra("object");
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText(object.getPrice() + "VNĐ");
        binding.ratingBar.setRating((float) object.getRating());
        binding.ratingTxt.setText(object.getRating() + "rating");

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberinCart(numberOrder);
                managmentCart.insertItem(object);
            }
        });
        binding.btnBack.setOnClickListener(v -> finish());
    }
}