package com.farid.weatherlogger.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Wind(
    @ColumnInfo(name = "deg")
    @field:SerializedName("deg")
    var deg: String? = null,

    @ColumnInfo(name = "speed")
    @field:SerializedName("speed")
    var speed: String? = null
)