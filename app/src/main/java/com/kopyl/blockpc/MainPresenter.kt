package com.kopyl.blockpc
import com.kopyl.blockpc.adapters.WorkstationAdapter
import com.kopyl.blockpc.database.dao.WorkstationDao
import com.kopyl.blockpc.di.App
import com.kopyl.blockpc.mvp.contract.MainContract
import javax.inject.Inject

class MainPresenter: MainContract.MainPresenter() {

    @Inject
    lateinit var workstationDao: WorkstationDao

    init {
        App.appComponent.inject(this)
    }

    override fun getWorkstations() {
        val adapter = WorkstationAdapter(view, workstationDao.getAllWorkstations().toMutableList())
        view.showWorkstations(adapter)
    }
}
