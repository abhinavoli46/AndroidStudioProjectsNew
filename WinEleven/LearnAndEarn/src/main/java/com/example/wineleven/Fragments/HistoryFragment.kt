package com.example.wineleven.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wineleven.Adapter.HistoryAdapter
import com.example.wineleven.ModelClass.HistoryModelClass
import com.example.wineleven.R
import com.example.wineleven.User
import com.example.wineleven.databinding.FragmentHistoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Collections


class HistoryFragment : Fragment() {
    val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }
    private var historyList = ArrayList<HistoryModelClass>()
    private lateinit var adapter : HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.reference.child("CoinHistory").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    historyList.clear()
                    var listHistorytemp = ArrayList<HistoryModelClass>()
                    for(datasnapshot in snapshot.children){
                        var data = datasnapshot.getValue(HistoryModelClass::class.java)
                        listHistorytemp.add(data!!)
                    }
                    Collections.reverse(listHistorytemp)
                    historyList.addAll(listHistorytemp)
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
                }

            })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
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

        //Adapter
        adapter = HistoryAdapter(historyList)
        binding.historyRecyclerView.adapter = adapter
        binding.historyRecyclerView.setHasFixedSize(true)

        //Getting data from database and setting name textview with the user name
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<User>()
                        binding.nameTextViewFragment.text = user?.name
                    }

                    override fun onCancelled(error: DatabaseError) {}

                })

        Firebase.database.reference.child("WonCoins").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        val wonCoins = snapshot.value as Long
                        binding.coinClickableText.text = (wonCoins).toString()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        return binding.root
    }

    companion object {

    }
}