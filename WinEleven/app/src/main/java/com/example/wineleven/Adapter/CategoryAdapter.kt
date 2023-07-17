package com.example.wineleven.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wineleven.ModelClass.CategoryModelClass
import com.example.wineleven.databinding.CategoryItemViewBinding

class CategoryAdapter(var categoryList : ArrayList<CategoryModelClass>) : RecyclerView.Adapter<CategoryAdapter.MyCategoryViewHolder> (){
    class MyCategoryViewHolder (var binding:CategoryItemViewBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryViewHolder {
        return MyCategoryViewHolder(CategoryItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyCategoryViewHolder, position: Int) {
        var datalist = categoryList[position]
        holder.binding.categoryImage.setImageResource(datalist.categoryImage)
        holder.binding.categoryItemText.text = datalist.categoryText
    }


}