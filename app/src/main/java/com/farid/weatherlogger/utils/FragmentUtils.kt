package com.farid.weatherlogger.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object FragmentUtils {

    fun replaceFragment(
        activity: AppCompatActivity,
        container: Int,
        fragment: Fragment,
        addToBackStack: Boolean
    ) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(container, fragment)
        if (addToBackStack)
            transaction.addToBackStack(null)
        transaction.commit()
    }

    fun addFragment(
        activity: AppCompatActivity,
        container: Int,
        fragment: Fragment,
        addToBackStack: Boolean
    ) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(container, fragment)
        if (addToBackStack)
            transaction.addToBackStack(null)
        transaction.commit()
    }
}