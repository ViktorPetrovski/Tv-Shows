package com.viktorpetrovski.moviesgo.util.rx

import io.reactivex.schedulers.Schedulers

/**
 * Created by Victor on 3/16/18.
 */
class TvShowsSchedulerProviderTest  : SchedulerProvider{

    override fun ui() =Schedulers.trampoline()


    override fun computation() =Schedulers.trampoline()


    override fun io() = Schedulers.trampoline()

}