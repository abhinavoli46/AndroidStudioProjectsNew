package com.example.wineleven.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wineleven.R
import com.example.wineleven.databinding.FragmentSpinnerBinding
import java.util.Timer

class SpinnerFragment : Fragment() {
   private lateinit var binding: FragmentSpinnerBinding
    private lateinit var timer : Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSpinnerBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {

    }
}