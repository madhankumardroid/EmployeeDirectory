package com.whiterabbit.employeedirectory.datasource.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Employee(
        @PrimaryKey
        @SerializedName("id")
        @Expose
        @ColumnInfo(name = "id")
        var id: Int? = null,

        @SerializedName("name")
        @Expose
        @ColumnInfo(name = "name")
        var employeeName: String? = null,

        @SerializedName("username")
        @Expose
        @ColumnInfo(name = "username")
        var username: String? = null,

        @SerializedName("email")
        @Expose
        @ColumnInfo(name = "email")
        var email: String? = null,

        @SerializedName("profile_image")
        @Expose
        @ColumnInfo(name = "profile_image")
        var profileImage: String? = null,

        @Embedded
        @SerializedName("address")
        @Expose
        var address: Address? = null,

        @SerializedName("phone")
        @Expose
        @ColumnInfo(name = "phone")
        var phone: String? = null,

        @SerializedName("website")
        @Expose
        @ColumnInfo(name = "website")
        var website: String? = null,

        @Embedded
        @SerializedName("company")
        @Expose
        var company: Company? = null
)