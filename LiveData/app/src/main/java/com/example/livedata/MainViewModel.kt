package com.example.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val factsLiveData = MutableLiveData<String>("A live data")

    fun changeLiveData() {
        factsLiveData.value = "Another Live Data"
    }
}