package com.viktorpetrovski.moviesgo.ui.tvShowsList

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.di.Injectable
import com.viktorpetrovski.moviesgo.ui.base.BaseFragment
import com.viktorpetrovski.moviesgo.ui.base.MainActivityNavigationController
import com.viktorpetrovski.moviesgo.util.NetworkLoadingStatus
import com.viktorpetrovski.moviesgo.util.ext.observe
import com.viktorpetrovski.moviesgo.util.view.CustomErrorItem
import kotlinx.android.synthetic.main.fragment_movies_list.*
import ru.alexbykov.nopaginate.paginate.Paginate
import ru.alexbykov.nopaginate.paginate.PaginateBuilder
import javax.inject.Inject


/**
 * A placeholder fragment containing a simple view.
 */
class TvShowsListFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var mainActivityNavigationController: MainActivityNavigationController

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var tvShowsListAdapter: TvShowsListAdapter

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    lateinit var tvShowsListViewModel: TvShowsListViewModel

    private lateinit var paginate: Paginate

    companion object {
        fun newInstance() = TvShowsListFragment()
    }

    override fun getLayout() = R.layout.fragment_movies_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvShowsListViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowsListViewModel::class.java)

        setUpRecyclerView()

        loadingObservable()

        tvShowsListViewModel.tvShowsListResponse.observe(this) {
            tvShowsListAdapter.mList = tvShowsListViewModel.tvShowsList
        }

        toolbar_tv_show_list.title = getString(R.string.app_name)
    }

    private fun loadingObservable() {
        tvShowsListViewModel.loadingObservable.observe(this) {
            when (it) {
                NetworkLoadingStatus.SUCCESS -> {
                    paginate.showLoading(false)
                    paginate.showError(false)
                    swipe_refresh.isRefreshing = false
                }
                NetworkLoadingStatus.LOADING -> {
                    paginate.setNoMoreItems(false)
                    paginate.showLoading(true)
                    paginate.showError(false)
                }
                NetworkLoadingStatus.ERROR -> {
                    paginate.showLoading(false)
                    paginate.showError(true)
                    swipe_refresh.isRefreshing = false
                }
                NetworkLoadingStatus.ALL_PAGES_LOADED ->{
                    paginate.setNoMoreItems(true)
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_tv_shows.layoutManager = mLayoutManager
        rv_tv_shows.itemAnimator = DefaultItemAnimator()
        rv_tv_shows.adapter = tvShowsListAdapter

        //Handles Tv Show ItemClick
        tvShowsListAdapter.clickEvent.subscribe({
            tvShowsListViewModel.handleOnTvShowListItemClick(it)
        })


        paginate = PaginateBuilder()
                .with(rv_tv_shows)
                .setLoadingTriggerThreshold(5) //0 by default
                .setCustomErrorItem(CustomErrorItem())
                .setOnLoadMoreListener {
                    tvShowsListViewModel.loadPopularTvShows()
                }
                .build()

        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)

        swipe_refresh.setOnRefreshListener {
            tvShowsListViewModel.resetPagination()
            tvShowsListViewModel.loadPopularTvShows()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        paginate.unbind()
    }
}
