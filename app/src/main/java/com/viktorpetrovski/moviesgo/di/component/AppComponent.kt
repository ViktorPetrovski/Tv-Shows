package com.viktorpetrovski.moviesgo.di.component

import android.app.Application
import com.viktorpetrovski.moviesgo.MoviesApp
import com.viktorpetrovski.moviesgo.di.module.AppModule
import com.viktorpetrovski.moviesgo.di.module.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by Victor on 3/13/18.
 */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, MainActivityModule::class))
interface AppComponent : AndroidInjector<MoviesApp>{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: MoviesApp)
}