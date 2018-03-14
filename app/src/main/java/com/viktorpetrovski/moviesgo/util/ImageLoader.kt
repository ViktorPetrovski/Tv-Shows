package com.viktorpetrovski.moviesgo.util

import android.widget.ImageView
import com.viktorpetrovski.moviesgo.R

/**
 * Created by Victor on 3/14/18.
 */
object ImageLoader {

    val bannerImagePrefix = "https://image.tmdb.org/t/p/w780/"

    val posterImagePrefix = "https://image.tmdb.org/t/p/w780/"


    fun loadBannerImage(imageView: ImageView, url : String?){
        GlideApp.with(imageView.context)
                .asBitmap()
                .centerCrop()
                .placeholder(R.color.primary_dark_material_dark)
                .load("$bannerImagePrefix$url")
               // .error(R.drawable.ic_avatar_placeholder)
                .into(imageView)
    }


    fun loadPosterImage(imageView: ImageView, url : String?){
        GlideApp.with(imageView.context)
                .asBitmap()
                .centerInside()
                .placeholder(R.color.primary_dark_material_dark)
                .load("$posterImagePrefix$url")
                // .error(R.drawable.ic_avatar_placeholder)
                .into(imageView)
    }
}