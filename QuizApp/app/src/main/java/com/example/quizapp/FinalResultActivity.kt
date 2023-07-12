package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class FinalResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finalresult)
        val nameTextView : TextView = findViewById(R.id.playerNameTextView)
        val finalButton : Button = findViewById(R.id.lastActivityFinishButton)
        val finalScoreTextView:TextView = findViewById(R.id.finalScoreTextView)


        val username : String? = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswers : Int = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)
        val totalQuestions : Int = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)

        nameTextView.text = username
        finalScoreTextView.text = "" + "Your Score is $correctAnswers out of $totalQuestions"
        finalButton.setOnClickListener()
        {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}