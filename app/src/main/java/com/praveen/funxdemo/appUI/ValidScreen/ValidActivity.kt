package com.praveen.funxdemo.appUI.ValidScreen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.exception.ApolloException
import com.praveen.funxdemo.R
import com.praveen.funxdemo.appUtils.ConstantKey
import com.praveen.funxdemo.appUtils.NavigationUIUtil
import com.praveen.funxdemo.applicationUtil.BaseActivity
import com.praveen.funxdemo.databinding.ActivityValidBinding
import com.praveen.funxdemo.funx.ValidatorListQuery
import com.praveen.funxdemo.graphUtil.apolloClient

class ValidActivity : BaseActivity(), InterfaceValidId, View.OnClickListener {
    private var mActivityValidBinding: ActivityValidBinding? = null
    private var mInterfaceValidId: InterfaceValidId = this
    private val mContext: Context = this@ValidActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityValidBinding = DataBindingUtil.setContentView(this, R.layout.activity_valid)
        mActivityValidBinding!!.ibBack.setOnClickListener(this)
        mActivityValidBinding!!.rvValidator.layoutManager = LinearLayoutManager(mContext)
        callValidListApi()
    }

    fun callValidListApi() {
        showProgressDialog("")
        lifecycleScope.launchWhenResumed {
            try {
                hideProgressDialog()
                val response =
                    apolloClient().query(ValidatorListQuery(ConstantKey.CHAIN_ID, ConstantKey.TYPE))
                        .execute()
                val dataList =
                    response.data?.validatorPage?.validators as List<ValidatorListQuery.Validator>
                val mHorizontalAdapter = ValidAdapter(dataList, mInterfaceValidId)
                mActivityValidBinding!!.rvValidator.adapter = mHorizontalAdapter

            } catch (e: ApolloException) {
                Log.e("Error", e.toString())
                hideProgressDialog()
                e.printStackTrace()
            }
        }
    }

    override fun onValidatorClick(validId: String) {
        NavigationUIUtil.startValidatorDetailActivity(mContext, validId)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ibBack -> finish()
        }
    }
}