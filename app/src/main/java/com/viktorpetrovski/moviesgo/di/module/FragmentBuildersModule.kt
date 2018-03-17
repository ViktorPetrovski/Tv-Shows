package com.viktorpetrovski.moviesgo.di.module

import com.viktorpetrovski.moviesgo.ui.tvShowsList.TvShowsListFragment
import com.viktorpetrovski.moviesgo.ui.tvShowsList.TvShowsListModule
import com.viktorpetrovski.moviesgo.ui.tvShowDetails.TvShowDetailsFragment
import com.viktorpetrovski.moviesgo.ui.tvShowDetails.TvShowDetailsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Victor on 3/13/18.
 *
 */
@Module
abstract class FragmentBuildersModule{

    @ContributesAndroidInjector(modules =  arrayOf(TvShowsListModule::class))
    internal abstract fun contributeTvShowsListingFragment() : TvShowsListFragment

    @ContributesAndroidInjector(modules = arrayOf(TvShowDetailsModule::class))
    internal abstract fun contributeTvShowDetailsFragment() : TvShowDetailsFragment


}