package com.viktorpetrovski.moviesgo.ui.tvShowsList

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.viktorpetrovski.moviesgo.data.model.TvShow
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse
import com.viktorpetrovski.moviesgo.repository.TvShowsRepository
import com.viktorpetrovski.moviesgo.util.NetworkLoadingStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by Victor on 3/13/18.
 */

class TvShowsListViewModel @Inject constructor(private val repository: TvShowsRepository) : ViewModel() {

    var tvShowsListResponse = MutableLiveData<TvShowListResponse>()

    var tvShowsList = ArrayList<TvShow>()

    //Pagination variables for Similar TVShows
    var loadingObservable = MutableLiveData<NetworkLoadingStatus>()

    private val startingPagination = 1

    var page = startingPagination

    fun resetPagination() {
        page = startingPagination
    }

    fun loadPopularTvShows() {
        loadingObservable.value = NetworkLoadingStatus.LOADING
        repository.loadPopularTvShows(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError)
    }


    private fun handleResults(response: TvShowListResponse) {
        loadingObservable.value = NetworkLoadingStatus.SUCCESS

        if (page == startingPagination)
            tvShowsList = ArrayList()

        if(response.totalPages >= page)
            loadingObservable.value = NetworkLoadingStatus.ALL_PAGES_LOADED

        page++

        tvShowsListResponse.value = response

        loadingObservable.value = NetworkLoadingStatus.SUCCESS

        tvShowsList.addAll(response.showsList)
    }

    private fun handleError(t: Throwable) {
        loadingObservable.value = NetworkLoadingStatus.ERROR
    }


}