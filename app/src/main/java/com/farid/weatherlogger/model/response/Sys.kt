package com.farid.weatherlogger.model.response

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class Sys(

    @ColumnInfo(name = "country")
    @field:SerializedName("country")
    var country: String? = null,

    @ColumnInfo(name = "sunrise")
    @field:SerializedName("sunrise")
    var sunrise: String? = null,

    @ColumnInfo(name = "sunset")
    @field:SerializedName("sunset")
    var sunset: String? = null,

    @ColumnInfo(name = "id_sys")
    @field:SerializedName("id")
    var id: String? = null,

    @ColumnInfo(name = "type")
    @field:SerializedName("type")
    var type: String? = null
)