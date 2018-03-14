package com.viktorpetrovski.moviesgo.di.builder

import com.viktorpetrovski.moviesgo.di.module.FragmentBuildersModule
import com.viktorpetrovski.moviesgo.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Victor on 3/13/18.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    abstract fun bindMainActivity(): MainActivity
}