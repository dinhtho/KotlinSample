package com.mvp.moviedbapi.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.mvp.moviedbapi.R
import com.mvp.moviedbapi.constants.Urls
import com.mvp.moviedbapi.models.response.MovieResult
import com.mvp.moviedbapi.models.response.SearchResults
import com.squareup.picasso.Picasso

class MovieSearchAdapter(var mSearchResults: SearchResults) : RecyclerView.Adapter<MovieSearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_movie_cell, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mSearchResults.results?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val movieResult = mSearchResults.results?.get(position)
        if (movieResult != null) {
            if (holder?.imageView != null) {
                Picasso.with(holder.imageView.context).load(getUrlFromResult(movieResult)).into(holder.imageView)
            }
            holder?.tvTile?.text = movieResult.title
        }


    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView?.findViewById(R.id.item_iv_poster) as ImageView
        val tvTile = itemView?.findViewById(R.id.item_tv_title) as TextView

    }

    private fun getUrlFromResult(movieResult: MovieResult): String {
        val stringBuilder = StringBuilder(Urls.IMAGE_BASE_URL)
        stringBuilder.append(Urls.IMAGE_SIZE_HD).append(movieResult.posterPath)
        return stringBuilder.toString()
    }
}