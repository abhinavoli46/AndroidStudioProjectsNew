package com.example.wineleven.Fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wineleven.R
import com.example.wineleven.databinding.FragmentSpinnerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Random
import java.util.Timer

class SpinnerFragment : Fragment() {
   private lateinit var binding: FragmentSpinnerBinding
    private lateinit var timer : CountDownTimer
    private var itemTitles = arrayOf("100", "Try Again", "500", "Try Again", "200", "Try Again")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSpinnerBinding.inflate(layoutInflater)
        //For Transaction Dialog appearing from Bottom when we click on coins above
        binding.coinClickable.setOnClickListener{
            val bottomSheetDialog : BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition

        }
        binding.coinClickableText.setOnClickListener{
            val bottomSheetDialog : BottomSheetDialogFragment = WithdrawalFragment()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "Test")
            bottomSheetDialog.enterTransition

        }
        return binding.root
    }

    private fun showResult(itemTitle : String)
    {
        Toast.makeText(requireContext(),itemTitle,Toast.LENGTH_SHORT).show()
        //Enables the button again
        binding.spinButton.isEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinButton.setOnClickListener{
            //Disable the button while spinning
            binding.spinButton.isEnabled = false

            val spin = Random().nextInt(6)
            val degrees = 60f * spin    //Calculate the rotation in degrees for the inner image according to random value generated in spin variable
            timer = object : CountDownTimer(5000,50) {
                var rotation = 0f
                //Implement two methods
                override fun onTick(millisecUntilFinised: Long) {
                    rotation += 5f
                    if(rotation >= degrees)
                    {
                        rotation = degrees
                        timer.cancel()
                        showResult(itemTitles[spin])
                    }
                    binding.prizesimage.rotation = rotation
                }

                override fun onFinish() {}
            }.start()
        }
    }

    companion object {

    }
}