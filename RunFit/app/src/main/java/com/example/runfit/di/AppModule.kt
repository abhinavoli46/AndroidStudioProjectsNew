package com.example.runfit.di

import android.content.Context
import androidx.room.Room
import com.example.runfit.db.RunningDatabase
import com.example.runfit.other.Constants.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRunningDataBase(@ApplicationContext app : Context) = Room.databaseBuilder(
        app, RunningDatabase::class.java,RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db : RunningDatabase) = db.getRunDao()


}