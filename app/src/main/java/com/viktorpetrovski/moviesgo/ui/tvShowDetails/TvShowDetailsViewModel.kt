package com.viktorpetrovski.moviesgo.ui.tvShowDetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.viktorpetrovski.moviesgo.data.model.TvShow
import com.viktorpetrovski.moviesgo.data.remote.apiModel.TvShowListResponse
import com.viktorpetrovski.moviesgo.repository.TvShowsRepository
import com.viktorpetrovski.moviesgo.util.NetworkEnum
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
    var loadingObservable = MutableLiveData<NetworkEnum>()

    //Pagination variables for Similar TVShows
    var isLoadingSimilarTvShows = MutableLiveData<Boolean>()
    var showError = MutableLiveData<Boolean>()
    private val startingPagination = 1

    var page = startingPagination
    private var tvShowId : Long = 0

    fun setTvShowId(tvShowId: Long){
        this.tvShowId = tvShowId
    }

    fun getTvShowDetails() {
        loadingObservable.value = NetworkEnum.LOADING
        tvShowsListingRepository.loadTvShowDetails(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError)
    }

    fun getSimilarTvShows(){
        isLoadingSimilarTvShows.value = true
        tvShowsListingRepository.loadSimilarTvShows(tvShowId,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSimilarTvShowResponse, this::handleSimillarTvShowsError)
    }

    private fun handleSimilarTvShowResponse( response : TvShowListResponse){
        if(page == startingPagination) {
            tvShowList = ArrayList()
        }

        page++
        tvShowList.addAll(response.showsList)
        similarTvShowsList.value = tvShowList
        isLoadingSimilarTvShows.value = false

    }

    private fun handleResults(response: TvShow) {
        loadingObservable.value = NetworkEnum.SUCCESS
        tvShow.value = response
    }

    private fun handleError(t: Throwable) {
        loadingObservable.value = NetworkEnum.ERROR
    }

    private fun handleSimillarTvShowsError(t: Throwable) {
        isLoadingSimilarTvShows.value = false
        showError.value = true
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