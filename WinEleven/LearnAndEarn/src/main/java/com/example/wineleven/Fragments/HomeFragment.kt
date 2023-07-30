package com.example.wineleven.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wineleven.Adapter.CategoryAdapter
import com.example.wineleven.ModelClass.CategoryModelClass
import com.example.wineleven.R
import com.example.wineleven.User
import com.example.wineleven.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var categoryList = ArrayList<CategoryModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryList.add(CategoryModelClass(R.drawable.science,"Science"))
        categoryList.add(CategoryModelClass(R.drawable.english,"English"))
        categoryList.add(CategoryModelClass(R.drawable.maths,"Maths"))
        categoryList.add(CategoryModelClass(R.drawable.computer,"Computer"))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
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
        //Getting data from database and setting name textview with the user name
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<User>()
                        binding.nameTextViewFragment.text = user?.name
                    }

                    override fun onCancelled(error: DatabaseError) {}

                }
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        var adapter = CategoryAdapter(categoryList,requireActivity())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }
    companion object {

    }
}


