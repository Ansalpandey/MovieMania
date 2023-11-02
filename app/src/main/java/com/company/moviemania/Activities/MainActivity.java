package com.company.moviemania.Activities;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.company.moviemania.Adapters.SliderAdapters;
import com.company.moviemania.Domain.SliderItems;
import com.company.moviemania.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    
    private ViewPager2 viewPager;
    private final Handler sliderHandler = new Handler();
    private static final long AUTO_SCROLL_DELAY = 4000; // 4 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        banners();
    }

    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide));
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide3));

        SliderAdapters sliderAdapter = new SliderAdapters(sliderItems, viewPager);
        viewPager.setAdapter(sliderAdapter);

        viewPager.setClipToPadding(false);
        viewPager.setOffscreenPageLimit(3); // Adjust this based on your needs
        viewPager.setClipChildren(false);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager.setPageTransformer(compositePageTransformer);
        viewPager.setCurrentItem(1);

        // Automatically scroll ViewPager
        startAutoScroll();
    }

    private void startAutoScroll() {
        sliderHandler.postDelayed(sliderRunnable, AUTO_SCROLL_DELAY);
    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = viewPager.getCurrentItem();
            int itemCount = Objects.requireNonNull(viewPager.getAdapter()).getItemCount();
            if (currentItem < itemCount - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            } else {
                viewPager.setCurrentItem(0);
            }
            startAutoScroll(); // Schedule the next automatic scroll
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoScroll();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpagerSlider);
    }
}
