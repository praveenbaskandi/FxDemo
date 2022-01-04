package com.praveen.funxdemo.appUtils

import android.content.Context
import android.content.Intent
import com.praveen.funxdemo.appUI.ValidScreen.ValidActivity
import com.praveen.funxdemo.appUI.validDetails.ValidDetailsActivity

object NavigationUIUtil {

    fun startValidatorActivity(packageContext: Context) {
        val intent = Intent(packageContext, ValidActivity::class.java)
        packageContext.startActivity(intent)
    }

    fun startValidatorDetailActivity(packageContext: Context, validId: String) {
        val intent = Intent(packageContext, ValidDetailsActivity::class.java)
        intent.putExtra(ValidDetailsActivity.VALIDATOR_DETAIL_ID, validId)
        packageContext.startActivity(intent)
    }
}