package com.viktorpetrovski.moviesgo.util

import android.widget.ImageView
import com.viktorpetrovski.moviesgo.R
import com.viktorpetrovski.moviesgo.di.module.GlideApp

/**
 * Created by Victor on 3/14/18.
 */
object ImageLoader {

    /**
     * Prefix of the URL provided by TheMoviesDB
     * */
    private const val bannerImagePrefix = "https://image.tmdb.org/t/p/w780/"

    private const val posterImagePrefix = "https://image.tmdb.org/t/p/w780/"

    /**
     * Load TV Show banner
     *
     * @param imageView View where the banner image will be loaded
     * @param url The url of image.
     */
    fun loadBannerImage(imageView: ImageView, url : String?){
        GlideApp.with(imageView.context)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.ic_tv_place_holder)
                .load("$bannerImagePrefix$url")
                .into(imageView)
    }


    /**
     * Load the poster of the TV Show
     *
     * @param imageView View where the poster image will be loaded
     * @param url The url of image.
     */
    fun loadPosterImage(imageView: ImageView, url : String?){
        GlideApp.with(imageView.context)
                .asBitmap()
                .centerInside()
                .placeholder(R.drawable.ic_tv_place_holder)
                .load("$posterImagePrefix$url")
                .into(imageView)
    }
}