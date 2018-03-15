package com.viktorpetrovski.moviesgo.data.remote.api

import com.viktorpetrovski.moviesgo.BuildConfig
import com.viktorpetrovski.moviesgo.data.model.TvShow
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Victor on 3/13/18.
 */
interface TvShowService {


    /**
     * Get a list of the current popular TV shows on TMDb.
     * */
    @GET("popular")
    fun listPopularTvShows(@Query(value = "api_key") api_key: String =  BuildConfig.MOVIES_DB_API_KEY,
                           @Query(value = "page") page : Int) : Observable<TvShowListResponse>


    /**
     * Get the primary TV show details by id.
     * */
    @GET("{tv_id}")
    fun getTvShowDetails(@Path("tv_id") tvId : Long, @Query(value = "api_key") api_key: String =  BuildConfig.MOVIES_DB_API_KEY) : Observable<TvShow>


    /**
     * Get a list of similar TV shows. These items are assembled by looking at keywords and genres.
     * */
    @GET("{tv_id}/similar")
    fun getSimilarTvShows(@Path("tv_id") tvId : Long, @Query(value = "api_key") api_key: String =  BuildConfig.MOVIES_DB_API_KEY,@Query(value = "page") page : Int) : Observable<TvShowListResponse>

}