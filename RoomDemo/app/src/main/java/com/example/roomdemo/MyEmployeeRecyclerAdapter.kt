package com.example.roomdemo

import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.EmployeerecorditemviewBinding

class MyEmployeeRecyclerAdapter(private val list : ArrayList<EmployeeEntity>,
                                private val updateListener: (id:Int)->Unit,
                                private val deleteListener: (id:Int)->Unit
) : RecyclerView.Adapter<MyEmployeeRecyclerAdapter.MyViewHolder> (){

    class MyViewHolder(binding:EmployeerecorditemviewBinding):RecyclerView.ViewHolder(binding.root){
        val llmain = binding.llMain
        val textName = binding.nametv
        val email = binding.emailtv
        val editbtn = binding.editButton
        val deletebtn = binding.deletebutton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(EmployeerecorditemviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = list[position]

        holder.textName.text = item.name
        holder.email.text = item.email
        if(position%2==0){
            holder.llmain.setBackgroundColor(ContextCompat.getColor(holder.llmain.context, R.color.orange))
        }
        holder.editbtn.setOnClickListener{
            item.id.let { it1 -> updateListener.invoke(it1) }
        }
        holder.deletebtn.setOnClickListener{
            item.id.let { it1 -> deleteListener.invoke(it1) }
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }
}