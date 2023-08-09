package com.example.runfit.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.runfit.R
import com.example.runfit.databinding.FragmentRunBinding
import com.example.runfit.databinding.FragmentSettingsBinding
import com.example.runfit.other.Constants.KEY_NAME
import com.example.runfit.other.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    val binding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater)
    }
    @Inject
    lateinit var sharedPref : SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFieldsForSharedPref()
        binding.btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPrefrences()
            if(success){
                Snackbar.make(view, "Saved Changes",Snackbar.LENGTH_LONG ).show()
            } else{
                Snackbar.make(view,"Please fill out all the fields",Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun loadFieldsForSharedPref(){
        val name = sharedPref.getString(KEY_NAME,"")
        val weight = sharedPref.getFloat(KEY_WEIGHT,80f)
        binding.etName.setText(name)
        binding.etWeight.setText(weight.toString())

    }
    private fun applyChangesToSharedPrefrences():Boolean{
        val nameText = binding.etName.text.toString()
        val weightText = binding.etWeight.text.toString()
        if(nameText.isEmpty() || weightText.isEmpty()){
            return false
        }
        sharedPref.edit()
            .putString(KEY_NAME,nameText)
            .putFloat(KEY_WEIGHT,weightText.toFloat())
            .apply()
        val toolbarText = "Let'S go $nameText"
        requireActivity().findViewById<MaterialTextView>(R.id.tvToolbarTitle)
        return true
    }
}