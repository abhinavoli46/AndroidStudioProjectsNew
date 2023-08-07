package com.example.runfit.ui.ViewModel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runfit.db.Run
import com.example.runfit.other.SortType
import com.example.runfit.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    val mainRepository : MainRepository,val savedStateHandle: SavedStateHandle) : ViewModel(),LifecycleObserver {

        fun insertRun(run : Run) = viewModelScope.launch {
            mainRepository.insertRun(run)
        }

    private val runsSortedByDate = mainRepository.getAllRunSortedByDate()
    private val runsSortedByDistance = mainRepository.getAllRunSortedByDistance()
    private val runsSortedByCaloriesBurned = mainRepository.getAllRunSortedByCaloriesBurned()
    private val runsSortedByTimeInMillis = mainRepository.getAllRunSortedByTimeInMillis()
    private val runsSortedByAvgSpeed = mainRepository.getAllRunSortedByAvgSpeed()


    val runs = MediatorLiveData<List<Run>>()
    var sortType = SortType.DATE

    init{
        runs.addSource(runsSortedByDistance){result->
            if(sortType == SortType.DISTANCE){
                result?.let{
                    runs.value = it
                }
            }
        }
        runs.addSource(runsSortedByCaloriesBurned){result->
            if(sortType == SortType.CALORIES_BURNED){
                result?.let{
                    runs.value = it
                }
            }
        }
        runs.addSource(runsSortedByTimeInMillis){result->
            if(sortType == SortType.RUNNING_TIME){
                result?.let{
                    runs.value = it
                }
            }
        }
        runs.addSource(runsSortedByAvgSpeed){result->
            if(sortType == SortType.AVG_SPEED){
                result?.let{
                    runs.value = it
                }
            }
        }

    }

    fun sortRuns(sortType: SortType) = when(sortType){
        SortType.DATE -> runsSortedByDate.value?.let { runs.value = it }
        SortType.RUNNING_TIME -> runsSortedByTimeInMillis.value?.let { runs.value = it }
        SortType.AVG_SPEED -> runsSortedByAvgSpeed.value?.let { runs.value = it }
        SortType.DISTANCE -> runsSortedByDistance.value?.let { runs.value = it }
        SortType.CALORIES_BURNED -> runsSortedByCaloriesBurned.value?.let { runs.value = it }
    }.also {
        this.sortType = sortType
    }
}


/*
* This is our MainViewModel class which takes data from MainRepository and data reaches
  our fragments and Activity
**/