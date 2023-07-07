package com.example.explicitintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

@Suppress("DEPRECATION")
class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        var bundle : Bundle? = intent.extras
        val age = bundle?.getInt("Abhinav")

        Toast.makeText(this, "Your age is $age",Toast.LENGTH_LONG).show()
    }
}