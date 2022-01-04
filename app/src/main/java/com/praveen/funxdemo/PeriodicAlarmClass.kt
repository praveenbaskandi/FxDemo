package com.praveen.funxdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.apollographql.apollo3.exception.ApolloException
import com.praveen.funxdemo.appUtils.ConstantKey
import com.praveen.funxdemo.appUtils.Notification.notification
import com.praveen.funxdemo.appUtils.StringUtils
import com.praveen.funxdemo.graphUtil.apolloClientUniswap
import com.praveen.funxdemo.uniswap.UniswapFunPriceQuery
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PeriodicAlarmClass : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        GlobalScope.launch {
            try {
                val response = apolloClientUniswap().query(
                    UniswapFunPriceQuery(
                        ConstantKey.UNISWAP_FX_ID,
                        StringUtils.getDateEPOS(),
                    )
                ).execute()
                val dataList =
                    response.data?.tokenHourDatas as List<UniswapFunPriceQuery.TokenHourData>
                notification(context!!, StringUtils.calculateAmount(dataList[0].close.toString()))


            } catch (e: ApolloException) {
                e.printStackTrace()
                Log.e("Error", e.toString())
            }
        }
    }
}