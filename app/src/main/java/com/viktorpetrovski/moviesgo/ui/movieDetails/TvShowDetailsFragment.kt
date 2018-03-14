package com.viktorpetrovski.moviesgo.ui.movieDetails


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.di.Injectable
import com.viktorpetrovski.moviesgo.ui.base.NavigationController
import com.viktorpetrovski.moviesgo.util.ImageLoader
import com.viktorpetrovski.moviesgo.util.ext.observe
import kotlinx.android.synthetic.main.details_tv_show.*
import kotlinx.android.synthetic.main.fragment_tv_show_details.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class TvShowDetailsFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var navigationController: NavigationController

    private lateinit var tvShowViewModel: TvShowDetailsViewModel

    var toolbarTitle = String()

    companion object {

        private val ARG_TVSHOW_ID = "tv_show_id"

        fun newInstance(showId : Long) : TvShowDetailsFragment{

            val newFragment = TvShowDetailsFragment()

            val args = Bundle()
            args.putLong(ARG_TVSHOW_ID,showId)
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


        tvShowViewModel = ViewModelProviders.of(this,viewModelFactory).get(TvShowDetailsViewModel::class.java)


        tvShowViewModel.tvShow.observe(this) {
            it?.let{
                ImageLoader.loadPosterImage(iv_poster,it.posterPath)
                ImageLoader.loadBannerImage(iv_header,it.backDropPath)
                tv_show_title.text = it.name
                toolbarTitle = it.name
                tv_description.text = "${it.overview} ${it.overview} ${it.overview} ${it.overview} "

            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            collapsing_toolbar_layout.setExpandedTitleTextAppearance(R.style.TransparentText)


        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_back_arrow)
        toolbar.setNavigationOnClickListener {
            navigationController.popBack()
        }

        arguments?.getLong(ARG_TVSHOW_ID)?.let {
            tvShowViewModel.getTvShowDetails(it)
        }

        hideToolbarTitleWhenExpanded()

    }


    private fun hideToolbarTitleWhenExpanded(){
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

}
