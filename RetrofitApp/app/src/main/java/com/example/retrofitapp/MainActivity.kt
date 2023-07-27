package com.example.retrofitapp

import android.net.http.HttpException
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitapp.databinding.ActivityMainBinding
import com.example.retrofitapp.databinding.ItemtodoBinding
import kotlinx.coroutines.launch
import retrofit2.http.HTTP
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressBar.visibility = View.INVISIBLE
        lifecycleScope.launch {
                val response = try{
                RetrofitInstance.api.getTodos()
            } catch (e:IOException){
                    binding.progressBar.visibility = View.INVISIBLE
                Log.e("error","Exception Occurred")
                return@launch
            }
            if(response.isSuccessful && response.body()!= null){
                val list = response.body()!!
                val myaAdapter = MyAdapter(list)
                binding.recyclerViewTodo.adapter = myaAdapter
                binding.recyclerViewTodo.layoutManager = LinearLayoutManager(this@MainActivity)
            }
            else{
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity,"Response Unsuccessful",Toast.LENGTH_SHORT).show()
            }
        }


    }
}