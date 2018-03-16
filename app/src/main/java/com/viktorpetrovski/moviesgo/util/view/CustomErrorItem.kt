package com.viktorpetrovski.moviesgo.util.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.viktorpetrovski.moviesgo.R
import ru.alexbykov.nopaginate.callback.OnRepeatListener
import ru.alexbykov.nopaginate.item.ErrorItem

/**
 * Created by Victor on 3/15/18.
 *
 * Custom Error Item that is used by [PaginateBuilder]
 */
class CustomErrorItem : ErrorItem {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empty_view, parent, false)
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, repeatListener: OnRepeatListener?) {
        val btnRepeat = holder.itemView.findViewById(R.id.btn_retry) as View
        btnRepeat.setOnClickListener { repeatListener?.onClickRepeat() }
    }
}