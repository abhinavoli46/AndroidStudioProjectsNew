package com.example.wineleven

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wineleven.Fragments.WithdrawalFragment
import com.example.wineleven.databinding.ActivityQuizBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class QuizActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


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
    }
}