package com.viktorpetrovski.moviesgo.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.util.rx.SchedulerProvider
import com.viktorpetrovski.moviesgo.util.rx.TvShowsSchedulerProvider
import dagger.Module
import dagger.Provides
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Singleton


/**
 * Created by Victor on 3/13/18.
 */
@Module(includes = arrayOf(ViewModelModule::class, NetworkModule::class))
class AppModule{

    @Provides
    @Singleton
    internal fun provideContext(application: Application)  = application

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application):
            // Application reference must come from AppModule.class
            SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    internal fun provideCalligraphyDefaultConfig() = CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proxima_nova.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()

    @Provides
    @Singleton
    internal fun provideSchedulerProvider() : SchedulerProvider  = TvShowsSchedulerProvider()

}