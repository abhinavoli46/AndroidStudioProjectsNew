package com.example.runfit.ui.ViewModel

import androidx.lifecycle.ViewModel
import com.example.runfit.repositories.MainRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    val mainRepository : MainRepository) : ViewModel() {
        
}


/*
* This is our StaticsViewModel class which takes data from MainRepository and data reaches
  our fragments and Activity
* */