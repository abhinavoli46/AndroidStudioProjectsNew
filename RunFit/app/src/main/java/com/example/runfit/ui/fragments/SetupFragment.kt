package com.example.runfit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runfit.R
import com.example.runfit.databinding.FragmentSetupBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SetupFragment : Fragment() {
    val binding by lazy {
        FragmentSetupBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            super.onCreateView(inflater, container, savedInstanceState)
            binding.tvContinue.setOnClickListener {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            }

        return binding.root

    }


}