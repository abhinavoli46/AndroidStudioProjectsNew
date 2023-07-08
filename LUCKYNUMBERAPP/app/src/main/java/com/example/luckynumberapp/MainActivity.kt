package com.example.luckynumberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Views declared in the main Activity
        var b1 : Button = findViewById(R.id.button)
        var txt : TextView = findViewById(R.id.textView)
        var edtTxt : EditText = findViewById(R.id.editText)

        b1.setOnClickListener()
        {
            val getName : String? = edtTxt.text.toString()

            var intent1 : Intent = Intent(this,LuckyNumberActivity::class.java)

            intent1.putExtra("name", getName)
            startActivity(intent1)
        }

    }
}