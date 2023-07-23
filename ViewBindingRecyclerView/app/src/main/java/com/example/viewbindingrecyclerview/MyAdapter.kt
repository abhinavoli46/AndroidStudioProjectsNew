package com.example.viewbindingrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewbindingrecyclerview.databinding.RecyclerviewitemBinding

class MyAdapter(var listProfile:ArrayList<Profile>) : RecyclerView.Adapter<MyAdapter.MyViewHolder> (){

    inner class MyViewHolder(val itemBinding:RecyclerviewitemBinding)
        :RecyclerView.ViewHolder(itemBinding.root){
            fun bindItem(profile: Profile)
            {
                itemBinding.Nametextview.text = profile.name
                itemBinding.collegeTextView.text = profile.college
                itemBinding.image.setImageResource(R.drawable.ic_launcher_background)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(RecyclerviewitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return listProfile.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val person = listProfile[position]
        holder.bindItem(person)
    }
}