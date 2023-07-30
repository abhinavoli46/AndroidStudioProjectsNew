package com.example.wineleven.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wineleven.R
import com.example.wineleven.User
import com.example.wineleven.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {
    val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    var isExpand = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding.imageButton.setOnClickListener{
            if(isExpand)
            {
                binding.expandableConstraintLayout.visibility = View.VISIBLE
                binding.imageButton.setImageResource(com.google.android.material.R.drawable.material_ic_menu_arrow_up_black_24dp)
                isExpand = false
            }
            else
            {
                binding.expandableConstraintLayout.visibility = View.GONE
                binding.imageButton.setImageResource(R.drawable.drpdownarrow)
                isExpand = true
            }

        }
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<User>()
                        binding.usernametextview.text = user?.name
                        binding.agetextview.text = user?.name.toString()
                        binding.emailtextview.text = user?.email
                        binding.passwordtextview.text = user?.password
                        binding.nameHeading.text = user?.name
                    }

                    override fun onCancelled(error: DatabaseError) {}

                }
            )
        return binding.root
    }

    companion object {

    }
}