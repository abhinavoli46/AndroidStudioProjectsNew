package com.example.runfit.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runfit.R
import com.example.runfit.databinding.ActivityMainBinding
import com.example.runfit.databinding.FragmentSetupBinding
import com.example.runfit.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.example.runfit.other.Constants.KEY_NAME
import com.example.runfit.other.Constants.KEY_WEIGHT
import com.example.runfit.ui.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment() {
    @Inject
    lateinit var sharedPref:SharedPreferences
    val binding by lazy {
        FragmentSetupBinding.inflate(layoutInflater)
    }
    @set:Inject
    var isFirstAppOpen = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

            super.onCreateView(inflater, container, savedInstanceState)
            if(!isFirstAppOpen){
                val navOption = NavOptions.Builder()
                    .setPopUpTo(R.id.setupFragment,true)
                    .build()
                findNavController().navigate(
                    R.id.action_setupFragment_to_runFragment,
                    savedInstanceState,
                    navOption
                )
            }

        binding.tvContinue.setOnClickListener {
                val success = writePersonalDataToSharedPrefrence()
                if(success)
                    findNavController().navigate(R.id.action_setupFragment_to_runFragment)
                else{
                    Snackbar.make(requireView(),"Please enter all the fields",Snackbar.LENGTH_SHORT).show()
                }

            }

        return binding.root

    }

    private fun writePersonalDataToSharedPrefrence() : Boolean{
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()

        if(name.isEmpty() || weight.isEmpty()){
            return false
        }
        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT,weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE,false)
            .apply()

        val toolbarText = "Let's Go, $name!"
        requireActivity().findViewById<MaterialTextView>(R.id.tvToolbarTitle).text = toolbarText
        return true
    }


}