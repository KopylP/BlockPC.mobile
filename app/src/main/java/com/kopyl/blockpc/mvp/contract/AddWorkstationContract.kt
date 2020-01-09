package com.kopyl.blockpc.mvp.contract

import com.kopyl.blockpc.models.WorkstationModel
import com.kopyl.blockpc.ui.addWorkstation.AddWorkstationActivity

class AddWorkstationContract {
    interface View: BaseContract.View {
        fun closeActivity(modelId: Long)
    }

    abstract  class  AddWorkstationPresenter: BaseContract.Presenter<AddWorkstationActivity>(){
        abstract fun addWorkstation(model: WorkstationModel)
    }
}