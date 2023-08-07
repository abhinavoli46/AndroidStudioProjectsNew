package com.example.runfit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.runfit.R
import com.example.runfit.databinding.FragmentSettingsBinding
import com.example.runfit.databinding.FragmentStatisticsBinding
import com.example.runfit.ui.ViewModel.MainViewModel
import com.example.runfit.ui.ViewModel.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment() {
    private val viewModel : StatisticsViewModel by viewModels()
    val binding by lazy {
        FragmentStatisticsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }
}