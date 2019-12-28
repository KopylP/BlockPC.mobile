package com.kopyl.blockpc.mvp.contract

import com.kopyl.blockpc.MainActivity
import com.kopyl.blockpc.adapters.WorkstationAdapter

class MainContract {
    interface View: BaseContract.View {
        fun showWorkstations(adapter: WorkstationAdapter)
    }

    abstract class MainPresenter: BaseContract.Presenter<MainActivity>() {
        abstract fun getWorkstations()
    }
}