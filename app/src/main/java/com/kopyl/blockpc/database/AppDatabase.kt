package com.kopyl.blockpc.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kopyl.blockpc.database.dao.WorkstationDao
import com.kopyl.blockpc.models.WorkstationModel

@Database(entities = arrayOf(WorkstationModel::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workstationDao(): WorkstationDao
}