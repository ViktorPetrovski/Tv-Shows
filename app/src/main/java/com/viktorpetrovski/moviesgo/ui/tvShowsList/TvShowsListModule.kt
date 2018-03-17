package com.viktorpetrovski.moviesgo.ui.tvShowsList

import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

/**
 * Created by Victor on 3/13/18.
 */
@Module
class TvShowsListModule {

    @Provides
    fun provideLinearLayoutManager(fragment : TvShowsListFragment) = LinearLayoutManager(fragment.activity)

}