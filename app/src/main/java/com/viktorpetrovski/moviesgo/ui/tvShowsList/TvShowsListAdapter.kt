package com.viktorpetrovski.moviesgo.ui.tvShowsList

import android.view.View
import android.view.ViewGroup
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.data.model.TvShow
import com.viktorpetrovski.moviesgo.ui.base.BaseRecyclerViewAdapter
import com.viktorpetrovski.moviesgo.ui.base.BaseViewHolder
import com.viktorpetrovski.moviesgo.util.ImageLoader
import kotlinx.android.synthetic.main.tv_show_list_item.view.*
import javax.inject.Inject

/**
 * Created by Victor on 3/13/18.
 */
class TvShowsListAdapter @Inject constructor() : BaseRecyclerViewAdapter<TvShow>() {

    override fun getLayoutItem(parent: ViewGroup, viewType: Int): Int = R.layout.tv_show_list_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MoviesViewHolder(getView(parent,viewType))

    inner class MoviesViewHolder(itemView: View) : BaseViewHolder<TvShow>(itemView) {

        override fun bind(item: TvShow) {
            ImageLoader.loadBannerImage(itemView.iv_tv_show_banner,item.backDropPath)
            itemView.tv_title.text = item.name
            itemView.tv_rating.text = item.vote.toString()

            itemView.setOnClickListener {
                clickSubject.onNext(item)
            }
        }
    }
}