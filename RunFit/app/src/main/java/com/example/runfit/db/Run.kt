package com.example.runfit.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running_table")
data class Run (
    var img : Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0F,
    var distanceInMeters : Int = 0,
    var timeInMillis : Long = 0L,
    var caloriesBurnt : Int = 0
)
{
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
}

/*
* img : image of run
* timestamp : time at which run started
* avgSpeedInKMH : speed of runner
* distanceInMeters : distance run
* timeInMillis : total time for which running took place
* caloriesBurnt : calories burned during run
* */