package com.kopyl.blockpc.mvp.contract.main

import androidx.recyclerview.widget.RecyclerView
import com.kopyl.blockpc.MainActivity
import com.kopyl.blockpc.adapters.WorkstationAdapter
import com.kopyl.blockpc.models.WorkstationModel
import com.kopyl.blockpc.mvp.contract.BaseContract

public class MainContract {
    interface View: BaseContract.View {
        fun showWorkstations(adapter: WorkstationAdapter)
    }

    abstract class MainPresenter: BaseContract.Presenter<MainActivity>() {
        abstract fun getWorkstations()
    }
}