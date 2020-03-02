package com.farid.weatherlogger.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateAndTimeUtils {
    var TIME_FORMATE = "dd/MM/yyyy hh:mm:ss aa"
    fun getDate(
        milliSeconds: Long,
        dateFormat: String
    ): String? { // Create a DateFormatter object for displaying date in specified format.
        var formatter: SimpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        var result: String = ""
        try {
            var date = Date(milliSeconds * 1000L)
            result = formatter.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return result
    }
}