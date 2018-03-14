package com.viktorpetrovski.moviesgo.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.ui.base.NavigationController
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject



class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Fragment>

    @Inject lateinit var navigatonController : NavigationController

    override fun supportFragmentInjector() = androidInjector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movies_list)

        if(savedInstanceState == null)
            navigatonController.navigateToTvShowList()

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
