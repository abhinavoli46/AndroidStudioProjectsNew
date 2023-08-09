package com.example.runfit.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.runfit.R
import com.example.runfit.databinding.FragmentSettingsBinding
import com.example.runfit.databinding.FragmentStatisticsBinding
import com.example.runfit.other.CustomMarkerView
import com.example.runfit.other.TrackingUtility
import com.example.runfit.ui.ViewModel.MainViewModel
import com.example.runfit.ui.ViewModel.StatisticsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.round
import kotlin.math.roundToInt

@AndroidEntryPoint
class StatisticsFragment : Fragment() {
    private val viewModel : StatisticsViewModel by viewModels()
    val binding by lazy {
        FragmentStatisticsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObserver()
        setUpBarChart()
    }
    private fun subscribeToObserver(){
        viewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let{
                val totalTimeRun = TrackingUtility.getFormattedTimeInStopwatch(it)
                binding.tvTotalTime.text = totalTimeRun
            }
        })

        viewModel.totalDistance.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it/1000f
                val totalDis = (km * 10f).roundToInt() /10f
                val totalDisString = "${totalDis}Km"
                binding.tvTotalDistance.text = totalDisString
            }
        })

        viewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalAvgSpeed =  (it * 10f).roundToInt() /10f
                val totalAvgSpeedString = "${totalAvgSpeed}Km/h"
                binding.tvAverageSpeed.text = totalAvgSpeedString
            }
        })
        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val caloriesTotal =  "${it}kcal"

                binding.tvTotalCalories.text = caloriesTotal
            }
        })

        viewModel.runsSortedByDate.observe(viewLifecycleOwner, Observer {
            it?.let{
                val allAvgSpeed = it.indices.map { i -> BarEntry(i.toFloat(),it[i].avgSpeedInKMH) }
                val barDataSet = BarDataSet(allAvgSpeed,"Avg Speed Over Time").apply {
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), pub.devrel.easypermissions.R.color.colorAccent)
                }
                binding.barChart.data = BarData(barDataSet)
                binding.barChart.marker = CustomMarkerView(it.reversed(),requireContext(),R.layout.marker_view)
                binding.barChart.invalidate()

            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    private fun setUpBarChart(){
        binding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        binding.barChart.axisLeft.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        binding.barChart.axisRight.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        binding.barChart.apply{
            description.text = "Avg Speed Over Time"
            legend.isEnabled = false
        }
    }
}