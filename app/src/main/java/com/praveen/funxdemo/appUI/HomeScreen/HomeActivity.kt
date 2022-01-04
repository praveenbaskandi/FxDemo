package com.praveen.funxdemo.appUI.HomeScreen

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.praveen.funxdemo.PeriodicAlarmClass
import com.praveen.funxdemo.R
import com.praveen.funxdemo.appUtils.ConstantKey
import com.praveen.funxdemo.appUtils.NavigationUIUtil
import com.praveen.funxdemo.appUtils.StringUtils
import com.praveen.funxdemo.applicationUtil.BaseActivity
import com.praveen.funxdemo.databinding.ActivityHomeBinding
import com.praveen.funxdemo.graphUtil.apolloClientUniswap
import com.praveen.funxdemo.uniswap.UniswapFunPriceQuery

class HomeActivity : BaseActivity(), View.OnClickListener {
    private var mActivityHomeBinding: ActivityHomeBinding? = null
    private val mContext: Context = this@HomeActivity
    private var mHomeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        mHomeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]
        mActivityHomeBinding?.lifecycleOwner = this
        mActivityHomeBinding?.cardValidList?.setOnClickListener(this)

        lifecycleScope.launchWhenResumed {
            try {
                val response = apolloClientUniswap().query(
                    UniswapFunPriceQuery(
                        ConstantKey.UNISWAP_FX_ID,
                        StringUtils.getDateEPOS(),
                    )
                ).execute()
                val dataList =
                    response.data?.tokenHourDatas as List<UniswapFunPriceQuery.TokenHourData>
                mActivityHomeBinding?.tvFxPrice?.text =
                    StringUtils.calculateAmount(dataList[0].close.toString())

            } catch (e: ApolloException) {
                e.printStackTrace()
                Log.e("Error", e.toString())
            }
        }
        setAlarmForRepeatFetch(StringUtils.convertTimeStampOfParticular())
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.cardValidList -> NavigationUIUtil.startValidatorActivity(mContext)
        }
    }


    private fun setAlarmForRepeatFetch(time: Long) {
        //getting the alarm manager
        val am = getSystemService(ALARM_SERVICE) as AlarmManager
        //creating a new intent specifying the broadcast receiver
        val i = Intent(this, PeriodicAlarmClass::class.java)
        //creating a pending intent using the intent
        val pi = PendingIntent.getBroadcast(mContext, 0, i, 0)
        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi)
    }
}