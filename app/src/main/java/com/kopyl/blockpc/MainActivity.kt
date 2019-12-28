package com.kopyl.blockpc

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.integration.android.IntentIntegrator
import com.kopyl.blockpc.adapters.WorkstationAdapter
import com.kopyl.blockpc.di.App
import com.kopyl.blockpc.mvp.contract.MainContract
import com.kopyl.blockpc.ui.addWorkstation.AddWorkstationActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var mainPresenter: MainPresenter

    lateinit var workstationAdapter: WorkstationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)
        mainPresenter.attach(this)
        mainPresenter.getWorkstations()
        supportActionBar?.title = getString(R.string.title_your_workstations)
        initListeners()
    }

    private fun initListeners(){
        this.floating_action_button.setOnClickListener {
            if(BuildConfig.FLAVOR == "noneQr"){
                intent = AddWorkstationActivity.newIntent(this, "first-pc")
                startActivityForResult(intent, REQUEST_CODE_ACTIVITY_ADD_WORKSTATION)
            } else {
                IntentIntegrator(this).initiateScan()
            }
        }
    }

    override fun showWorkstations(adapter: WorkstationAdapter) {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        workstationAdapter = adapter
    }

    companion object {
        private const val REQUEST_CODE_ACTIVITY_ADD_WORKSTATION = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CODE_ACTIVITY_ADD_WORKSTATION -> {
                    //workstationAdapter.addItem(AddWorkstationActivity.getWorkstation(data!!)!!)
                }
            }
        }
    }

}
