package com.example.firebaseapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firebaseapp.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONObject.NULL

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        //For SignUp Button
        binding.button.setOnClickListener{
            auth.createUserWithEmailAndPassword(binding.emailIdEditText.text.toString(), binding.passwordEditText.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(this,task.result.toString() + "$user",Toast.LENGTH_LONG).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()

                    }
                }
        }
        //For Google Sign Up button

        binding.googleSignUp.setOnClickListener{
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser!= null)
        {
            startActivity(Intent(this,SignOutActivity::class.java))
        }
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful)
            {
                val account:GoogleSignInAccount?= task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if(it.isSuccessful){
                        startActivity(Intent(this,SignOutActivity::class.java))
                    }else{
                        Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        else{
            Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show()
        }
    }
}