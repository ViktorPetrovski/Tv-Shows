package com.viktorpetrovski.moviesgo.repository

import com.viktorpetrovski.moviesgo.data.remote.api.TvShowService
import javax.inject.Inject

/**
 * Created by Victor on 3/13/18.
 */
class TvShowsRepository @Inject constructor(private val tvShowService: TvShowService){

    fun loadPopularTvShows(page : Int) = tvShowService.listPopularTvShows(page = page)

    fun loadTvShowDetails(tvShowId : Long) = tvShowService.getTvShowDetails(tvShowId)

    fun loadSimilarTvShows(tvShowId : Long) = tvShowService.getSimilarTvShows(tvShowId)

}