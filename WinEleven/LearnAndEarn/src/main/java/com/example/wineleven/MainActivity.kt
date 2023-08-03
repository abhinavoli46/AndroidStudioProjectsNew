package com.example.wineleven

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.wineleven.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.net.UnknownServiceException

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignUp.setOnClickListener {
            //Create User in FireBase if all entries are filled
            if(validate())
            {
                Firebase.auth.createUserWithEmailAndPassword(binding.emailSignUp.text.toString(),binding.passwordSignUp.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful){
//                            create A User and add it to the FireBase database
                            var user = User(binding.nameSignUp.text.toString(),
                                binding.ageSignUp.text.toString().toInt(),
                                binding.emailSignUp.text.toString(),
                                binding.passwordSignUp.text.toString())


                            Firebase.database.reference.child("Users")
                                .child(Firebase.auth.currentUser!!.uid).setValue(user).addOnSuccessListener {
                                    startActivity(Intent(this@MainActivity,HomeActivity::class.java))
                                    finish()
                                }


                        }
                        else{
                            Toast.makeText(this@MainActivity,it.exception?.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                        }

                    }
            }
            else{
                Toast.makeText(this@MainActivity,"Enter Correct Details",Toast.LENGTH_SHORT).show()
            }
        }
        binding.signInLink.setOnClickListener {
            startActivity(Intent(this,SigninActivity::class.java))
        }
    }

    private fun validate(): Boolean{
        if(binding.nameSignUp.text.toString().isEmpty() || binding.ageSignUp.text.toString().isEmpty() || binding.passwordSignUp.text.toString().isEmpty() || binding.emailSignUp.text.toString().isEmpty())
                return false
        return true
    }

    //To check if any user is already logged in.
    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser != null)
        {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

    }
}