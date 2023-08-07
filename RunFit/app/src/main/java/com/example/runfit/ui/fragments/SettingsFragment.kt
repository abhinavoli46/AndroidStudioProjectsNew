package com.example.runfit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.runfit.R
import com.example.runfit.databinding.FragmentRunBinding
import com.example.runfit.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    val binding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }
}