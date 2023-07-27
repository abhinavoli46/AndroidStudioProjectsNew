package com.example.jsonparsingusingvolleyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
    }

    private fun getData() {
        var url = "https://api.github.com/users"
        val queue = Volley.newRequestQueue(this@MainActivity)

        var userInfo = userInfo()

        val stringRequest = StringRequest(
            url,
            {
                val gsonBuilder = GsonBuilder()
                val gson = gsonBuilder.create()
                val userInfoItem = gson.fromJson(it,Array<userInfoItem>::class.java).toList()
                val textView = findViewById<TextView>(R.id.anstv)
                textView.text = userInfoItem.toString()
//                Toast.makeText(this@MainActivity,userInfoItem.toString(),Toast.LENGTH_SHORT).show()
            },
            {
                error ->
                Toast.makeText(this@MainActivity,"${error.localizedMessage}",Toast.LENGTH_SHORT).show()
            }

        )
        queue.add(stringRequest)
    }
}