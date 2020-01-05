package com.kopyl.blockpc.mvp.contract

import com.kopyl.blockpc.MainActivity
import com.kopyl.blockpc.adapters.WorkstationAdapter
import com.kopyl.blockpc.models.WorkstationModel

class MainContract {
    interface View: BaseContract.View {
        fun showWorkstations(adapter: WorkstationAdapter)
        fun showSnackbar(message: String)
        fun openBottomSheet(workstationModel: WorkstationModel)
        fun showSuccessfulBottomSheet()
        fun showFailureBotomSheet()
    }

    abstract class MainPresenter: BaseContract.Presenter<MainActivity>() {
        abstract fun getWorkstations()
        abstract fun lockWorktation(workstationModel: WorkstationModel)
    }
}