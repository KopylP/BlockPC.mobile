package com.kopyl.blockpc.ui.lockWorkstation

import com.google.android.gms.tasks.Task
import com.kopyl.blockpc.di.App
import com.kopyl.blockpc.firebase.interfaces.ILockWorkstationHelper
import com.kopyl.blockpc.models.WorkstationModel
import com.kopyl.blockpc.mvp.contract.LockWorkstationContract
import javax.inject.Inject

class LockWorkstationPresenter : LockWorkstationContract.LockWorkstationPresenter(),
    ILockWorkstationHelper.ILockWorkstationResponse {

    @Inject
    lateinit var lockWorkstationHelper: ILockWorkstationHelper

    private var lockCall: Task<Void>? = null

    init {
        App.appComponent.inject(this)
    }

    override fun lockWorkstation(workstationModel: WorkstationModel) {
        lockCall = lockWorkstationHelper.lockWorkstation(workstationModel, this)
    }

    override fun lockWorkstationSuccessResponse() {
        view.showLockWorkstationResult(LockWorkstationFragment.ResponseState.SUCCESS)
    }

    override fun lockWorkstationFailureResponse() {
        view.showLockWorkstationResult(LockWorkstationFragment.ResponseState.RESPONSE_FAILURE)
    }

    override fun lockWorkstationFailure() {
        view.showLockWorkstationResult(LockWorkstationFragment.ResponseState.FAILURE)
    }

    override fun lockWorkstationCancel() {
        view.showLockWorkstationResult(LockWorkstationFragment.ResponseState.FAILURE)
    }

}