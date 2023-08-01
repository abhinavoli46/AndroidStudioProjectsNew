package com.example.quotes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import java.nio.charset.Charset

class MainViewModel(val context : Context) : ViewModel(){

    private var QuoteList : Array<Quote> = emptyArray()
    private var index = 0

    init{
        QuoteList = loadQuotesFromAssets()
    }

    private fun loadQuotesFromAssets(): Array<Quote> {
         val inputStream = context.assets.open("quotes.json")
        val size : Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json,Array<Quote>::class.java)
    }
    fun getQuote():Quote
    {
        return QuoteList[index]
    }
    fun nextQuote():Quote{
        return QuoteList[(++index) % QuoteList.size]
    }
    fun prevQuote():Quote{
        if(index > 0)
            return QuoteList[(--index)%QuoteList.size]
        else
            return QuoteList[index]
    }


}