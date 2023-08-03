package com.example.wineleven.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wineleven.MainActivity
import com.example.wineleven.R
import com.example.wineleven.User
import com.example.wineleven.databinding.FragmentProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
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
        binding.logoutbtn.setOnClickListener {
            try{
                val acct : GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())
                if(acct != null) {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
                    GoogleSignIn.getClient(requireContext(),gso).signOut()
                    startActivity(Intent(requireContext(), MainActivity::class.java))

                }
//                else{
//                    FirebaseAuth.getInstance().signOut()
//                    startActivity(Intent(context,MainActivity::class.java))
//                }


            } catch ( e:Exception){
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()

            }

        }
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<User>()
                        binding.usernametextview.text = user?.name
                        binding.agetextview.text = user?.age.toString()
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