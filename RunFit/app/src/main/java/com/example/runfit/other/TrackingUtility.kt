package com.example.runfit.other

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.health.connect.datatypes.ExerciseRoute.Location
import android.os.Build
import androidx.core.content.ContextCompat
import com.example.runfit.services.PolyLines
import com.example.runfit.services.Polyline
import pub.devrel.easypermissions.EasyPermissions
import java.util.concurrent.TimeUnit

object TrackingUtility {


    fun hasLocationPermission(context: Context) : Boolean{
        return ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun Context.checkSinglePermission(permission: String) : Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun getFormattedTimeInStopwatch(ms: Long,includeMillis:Boolean = false) : String{
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)

        var seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        if(!includeMillis){
            return "${if(hours < 10) "0" else ""}$hours:" +
                   "${if(minutes < 10)"0" else ""}$minutes:" +
                   "${if(seconds < 10)"0" else ""}$seconds"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10
        return "${if(hours < 10) "0" else ""}$hours:" +
                "${if(minutes < 10)"0" else ""}$minutes:" +
                "${if(seconds < 10)"0" else ""}$seconds:" +
                "${if(milliseconds < 10)"0" else ""}$milliseconds"

    }

    fun calculatePolyLineLength(polyline: Polyline) : Float{
        var distance = 0f
        for(i in 0..polyline.size-2){
            val pos1 = polyline[i]
            val pos2 = polyline[i+1]
            val result = FloatArray(1)
            android.location.Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
            )
            distance += result[0]
        }
        return distance
    }
}

/*
* This object class is just used to check the build version of the mobile device
* If it is less then Version Q no need to take background permission
* Otherwise take the background permission
*/