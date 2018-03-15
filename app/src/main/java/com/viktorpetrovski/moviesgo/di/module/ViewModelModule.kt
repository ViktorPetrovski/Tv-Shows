package com.viktorpetrovski.moviesgo.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.viktorpetrovski.moviesgo.ViewModelProviderFactory
import com.viktorpetrovski.moviesgo.ui.tvShowDetails.TvShowDetailsViewModel
import com.viktorpetrovski.moviesgo.ui.movieslist.TvShowViewModel
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
    @ViewModelKey(TvShowViewModel::class)
    internal abstract fun bindTvShowViewModel(tvShowViewModel: TvShowViewModel) : ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TvShowDetailsViewModel::class)
    internal abstract fun bindTvShowDetailsViewModel(tvShowDetailsViewModel: TvShowDetailsViewModel) : ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelProviderFactory) : ViewModelProvider.Factory

}