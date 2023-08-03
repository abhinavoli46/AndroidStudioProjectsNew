package com.example.wineleven.Fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wineleven.ModelClass.HistoryModelClass
import com.example.wineleven.R
import com.example.wineleven.User
import com.example.wineleven.databinding.FragmentSpinnerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Random
import java.util.Timer

class SpinnerFragment : Fragment() {
   private lateinit var binding: FragmentSpinnerBinding
    private lateinit var timer : CountDownTimer
    private var itemTitles = arrayOf("100", "Try Again", "500", "Try Again", "200", "Try Again")
    private var chancesLeft = 0L
    private var wonCoins = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSpinnerBinding.inflate(layoutInflater)

        //Getting data from database and setting name textview with the user name
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<User>()
                        binding.nameTextViewFragment.text = user?.name
                    }

                    override fun onCancelled(error: DatabaseError) {}

                })


        Firebase.database.reference.child("PlayChance").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        chancesLeft = snapshot.value as Long
                       binding.spinchances.text = (chancesLeft).toString()
                    }
                    else{
                        binding.spinchances.text = chancesLeft.toString()
                        binding.spinButton.isEnabled = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        Firebase.database.reference.child("WonCoins").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        wonCoins = snapshot.value as Long
                        binding.coinClickableText.text = (wonCoins).toString()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        return binding.root
    }

    private fun showResult(itemTitle : String,spinNumber:Int)
    {
        if(spinNumber%2 == 0)
        {
            var winning = itemTitle.toInt()
            wonCoins += winning
            Firebase.database.reference.child("WonCoins").child(Firebase.auth.currentUser!!.uid).setValue(wonCoins)
            var historyModel = HistoryModelClass(System.currentTimeMillis().toString(),winning.toString(),false)
            Firebase.database.reference.child("CoinHistory").child(Firebase.auth.currentUser!!.uid).push().setValue(historyModel)
        }
        Toast.makeText(requireContext(),itemTitle,Toast.LENGTH_SHORT).show()
        chancesLeft -= 1
        Firebase.database.reference.child("PlayChance").child(Firebase.auth.currentUser!!.uid).setValue(chancesLeft)
        //Enables the button again
        binding.spinButton.isEnabled = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        binding.spinButton.setOnClickListener{
            //Disable the button while spinning
            binding.spinButton.isEnabled = false
            if(chancesLeft > 0) {
                val spin = Random().nextInt(6)
                val degrees =
                    60f * spin    //Calculate the rotation in degrees for the inner image according to random value generated in spin variable
                timer = object : CountDownTimer(5000, 50) {
                    var rotation = 0f

                    //Implement two methods
                    override fun onTick(millisecUntilFinised: Long) {
                        rotation += 5f
                        if (rotation >= degrees) {
                            rotation = degrees
                            timer.cancel()
                            showResult(itemTitles[spin],spin)
                        }
                        binding.prizesimage.rotation = rotation
                    }

                    override fun onFinish() {}
                }.start()
            }else{
                Toast.makeText(activity,"You have no chances left!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

    }
}