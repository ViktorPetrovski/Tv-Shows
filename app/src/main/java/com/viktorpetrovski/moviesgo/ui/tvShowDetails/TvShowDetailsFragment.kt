package com.viktorpetrovski.moviesgo.ui.tvShowDetails


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.di.Injectable
import com.viktorpetrovski.moviesgo.ui.base.BaseFragment
import com.viktorpetrovski.moviesgo.ui.base.MainActivityNavigationController
import com.viktorpetrovski.moviesgo.util.ImageLoader
import com.viktorpetrovski.moviesgo.util.NetworkLoadingStatus
import com.viktorpetrovski.moviesgo.util.ext.observe
import com.viktorpetrovski.moviesgo.util.view.CustomErrorItem
import kotlinx.android.synthetic.main.details_tv_show.*
import kotlinx.android.synthetic.main.fragment_tv_show_details.*
import kotlinx.android.synthetic.main.tv_show_details_stats.*
import ru.alexbykov.nopaginate.paginate.Paginate
import ru.alexbykov.nopaginate.paginate.PaginateBuilder
import javax.inject.Inject





/**
 * A simple [Fragment] subclass.
 */
class TvShowDetailsFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mainActivityNavigationController : MainActivityNavigationController

    @Inject
    lateinit var similarShowsAdapter: SimilarShowsAdapter

    @Inject lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var tvShowViewModel: TvShowDetailsViewModel

    private lateinit var paginate: Paginate

    var toolbarTitle = String()

    companion object {

        private const val ARG_TVSHOW_ID = "tv_show_id"

        fun newInstance(showId: Long): TvShowDetailsFragment {

            val newFragment = TvShowDetailsFragment()

            val args = Bundle()
            args.putLong(ARG_TVSHOW_ID, showId)
            newFragment.arguments = args

            return newFragment
        }
    }

    override fun getLayout() = R.layout.fragment_tv_show_details

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvShowViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowDetailsViewModel::class.java)


        arguments?.getLong(ARG_TVSHOW_ID)?.let {
            tvShowViewModel.setTvShowId(it)
            tvShowViewModel.getTvShowDetails()
        }

        if(savedInstanceState != null){
            //Fragment's been re-created we need to re-provide the NavigationController
            tvShowViewModel.mainActivityNavigationController = mainActivityNavigationController
        }

        setUpRecyclerView()

        observeMainContentStatus()

        observeSimilarTvShows()

        observeMainContent()

        customizeToolbar()

        hideToolbarTitleWhenExpanded()

    }


    private fun observeMainContentStatus() {
        tvShowViewModel.loadingObservable.observe(this) {
            it?.let {
                when (it) {
                    NetworkLoadingStatus.SUCCESS -> {
                        progresss_waiting.hide()
                        content.visibility = View.VISIBLE
                        content_error.visibility = View.GONE
                    }
                    NetworkLoadingStatus.LOADING -> {
                        progresss_waiting.show()
                        content.visibility = View.GONE
                        content_error.visibility = View.GONE
                    }
                    NetworkLoadingStatus.ERROR -> {
                        progresss_waiting.hide()
                        content.visibility = View.GONE
                        content_error.visibility = View.VISIBLE
                    }
                }
            }
        }

        //Handle Retry Click
        content_error.setOnClickListener {
            tvShowViewModel.getTvShowDetails()
        }
    }

    private fun observeMainContent() = tvShowViewModel.tvShow.observe(this) {
        it?.let {
            ImageLoader.loadPosterImage(iv_poster, it.posterPath)
            ImageLoader.loadBannerImage(iv_header, it.backDropPath)
            tv_show_title.text = it.name
            toolbarTitle = it.name
            tv_description.text = it.overview
            tv_show_genres.text = tvShowViewModel.createGenresString(it)

            tv_episodes.text = String.format(getString(R.string.no_episodes), it.numberEpisodes)
            tv_seasons.text = String.format(getString(R.string.no_seasons), it.numberSeasons)
            tv_vote_avg.text = String.format(getString(R.string.avg_rating), it.vote)

            tv_number_of_votes.text = it.voteCount.toString()
        }
    }

    private fun customizeToolbar(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            collapsing_toolbar_layout.setExpandedTitleTextAppearance(R.style.TransparentText)


        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            mainActivityNavigationController.popBack()
        }

    }

    private fun hideToolbarTitleWhenExpanded() {
        appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsing_toolbar_layout.title = toolbarTitle
                    isShow = true
                } else if (isShow) {
                    collapsing_toolbar_layout.title = ""
                    isShow = false
                }
            }
        })
    }

    private fun observeSimilarTvShows(){
        tvShowViewModel.similarTvShowsList.observe(this) {
            it?.let {
                similarShowsAdapter.mList = tvShowViewModel.tvShowList
            }
        }

        tvShowViewModel.similarShowsLoadingObservable.observe(this) {
            it?.let {
                when (it) {
                    NetworkLoadingStatus.SUCCESS -> {
                        paginate.showLoading(false)
                        paginate.showError(false)
                    }
                    NetworkLoadingStatus.LOADING -> {
                        paginate.showLoading(true)
                        paginate.showError(false)
                        paginate.setNoMoreItems(false)
                    }
                    NetworkLoadingStatus.ERROR -> {
                        paginate.showLoading(false)
                        paginate.showError(true)
                    }
                    NetworkLoadingStatus.ALL_PAGES_LOADED ->{
                        paginate.setNoMoreItems(true)
                    }
                }
            }
        }

    }

    private fun setUpRecyclerView() {

        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rv_similar_tv_show.layoutManager = linearLayoutManager
        rv_similar_tv_show.adapter = similarShowsAdapter


        ViewCompat.setNestedScrollingEnabled(rv_similar_tv_show, false)

        paginate = PaginateBuilder()
                .with(rv_similar_tv_show)
                .setLoadingTriggerThreshold(5) //0 by default
                .setCustomErrorItem(CustomErrorItem())
                .setOnLoadMoreListener {
                    tvShowViewModel.getSimilarTvShows()
                }
                .build()

        //Handle Adapter OnClick event
        similarShowsAdapter.clickEvent.subscribe({
            tvShowViewModel.handleOnSimilarTvShowListItemClick(it)
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        paginate.unbind()
    }

}
