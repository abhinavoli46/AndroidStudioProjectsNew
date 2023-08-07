package com.example.runfit.repositories

import com.example.runfit.db.Run
import com.example.runfit.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDao : RunDAO
){
    suspend fun insertRun(run: Run) = runDao.insertRun(run)
    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

    //This is not of suspend type because it returns livedata which is itself asynchronous
    fun getAllRunSortedByDate() = runDao.getAllRunsSortedByDate()
    fun getAllRunSortedByDistance() = runDao.getAllRunsSortedByDistanceInMeters()
    fun getAllRunSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()
    fun getAllRunSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeedInKMH()
    fun getAllRunSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurnt()

    fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()
    fun getTotalDistance() = runDao.getTotalDistanceInMeters()
    fun getTotalCaloriesBurnt() = runDao.getTotalCaloriesBurnt()
    fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()

}

/*
* This is the repository of our project which interact with data sources like our room database and API
* and provides data to ViewModel
* */