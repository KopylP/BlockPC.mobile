package com.kopyl.blockpc.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.kopyl.blockpc.models.WorkstationModel

@Dao
interface WorkstationDao {
    @Query("select * from workstation")
    fun getAllWorkstations(): List<WorkstationModel>

    @Query("select * from workstation where id = :id")
    fun getWorkstationById(id: Long): WorkstationModel

    @Insert(onConflict = REPLACE)
    fun insertWorkstation(workstation: WorkstationModel)

    @Update(onConflict = REPLACE)
    fun updateWorkstation(workstation: WorkstationModel)

    @Delete
    fun deleteWorkstation(workstation: WorkstationModel)
}