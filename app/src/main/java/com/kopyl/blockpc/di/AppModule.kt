package com.kopyl.blockpc.di

import android.content.Context
import androidx.room.Room
import com.kopyl.blockpc.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "block-pc-db")
            .allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    fun provideWorkstationDao(database: AppDatabase) = database.workstationDao()
}