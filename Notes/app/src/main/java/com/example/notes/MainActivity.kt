package com.example.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var binding : ActivityMainBinding
    private var list = ArrayList<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        database = Firebase.database.getReference("Notes")
        setContentView(binding.root)
        //Create a layoutManager and an a object of your adapter class and initalize adapter property of recyclerView with it
        binding.recycleNotesView.layoutManager = LinearLayoutManager(this)
        val adapter = AdapterRecyclerView(list,this)
        binding.recycleNotesView.adapter = adapter



        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //Storing data from database inside list
                list.clear()
                for(dataSnap in snapshot.children){
                    val data : Data? = dataSnap.getValue(Data::class.java)
                    list.add(data!!)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        binding.createbtn.setOnClickListener{
            startActivity(Intent(this,CreateUpdateActivity::class.java).putExtra("MODE","CREATE"))
        }

    }
}