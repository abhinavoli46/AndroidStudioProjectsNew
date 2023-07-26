package com.example.a7minuteworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkoutapp.databinding.ActivityHistoryBinding
import com.example.a7minuteworkoutapp.databinding.HistoryitemviewBinding

class HistoryAdapter(private var items : ArrayList<String>) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {
    class MyViewHolder (binding: HistoryitemviewBinding):RecyclerView.ViewHolder(binding.root){
        val consLayoutHistory = binding.historyitemconstraintlayout
        val dateTimeTextView = binding.datetimestoretextview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return(MyViewHolder(HistoryitemviewBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dateItem : String = items[position]
        holder.dateTimeTextView.text = dateItem
        if(position%2 == 0){
            holder.consLayoutHistory.setBackgroundColor(Color.parseColor("#000000"))
            holder.dateTimeTextView.setTextColor(Color.parseColor("#FFFFFF"))
        }
        else{ holder.consLayoutHistory.setBackgroundColor(Color.parseColor("#FFFFFF"))
                holder.dateTimeTextView.setTextColor(Color.parseColor("#000000"))

        }
    }
}