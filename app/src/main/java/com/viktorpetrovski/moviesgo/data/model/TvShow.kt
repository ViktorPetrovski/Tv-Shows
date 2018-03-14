package com.viktorpetrovski.moviesgo.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Victor on 3/13/18.
 */
data class TvShow(
        @SerializedName("id")
        var id: Long,
        @SerializedName("overview")
        var overview: String,
        @SerializedName("vote_average")
        var vote: Double,
        @SerializedName("name")
        var name: String,
        @SerializedName("poster_path")
        var posterPath: String,
        @SerializedName("backdrop_path")
        var backDropPath: String
        )