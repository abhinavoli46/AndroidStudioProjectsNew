package com.example.luckynumberapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

@Suppress("DEPRECATION")
class LuckyNumberActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lucky_number)

        //Views Declared in the LuckyNumberActivity
        val txt2 : TextView = findViewById(R.id.textView2)
        val txt3 : TextView = findViewById(R.id.textView3)
        val btn2 : Button = findViewById(R.id.button2)

        var userName : String = receiveUserName()
        var random = generateRandomNumber()
        txt3.setText(random.toString())

        btn2.setOnClickListener()
        {
            shareData(userName,random)
        }

    }

    private fun receiveUserName(): String
    {
        var bundle: Bundle? = intent.extras
        return bundle?.get("name").toString()
    }
    private fun generateRandomNumber():Int
    {
        val random = Random.nextInt(1000)
        return random
    }
    private fun shareData(userName : String , num : Int)
    {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_SUBJECT,"$userName is lucky Today")
        intent.putExtra(Intent.EXTRA_TEXT, "His lucky number is $num")
        startActivity(intent)
    }


}