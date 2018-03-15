package com.viktorpetrovski.moviesgo.ui.tvShowDetails

import android.support.v7.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides

/**
 * Created by Victor on 3/15/18.
 */
@Module
class TvShowDetailsModule {

    @Provides
    fun provideLinearLayoutManager(fragment : TvShowDetailsFragment) : LinearLayoutManager {
        return LinearLayoutManager(fragment.activity)
    }
}