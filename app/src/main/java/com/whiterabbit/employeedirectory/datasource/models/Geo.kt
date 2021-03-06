package com.whiterabbit.employeedirectory.datasource.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Geo(
    @SerializedName("lat")
    @Expose
    val lat: String? = null,

    @SerializedName("lng")
    @Expose
    val lng: String? = null
)