package com.viktorpetrovski.moviesgo.ui.tvShowDetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.viktorpetrovski.moviesgo.data.model.TvShow
import com.viktorpetrovski.moviesgo.data.model.apiModel.TvShowListResponse
import com.viktorpetrovski.moviesgo.data.repository.TvShowsRepository
import com.viktorpetrovski.moviesgo.ui.base.MainActivityNavigationController
import com.viktorpetrovski.moviesgo.util.NetworkLoadingStatus
import com.viktorpetrovski.moviesgo.util.rx.SchedulerProvider
import javax.inject.Inject

/**
 * Created by Victor on 3/14/18.
 */
class TvShowDetailsViewModel @Inject constructor(private val tvShowsListingRepository: TvShowsRepository,
                                                 private val mSchedulers : SchedulerProvider,
                                                 private var mainActivityNavigationController: MainActivityNavigationController) : ViewModel() {

    var tvShow = MutableLiveData<TvShow>()

    var similarTvShowsList = MutableLiveData<List<TvShow>>()

    var tvShowList = ArrayList<TvShow>()

    var loadingObservable = MutableLiveData<NetworkLoadingStatus>()

    /**
     * Pagination variables for Similar TVShows
     * */
    var similarShowsLoadingObservable = MutableLiveData<NetworkLoadingStatus>()

    private val startingPagination = 1

    var page = startingPagination

    private var tvShowId : Long = 0

    fun setTvShowId(tvShowId: Long){
        this.tvShowId = tvShowId
    }

    fun getTvShowDetails() {
        loadingObservable.value = NetworkLoadingStatus.LOADING
        tvShowsListingRepository.loadTvShowDetails(tvShowId)
                .subscribeOn(mSchedulers.io())
                .observeOn(mSchedulers.ui())
                .subscribe(this::handleResults, this::handleError)
    }

    fun getSimilarTvShows(){
        similarShowsLoadingObservable.value = NetworkLoadingStatus.LOADING
        tvShowsListingRepository.loadSimilarTvShows(tvShowId,page)
                .subscribeOn(mSchedulers.io())
                .observeOn(mSchedulers.ui())
                .subscribe(this::handleSimilarTvShowResponse, this::handleSimilarTvShowsError)
    }

    private fun handleSimilarTvShowResponse( response : TvShowListResponse){
        if(page == startingPagination) {
            tvShowList = ArrayList()
        }

        similarShowsLoadingObservable.value = NetworkLoadingStatus.SUCCESS

        if(response.totalPages <= page)
            similarShowsLoadingObservable.value = NetworkLoadingStatus.ALL_PAGES_LOADED

        page++
        tvShowList.addAll(response.showsList)
        similarTvShowsList.value = tvShowList

    }

    @VisibleForTesting
    fun handleResults(response: TvShow) {
        loadingObservable.value = NetworkLoadingStatus.SUCCESS
        tvShow.value = response
    }

    private fun handleError(t: Throwable) {
        loadingObservable.value = NetworkLoadingStatus.ERROR
    }

    private fun handleSimilarTvShowsError(t : Throwable) {
        similarShowsLoadingObservable.value = NetworkLoadingStatus.ERROR
    }

    fun handleOnSimilarTvShowListItemClick(tvShow : TvShow){
        mainActivityNavigationController.navigateToTvShowDetails(tvShow.id)
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