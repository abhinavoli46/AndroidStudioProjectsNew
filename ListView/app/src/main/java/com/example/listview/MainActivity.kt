package com.example.listview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //1. Defining a ListView from xml file
        var lisView : ListView = findViewById(R.id.listview)

        //2. Define an Array of Numbers
        val numbers : Array<Int> = arrayOf(1,2,3,4,5,6)

        //3. Define an adapter and pass activity context, design of your single list item and data item
        val adapter : ArrayAdapter<*> =
            ArrayAdapter(this,R.layout.list_item_layout,numbers)

        //4. Connect the adapter with the listView
            lisView.adapter = adapter
    }
}