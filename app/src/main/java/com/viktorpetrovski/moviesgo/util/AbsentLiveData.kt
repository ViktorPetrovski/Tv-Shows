package com.viktorpetrovski.moviesgo.util

import android.arch.lifecycle.LiveData

/**
 * Created by Victor on 3/13/18.
 */
class AbsentLiveData<T> private constructor() : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}
