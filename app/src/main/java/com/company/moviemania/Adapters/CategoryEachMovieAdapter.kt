package com.company.moviemania.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.moviemania.Domian.GenresItem;
import com.company.moviemania.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryEachMovieAdapter extends RecyclerView.Adapter<CategoryEachMovieAdapter.ViewHolder> {

    List<String> item;
    Context context;

    public CategoryEachMovieAdapter(List<String> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public CategoryEachMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryEachMovieAdapter.ViewHolder holder, int position) {
        holder.titleText.setText(item.get(position));
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.TitleTxt);

        }
    }
}
