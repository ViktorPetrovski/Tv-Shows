package com.viktorpetrovski.moviesgo

import android.app.Activity
import android.app.Application
import com.viktorpetrovski.moviesgo.di.applyAutoInjector
import com.viktorpetrovski.moviesgo.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject

/**
 * Created by Victor on 3/13/18.
 */
class MoviesApp : Application(), HasActivityInjector {

    @Inject lateinit var mCalligraphyConfig: CalligraphyConfig

    @Inject lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()

        applyAutoInjector()

        //Init Dagger
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)


        CalligraphyConfig.initDefault(mCalligraphyConfig)

    }


    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector
}