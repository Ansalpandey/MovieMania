package com.company.moviemania.Activities;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.company.moviemania.Adapters.CategoryListAdapter;
import com.company.moviemania.Adapters.MovieListAdapter;
import com.company.moviemania.Adapters.SliderAdapter;
import com.company.moviemania.Domian.GenresItem;
import com.company.moviemania.Domian.ListMovie;
import com.company.moviemania.Domian.SliderItems;
import com.company.moviemania.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterBestMovies, adapterUpComing, adapterCategory;
    private RecyclerView recyclerViewBestMovies, recyclerViewUpComing, recyclerViewCategory;
    private RequestQueue mRequestQueue;
    private StringRequest stringRequest, stringRequest2, stringRequest3;
    private ViewPager2 viewPager;
    private ProgressBar load1, load2, load3;
    private final Handler sliderHandler = new Handler();
    private static final long AUTO_SCROLL_DELAY = 4000; // 4 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        banners();
        sendRequestBestMovies();
        sendRequestUpComing();
        sendRequestCategory();
    }

    private void sendRequestBestMovies() {
        mRequestQueue = Volley.newRequestQueue(this);
        load1.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                load1.setVisibility(View.GONE);
                ListMovie items = gson.fromJson(response, ListMovie.class);
                adapterBestMovies = new MovieListAdapter(items);
                recyclerViewBestMovies.setAdapter(adapterBestMovies);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                load1.setVisibility(View.GONE);
                Log.i("TAG", "Error Message:- " + error.toString());
            }
        });
        mRequestQueue.add(stringRequest);
    }

    private void sendRequestUpComing() {
        mRequestQueue = Volley.newRequestQueue(this);
        load3.setVisibility(View.VISIBLE);
        stringRequest3 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=2", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                load3.setVisibility(View.GONE);
                ListMovie items = gson.fromJson(response, ListMovie.class);
                adapterUpComing = new MovieListAdapter(items);
                recyclerViewUpComing.setAdapter(adapterUpComing);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                load3.setVisibility(View.GONE);
                Log.i("TAG", "Error Message:- " + error.toString());
            }
        });
        mRequestQueue.add(stringRequest3);
    }

    private void sendRequestCategory() {
        mRequestQueue = Volley.newRequestQueue(this);
        load2.setVisibility(View.VISIBLE);
        stringRequest2 = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/genres", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                load2.setVisibility(View.GONE);
                ArrayList<GenresItem> categoryItems = gson.fromJson(response, new TypeToken<ArrayList<GenresItem>>(){}.getType());
                adapterCategory = new CategoryListAdapter(categoryItems);
                recyclerViewCategory.setAdapter(adapterCategory);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                load2.setVisibility(View.GONE);
                Log.i("TAG", "Error Message:- " + error.toString());
            }
        });
        mRequestQueue.add(stringRequest2);
    }

    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide));
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide3));

        SliderAdapter sliderAdapter = new SliderAdapter(sliderItems, viewPager);
        viewPager.setAdapter(sliderAdapter);

        viewPager.setClipToPadding(false);
        viewPager.setOffscreenPageLimit(3);
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
        recyclerViewBestMovies = findViewById(R.id.view1);
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory = findViewById(R.id.view2);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpComing = findViewById(R.id.view3);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        load1 = findViewById(R.id.progressBar1);
        load2 = findViewById(R.id.progressBar2);
        load3 = findViewById(R.id.progressBar3);
    }
}

