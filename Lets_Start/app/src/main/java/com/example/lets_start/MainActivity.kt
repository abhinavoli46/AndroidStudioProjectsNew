package com.example.lets_start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Fetching the views from the layout file
        val input : EditText = findViewById(R.id.kilo_edt)
        val output : TextView = findViewById(R.id.ans_txtv)
        val pressBtn : Button = findViewById(R.id.ans_btn)

        //Set on click listener on button
        pressBtn.setOnClickListener()
        {
            val inputInKilos:Double = input.text.toString().toDouble()
            output.setText("" + conversion(inputInKilos) + "\nPounds")
        }
    }

    fun conversion(kilos: Double): Double {
        val pounds : Double = 2.20462 * kilos
        val roundoff : Double = (pounds * 100.0).roundToInt() / 100.0
        return roundoff
    }
}