package com.mvp.moviedbapi.main

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.mvp.moviedbapi.R
import com.mvp.moviedbapi.models.response.SearchResults
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The main [android.app.Activity]
 * Improvements:
 */
class MainActivity : AppCompatActivity(), MainActivityView {

    private var mPresenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter.attach(this)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchButton.setOnClickListener { mPresenter.searchMovie(edittext.text.toString(), 1) }
    }

    override fun onDestroy() {
        mPresenter.detach()
        super.onDestroy()
    }

    override fun showToast(@StringRes idString: Int) {
        Toast.makeText(this, idString, Toast.LENGTH_LONG).show()
    }

    /**
     * Create / update the [RecyclerView.getAdapter]

     * @param searchResults The results obtained in [MainActivityPresenter.searchMovie]
     */
    override fun updateMovieAdapter(searchResults: SearchResults) {
        if (recyclerView.adapter is MovieSearchAdapter) {
            //Already an adapter, just needs to update
            val movieSearchAdapter = recyclerView.adapter as MovieSearchAdapter
            movieSearchAdapter.mSearchResults = searchResults
            movieSearchAdapter.notifyDataSetChanged()
        } else {
            //Create a new adapter
            recyclerView.adapter = MovieSearchAdapter(searchResults)
        }
    }

    override fun setUpOnNextPageButton(text: String, visibility: Int, page: Int) {
        nextButton.visibility = visibility
        nextButton.setOnClickListener { mPresenter.searchMovie(text, page) }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE;
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE;
    }
}
