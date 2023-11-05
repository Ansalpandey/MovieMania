package com.company.moviemania.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.company.moviemania.Adapters.ActorsListAdapter;
import com.company.moviemania.Adapters.CategoryEachMovieAdapter;
import com.company.moviemania.Domian.MovieItem;
import com.company.moviemania.R;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateText, movieTimeText, movieSummaryInformation, movieActorInformation;
    private int idMovie;
    private ImageView banner, backImg;
    private RecyclerView.Adapter adapterActor, adapterCategory;
    private RecyclerView recyclerViewActors, recyclerViewCategory;
    private NestedScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        idMovie = getIntent().getIntExtra("id", 0);
        initView();
        sendRequest();

    }

    private void sendRequest() {
        requestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        stringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/" + idMovie, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                MovieItem item = gson.fromJson(response, MovieItem.class);
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Glide.with(DetailActivity.this)
                        .load(item.getPoster()).into(banner);
                titleTxt.setText(item.getTitle());
                movieRateText.setText(item.getImdbRating());
                movieTimeText.setText(item.getRuntime());
                movieSummaryInformation.setText(item.getPlot());
                movieActorInformation.setText(item.getActors());

                if (item.getImages() != null) {
                    adapterActor = new ActorsListAdapter(item.getImages());
                }
                if (item.getGenres() != null) {
                    adapterCategory = new CategoryEachMovieAdapter(item.getGenres());
                    recyclerViewActors.setAdapter(adapterActor);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void initView() {
        titleTxt = findViewById(R.id.movie_name);
        progressBar = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.scrollView2);
        banner = findViewById(R.id.banner);
        movieRateText = findViewById(R.id.movie_stars);
        movieTimeText = findViewById(R.id.movie_time);
        movieSummaryInformation = findViewById(R.id.movie_summary);
        movieActorInformation = findViewById(R.id.movieActorInfo);
        backImg = findViewById(R.id.back_btn);
        recyclerViewCategory = findViewById(R.id.genre_view);
        recyclerViewActors = findViewById(R.id.imgRecycler);
        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}