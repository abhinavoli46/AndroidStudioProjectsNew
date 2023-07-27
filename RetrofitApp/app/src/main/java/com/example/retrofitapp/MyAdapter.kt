package com.example.retrofitapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitapp.databinding.ItemtodoBinding

class MyAdapter (var list : List<Todo>):RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    class MyViewHolder (binding: ItemtodoBinding) : RecyclerView.ViewHolder(binding.root){
        var textView = binding.textView
        var checkBox = binding.checkBoxDone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemtodoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val item : Todo = list[position]
        holder.textView.text = item.title
        holder.checkBox.isChecked = item.completed
    }
}