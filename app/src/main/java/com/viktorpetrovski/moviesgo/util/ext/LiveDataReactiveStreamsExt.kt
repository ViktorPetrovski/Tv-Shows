package com.viktorpetrovski.moviesgo.util.ext

import android.arch.lifecycle.LiveDataReactiveStreams
import org.reactivestreams.Publisher

/**
 * Created by Victor on 3/13/18.
 */
fun <T> Publisher<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this)
