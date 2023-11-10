package com.company.moviemania.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.moviemania.R

class ActorsListAdapter(var images: List<String>) :
    RecyclerView.Adapter<ActorsListAdapter.ViewHolder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.view_actors, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context!!).load(images[position]).into(holder.banner)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var banner: ImageView

        init {
            banner = itemView.findViewById(R.id.itemImages)
        }
    }
}