package com.whiterabbit.employeedirectory.datasource.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Address(
        @ColumnInfo(name = "street")
        @SerializedName("street")
        @Expose
        var street: String? = null,

        @ColumnInfo(name = "suite")
        @SerializedName("suite")
        @Expose
        var suite: String? = null,

        @ColumnInfo(name = "city")
        @SerializedName("city")
        @Expose
        var city: String? = null,

        @ColumnInfo(name = "zipcode")
        @SerializedName("zipcode")
        @Expose
        var zipcode: String? = null,

        @Ignore
        @SerializedName("geo")
        @Expose
        var geo: Geo? = null
)