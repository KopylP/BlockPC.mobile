package com.kopyl.blockpc.firebase.interfaces

import com.google.android.gms.tasks.Task
import com.kopyl.blockpc.models.WorkstationModel

interface ILockWorkstationHelper {
    fun lockWorkstation(workstationModel: WorkstationModel, lockWorkstationResponse: ILockWorkstationResponse): Task<Void>

    interface ILockWorkstationResponse {
        fun lockWorkstationSuccessResponse()
        fun lockWorkstationFailureResponse()
        fun lockWorkstationFailure()
        fun lockWorkstationCancel()
    }
}