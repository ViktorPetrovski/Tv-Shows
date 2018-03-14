package com.viktorpetrovski.moviesgo.data.remote.api

import com.viktorpetrovski.moviesgo.BuildConfig
import com.viktorpetrovski.moviesgo.data.model.TvShow
import com.viktorpetrovski.moviesgo.data.remote.apiModel.MoviesListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Victor on 3/13/18.
 */
interface TvShowService {

    @GET("popular")
    fun listPopularTvShows(@Query(value = "api_key") api_key: String =  BuildConfig.MOVIES_DB_API_KEY,
                           @Query(value = "page") page : Int) : Observable<MoviesListResponse>


    @GET("{tv_id}")
    fun getTvShowDetails(@Path("tv_id") tvId : Long, @Query(value = "api_key") api_key: String =  BuildConfig.MOVIES_DB_API_KEY) : Observable<TvShow>

}