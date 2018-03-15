package com.viktorpetrovski.moviesgo.data.remote.apiModel

import com.google.gson.annotations.SerializedName
import com.viktorpetrovski.moviesgo.data.model.TvShow
import java.util.*

/**
 * Created by Victor on 3/13/18.
 */
data class TvShowListResponse(
        @SerializedName("page")
        var page : Int,
        @SerializedName("results")
        var showsList : ArrayList<TvShow>,
        @SerializedName("total_pages")
        var totalPages : Int,
        @SerializedName("total_results")
        var totalResults : Int
)