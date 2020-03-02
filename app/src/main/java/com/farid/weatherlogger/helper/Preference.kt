package com.farid.weatherlogger.helper

import android.content.Context

object Preference {

    val TAG = Preference::class.java.simpleName

    const val PrefName = "com.farid.weatherlogger.key_prefs_weatherlogger"

    fun clearPreference(context: Context) {
        val sharedPref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }

    fun saveString(context: Context, key: String, value: String) {
        val sharedPref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(context: Context, pRef: String, defaultValue: String?): String? {
        val sharedPref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)
        return sharedPref.getString(pRef, defaultValue)
    }

    fun saveInt(context: Context, key: String, value: Int) {
        val sharedPref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt(key, value)
            apply()
        }
    }

    fun getInt(context: Context, pRef: String, defaultValue: Int): Int? {
        val sharedPref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)
        return sharedPref.getInt(pRef, defaultValue)
    }

    fun saveBoolean(context: Context, key: String, value: Boolean){
        val sharedPref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(key, value)
            apply()
        }
    }
    fun isBoolean(context: Context, pRef: String, defaultValue: Boolean):Boolean?{
        val sharedPref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(pRef, defaultValue)
    }
}
