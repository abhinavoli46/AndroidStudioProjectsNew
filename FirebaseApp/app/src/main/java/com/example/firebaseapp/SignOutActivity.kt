package com.example.firebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class SignOutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_out)

        var button : Button? = findViewById<Button?>(R.id.signOutButton)


        button!!.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
            GoogleSignIn.getClient(this,gso).signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}