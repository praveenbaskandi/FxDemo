package com.praveen.funxdemo.appUtils

import android.os.Build
import java.util.*

object StringUtils {
    fun calculateAmount(amount: String): String {
        val number:Double =amount.toDouble()
        val roundOff = String.format("%.2f", number)
        return "$${roundOff}"
    }


    fun getDateEPOS(): Int {
        val date = (Calendar.getInstance().timeInMillis  -  (24 * 60 * 60 * 1000))/1000
        return date.toInt()
    }


    fun convertTimeStampOfParticular(): Long {
        val calendar = Calendar.getInstance()

        if (Build.VERSION.SDK_INT >= 23) {
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                12,
                0,
                0
            )
        } else {
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                12,
                0,
                0
            )
        }
        return calendar.timeInMillis
    }

}