package com.viktorpetrovski.moviesgo.ui.movieslist

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.di.Injectable
import com.viktorpetrovski.moviesgo.ui.base.NavigationController
import com.viktorpetrovski.moviesgo.util.ext.observe
import kotlinx.android.synthetic.main.fragment_movies_list.*
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

        setUp()

        viewModel.tvShowsList.observe(this) {
            it?.let{
                moviesListAdapter.mList = it.showsList
            }
        }

        moviesListAdapter.clickEvent.subscribe({
            navigatonController.navigateToTvShowDetails(it.id)
        })

        viewModel.loadPopularTvShows(1)

    }

    private fun setUp() {
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_tv_shows.layoutManager = mLayoutManager
        rv_tv_shows.itemAnimator = DefaultItemAnimator()
        rv_tv_shows.adapter = moviesListAdapter
    }
}
