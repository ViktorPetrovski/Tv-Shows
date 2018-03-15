package com.viktorpetrovski.moviesgo.ui.tvShowDetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.viktorpetrovski.moviesgo.data.model.TvShow
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse
import com.viktorpetrovski.moviesgo.repository.TvShowsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Victor on 3/14/18.
 */
class TvShowDetailsViewModel @Inject constructor(private val tvShowsListingRepository: TvShowsRepository) : ViewModel() {

    var tvShow = MutableLiveData<TvShow>()
    var similarTvShowsList = MutableLiveData<List<TvShow>>()

    fun getTvShowDetails(tvShowId: Long) {
        tvShowsListingRepository.loadTvShowDetails(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError)
    }

    fun getSimilarTvShows(tvShowId: Long){
        tvShowsListingRepository.loadSimilarTvShows(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSimilarTvShowResponse, this::handleError)
    }

    private fun handleSimilarTvShowResponse( response : TvShowListResponse){
        similarTvShowsList.value = response.showsList
    }

    private fun handleResults(response: TvShow) {
        //tvShow = MutableLiveData()
        tvShow.value = response
    }

    private fun handleError(t: Throwable) {

    }


    fun createGenresString(tvShow: TvShow): String {

        val sb = StringBuilder()

        var index = 0
        val size = tvShow.genresList.size
        tvShow.genresList.forEach {
            sb.append(it.genreName)

            if (index++ != size - 1)
                sb.append("  |  ")

        }

        return sb.toString()
    }

}