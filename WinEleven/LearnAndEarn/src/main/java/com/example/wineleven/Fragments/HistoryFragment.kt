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


class HistoryFragment : Fragment() {
    val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }
    private var historyList = ArrayList<HistoryModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       historyList.add(HistoryModelClass("03:09","200"))
       historyList.add(HistoryModelClass("06:59","500"))
       historyList.add(HistoryModelClass("11:43","100"))
       historyList.add(HistoryModelClass("12:09","500"))
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
        val adapter = HistoryAdapter(historyList)
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

        return binding.root
    }

    companion object {

    }
}