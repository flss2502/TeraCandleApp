package com.group04.shop.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group04.shop.Adapter.SizeAdapter;
import com.group04.shop.Adapter.SliderAdapter;
import com.group04.shop.Domain.ItemsDomain;
import com.group04.shop.Domain.SliderItems;
import com.group04.shop.Fragment.DescriptionFragment;
import com.group04.shop.Fragment.ReviewFragment;
import com.group04.shop.Fragment.SoldFragment;
import com.group04.shop.Helper.ManagmentCart;
import com.group04.shop.databinding.ActivityDetailBinding;

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
        initBanner();
        initSize();
        setupViewpager();
    }

    private void initSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("S");
        list.add("M");

        binding.recyclerSize.setAdapter(new SizeAdapter(list));
        binding.recyclerSize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    private void initBanner() {
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
    private void setupViewpager(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        DescriptionFragment descriptionFragment = new DescriptionFragment();
        ReviewFragment reviewFragment = new ReviewFragment();
        SoldFragment soldFragment = new SoldFragment();

        Bundle bundle = new Bundle();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();

        bundle.putString("description", object.getDescription());

        descriptionFragment.setArguments(bundle);
        reviewFragment.setArguments(bundle1);
        soldFragment.setArguments(bundle2);

        adapter.addFrag(descriptionFragment, "Descriptions");
        adapter.addFrag(reviewFragment, "Reviews");
        adapter.addFrag(soldFragment, "Sold");

        binding.viewpaper.setAdapter(adapter);
        binding.tablelayout.setupWithViewPager(binding.viewpaper);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFrag(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }
}