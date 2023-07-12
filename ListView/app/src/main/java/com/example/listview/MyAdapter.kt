package com.example.listview

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class MyAdapter(private val activity: Activity, private val items : ArrayList<CountryModel>):BaseAdapter()
{
    override fun getCount(): Int {
        return  items.size
    }

    override fun getItem(position: Int): CountryModel {
        return items[position]
    }

    override fun getItemId(position : Int): Long {
        return position.toLong()
    }
    //It is used to generate the view items
    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View
    {
        TODO("Not yet implemented")
    }

}