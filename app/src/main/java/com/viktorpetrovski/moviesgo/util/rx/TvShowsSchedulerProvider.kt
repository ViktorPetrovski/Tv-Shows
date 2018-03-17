package com.viktorpetrovski.moviesgo.util.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Victor on 3/16/18.
 */
class TvShowsSchedulerProvider : SchedulerProvider{

    override fun ui() = AndroidSchedulers.mainThread()

    override fun computation() = Schedulers.computation()

    override fun io() = Schedulers.io()

}