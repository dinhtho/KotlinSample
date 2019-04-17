package com.mvp.moviedbapi.network.service.movie

import com.mvp.moviedbapi.constants.Urls
import com.mvp.moviedbapi.models.response.SearchResults
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by olivier.goutay on 4/18/17.
 * Declaration of the [RestMovieSearchService] allowing to query [com.mvp.moviedbapi.constants.Urls.MOVIEDB_BASE_URL]
 */
interface RestMovieSearchService {

    @GET("movie?")
    fun getMovies(@Query(Urls.MOVIEDB_API_KEY_QUERY) apiKey: String,
                  @Query(Urls.MOVIEDB_MOVIE_TITLE_QUERY) movieTitle: String,
                  @Query(Urls.MOVIEDB_PAGE_QUERY) page: Int): Observable<SearchResults>

    @GET("movie?")
    fun getMoviesCoroutines(@Query(Urls.MOVIEDB_API_KEY_QUERY) apiKey: String,
                  @Query(Urls.MOVIEDB_MOVIE_TITLE_QUERY) movieTitle: String,
                  @Query(Urls.MOVIEDB_PAGE_QUERY) page: Int): Deferred<SearchResults>

}
