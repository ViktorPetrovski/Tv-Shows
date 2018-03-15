package com.viktorpetrovski.moviesgo.di.module

import com.viktorpetrovski.moviesgo.ui.movieslist.MoviesListFragment
import com.viktorpetrovski.moviesgo.ui.movieslist.MoviesListModule
import com.viktorpetrovski.moviesgo.ui.tvShowDetails.TvShowDetailsFragment
import com.viktorpetrovski.moviesgo.ui.tvShowDetails.TvShowDetailsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Victor on 3/13/18.
 */
@Module
abstract class FragmentBuildersModule{

    @ContributesAndroidInjector(modules =  arrayOf(MoviesListModule::class))
    internal abstract fun contributeTvShowsListingFragment() : MoviesListFragment

    @ContributesAndroidInjector(modules = arrayOf(TvShowDetailsModule::class))
    internal abstract fun contributeTvShowDetailsFragment() : TvShowDetailsFragment


}