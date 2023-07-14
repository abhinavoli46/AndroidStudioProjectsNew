package com.example.viewbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.BindingBuildInfo
import androidx.databinding.DataBindingUtil
import com.example.viewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var  binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
//
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.apply {
            button1.text = "Hello"
            button2.text = "There"
            textView1.text = "Mufo"
            textView1.textSize = 32.0f
        }
    }
}