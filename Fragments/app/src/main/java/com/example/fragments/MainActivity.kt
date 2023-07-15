package com.example.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fragments.Fragments.BlankFragment1
import com.example.fragments.Fragments.BlankFragment2
import com.example.fragments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener{
            var manager = supportFragmentManager
            var transition = manager.beginTransaction()
            transition.replace(R.id.fragmentHolder,BlankFragment1())
            transition.addToBackStack(null)
            transition.commit()
        }
        binding.button2.setOnClickListener{
            var manager = supportFragmentManager
            var transition = manager.beginTransaction()
            transition.replace(R.id.fragmentHolder, BlankFragment2())
            transition.addToBackStack(null)
            transition.commit()
        }


    }
}