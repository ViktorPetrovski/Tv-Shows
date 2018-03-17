package com.viktorpetrovski.moviesgo.ui.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Victor on 3/14/18.
 */
abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    val clickSubject = PublishSubject.create<T>()

    val clickEvent : Observable<T> = clickSubject

    var mList = ArrayList<T>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    fun clearItems(){
        mList.clear()
        notifyDataSetChanged()
    }

    fun getView(parent: ViewGroup, viewType: Int) : View = LayoutInflater.from(parent.context).inflate(getLayoutItem(parent,viewType),parent,false)

    abstract fun getLayoutItem(parent: ViewGroup, viewType: Int) : Int

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) = holder.bind(mList.get(position))
}