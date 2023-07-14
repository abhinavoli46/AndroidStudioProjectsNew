package com.example.boxapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.boxapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setListeners()
    }

    private fun setListeners()
    {
        var boxes : List<View> = listOf<View>(binding.box1,binding.box2,binding.box3,binding.box4,binding.box5)
        for (box in boxes)
        {
            box.setOnClickListener{
                makeColored(it)
            }
        }
    }
    private fun makeColored(view:View)
    {
        when(view.id)
        {
            R.id.box1 -> {
                view.setBackgroundColor(Color.BLUE)
            }
            R.id.box2 -> {
                view.setBackgroundColor(Color.BLUE)
            }R.id.box3 -> {
            view.setBackgroundColor(Color.BLUE)
        }R.id.box4 -> {
            view.setBackgroundColor(Color.BLUE)
        }R.id.box5 -> {
            view.setBackgroundColor(Color.BLUE)
        }

            else -> {
                binding.outer.setBackgroundColor(Color.RED)
            }
        }
    }

}