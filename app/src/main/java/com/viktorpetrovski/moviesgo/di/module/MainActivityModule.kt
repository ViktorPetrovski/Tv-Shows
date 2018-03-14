package com.viktorpetrovski.moviesgo.di.module

import com.viktorpetrovski.moviesgo.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Victor on 3/13/18.
 */
@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    internal abstract fun contributeMainActivity(): MainActivity

}
