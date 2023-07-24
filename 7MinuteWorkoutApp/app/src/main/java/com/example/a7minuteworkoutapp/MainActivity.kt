package com.example.a7minuteworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.black)
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