package com.example.runfit.ui.ViewModel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.runfit.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
@HiltViewModel
class StatisticsViewModel @Inject constructor(
    val mainRepository : MainRepository, val savedStateHandle: SavedStateHandle) : ViewModel(),LifecycleObserver {

     val totalTimeRun = mainRepository.getTotalTimeInMillis()
    val totalDistance = mainRepository.getTotalDistance()
    val totalCaloriesBurned = mainRepository.getTotalCaloriesBurnt()
    val totalAvgSpeed = mainRepository.getTotalAvgSpeed()

    val runsSortedByDate = mainRepository.getAllRunSortedByDate()
}


/*
* This is our StaticsViewModel class which takes data from MainRepository and data reaches
  our fragments and Activity
* */