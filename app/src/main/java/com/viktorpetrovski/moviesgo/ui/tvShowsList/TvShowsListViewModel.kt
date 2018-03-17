package com.viktorpetrovski.moviesgo.ui.tvShowsList

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.viktorpetrovski.moviesgo.data.model.TvShow
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse
import com.viktorpetrovski.moviesgo.repository.TvShowsRepository
import com.viktorpetrovski.moviesgo.ui.base.MainActivityNavigationController
import com.viktorpetrovski.moviesgo.util.NetworkLoadingStatus
import com.viktorpetrovski.moviesgo.util.rx.SchedulerProvider
import javax.inject.Inject


/**
 * Created by Victor on 3/13/18.
 */

class TvShowsListViewModel @Inject constructor(private val repository: TvShowsRepository, private val mSchedulers: SchedulerProvider, private var mainActivityNavigationController: MainActivityNavigationController) : ViewModel() {

    @VisibleForTesting
    var tvShowsListResponse = MutableLiveData<TvShowListResponse>()

    var tvShowsList = ArrayList<TvShow>()

    //Pagination variables for Similar TVShows
    @VisibleForTesting
    var loadingObservable = MutableLiveData<NetworkLoadingStatus>()

    private val startingPagination = 1

    var page = startingPagination

    fun resetPagination() {
        page = startingPagination
    }

    fun loadPopularTvShows() {
        loadingObservable.value = NetworkLoadingStatus.LOADING
        repository.loadPopularTvShows(page)
                .subscribeOn(mSchedulers.io())
                .observeOn(mSchedulers.ui())
                .subscribe(this::handleResults, this::handleError)

    }


    @VisibleForTesting
    fun handleResults(response: TvShowListResponse) {

        if (page == startingPagination)
            tvShowsList = ArrayList()

        loadingObservable.value = NetworkLoadingStatus.SUCCESS

        page++

        if (response.totalPages <= page)
            loadingObservable.value = NetworkLoadingStatus.ALL_PAGES_LOADED


        tvShowsListResponse.value = response

        tvShowsList.addAll(response.showsList)
    }

    fun handleOnTvShowListItemClick(tvShow: TvShow) {
        mainActivityNavigationController.navigateToTvShowDetails(tvShow.id)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun handleError(t: Throwable) {
        loadingObservable.value = NetworkLoadingStatus.ERROR
    }


}