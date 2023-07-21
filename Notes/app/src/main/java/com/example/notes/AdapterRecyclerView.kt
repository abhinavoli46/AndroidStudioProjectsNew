package com.example.notes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class AdapterRecyclerView(val dataList: ArrayList<Data>,val context : Context):RecyclerView.Adapter<AdapterRecyclerView.ViewHolder>() {


    open class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var textView = itemView.findViewById<TextView>(R.id.layoutTextView)
        var deleteButton = itemView.findViewById<Button>(R.id.deleteButton)
        var editButton = itemView.findViewById<Button>(R.id.editButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerviewdesign,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataList[position].data
        holder.deleteButton.setOnClickListener{
            val key = dataList[position].key
            val database:DatabaseReference = Firebase.database.getReference("Notes").child(key!!)
            database.removeValue().addOnCompleteListener {
                if(it.isSuccessful)
                {
                    notifyDataSetChanged()
                   Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show()
                }
            }
        }
        holder.editButton.setOnClickListener{
            val intent : Intent = Intent(context,CreateUpdateActivity::class.java)
            intent.putExtra("MODE","UPDATE")
            intent.putExtra("KEY", dataList[position].key)
            intent.putExtra("DATA",dataList[position].data)
            context.startActivity(intent)
        }

    }
}