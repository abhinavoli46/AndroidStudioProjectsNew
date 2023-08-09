package com.example.runfit.ui.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.runfit.R
import com.example.runfit.databinding.FragmentStatisticsBinding
import com.example.runfit.databinding.FragmentTrackingBinding
import com.example.runfit.db.Run
import com.example.runfit.other.Constants.ACTION_PAUSE_SERVICE
import com.example.runfit.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.runfit.other.Constants.ACTION_STOP_SERVICE
import com.example.runfit.other.Constants.MAP_ZOOM
import com.example.runfit.other.Constants.POLYLINE_COLOR
import com.example.runfit.other.Constants.POLYLINE_WIDTH
import com.example.runfit.other.TrackingUtility
import com.example.runfit.services.Polyline
import com.example.runfit.services.TrackingService
import com.example.runfit.ui.ViewModel.MainViewModel
import com.example.runfit.ui.ViewModel.StatisticsViewModel
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.round

@AndroidEntryPoint
class TrackingFragment : Fragment() {
    private val viewModel : MainViewModel by viewModels()
    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()
    private var currentTimeInMills = 0L
    private var menu: Menu? = null
    private lateinit var toolbar : MaterialToolbar
    @set:Inject
    var weight = 80f

    val binding by lazy {
        FragmentTrackingBinding.inflate(layoutInflater)
    }
    private var map : GoogleMap? = null
    private var notificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(!it){
            Toast.makeText(requireContext(),"Notification Permission Not Given",Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        super.onCreateView(inflater, container, savedInstanceState)
        toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_close_white)
        toolbar.setNavigationOnClickListener{
            showCancelTrackingDialog()
        }

        return binding.root
    }



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync {
            map = it
            addAllPolylines()
        }

        subscribeToObserver()
        notificationPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)

        binding.btnToggleRun.setOnClickListener{
            toggleRun()
        }
        binding.btnFinishRun.setOnClickListener{
            zoomToWholeTrack()
            endRunAndSaveToDb()
        }

    }

    private fun subscribeToObserver(){
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyLine()
            moveCameraToUser()
        })

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, Observer {
            currentTimeInMills = it

            val formattedTime = TrackingUtility.getFormattedTimeInStopwatch(currentTimeInMills,true)
            binding.tvTimer.text = formattedTime

        })

    }
    private fun toggleRun(){
        if(isTracking){
            menu?.getItem(0)?.isVisible = true
            sendCommandToService(ACTION_PAUSE_SERVICE)
        }else{
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun updateTracking(isTracking : Boolean){
        this.isTracking = isTracking
        if(!isTracking){
            binding.btnToggleRun.text = "Start"
            binding.btnFinishRun.visibility = View.VISIBLE
        }
        else{
            binding.btnToggleRun.text = "Stop"
            menu?.getItem(0)?.isVisible = true
            binding.btnFinishRun.visibility = View.GONE
        }
    }
    private fun moveCameraToUser(){
        if(pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()){
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }
    private fun addAllPolylines(){
        for (polyline in pathPoints){
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }
    private fun addLatestPolyLine(){
        if(pathPoints.isNotEmpty() && pathPoints.last().size > 1){
            val preLastLatLng = pathPoints.last()[pathPoints.last().size-2]
            val lastLatLong = pathPoints.last().last()

            val polyLineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLong)
            map?.addPolyline(polyLineOptions)
        }
    }

    private fun sendCommandToService(action : String){
        Intent(requireContext(),TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }
    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState )
    }

    private fun showCancelTrackingDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext(),R.style.AlertDialogTheme)
            .setTitle("Cancel the Run?")
            .setMessage("Are you sure to cancel the current and delete all its data?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Yes"){
                _,_ ->
                stopRun()
            }
            .setNegativeButton("No"){
                dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
        dialog.show()
    }

    private fun stopRun(){
        sendCommandToService(ACTION_STOP_SERVICE)
        findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
    }
    private fun zoomToWholeTrack() {
        if(!pathPoints.isEmpty()){
            val bounds = LatLngBounds.Builder()
            for(polyline in pathPoints){
                for(pos in polyline){
                    bounds.include(pos)
                }
            }
            map?.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds.build(),
                    binding.mapView.width,
                    binding.mapView.height,
                    (binding.mapView.height * 0.05).toInt()
                )
            )
        }
    }

    private fun endRunAndSaveToDb(){
        map?.snapshot { bmp->
            var distanceInMeters = 0
            for(polyline in pathPoints){
                distanceInMeters += TrackingUtility.calculatePolyLineLength(polyline).toInt()
            }
            val avgSpeed = round((distanceInMeters/1000f) /(currentTimeInMills/1000f/60/60)*10)/ 10f
            val dateTimeStamp = Calendar.getInstance().timeInMillis
            val caloriesBurned = ((distanceInMeters)/1000f * weight).toInt()

            val run = Run(bmp,dateTimeStamp,avgSpeed,distanceInMeters,currentTimeInMills,caloriesBurned)
            viewModel.insertRun(run)

            Snackbar.make(requireActivity().findViewById(R.id.rootView),
                "Run Saved Successfully",
                Snackbar.LENGTH_LONG).show()
            stopRun()
        }
    }




}