package com.kopyl.blockpc
import android.util.Log
import com.kopyl.blockpc.adapters.WorkstationAdapter
import com.kopyl.blockpc.database.dao.WorkstationDao
import com.kopyl.blockpc.di.App
import com.kopyl.blockpc.firebase.IFirebaseClient
import com.kopyl.blockpc.interfaces.WorkstationItemInterface
import com.kopyl.blockpc.models.LockModel
import com.kopyl.blockpc.models.WorkstationModel
import com.kopyl.blockpc.mvp.contract.MainContract
import javax.inject.Inject

class MainPresenter: MainContract.MainPresenter(), WorkstationItemInterface {

    @Inject
    lateinit var workstationDao: WorkstationDao

    @Inject
    lateinit var lockFirebaseClient: IFirebaseClient

    init {
        App.appComponent.inject(this)
    }

    override fun getWorkstations() {
        val adapter = WorkstationAdapter(view, workstationDao.getAllWorkstations().toMutableList(), this)
        view.showWorkstations(adapter)
    }

    override fun updateInformation(workstationModel: WorkstationModel) {

    }

    override fun lockPC(workstationModel: WorkstationModel) {
        view.openBottomSheet(workstationModel)
    }

    override fun lockWorktation(workstationModel: WorkstationModel) {
        lockFirebaseClient.post(workstationModel.code + "/lock-model", LockModel(true))
            .addOnCompleteListener {
                when {
                    it.isSuccessful -> view.showSuccessfulBottomSheet()
                    else -> view.showFailureBotomSheet()
                }
            }
    }
}
