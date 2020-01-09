package com.kopyl.blockpc.ui.addWorkstation

import com.kopyl.blockpc.database.dao.WorkstationDao
import com.kopyl.blockpc.di.App
import com.kopyl.blockpc.firebase.IFirebaseClient
import com.kopyl.blockpc.models.WorkstationModel
import com.kopyl.blockpc.mvp.contract.AddWorkstationContract
import javax.inject.Inject

class AddWorkstationPresenter: AddWorkstationContract.AddWorkstationPresenter() {

    @Inject
    lateinit var workstationDao: WorkstationDao

    init {
        App.appComponent.inject(this)
    }

    override fun addWorkstation(model: WorkstationModel) {
        val id = workstationDao.insertWorkstation(model)
        view.closeActivity(id)
    }

}