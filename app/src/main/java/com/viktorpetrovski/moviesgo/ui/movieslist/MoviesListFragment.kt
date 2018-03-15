package com.viktorpetrovski.moviesgo.ui.movieslist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.di.Injectable
import com.viktorpetrovski.moviesgo.ui.base.NavigationController
import com.viktorpetrovski.moviesgo.util.ext.observe
import kotlinx.android.synthetic.main.fragment_movies_list.*
import ru.alexbykov.nopaginate.paginate.Paginate
import ru.alexbykov.nopaginate.paginate.PaginateBuilder
import javax.inject.Inject



/**
 * A placeholder fragment containing a simple view.
 */
class MoviesListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var navigatonController : NavigationController

    @Inject lateinit var moviesListAdapter: MoviesListAdapter

    @Inject lateinit var mLayoutManager: LinearLayoutManager

    private lateinit var viewModel : TvShowViewModel

    private lateinit var paginate : Paginate

    private lateinit var recyclerViewTvShows : RecyclerView

    private var page = 1

    companion object {
        fun newInstance() = MoviesListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!,viewModelFactory).get(TvShowViewModel::class.java)

        setUpRecyclerView()

        viewModel.tvShowsListResponse.observe(this) {
            it?.let{
                moviesListAdapter.addItems( it.showsList )
            }
            moviesListAdapter.mList = viewModel.tvShowsList
        }

        viewModel.isLoading.observe(this){
            it?.let {
                paginate.showLoading(it)
                //Close the Swipe Refresh only if it's been active before
                if(swipe_refresh.isRefreshing)
                    swipe_refresh.isRefreshing = it
            }
        }

        viewModel.showError.observe(this){
            it?.let {
                paginate.showError(it)
            }
        }

        moviesListAdapter.clickEvent.subscribe({
            navigatonController.navigateToTvShowDetails(it.id)
        })

        toolbar_tv_show_list.title = getString(R.string.app_name)

    }

    private fun setUpRecyclerView() {
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_tv_shows.layoutManager = mLayoutManager
        rv_tv_shows.itemAnimator = DefaultItemAnimator()
        rv_tv_shows.adapter = moviesListAdapter

        paginate = PaginateBuilder()
                .with(rv_tv_shows)
                .setOnLoadMoreListener {
                    viewModel.loadPopularTvShows()
                }
                .build()

        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)

        swipe_refresh.setOnRefreshListener {
            viewModel.resetPagination()
            viewModel.loadPopularTvShows()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        paginate.unbind()
    }
}
