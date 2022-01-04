package com.praveen.funxdemo.appUI.validDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloException
import com.praveen.funxdemo.appUtils.ConstantKey
import com.praveen.funxdemo.applicationUtil.BaseViewModel
import com.praveen.funxdemo.funx.ValidatorDetailQuery
import com.praveen.funxdemo.graphUtil.apolloClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ValidDetailsViewModel : BaseViewModel() {
    val blockId = MutableLiveData<String>()
    val fName = MutableLiveData<String>()
    val fValidator = MutableLiveData<String>()
    val fDesc = MutableLiveData<String>()
    val fAddress = MutableLiveData<String>()
    val fCurrentAyp = MutableLiveData<String>()
    val fVotingPower = MutableLiveData<String>()
    val fRate = MutableLiveData<String>()
    val fWebsite = MutableLiveData<String>()

    init {
        blockId.value = ""
        fName.value = "f(x) Core"
        fValidator.value = ""
        fDesc.value = ""
        fAddress.value = ""
        fCurrentAyp.value = ""
        fVotingPower.value = ""
        fRate.value = ""
        fWebsite.value = ""
    }


    /**
     * calling graphql api to get validator data
     * */
    fun getDataListApi() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apolloClient().query(
                    ValidatorDetailQuery(
                        ConstantKey.CHAIN_ID,
                        blockId.value as String
                    )
                ).execute()
                val validData = response.data?.validator
                fValidator.postValue(validData?.validatorName)
                fDesc.postValue(validData?.description)
                fAddress.postValue(validData?.operateAddress)
                fWebsite.postValue(validData?.webSite)
                fCurrentAyp.postValue("${validData?.rewards}%")
                fVotingPower.postValue("${validData?.votingPowerPercent}%")
                fRate.postValue("${validData?.commission}%(Max ${validData?.commissionMaxRate}%)")
            } catch (e: ApolloException) {
                e.printStackTrace()
            }
        }
    }
}