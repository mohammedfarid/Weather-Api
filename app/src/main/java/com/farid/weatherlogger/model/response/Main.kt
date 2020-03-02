package com.farid.weatherlogger.model.response

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class Main(
    @ColumnInfo(name = "temp")
    @field:SerializedName("temp")
    var temp: String? = null,

    @ColumnInfo(name = "temp_min")
    @field:SerializedName("temp_min")
    var tempMin: String? = null,

    @ColumnInfo(name = "humidity")
    @field:SerializedName("humidity")
    var humidity: String? = null,

    @ColumnInfo(name = "pressure")
    @field:SerializedName("pressure")
    var pressure: String? = null,

    @ColumnInfo(name = "feels_like")
    @field:SerializedName("feels_like")
    var feelsLike: String? = null,

    @ColumnInfo(name = "temp_max")
    @field:SerializedName("temp_max")
    var tempMax: String? = null
)