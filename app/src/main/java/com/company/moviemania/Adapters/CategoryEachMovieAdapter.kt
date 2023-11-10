package com.company.moviemania.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.moviemania.R

class CategoryEachMovieAdapter(var item: List<String>) :
    RecyclerView.Adapter<CategoryEachMovieAdapter.ViewHolder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.view_category, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleText.text = item[position]
    }

    override fun getItemCount(): Int {
        return item.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleText: TextView

        init {
            titleText = itemView.findViewById(R.id.TitleTxt)
        }
    }
}