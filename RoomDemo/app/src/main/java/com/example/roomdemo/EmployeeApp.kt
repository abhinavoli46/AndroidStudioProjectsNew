package com.example.roomdemo

import android.app.Application


//Gets database reference and in android manifest we have to include the android:name=".EmployeeApp" under application tab so that all activities can use it.
class EmployeeApp:Application() {
    val db by lazy {
        EmployeeDatabase.getInstance(this)
    }
}