package com.kopyl.blockpc.mvp.contract

import com.kopyl.blockpc.models.WorkstationModel
import com.kopyl.blockpc.ui.lockWorkstation.LockWorkstationFragment

class LockWorkstationContract {
    interface LockWorkstationView: BaseContract.View {
        fun showLockWorkstationResult(state: LockWorkstationFragment.ResponseState)
    }

    abstract class LockWorkstationPresenter: BaseContract.Presenter<LockWorkstationView>() {
        abstract fun lockWorkstation(workstationModel: WorkstationModel)
    }
}