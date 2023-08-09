package com.example.runfit.ui.fragments
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runfit.R
import com.example.runfit.adapter.RunAdapter
import com.example.runfit.databinding.FragmentRunBinding
import com.example.runfit.other.SortType

import com.example.runfit.ui.ViewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : Fragment() {
    val binding by lazy {
        FragmentRunBinding.inflate(layoutInflater)
    }
    private lateinit var runAdapter: RunAdapter
    private val viewModel: MainViewModel by viewModels()

    private fun setUpRecyclerView() = binding.rvRuns.apply{
        runAdapter = RunAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private val backgroundLocationPermissions = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            Log.d("Permission: ","Background Location Permission Granted")
        }
    }
    private val locationPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        when {
            it.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.d("Permission: ", "Coarse Location Permission Granted")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (ActivityCompat.checkSelfPermission(
                            requireActivity(),
                            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        backgroundLocationPermissions.launch(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    }
                }
            }

            it.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.d("Permission: ", "Fine Location Permission Granted")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (ActivityCompat.checkSelfPermission(
                            requireActivity(),
                            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        backgroundLocationPermissions.launch(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    }
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
        checkPermissions()
        setUpRecyclerView()

        when(viewModel.sortType){
            SortType.DATE -> binding.spFilter.setSelection(0)
            SortType.RUNNING_TIME -> binding.spFilter.setSelection(1)
            SortType.DISTANCE -> binding.spFilter.setSelection(2)
            SortType.AVG_SPEED -> binding.spFilter.setSelection(3)
            SortType.CALORIES_BURNED -> binding.spFilter.setSelection(4)
        }

        binding.spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                when(pos){
                    0->viewModel.sortRuns(SortType.DATE)
                    1->viewModel.sortRuns(SortType.RUNNING_TIME)
                    2->viewModel.sortRuns(SortType.DISTANCE)
                    3->viewModel.sortRuns(SortType.AVG_SPEED)
                    4->viewModel.sortRuns(SortType.CALORIES_BURNED)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        viewModel.runs.observe(viewLifecycleOwner, Observer {
            runAdapter.submitList(it)
        })

    }

    fun checkPermissions(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Check if we have any permission
            if(ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                ||
                ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                ){
                locationPermissions.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
            else{
                //Service
            }
        }
    }


}

