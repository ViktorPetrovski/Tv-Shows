package com.viktorpetrovski.moviesgo.ui.base

import android.support.v4.app.FragmentManager
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.ui.MainActivity
import com.viktorpetrovski.moviesgo.ui.tvShowDetails.TvShowDetailsFragment
import com.viktorpetrovski.moviesgo.ui.movieslist.MoviesListFragment
import javax.inject.Inject

/**
 * Created by Victor on 3/14/18.
 *
 * A utility class that handles navigation in MainActivity}.
 *
 */

class NavigationController @Inject constructor(private val mainActivity: MainActivity) {

    private val containerId: Int = R.id.containerLayout

    private val fragmentManager: FragmentManager = mainActivity.supportFragmentManager

    fun navigateToTvShowList(){
        fragmentManager.beginTransaction()
                .replace(containerId, MoviesListFragment.newInstance())
                .commitAllowingStateLoss()
    }

    fun navigateToTvShowDetails( tvShowId : Long){
        val tag = "tv_show $tvShowId"
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(containerId, TvShowDetailsFragment.newInstance(tvShowId) , tag)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    fun popBack() = mainActivity.onBackPressed()

}