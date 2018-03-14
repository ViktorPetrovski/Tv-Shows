package com.viktorpetrovski.moviesgo.ui.movieslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.viktorpetrovski.moviesgo.data.remote.apiModel.MoviesListResponse
import com.viktorpetrovski.moviesgo.repository.TvShowsListingRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by Victor on 3/13/18.
 */
class TvShowViewModel @Inject constructor( private val repository: TvShowsListingRepository) : ViewModel(){

    val tvShowsPage = MutableLiveData<Int>()
    var tvShowsList = MutableLiveData<MoviesListResponse>()

    init {
//        tvShowsList = tvShowsPage.switchMap {
//            repository.loadPopularTvShows(it)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .onErrorResumeNext(Flowable.empty())
//                    .toLiveData()
//
//        }
    }

    fun loadPopularTvShows( page : Int){
        repository.loadPopularTvShows(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError)
    }


    private fun handleResults(response: MoviesListResponse) {
        tvShowsList.value = response
    }

    private fun handleError(t: Throwable) {

    }


}