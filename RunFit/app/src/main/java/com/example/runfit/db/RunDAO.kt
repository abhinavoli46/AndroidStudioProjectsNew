package com.example.runfit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RunDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run : Run)

    @Delete
    suspend fun deleteRun(run : Run)

    //Queries required for a single run
    @Query("SELECT * FROM running_table ORDER BY timestamp DESC")
    fun getAllRunsSortedByDate() : LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY timeInMillis DESC")
    fun getAllRunsSortedByTimeInMillis() : LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY caloriesBurnt DESC")
    fun getAllRunsSortedByCaloriesBurnt() : LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY distanceInMeters DESC")
    fun getAllRunsSortedByDistanceInMeters() : LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY avgSpeedInKMH DESC")
    fun getAllRunsSortedByAvgSpeedInKMH() : LiveData<List<Run>>


    //Queries required for calculating averages and totals
    @Query("SELECT SUM(timeInMillis) FROM running_table")
    fun getTotalTimeInMillis() : LiveData<Long>

    @Query("SELECT SUM(caloriesBurnt) FROM running_table")
    fun getTotalCaloriesBurnt() : LiveData<Long>

    @Query("SELECT SUM(distanceInMeters) FROM running_table")
    fun getTotalDistanceInMeters() : LiveData<Long>

    @Query("SELECT AVG(avgSpeedInKMH) FROM running_table")
    fun getTotalAvgSpeed() : LiveData<Long>


}