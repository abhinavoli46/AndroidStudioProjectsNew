package com.example.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class], version = 1)
abstract class EmployeeDatabase:RoomDatabase(){

    //Connect the database with Dao
    abstract fun employeeDao():EmployeeDao

    companion object {
        @Volatile
        private  var INSTANCE: EmployeeDatabase? = null

        fun getInstance(context : Context):EmployeeDatabase{
            //At a time multiple threads could ask for database instance so
            //We put that code inside synchronised block acting as critical section
            //which can be accessed by one thread at a time.
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeeDatabase::class.java,
                        "employee_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}