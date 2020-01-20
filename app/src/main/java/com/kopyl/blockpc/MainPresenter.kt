package com.kopyl.blockpc
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import com.kopyl.blockpc.adapters.SwipeToDeleteCallback
import com.kopyl.blockpc.adapters.WorkstationAdapter
import com.kopyl.blockpc.database.dao.WorkstationDao
import com.kopyl.blockpc.di.App
import com.kopyl.blockpc.firebase.IFirebaseClient
import com.kopyl.blockpc.interfaces.WorkstationItemInterface
import com.kopyl.blockpc.models.LockModel
import com.kopyl.blockpc.models.WorkstationModel
import com.kopyl.blockpc.mvp.contract.MainContract
import java.util.concurrent.locks.Lock
import javax.inject.Inject

class MainPresenter: MainContract.MainPresenter(), WorkstationItemInterface {

    @Inject
    lateinit var workstationDao: WorkstationDao


    private var itemTouchHelper: ItemTouchHelper? = null

    init {
        App.appComponent.inject(this)
    }

    override fun getWorkstations() {
        val adapter = WorkstationAdapter(view, workstationDao.getAllWorkstations().toMutableList(), this)
        itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        view.showWorkstations(adapter)
    }

    override fun getItemTouchHelper(): ItemTouchHelper? {
        return itemTouchHelper
    }



    override fun updateInformation(workstationModel: WorkstationModel) {

    }

    override fun lockPC(workstationModel: WorkstationModel) {
        view.openBottomSheet(workstationModel)
    }

    override fun deletePC(workstationModel: WorkstationModel) {
        workstationDao.deleteWorkstation(workstationModel)
    }


    override fun getWorkstationById(id: Long): WorkstationModel? {
        return workstationDao.getWorkstationById(id)
    }

}
