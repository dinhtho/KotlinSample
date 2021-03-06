package com.mvp.moviedbapi.network.service.movie

import com.mvp.moviedbapi.constants.Urls
import com.mvp.moviedbapi.models.response.SearchResults
import com.mvp.moviedbapi.network.NetworkProvider
import kotlinx.coroutines.Deferred
import rx.Observable

class MovieServiceBuilder {
    fun getMovies(apiKey: String, text: String, page: Int): Observable<SearchResults> {
        val restMovieSearchService = NetworkProvider.instance
                .provideApi(Urls.MOVIEDB_BASE_URL, RestMovieSearchService::class.java)
        return restMovieSearchService.getMovies(apiKey, text, page)
    }

    fun getMoviesCoroutine(apiKey: String, text: String, page: Int): Deferred<SearchResults> {
        val restMovieSearchService = NetworkProvider.instance
                .provideApiCoroutine(Urls.MOVIEDB_BASE_URL, RestMovieSearchService::class.java)
        return restMovieSearchService.getMoviesCoroutines(apiKey, text, page)
    }
}