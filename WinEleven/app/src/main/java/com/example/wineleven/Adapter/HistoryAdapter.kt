package com.example.wineleven.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wineleven.ModelClass.HistoryModelClass
import com.example.wineleven.databinding.HistoryItemViewBinding

class HistoryAdapter(var listHistory : ArrayList<HistoryModelClass>) : RecyclerView.Adapter<HistoryAdapter.MyHistoryViewHolder>() {
    class MyHistoryViewHolder(var binding:HistoryItemViewBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHistoryViewHolder {
        return MyHistoryViewHolder(HistoryItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: MyHistoryViewHolder, position: Int) {
        holder.binding.timeanddate.text = listHistory[position].TimeAndDate
        holder.binding.coinHistory.text = listHistory[position].coin
    }
}