package com.example.databinding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    val quoteLiveData = MutableLiveData("Hello")
    fun updateQuote(){
        quoteLiveData.value = "Hi"
    }
}