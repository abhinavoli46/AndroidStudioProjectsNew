package com.example.runfit.other

import android.content.Context
import android.widget.TextView
import com.example.runfit.R
import com.example.runfit.databinding.MarkerViewBinding
import com.example.runfit.db.Run
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CustomMarkerView (
    val runs : List<Run>,
    c : Context,
    layoutId:Int
) : MarkerView(c, layoutId){
    var markerDateTextView : TextView? = null
    var markerCaloriesTextView : TextView? = null
    var markerDurationTextView : TextView? = null
    var markerAvgSpeedTextView : TextView? = null
    var markerDistanceTextView : TextView? = null

    init{
        markerDateTextView = findViewById(R.id.tvDateMarker)
        markerCaloriesTextView = findViewById(R.id.tvCaloriesBurnedMarker)
        markerDurationTextView = findViewById(R.id.tvDurationMarker)
        markerAvgSpeedTextView = findViewById(R.id.tvAvgSpeedMarker)
        markerDistanceTextView = findViewById(R.id.tvDistanceMarker)

    }

    override fun getOffset(): MPPointF {
        return MPPointF(-width/2f,-height.toFloat())
    }
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)

        if(e == null)
            return

        val currentId = e.x.toInt()
        val run = runs[currentId]

        val calender = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        markerDateTextView?.text = dateFormat.format(calender.time)

        val avgSpeed = "${run.avgSpeedInKMH}Km/h"
        markerAvgSpeedTextView?.text = avgSpeed

        val disInKm = "${run.distanceInMeters / 1000f}Km"
        markerDistanceTextView?.text = disInKm

       markerDurationTextView?.text = TrackingUtility.getFormattedTimeInStopwatch(run.timeInMillis)

        val calories = "${run.caloriesBurnt}kcal"
            markerCaloriesTextView?.text = calories
    }
}