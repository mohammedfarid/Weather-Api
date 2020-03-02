package com.farid.weatherlogger.model.response

import androidx.room.*
import com.google.gson.annotations.SerializedName

@DatabaseView
@Entity(tableName = "weather_table")
data class WeatherResponse(
    @PrimaryKey(autoGenerate = true)
    var idKey: Int = 0,

    @ColumnInfo(name = "dt")
    @field:SerializedName("dt")
    var dt: String? = null,

    @Embedded
    @field:SerializedName("coord")
    var coord: Coord? = null,

    @field:SerializedName("timezone")
    var timezone: String? = null,

    @Ignore
    @field:SerializedName("weather")
    var weather: List<WeatherItem?>? = null,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("cod")
    var cod: Int? = null,

    @Embedded
    @field:SerializedName("main")
    var main: Main? = null,

    @Embedded
    @field:SerializedName("clouds")
    var clouds: Clouds? = null,

    @ColumnInfo(name = "id_")
    @field:SerializedName("id")
    var id: Int? = null,

    @Embedded
    @field:SerializedName("sys")
    var sys: Sys? = null,

    @field:SerializedName("base")
    var base: String? = null,

    @Embedded
    @field:SerializedName("wind")
    var wind: Wind? = null
)