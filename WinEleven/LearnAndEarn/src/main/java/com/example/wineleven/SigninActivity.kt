package com.example.wineleven

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.wineleven.databinding.ActivitySigninBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SigninActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySigninBinding
    private lateinit var googleSignInClient:GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        binding.buttonSignIn.setOnClickListener {
            //Create User in FireBase if all entries are filled
            if(validate())
            {
                Firebase.auth.signInWithEmailAndPassword(binding.emailSignIn.text.toString(),binding.passwordSignIn.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful){
//                            create A User and add it to the FireBase database
                            startActivity(Intent(this@SigninActivity,HomeActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this@SigninActivity,"Wrong UserName or Password",
                                Toast.LENGTH_SHORT).show()
                            binding.emailSignIn.text.clear()
                            binding.passwordSignIn.text.clear()
                        }

                    }
            }
            else{
                Toast.makeText(this@SigninActivity,"Enter Correct Details", Toast.LENGTH_SHORT).show()
            }
        }
        binding.googleBtnSignUp.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }
        binding.signUpLink.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }


    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
            if(result.resultCode == Activity.RESULT_OK){
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if(task.isSuccessful){
                    val account:GoogleSignInAccount?=task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
                    Firebase.auth.signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful){
                            startActivity(Intent(this@SigninActivity,HomeActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this@SigninActivity,"Failed",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this@SigninActivity,"Failed",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun validate(): Boolean{
        if( binding.passwordSignIn.text.toString().isEmpty() || binding.emailSignIn.text.toString().isEmpty())
            return false
        return true
    }
}