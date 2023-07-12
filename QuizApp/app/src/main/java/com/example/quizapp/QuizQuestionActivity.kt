package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat


class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {
    //Created to send info to final activity using intent
    private var userName : String? = null
    private var correctAnswers : Int = 0

    //Contains the list of Questions
    private var questionList: ArrayList<Question>? = null


    private var currentPosition = 1
    private var selectedOption: Int = 0
    private var progressBar: ProgressBar? = null
    private var scoreCountTextView: TextView? = null
    private var questionTextView: TextView? = null
    private var flagImageView: ImageView? = null

    private var optionOneTextView: TextView? = null
    private var optionTwoTextView: TextView? = null
    private var optionThreeTextView: TextView? = null
    private var optionFourTextView: TextView? = null
    private var submitButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        userName = intent.getStringExtra(Constants.USER_NAME)
        progressBar = findViewById(R.id.progressBar)
        scoreCountTextView = findViewById(R.id.scoreCountTextView)
        questionTextView = findViewById(R.id.questionTextView)
        flagImageView = findViewById(R.id.flagImageView)
        optionOneTextView = findViewById(R.id.optionOneTextView)
        optionTwoTextView = findViewById(R.id.optionTwoTextView)
        optionThreeTextView = findViewById(R.id.optionThreeTextView)
        optionFourTextView = findViewById(R.id.optionFourTextView)
        questionList = Constants.getQuestions()
        submitButton = findViewById(R.id.submitButton)

        optionOneTextView?.setOnClickListener(this)
        optionTwoTextView?.setOnClickListener(this)
        optionThreeTextView?.setOnClickListener(this)
        optionFourTextView?.setOnClickListener(this)
        submitButton?.setOnClickListener(this)
        questionList = Constants.getQuestions()

        setQuestion()


    }

    private fun setQuestion()
    {
        //First set the view to be default where no options are selected every time this method is called
        defaultOptionsView()

        //Start setting the question
        val question: Question = questionList!![currentPosition - 1]
        progressBar?.progress = currentPosition
        scoreCountTextView?.text = "${currentPosition}/${progressBar?.max}"
        questionTextView?.text = question.question
        optionOneTextView?.text = question.optionOne
        optionTwoTextView?.text = question.optionTwo
        optionThreeTextView?.text = question.optionThree
        optionFourTextView?.text = question.optionFour
        flagImageView?.setImageResource(question.image)

        if (currentPosition == questionList!!.size) {
            submitButton?.text = "FINISH"
        } else {
            submitButton?.text = "SUBMIT" + ""
        }

    }

    //Make an array of 4 textViews of options and set the properties for each textView using for loop
    private fun defaultOptionsView() {
        val options: ArrayList<TextView> = ArrayList<TextView>()
        optionOneTextView?.let {
            options.add(0, it)
        }
        optionTwoTextView?.let {
            options.add(1, it)
        }
        optionThreeTextView?.let {
            options.add(2, it)
        }
        optionFourTextView?.let {
            options.add(3, it)
        }
        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this, R.drawable.default_option_border_bg
            )
        }
    }


    private fun selectedOptionsView(selectedView: TextView, optionSelected: Int) {
        defaultOptionsView()
        selectedOption = optionSelected
        selectedView.setTextColor(Color.parseColor("#363A43"))
        selectedView.setTypeface(selectedView.typeface, Typeface.BOLD)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.optionOneTextView -> {
                optionOneTextView?.let {

                    selectedOptionsView(it, 1)
                }
            }

            R.id.optionTwoTextView -> {
                optionTwoTextView?.let {

                    selectedOptionsView(it, 2)
                }
            }

            R.id.optionThreeTextView -> {
                optionThreeTextView?.let {

                    selectedOptionsView(it, 3)
                }
            }

            R.id.optionFourTextView -> {
                optionFourTextView?.let {

                    selectedOptionsView(it, 4)
                }
            }

            R.id.submitButton -> {
                //If no option is selected and we click on submit button only increase the position
                //until it satisfies the condition
                if(selectedOption == 0)
                {
                    currentPosition++
                    //Increase the position and set the next question
                    when
                    {
                        currentPosition <= questionList!!.size ->
                        {
                            setQuestion()
                        }
                        else -> {
                                val intent : Intent = Intent(this,FinalResultActivity::class.java)
                                intent.putExtra(Constants.USER_NAME,userName)
                                intent.putExtra(Constants.CORRECT_ANSWERS,correctAnswers)
                                intent.putExtra(Constants.TOTAL_QUESTIONS,questionList?.size)
                                startActivity(intent)
                                finish()

                        }
                    }

                }

                else
                {
                    val question = questionList?.get(currentPosition-1)
                    if(question!!.correctAns != selectedOption)
                    {
                        answerView(selectedOption,R.drawable.wrong_option_border_bg)
                    }
                    else
                    {
                        correctAnswers++;
                    }
                    answerView(question.correctAns,R.drawable.correct_option_border_bg)

                    if(currentPosition == questionList!!.size)
                    {
                        submitButton?.text = " FINISH "
                    }
                    else
                    {
                        submitButton?.text = " GO TO NEXT QUESTION"
                    }
                    selectedOption = 0
                }

            }
        }


    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 ->
            {
                optionOneTextView?.background = ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2 ->
            {
                optionTwoTextView?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivity,drawableView
                )
            }
            3 ->
            {
                optionThreeTextView?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivity,drawableView
                )
            }
            4->
            {
                optionFourTextView?.background = ContextCompat.getDrawable(
                    this@QuizQuestionActivity,drawableView
                )
            }
        }
    }
}
