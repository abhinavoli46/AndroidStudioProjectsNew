package com.example.a7minuteworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.a7minuteworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding? = null
    private var keepSplashOnScreen = true
    private val delay = 2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val window = this.window

        setContentView(binding?.root)
        binding?.bmiButton?.setOnClickListener{
            startActivity(Intent(this,BmiActivity::class.java))
        }
        binding?.historyButton?.setOnClickListener{
            startActivity(Intent(this,HistoryActivity::class.java))
        }
        binding?.frameLayoutStartButton?.setOnClickListener{
            val intent : Intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}