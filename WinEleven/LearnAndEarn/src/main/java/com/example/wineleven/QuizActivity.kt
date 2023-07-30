package com.example.wineleven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wineleven.Fragments.WithdrawalFragment
import com.example.wineleven.ModelClass.QuestionModel
import com.example.wineleven.databinding.ActivityQuizBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QuizActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }
    private lateinit var questionList : ArrayList<QuestionModel>
    private var currentQuestion : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var image = intent.getIntExtra("categoryImage",0)
        var catText = intent.getStringExtra("questionType")
        questionList = ArrayList<QuestionModel>()
        //Using FireStore by its instance as Firebase.firestore
        //Fetching questions according to category
        Firebase.firestore.collection("Questions").document(catText.toString()).collection("QuestionMaths").get().addOnSuccessListener {
            questionData ->
                questionList.clear()
                for(data in questionData.documents) {

                    var question: QuestionModel? = data.toObject(QuestionModel::class.java)
                    questionList.add(question!!)
                }
            if(questionList.size > 0)
            {
                binding.questionTextView.text = questionList[currentQuestion].question
                binding.optionOneButton.text = questionList[currentQuestion].option1
                binding.optionTwoButton.text = questionList[currentQuestion].option2
                binding.optionThreeButton.text = questionList[currentQuestion].option3
                binding.optionFourButton.text = questionList[currentQuestion].option4
            }
        }

        //For Transaction Dialog appearing from Bottom when we click on coins above
        binding.coinClickable.setOnClickListener{
            val bottomSheetDialog : BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(this@QuizActivity.supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition

        }
        binding.coinClickableText.setOnClickListener{
            val bottomSheetDialog : BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(this@QuizActivity.supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition

        }
        binding.optionOneButton.setOnClickListener {
            nextQuestionAndUpdateScore()
        }
        binding.optionTwoButton.setOnClickListener {
            nextQuestionAndUpdateScore()
        }
        binding.optionThreeButton.setOnClickListener {
            nextQuestionAndUpdateScore()
        }
        binding.optionFourButton.setOnClickListener {
            nextQuestionAndUpdateScore()
        }
    }

    private fun nextQuestionAndUpdateScore() {
        currentQuestion++
        if(currentQuestion < questionList.size)
        {
            binding.questionTextView.text = questionList[currentQuestion].question
            binding.optionOneButton.text = questionList[currentQuestion].option1
            binding.optionTwoButton.text = questionList[currentQuestion].option2
            binding.optionThreeButton.text = questionList[currentQuestion].option3
            binding.optionFourButton.text = questionList[currentQuestion].option4

        }
        else
        {
//            TODO(When questions get over show result activity)
        }

    }
}