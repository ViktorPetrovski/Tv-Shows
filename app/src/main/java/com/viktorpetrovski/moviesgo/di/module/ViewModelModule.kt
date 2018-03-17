package com.viktorpetrovski.moviesgo.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.viktorpetrovski.moviesgo.di.annotation.ViewModelKey
import com.viktorpetrovski.moviesgo.util.provider.ViewModelProviderFactory
import com.viktorpetrovski.moviesgo.ui.tvShowsList.TvShowsListViewModel
import com.viktorpetrovski.moviesgo.ui.tvShowDetails.TvShowDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Victor on 3/13/18.
 */

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(TvShowsListViewModel::class)
    internal abstract fun bindTvShowViewModel(tvShowViewModel: TvShowsListViewModel) : ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TvShowDetailsViewModel::class)
    internal abstract fun bindTvShowDetailsViewModel(tvShowDetailsViewModel: TvShowDetailsViewModel) : ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelProviderFactory) : ViewModelProvider.Factory

}