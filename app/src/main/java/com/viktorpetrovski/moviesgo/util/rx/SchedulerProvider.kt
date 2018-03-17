package com.viktorpetrovski.moviesgo.util.rx

import io.reactivex.Scheduler

/**
 * Created by Victor on 3/16/18.
 */
interface SchedulerProvider {

    fun ui(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler

}
