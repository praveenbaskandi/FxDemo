package com.praveen.funxdemo.appUI.validDetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.exception.ApolloException
import com.praveen.funxdemo.R
import com.praveen.funxdemo.appUtils.ConstantKey
import com.praveen.funxdemo.applicationUtil.BaseActivity
import com.praveen.funxdemo.databinding.ActivityDetailsBinding
import com.praveen.funxdemo.funx.BlockPageQuery
import com.praveen.funxdemo.graphUtil.apolloClient

class ValidDetailsActivity : BaseActivity(), View.OnClickListener {
    private var mActivityDetailsBinding: ActivityDetailsBinding? = null
    private var mValidDetailsViewModel: ValidDetailsViewModel? = null
    private val mContext: Context = this@ValidDetailsActivity

    companion object {
        var VALIDATOR_DETAIL_ID = "VALIDATOR_DETAIL_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        mValidDetailsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ValidDetailsViewModel::class.java]
        mActivityDetailsBinding?.lifecycleOwner = this
        mActivityDetailsBinding?.viewModel = mValidDetailsViewModel
        val addId: String = intent.getStringExtra(VALIDATOR_DETAIL_ID) as String
        mValidDetailsViewModel?.blockId?.value = addId
        mActivityDetailsBinding!!.rvBlock.layoutManager = LinearLayoutManager(mContext)
        mActivityDetailsBinding!!.ibBack.setOnClickListener(this)
        callSampleApi()
    }

    fun callSampleApi() {
        showProgressDialog("")
        mValidDetailsViewModel?.getDataListApi()

        lifecycleScope.launchWhenResumed {
            try {
                val response = apolloClient().query(
                    BlockPageQuery(
                        ConstantKey.CHAIN_ID,
                        mValidDetailsViewModel?.blockId?.value as String
                    )
                ).execute()
                hideProgressDialog()
                val dataList = response.data?.blockPage?.blocks as List<BlockPageQuery.Block>
                val mHorizontalAdapter = BlockAdapter(dataList)
                mActivityDetailsBinding!!.rvBlock.adapter = mHorizontalAdapter

            } catch (e: ApolloException) {
                hideProgressDialog()
                e.printStackTrace()
                Log.e("Error", e.toString())
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ibBack -> finish()
        }
    }
}