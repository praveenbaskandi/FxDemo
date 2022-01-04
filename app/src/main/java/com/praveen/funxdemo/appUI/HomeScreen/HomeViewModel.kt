package com.praveen.funxdemo.appUI.HomeScreen

import androidx.lifecycle.MutableLiveData
import com.praveen.funxdemo.applicationUtil.BaseViewModel

class HomeViewModel :BaseViewModel(){
    val fxPrice = MutableLiveData<String>()

    init {
        fxPrice.value= ""
    }

}