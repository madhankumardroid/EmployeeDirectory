package com.whiterabbit.employeedirectory.datasource.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Company(
        @ColumnInfo(name = "company_name")
        @SerializedName("name")
        @Expose
        var name: String? = null,

        @ColumnInfo(name = "catchPhrase")
        @SerializedName("catchPhrase")
        @Expose
        var catchPhrase: String? = null,

        @ColumnInfo(name = "bs")
        @SerializedName("bs")
        @Expose
        var bs: String? = null
)