package com.farid.weatherlogger.model.response


import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName


data class Clouds(

    @ColumnInfo(name = "all")
    @field:SerializedName("all")
    var all: String? = null
)