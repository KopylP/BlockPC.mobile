package com.kopyl.blockpc.interfaces

import com.kopyl.blockpc.models.WorkstationModel

interface WorkstationItemInterface {
    fun updateInformation(workstationModel: WorkstationModel)
    fun lockPC(workstationModel: WorkstationModel)
    fun deletePC(workstationModel: WorkstationModel)
}