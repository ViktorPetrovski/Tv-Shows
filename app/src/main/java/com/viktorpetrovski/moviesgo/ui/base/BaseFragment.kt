package com.viktorpetrovski.moviesgo.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Victor on 3/13/18.
 */
abstract class BaseFragment : Fragment() {

    abstract fun getLayout() : Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(getLayout(), container, false)


}