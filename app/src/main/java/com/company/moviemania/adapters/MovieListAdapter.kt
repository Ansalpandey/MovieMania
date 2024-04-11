package com.company.moviemania.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.company.moviemania.R
import com.company.moviemania.activities.DetailActivity
import com.company.moviemania.domain.ListMovie

class MovieListAdapter(var item: ListMovie) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
  var context: Context? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    context = parent.context
    val inflate = LayoutInflater.from(parent.context).inflate(R.layout.view_movies, parent, false)
    return ViewHolder(inflate)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.titleText.text = item.data!![position]?.title
    var requestOptions = RequestOptions()
    requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(30))
    Glide.with(context!!)
      .load(item.data!![position].poster)
      .apply(requestOptions)
      .into(holder.banner)
    holder.itemView.setOnClickListener {
      val i = Intent(holder.itemView.context, DetailActivity::class.java)
      i.putExtra("id", item.data!![position].id)
      context!!.startActivity(i)
    }
  }

  override fun getItemCount(): Int {
    return item.data?.size!!
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titleText: TextView
    var banner: ImageView

    init {
      titleText = itemView.findViewById(R.id.titleTxt)
      banner = itemView.findViewById(R.id.pic)
    }
  }
}
