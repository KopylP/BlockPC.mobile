package com.kopyl.blockpc.firebase.helpers

import com.google.android.gms.tasks.Task
import com.kopyl.blockpc.firebase.IFirebaseClient
import com.kopyl.blockpc.firebase.interfaces.ILockWorkstationHelper
import com.kopyl.blockpc.models.LockModel
import com.kopyl.blockpc.models.WorkstationModel
import javax.inject.Inject

class LockWorkstationHelper( private val firebaseClient: IFirebaseClient): ILockWorkstationHelper {
    override fun lockWorkstation(workstationModel: WorkstationModel,lockWorkstationResponse: ILockWorkstationHelper.ILockWorkstationResponse): Task<Void> {
        val result: Task<Void> = firebaseClient.post(workstationModel.code + "/lock-model", LockModel(true))
        result.addOnSuccessListener {
            lockWorkstationResponse.lockWorkstationSuccessResponse()
        }

        result.addOnFailureListener {
            lockWorkstationResponse.lockWorkstationFailure()
        }

        result.addOnCanceledListener {
            lockWorkstationResponse.lockWorkstationCancel()
        }
        return result
    }
}