package com.example.a7minuteworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items : ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseStatusAdapter.MyViewHolder> (){

    inner class MyViewHolder(binding: ItemExerciseStatusBinding):
        RecyclerView.ViewHolder(binding.root){
            val numberTextView = binding.countTextView
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.numberTextView.text = model.getId().toString()
        when{
            model.getisSelected()->{
                holder.numberTextView.background = ContextCompat.getDrawable(holder.itemView.context,R.drawable.selectedbackground)
                holder.numberTextView.setTextColor(Color.parseColor("#212121"))
            }
            model.getisCompleted()->{
                holder.numberTextView.background = ContextCompat.getDrawable(holder.itemView.context,R.drawable.circular_button_background)
                holder.numberTextView.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else->{
                holder.numberTextView.background = ContextCompat.getDrawable(holder.itemView.context,R.drawable.notdonebackground)
                holder.numberTextView.setTextColor(Color.parseColor("#212121"))
            }
        }
    }
}