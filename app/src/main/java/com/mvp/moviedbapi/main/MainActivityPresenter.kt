package com.mvp.moviedbapi.main

import android.util.Log
import android.view.View
import com.mvp.moviedbapi.R
import com.mvp.moviedbapi.base.BasePresenter
import com.mvp.moviedbapi.constants.Urls
import com.mvp.moviedbapi.models.response.SearchResults
import com.mvp.moviedbapi.network.service.movie.MovieServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by olivier.goutay on 4/28/17.
 */

class MainActivityPresenter : BasePresenter<MainActivityView> {

    val TAG = "MainActivityPresenter"

    /**
     * The reference to [MainActivityView]
     * Created in [.attach] and destroyed in [.detach]
     */
    var mView: MainActivityView? = null

    override fun attach(view: MainActivityView) {
        this.mView = view
    }

    override fun detach() {
        this.mView = null
    }

    fun searchMovie(text: String, page: Int) {
        mView?.showLoading()
        if (text.isEmpty()) {
            mView?.showToast(R.string.search_error_no_text)
        }


        MovieServiceBuilder().getMovies(Urls.MOVIEDB_API_KEY_VALUE, text, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { mView?.hideLoading() }
                .subscribe(object : Subscriber<SearchResults>() {
                    override fun onCompleted() {
                        Log.e(TAG, "onCompleted")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "OnError" + e.message)
                        mView?.showToast(R.string.search_error_text)
                    }

                    override fun onNext(searchResults: SearchResults) {
                        Log.e(TAG, "onNext" + searchResults.results?.get(0)?.originalTitle)
                        mView?.updateMovieAdapter(searchResults)
                        val nextButtonGone = searchResults.totalPages!! < 2 || searchResults.page == searchResults.totalPages
                        mView?.setUpOnNextPageButton(text, if (nextButtonGone) View.GONE else View.VISIBLE, searchResults.page!! + 1)
                    }
                })
    }

    fun searchMovieCoroutine(text: String, page: Int) {
        mView?.showLoading()
        if (text.isEmpty()) {
            mView?.showToast(R.string.search_error_no_text)
        }

        GlobalScope.launch(Dispatchers.Main){
           val searchResults = MovieServiceBuilder().
                   getMoviesCoroutine(Urls.MOVIEDB_API_KEY_VALUE, text, page).await()
           Log.e(TAG, "onNext" + searchResults.results?.get(0)?.originalTitle)
           mView?.hideLoading()
           mView?.updateMovieAdapter(searchResults)
           val nextButtonGone = searchResults.totalPages!! < 2 || searchResults.page == searchResults.totalPages
           mView?.setUpOnNextPageButton(text, if (nextButtonGone) View.GONE else View.VISIBLE, searchResults.page!! + 1)
       }
    }



}
