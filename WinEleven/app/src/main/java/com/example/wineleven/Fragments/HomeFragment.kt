package com.example.wineleven.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wineleven.Adapter.CategoryAdapter
import com.example.wineleven.ModelClass.CategoryModelClass
import com.example.wineleven.R
import com.example.wineleven.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var categoryList = ArrayList<CategoryModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryList.add(CategoryModelClass(R.drawable.avatar,"Science"))
        categoryList.add(CategoryModelClass(R.drawable.avatar,"English"))
        categoryList.add(CategoryModelClass(R.drawable.avatar,"Maths"))
        categoryList.add(CategoryModelClass(R.drawable.avatar,"Computer"))

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        var adapter = CategoryAdapter(categoryList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }
    companion object {

    }
}


