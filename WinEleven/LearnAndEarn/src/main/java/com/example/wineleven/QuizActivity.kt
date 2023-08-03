package com.example.wineleven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.wineleven.Fragments.WithdrawalFragment
import com.example.wineleven.ModelClass.QuestionModel
import com.example.wineleven.databinding.ActivityQuizBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QuizActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }
    private lateinit var questionList : ArrayList<QuestionModel>
    private var currentQuestion : Int = 0
    var score = 0
    var currentChance = 0L
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Firebase.database.reference.child("PlayChance").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        currentChance = snapshot.value as Long
                    }
                    else{
                        Firebase.database.reference.child("PlayChance").child(Firebase.auth.currentUser!!.uid).setValue(currentChance)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        var image = intent.getIntExtra("categoryImage",0)
        var catText = intent.getStringExtra("questionType")
        questionList = ArrayList<QuestionModel>()
        //Using FireStore by its instance as Firebase.firestore
        //Fetching questions according to category
        Firebase.firestore.collection("Questions").document(catText.toString()).collection("questionlist").get().addOnSuccessListener {
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
            nextQuestionAndUpdateScore(binding.optionOneButton.text.toString())
        }
        binding.optionTwoButton.setOnClickListener {
            nextQuestionAndUpdateScore(binding.optionTwoButton.text.toString())
        }
        binding.optionThreeButton.setOnClickListener {
            nextQuestionAndUpdateScore(binding.optionThreeButton.text.toString())
        }
        binding.optionFourButton.setOnClickListener {
            nextQuestionAndUpdateScore(binding.optionFourButton.text.toString())
        }
    }

    private fun nextQuestionAndUpdateScore(s:String) {
        if(s == questionList[currentQuestion].answer){
            score += 20
        }
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
            if(score >= 60) {
                binding.winnerLayout.visibility = View.VISIBLE
                currentChance++
                Firebase.database.reference.child("PlayChance").child(Firebase.auth.currentUser!!.uid).setValue(currentChance)
                startActivity(Intent(this@QuizActivity,MainActivity::class.java))
                finish()
            }
            else
                binding.loserLayout.visibility = View.VISIBLE
        }

    }
}