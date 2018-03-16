package com.viktorpetrovski.moviesgo.data.remote.api

import com.viktorpetrovski.moviesgo.data.model.TvShow
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Victor on 3/13/18.
 *
 * [retrofit2.Retrofit] service all about [TvShow]s.
 */
interface TvShowService {

    /**
     * Get a list of the current popular TV shows on TMDb.
     *
     * @param page The value of the current page we are loading. Default ( and starting ) page is 1
     * */
    @GET("popular")
    fun listPopularTvShows(@Query(value = "page") page : Int) : Observable<TvShowListResponse>


    /**
     * Get the primary TV show details by id.
     *
     * @param tv_id The ID of the TV Show we want to load.
     * */
    @GET("{tv_id}")
    fun getTvShowDetails(@Path("tv_id") tvId : Long) : Observable<TvShow>


    /**
     * Get a list of similar TV shows. These items are assembled by looking at keywords and genres.
     *
     * @param tv_id The ID of the TV Show we want to load similar Tv Shows.
     * */
    @GET("{tv_id}/similar")
    fun getSimilarTvShows(@Path("tv_id") tvId : Long, @Query(value = "page") page : Int) : Observable<TvShowListResponse>

}