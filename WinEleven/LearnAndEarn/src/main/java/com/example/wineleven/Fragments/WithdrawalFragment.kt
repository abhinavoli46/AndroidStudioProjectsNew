package com.example.wineleven.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wineleven.ModelClass.HistoryModelClass
import com.example.wineleven.R
import com.example.wineleven.databinding.ActivityHomeBinding
import com.example.wineleven.databinding.FragmentWithdrawlBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class WithdrawalFragment : BottomSheetDialogFragment() {
private lateinit var binding: FragmentWithdrawlBinding
var currentCoin = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWithdrawlBinding.inflate(inflater,container,false)
        Firebase.database.reference.child("WonCoins").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        currentCoin = snapshot.value as Long
                        binding.currentCoin.text = (currentCoin).toString()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        binding.transferButton.setOnClickListener{
            if(binding.amount.text.toString().toDouble() <= currentCoin)
            {
                Firebase.database.reference.child("WonCoins").child(Firebase.auth.currentUser!!.uid).setValue(currentCoin-binding.amount.text.toString().toDouble())
                var historyModel = HistoryModelClass(System.currentTimeMillis().toString(),binding.amount.text.toString(),true)
                Firebase.database.reference.child("CoinHistory").child(Firebase.auth.currentUser!!.uid).push().setValue(historyModel)
            }
            else
            {
                Toast.makeText(activity,"Out of Money",Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    companion object {

    }
}