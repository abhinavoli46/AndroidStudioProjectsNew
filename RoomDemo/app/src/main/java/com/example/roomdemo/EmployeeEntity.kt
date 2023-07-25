package com.example.roomdemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee-table")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var name : String = "",           //By default the column name will be the name given to variable
    @ColumnInfo(name = "email-id")   // It means internally email variable have column name as email-id
    var email : String = ""
)
