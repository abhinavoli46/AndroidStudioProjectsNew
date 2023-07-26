package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding : ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.black)

        setSupportActionBar(binding?.historyToolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding?.historyToolbar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val historyDao = (application as WorkoutApp).db.historyDao()
        getAllDatesFromDatabase(historyDao)
    }

    private fun getAllDatesFromDatabase(historyDao: HistoryDao){
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect{ allCompletedDatesList ->
                if(allCompletedDatesList.isNotEmpty()){
                    binding?.exercisehisheadingtv?.visibility = View.VISIBLE
                    binding?.historyrecyclervview?.visibility = View.VISIBLE
                    binding?.nohistoryavailabletv?.visibility = View.INVISIBLE
                    binding?.historyrecyclervview?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val dateList = ArrayList<String>()
                    for(date in allCompletedDatesList){
                        dateList.add(date.date)
                    }
                    val historyAdaptder = HistoryAdapter(dateList)
                    binding?.historyrecyclervview?.adapter = historyAdaptder
                }
                else{
                    binding?.exercisehisheadingtv?.visibility = View.INVISIBLE
                    binding?.historyrecyclervview?.visibility = View.INVISIBLE
                    binding?.nohistoryavailabletv?.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}