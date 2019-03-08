package com.mvp.moviedbapi.main

import android.support.annotation.StringRes
import com.mvp.moviedbapi.base.BasePresenter

import com.mvp.moviedbapi.models.response.SearchResults

/**
 * Created by olivier.goutay on 4/28/17.
 */


interface MainActivityView {

    fun showLoading()

    fun hideLoading()

    fun showToast(@StringRes idString: Int)


    fun updateMovieAdapter(searchResults: SearchResults)


    fun setUpOnNextPageButton(text: String, visibility: Int, page: Int)
}

