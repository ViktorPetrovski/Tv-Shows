package com.viktorpetrovski.moviesgo.ui.tvShowDetails


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.di.Injectable
import com.viktorpetrovski.moviesgo.ui.base.NavigationController
import com.viktorpetrovski.moviesgo.util.ImageLoader
import com.viktorpetrovski.moviesgo.util.NetworkEnum
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
class TvShowDetailsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigationController: NavigationController

    @Inject
    lateinit var similarShowsAdapter: SimilarShowsAdapter

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_tv_show_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvShowViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowDetailsViewModel::class.java)

        setUpRecyclerView()

        progresss_waiting.show()

        tvShowViewModel.loadingObservable.observe(this){
            it?.let {
                when(it){
                    NetworkEnum.SUCCESS ->{
                        progresss_waiting.hide()
                        content.visibility = View.VISIBLE
                        content_error.visibility = View.GONE
                    }
                    NetworkEnum.LOADING ->{
                        progresss_waiting.show()
                        content.visibility = View.GONE
                        content_error.visibility = View.GONE
                    }
                    NetworkEnum.ERROR ->{
                        progresss_waiting.hide()
                        content.visibility = View.GONE
                        content_error.visibility = View.VISIBLE
                    }
                }
            }

        }

        content_error.setOnClickListener {
            tvShowViewModel.getTvShowDetails()
        }

        tvShowViewModel.tvShow.observe(this) {
            it?.let {
                ImageLoader.loadPosterImage(iv_poster, it.posterPath)
                ImageLoader.loadBannerImage(iv_header, it.backDropPath)
                tv_show_title.text = it.name
                toolbarTitle = it.name
                tv_description.text = "${it.overview}"
                tv_show_genres.text = tvShowViewModel.createGenresString(it)

                tv_episodes.text = String.format(getString(R.string.no_episodes),it.numberEpisodes)
                tv_seasons.text = String.format(getString(R.string.no_seasons),it.numberSeasons)
                tv_vote_avg.text = String.format(getString(R.string.avg_rating),it.vote)

                tv_number_of_votes.text = it.voteCount.toString()

                //progresss_waiting.hide()
            }
        }

        tvShowViewModel.similarTvShowsList.observe(this) {
            it?.let {
                similarShowsAdapter.mList = tvShowViewModel.tvShowList
            }
        }

        tvShowViewModel.isLoadingSimilarTvShows.observe(this){
            it?.let {
                paginate.showLoading(it)
            }
        }

        tvShowViewModel.showError.observe(this){
            it?.let {
                paginate.showError(it)
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            collapsing_toolbar_layout.setExpandedTitleTextAppearance(R.style.TransparentText)


        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            navigationController.popBack()
        }

        arguments?.getLong(ARG_TVSHOW_ID)?.let {
            tvShowViewModel.setTvShowId(it)
            tvShowViewModel.getTvShowDetails()
        }

        hideToolbarTitleWhenExpanded()

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

    private fun setUpRecyclerView() {
        ViewCompat.setNestedScrollingEnabled(rv_similar_tv_show, false);

//        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_similar_tv_show.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //rv_similar_tv_show.isNestedScrollingEnabled = false
        rv_similar_tv_show.adapter = similarShowsAdapter


        paginate = PaginateBuilder()
                .with(rv_similar_tv_show)
                .setLoadingTriggerThreshold(5) //0 by default
                .setCustomErrorItem(CustomErrorItem())
                .setOnLoadMoreListener {
                    tvShowViewModel.getSimilarTvShows()
                }
                .build()

//        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
//
//        swipe_refresh.setOnRefreshListener {
//            tvShowViewModel.resetPagination()
//            tvShowViewModel.getSimilarTvShows()
//        }

        //Handle Adapter OnClick event
        similarShowsAdapter.clickEvent.subscribe({
            navigationController.navigateToTvShowDetails(it.id)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        paginate.unbind()
    }

}
