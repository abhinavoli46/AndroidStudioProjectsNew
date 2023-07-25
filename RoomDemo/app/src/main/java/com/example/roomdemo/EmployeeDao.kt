package com.example.roomdemo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Upsert
    suspend fun upsert(employeeEntity: EmployeeEntity)

    @Delete
    suspend fun delete(employeeEntity: EmployeeEntity)

    //To fetch all the data
    @Query("SELECT * FROM `employee-table`")
    fun fetchAllEmployees() : Flow<List<EmployeeEntity>>            //Flow from coroutines

    //To fetch data according to a particular id.
    @Query("SELECT * FROM `employee-table` where id =:id")
    fun fetchEmployeeById(id:Int):Flow<EmployeeEntity>
}