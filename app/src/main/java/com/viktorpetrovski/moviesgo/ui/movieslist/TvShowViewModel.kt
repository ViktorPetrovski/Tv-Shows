package com.viktorpetrovski.moviesgo.ui.movieslist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse
import com.viktorpetrovski.moviesgo.repository.TvShowsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by Victor on 3/13/18.
 */

class TvShowViewModel @Inject constructor( private val repository: TvShowsRepository) : ViewModel(){

    val tvShowsPage = MutableLiveData<Int>()
    var tvShowsList = MutableLiveData<TvShowListResponse>()
    var isLoading = MutableLiveData<Boolean>()
    var showError = MutableLiveData<Boolean>()
    var shouldClearList = MutableLiveData<Boolean>()

    private val startingPagination = 1

    var page = startingPagination

    fun resetPagination(){
        page = startingPagination
    }

    fun loadPopularTvShows(){
        isLoading.value = true
        repository.loadPopularTvShows(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError)
    }


    private fun handleResults(response: TvShowListResponse) {

        if(page == startingPagination)
            shouldClearList.value = true

        page++
        tvShowsList.value = response
        isLoading.value = false
    }

    private fun handleError(t: Throwable) {
        isLoading.value = false
        showError.value = true
    }


}