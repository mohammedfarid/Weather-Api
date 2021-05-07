package com.farid.weatherlogger.bases

import android.graphics.Color
import androidx.fragment.app.Fragment
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower

open class BaseFragment : Fragment() {
    companion object {
        private var dialog: ACProgressFlower? = null
    }

    fun showLoadingDialog() {
        try {
            dialog?.cancel()
            dialog = ACProgressFlower.Builder(activity)
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