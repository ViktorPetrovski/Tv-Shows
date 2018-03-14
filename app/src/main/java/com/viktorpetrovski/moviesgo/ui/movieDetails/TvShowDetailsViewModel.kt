package com.viktorpetrovski.moviesgo.ui.movieDetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.viktorpetrovski.moviesgo.data.model.TvShow
import com.viktorpetrovski.moviesgo.repository.TvShowsListingRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Victor on 3/14/18.
 */
class TvShowDetailsViewModel @Inject constructor(private val tvShowsListingRepository : TvShowsListingRepository) : ViewModel() {

    var tvShow = MutableLiveData<TvShow>()

    fun getTvShowDetails( tvShowId : Long){
        tvShowsListingRepository.loadTvShowDetails(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError)
    }

    private fun handleResults(response : TvShow) {
        //tvShow = MutableLiveData()
        tvShow.value = response
    }

    private fun handleError(t: Throwable) {

    }

}