package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: rvadapter
    private var datalist : ArrayList<rvmodel> = ArrayList<rvmodel>()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datalist.add(rvmodel(R.drawable.attt,"Attitude","India"))
        datalist.add(rvmodel(R.drawable.attt,"Attitude","India"))
        datalist.add(rvmodel(R.drawable.attt,"Attitude","India"))
        datalist.add(rvmodel(R.drawable.attt,"Attitude","India"))
        datalist.add(rvmodel(R.drawable.attt,"Attitude","India"))
        datalist.add(rvmodel(R.drawable.attt,"Attitude","India"))
        datalist.add(rvmodel(R.drawable.attt,"Attitude","India"))

        adapter = rvadapter(datalist,this)
        binding.rview.layoutManager = LinearLayoutManager(this)
        binding.rview.adapter = adapter
    }
}