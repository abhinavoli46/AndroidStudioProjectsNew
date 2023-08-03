package com.example.wineleven.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wineleven.ModelClass.HistoryModelClass
import com.example.wineleven.databinding.HistoryItemViewBinding
import java.sql.Timestamp
import java.util.Date

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
        var timestamp = Timestamp(listHistory[position].TimeAndDate.toLong())
        holder.binding.timeanddate.text = Date(timestamp.time).toString()
        holder.binding.coinHistory.text = listHistory[position].coin
        holder.binding.creditordebit.text = if(listHistory[position].isDebit){
            "-Debit"
        }
        else{
            "+Credit"
        }
    }
}