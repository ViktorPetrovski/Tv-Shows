package com.viktorpetrovski.moviesgo.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Victor on 3/13/18.
 */
abstract class BaseViewHolder<T>(view : View) : RecyclerView.ViewHolder(view){

    abstract fun bind(item : T)

}