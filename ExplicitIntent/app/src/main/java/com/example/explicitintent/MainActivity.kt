package com.example.explicitintent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn : Button = findViewById(R.id.button1)
        btn.setOnClickListener()
        {
//            Explicit Intent
            var newIntent : Intent = Intent(this,Activity2::class.java)
            startActivity(newIntent)

            newIntent.putExtra("Abhinav",23)
        }

    }
}