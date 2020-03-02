package com.farid.weatherlogger.bases

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import java.util.regex.Pattern

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    companion object {
        private var dialog: ACProgressFlower? = null
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    fun showLoadingDialog() {
        try {
            dialog?.cancel()
            dialog = ACProgressFlower.Builder(this@BaseActivity)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build()
            dialog?.setCanceledOnTouchOutside(true)
            dialog?.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideLoadingDialog() {
        dialog?.cancel()
    }

}