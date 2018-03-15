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

    var tvShowList = ArrayList<TvShow>()

    //Pagination variables for Similar TVShows
    var isLoading = MutableLiveData<Boolean>()
    var showError = MutableLiveData<Boolean>()
    private val startingPagination = 1

    var page = startingPagination
    private var tvShowId : Long = 0

    fun setTvShowId(tvShowId: Long){
        this.tvShowId = tvShowId
    }

    fun getTvShowDetails() {
        tvShowsListingRepository.loadTvShowDetails(tvShowId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError)
    }

    fun getSimilarTvShows(){
        isLoading.value = true
        tvShowsListingRepository.loadSimilarTvShows(tvShowId,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSimilarTvShowResponse, this::handleError)
    }

    private fun handleSimilarTvShowResponse( response : TvShowListResponse){
        if(page == startingPagination) {
            tvShowList = ArrayList()
        }

        page++
        tvShowList.addAll(response.showsList)
        similarTvShowsList.value = tvShowList
        isLoading.value = false

    }

    private fun handleResults(response: TvShow) {
        //tvShow = MutableLiveData()
        tvShow.value = response
    }

    private fun handleError(t: Throwable) {
        isLoading.value = false
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