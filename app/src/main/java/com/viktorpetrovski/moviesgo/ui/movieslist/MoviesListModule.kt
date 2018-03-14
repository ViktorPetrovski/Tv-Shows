package com.viktorpetrovski.moviesgo.ui.movieslist

import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

/**
 * Created by Victor on 3/13/18.
 */
@Module
class MoviesListModule {

    @Provides
    fun provideLinearLayoutManager(fragment : MoviesListFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }
}