package com.example.quotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.quotes.databinding.ActivityMainBinding
import kotlin.text.Typography.quote

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this,MainViewModelFactory(applicationContext)).get(MainViewModel::class.java)
        setQuote(mainViewModel.getQuote())
        binding.sharebtn.setOnClickListener{
            onShare()
        }
        binding.prevBtn.setOnClickListener{
            onPrevious()
        }
        binding.nextBtn.setOnClickListener{
            onNext()
        }
    }

    private fun setQuote(quote: Quote) {
        binding.quoteText.text = quote.text
        binding.authorText.text = quote.author
    }
    private fun onPrevious()
    {
        setQuote(mainViewModel.prevQuote())
    }

    private fun onNext()
    {
        setQuote(mainViewModel.nextQuote())
    }
    private fun onShare(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT,mainViewModel.getQuote().text)
        startActivity(intent)
    }
}