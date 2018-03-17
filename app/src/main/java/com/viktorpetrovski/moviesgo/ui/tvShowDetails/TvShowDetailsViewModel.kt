package com.viktorpetrovski.moviesgo.ui.tvShowDetails

import android.annotation.SuppressLint
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
 * Created by Victor on 3/14/18.
 */
class TvShowDetailsViewModel @Inject constructor(private val tvShowsListingRepository: TvShowsRepository) : ViewModel() {

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

    @SuppressLint("RxLeakedSubscription")
    fun getTvShowDetails() {
        loadingObservable.value = NetworkLoadingStatus.LOADING
        tvShowsListingRepository.loadTvShowDetails(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError)
    }

    @SuppressLint("RxLeakedSubscription")
    fun getSimilarTvShows(){
        similarShowsLoadingObservable.value = NetworkLoadingStatus.LOADING
        tvShowsListingRepository.loadSimilarTvShows(tvShowId,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    private fun handleResults(response: TvShow) {
        loadingObservable.value = NetworkLoadingStatus.SUCCESS
        tvShow.value = response
    }

    private fun handleError(t: Throwable) {
        loadingObservable.value = NetworkLoadingStatus.ERROR
    }

    private fun handleSimilarTvShowsError(t : Throwable) {
        similarShowsLoadingObservable.value = NetworkLoadingStatus.ERROR
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