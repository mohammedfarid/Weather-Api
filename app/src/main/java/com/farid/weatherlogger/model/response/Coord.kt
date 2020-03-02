package com.farid.weatherlogger.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Coord(

    @ColumnInfo(name = "lon")
    @field:SerializedName("lon")
    var lon: String? = null,

    @ColumnInfo(name = "lat")
    @field:SerializedName("lat")
    var lat: String? = null
)