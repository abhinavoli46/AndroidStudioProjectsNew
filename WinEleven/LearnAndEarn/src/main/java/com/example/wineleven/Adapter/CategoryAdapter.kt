package com.example.wineleven.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wineleven.ModelClass.CategoryModelClass
import com.example.wineleven.QuizActivity
import com.example.wineleven.databinding.CategoryItemViewBinding

class CategoryAdapter(
    var categoryList: ArrayList<CategoryModelClass>,
    var requireActivity: FragmentActivity
) : RecyclerView.Adapter<CategoryAdapter.MyCategoryViewHolder> (){
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
        holder.binding.cardViewButton.setOnClickListener{
            var intent : Intent = Intent(requireActivity,QuizActivity::class.java)
            intent.putExtra("categoryImage",datalist.categoryImage)
            intent.putExtra("questionType",datalist.categoryText)
            requireActivity.startActivity(intent)
        }
    }


}