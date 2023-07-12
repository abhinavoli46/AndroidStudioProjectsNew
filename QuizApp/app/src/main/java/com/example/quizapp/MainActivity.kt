package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn : Button = findViewById(R.id.startButton)
        val name : EditText  = findViewById(R.id.nameEditText)

        startBtn.setOnClickListener()
        {
            if(name.text.isEmpty())
            {
                Toast.makeText(this,"Please enter name before you start",Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent : Intent = Intent(this,QuizQuestionActivity::class.java)
                intent.putExtra(Constants.USER_NAME, name.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}