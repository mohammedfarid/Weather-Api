package com.farid.weatherlogger.model.response

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class WeatherItem(

    @field:SerializedName("icon")
    var icon: String? = null,

    @field:SerializedName("description")
    var description: String? = null,

    @field:SerializedName("main")
    var main: String? = null,

    @field:SerializedName("id")
    var id: String? = null
)