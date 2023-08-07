package com.example.runfit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runfit.databinding.ItemRunBinding
import com.example.runfit.db.Run
import com.example.runfit.other.TrackingUtility
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>(){

    inner class RunViewHolder(binding:ItemRunBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView = binding.ivRunImage
        val dateTextView = binding.tvDate
        val averageSpeedTextView = binding.tvAvgSpeed
        val distanceInKm = binding.tvDistance
        val timeTextView =  binding.tvTime
        val caloriesBurnedTextView = binding.tvCalories
    }

    val diffCallback = object : DiffUtil.ItemCallback<Run>(){
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    val differ = AsyncListDiffer(this,diffCallback)
    fun submitList(list:List<Run>) = differ.submitList(list)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(ItemRunBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(run.img).into(holder.imageView)
            val calender = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            holder.dateTextView.text = dateFormat.format(calender.time)

            val avgSpeed = "${run.avgSpeedInKMH}Km/h"
            holder.averageSpeedTextView.text = avgSpeed

            val disInKm = "${run.distanceInMeters / 1000f}Km"
            holder.distanceInKm.text = disInKm

            holder.timeTextView.text = TrackingUtility.getFormattedTimeInStopwatch(run.timeInMillis)

            val calories = "${run.caloriesBurnt}kcal"
            holder.caloriesBurnedTextView.text = calories
        }
    }
}

