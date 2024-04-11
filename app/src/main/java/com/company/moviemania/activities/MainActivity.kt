package com.company.moviemania.activities

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.company.moviemania.R
import com.company.moviemania.adapters.CategoryListAdapter
import com.company.moviemania.adapters.MovieListAdapter
import com.company.moviemania.adapters.SliderAdapter
import com.company.moviemania.domain.GenresItem
import com.company.moviemania.domain.ListMovie
import com.company.moviemania.domain.SliderItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Objects

class MainActivity : AppCompatActivity() {
  private var adapterBestMovies: RecyclerView.Adapter<*>? = null
  private var adapterUpComing: RecyclerView.Adapter<*>? = null
  private var adapterCategory: RecyclerView.Adapter<*>? = null
  private lateinit var recyclerViewBestMovies: RecyclerView
  private lateinit var recyclerViewUpComing: RecyclerView
  private lateinit var recyclerViewCategory: RecyclerView
  private var mRequestQueue: RequestQueue? = null
  private var stringRequest: StringRequest? = null
  private var stringRequest2: StringRequest? = null
  private var stringRequest3: StringRequest? = null
  private lateinit var viewPager: ViewPager2
  private var load1: ProgressBar? = null
  private var load2: ProgressBar? = null
  private var load3: ProgressBar? = null
  private val sliderHandler = Handler()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initView()
    banners()
    sendRequestBestMovies()
    sendRequestUpComing()
    sendRequestCategory()
  }

  private fun sendRequestBestMovies() {
    mRequestQueue = Volley.newRequestQueue(this)
    load1!!.visibility = View.VISIBLE
    stringRequest =
      StringRequest(
        Request.Method.GET,
        "https://moviesapi.ir/api/v1/movies?page=1",
        { response ->
          val gson = Gson()
          load1!!.visibility = View.GONE
          val items = gson.fromJson(response, ListMovie::class.java)
          adapterBestMovies = MovieListAdapter(items)
          recyclerViewBestMovies!!.adapter = adapterBestMovies
        },
      ) { error ->
        load1!!.visibility = View.GONE
        Log.i("TAG", "Error Message:- $error")
      }
    mRequestQueue!!.add(stringRequest)
  }

  private fun sendRequestUpComing() {
    mRequestQueue = Volley.newRequestQueue(this)
    load3!!.visibility = View.VISIBLE
    stringRequest3 =
      StringRequest(
        Request.Method.GET,
        "https://moviesapi.ir/api/v1/movies?page=2",
        { response ->
          val gson = Gson()
          load3!!.visibility = View.GONE
          val items = gson.fromJson(response, ListMovie::class.java)
          adapterUpComing = MovieListAdapter(items)
          recyclerViewUpComing!!.adapter = adapterUpComing
        },
      ) { error ->
        load3!!.visibility = View.GONE
        Log.i("TAG", "Error Message:- $error")
      }
    mRequestQueue!!.add(stringRequest3)
  }

  private fun sendRequestCategory() {
    mRequestQueue = Volley.newRequestQueue(this)
    load2!!.visibility = View.VISIBLE
    stringRequest2 =
      StringRequest(
        Request.Method.GET,
        "https://moviesapi.ir/api/v1/genres",
        { response ->
          val gson = Gson()
          load2!!.visibility = View.GONE
          val categoryItems =
            gson.fromJson<ArrayList<GenresItem>>(
              response,
              object : TypeToken<ArrayList<GenresItem?>?>() {}.type,
            )
          adapterCategory = CategoryListAdapter(categoryItems)
          recyclerViewCategory!!.adapter = adapterCategory
        },
      ) { error ->
        load2!!.visibility = View.GONE
        Log.i("TAG", "Error Message:- $error")
      }
    mRequestQueue!!.add(stringRequest2)
  }

  private fun banners() {
    val sliderItems: MutableList<SliderItems> = ArrayList()
    sliderItems.add(SliderItems(R.drawable.wide))
    sliderItems.add(SliderItems(R.drawable.wide1))
    sliderItems.add(SliderItems(R.drawable.wide3))
    val sliderAdapter = SliderAdapter(sliderItems, viewPager)
    viewPager!!.adapter = sliderAdapter
    viewPager!!.clipToPadding = false
    viewPager!!.offscreenPageLimit = 3
    viewPager!!.clipChildren = false
    viewPager!!.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
    val compositePageTransformer = CompositePageTransformer()
    compositePageTransformer.addTransformer(MarginPageTransformer(40))
    compositePageTransformer.addTransformer { page: View, position: Float ->
      val r = 1 - Math.abs(position)
      page.scaleY = 0.85f + r * 0.15f
    }
    viewPager!!.setPageTransformer(compositePageTransformer)
    viewPager!!.currentItem = 1

    // Automatically scroll ViewPager
    startAutoScroll()
  }

  private fun startAutoScroll() {
    sliderHandler.postDelayed(sliderRunnable, AUTO_SCROLL_DELAY)
  }

  private val sliderRunnable = Runnable {
    val currentItem = viewPager!!.currentItem
    val itemCount = Objects.requireNonNull(viewPager!!.adapter).itemCount
    if (currentItem < itemCount - 1) {
      viewPager!!.currentItem = currentItem + 1
    } else {
      viewPager!!.currentItem = 0
    }
    startAutoScroll() // Schedule the next automatic scroll
  }

  override fun onPause() {
    super.onPause()
    sliderHandler.removeCallbacks(sliderRunnable)
  }

  override fun onResume() {
    super.onResume()
    startAutoScroll()
  }

  private fun initView() {
    viewPager = findViewById(R.id.viewpagerSlider)
    recyclerViewBestMovies = findViewById(R.id.view1)
    recyclerViewBestMovies.setLayoutManager(
      LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    )
    recyclerViewCategory = findViewById(R.id.view2)
    recyclerViewCategory.setLayoutManager(
      LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    )
    recyclerViewUpComing = findViewById(R.id.view3)
    recyclerViewUpComing.setLayoutManager(
      LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    )
    load1 = findViewById(R.id.progressBar1)
    load2 = findViewById(R.id.progressBar2)
    load3 = findViewById(R.id.progressBar3)
  }

  companion object {
    private const val AUTO_SCROLL_DELAY: Long = 4000 // 4 seconds
  }
}
