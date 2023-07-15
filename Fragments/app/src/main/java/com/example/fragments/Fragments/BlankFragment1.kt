package com.example.fragments.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.fragments.R
import com.example.fragments.databinding.ActivityMainBinding.inflate
import com.example.fragments.databinding.FragmentBlank1Binding

class BlankFragment1 : Fragment() {

    private lateinit var binding:FragmentBlank1Binding
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentBlank1Binding.inflate(inflater,container,false)


        binding.changeButton.setOnClickListener{
            navController.navigate(R.id.action_blankFragment1_to_blankFragment2)
        }
        return binding.root

    }

    companion object {
    }
}