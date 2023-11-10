package com.company.moviemania.Activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.company.moviemania.Adapters.ActorsListAdapter
import com.company.moviemania.Adapters.CategoryEachMovieAdapter
import com.company.moviemania.Domian.MovieItem
import com.company.moviemania.R
import com.google.gson.Gson

class DetailActivity : AppCompatActivity() {
    private var requestQueue: RequestQueue? = null
    private var stringRequest: StringRequest? = null
    private var progressBar: ProgressBar? = null
    private var titleTxt: TextView? = null
    private var movieRateText: TextView? = null
    private var movieTimeText: TextView? = null
    private var movieSummaryInformation: TextView? = null
    private var movieActorInformation: TextView? = null
    private var idMovie = 0
    private var banner: ImageView? = null
    private lateinit var backImg: ImageView
    private var adapterActor: RecyclerView.Adapter<*>? = null
    private var adapterCategory: RecyclerView.Adapter<*>? = null
    private lateinit var recyclerViewActors: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView
    private var scrollView: NestedScrollView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        idMovie = intent.getIntExtra("id", 0)
        initView()
        sendRequest()
    }

    private fun sendRequest() {
        requestQueue = Volley.newRequestQueue(this)
        progressBar!!.visibility = View.VISIBLE
        scrollView!!.visibility = View.GONE
        stringRequest = StringRequest(
            Request.Method.GET,
            "https://moviesapi.ir/api/v1/movies/$idMovie",
            { response ->
                val gson = Gson()
                val item = gson.fromJson(response, MovieItem::class.java)
                progressBar!!.visibility = View.GONE
                scrollView!!.visibility = View.VISIBLE
                Glide.with(this@DetailActivity)
                    .load(item.poster).into(banner!!)
                titleTxt!!.text = item.title
                movieRateText!!.text = item.imdbRating
                movieTimeText!!.text = item.runtime
                movieSummaryInformation!!.text = item.plot
                movieActorInformation!!.text = item.actors
                if (item.images != null) {
                    adapterActor = ActorsListAdapter(item.images!!)
                }
                if (item.genres != null) {
                    adapterCategory = CategoryEachMovieAdapter(item.genres!!)
                    recyclerViewActors!!.adapter = adapterActor
                }
            }) { progressBar!!.visibility = View.GONE }
        requestQueue!!.add(stringRequest)
    }

    private fun initView() {
        titleTxt = findViewById(R.id.movie_name)
        progressBar = findViewById(R.id.progressBar)
        scrollView = findViewById(R.id.scrollView2)
        banner = findViewById(R.id.banner)
        movieRateText = findViewById(R.id.movie_stars)
        movieTimeText = findViewById(R.id.movie_time)
        movieSummaryInformation = findViewById(R.id.movie_summary)
        movieActorInformation = findViewById(R.id.movieActorInfo)
        backImg = findViewById(R.id.back_btn)
        recyclerViewCategory = findViewById(R.id.genre_view)
        recyclerViewActors = findViewById(R.id.imgRecycler)
        recyclerViewActors.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        recyclerViewCategory.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        backImg.setOnClickListener(View.OnClickListener { finish() })
    }
}