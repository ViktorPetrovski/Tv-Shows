package com.viktorpetrovski.moviesgo.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Victor on 3/15/18.
 */
data class Genre(@SerializedName("id") var  id : Long,
                 @SerializedName("name") var genreName : String)