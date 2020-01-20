package com.kopyl.blockpc

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import com.kopyl.blockpc.adapters.WorkstationAdapter
import com.kopyl.blockpc.di.App
import com.kopyl.blockpc.mvp.contract.MainContract
import com.kopyl.blockpc.ui.addWorkstation.AddWorkstationActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kopyl.blockpc.extensions.ownInitScan
import com.kopyl.blockpc.models.WorkstationModel
import com.kopyl.blockpc.ui.lockWorkstation.LockWorkstationFragment
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_add_workstation.*
import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy


class MainActivity : AppCompatActivity(), MainContract.View, LockWorkstationFragment.ILockWorkstationResult {

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
                intent = AddWorkstationActivity.newIntent(this, BuildConfig.CODE)
                startActivityForResult(intent, REQUEST_CODE_ACTIVITY_ADD_WORKSTATION)
            } else {
                //IntentIntegrator(this).initiateScan()
                val integrator = IntentIntegrator(this)
                    .ownInitScan()
            }
        }
    }

    override fun showWorkstations(adapter: WorkstationAdapter) {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        workstationAdapter = adapter
        mainPresenter.getItemTouchHelper()?.attachToRecyclerView(recycler_view)
    }

    override fun showSnackbar(message: String) {
        Snackbar.make(btn_add, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun openBottomSheet(workstationModel: WorkstationModel) {
        if(supportFragmentManager.findFragmentByTag(LockWorkstationFragment.TAG) == null) {
            val lockWorkstationFragment = LockWorkstationFragment.newInstance(workstationModel)
            lockWorkstationFragment.show(supportFragmentManager, LockWorkstationFragment.TAG)
        }
    }


    companion object {
        private const val REQUEST_CODE_ACTIVITY_ADD_WORKSTATION = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(result != null){
            if(result.contents != null){
                val qrCodeArray = result.contents.split("/")
                if(qrCodeArray.size != 2 || qrCodeArray[0] != getString(R.string.validation_qr_code)){
                    showSnackbar(getString(R.string.msg_qr_code_is_incorrect))
                    return
                }
                intent = AddWorkstationActivity.newIntent(this, qrCodeArray[1])
                startActivityForResult(intent, REQUEST_CODE_ACTIVITY_ADD_WORKSTATION)
                return
            }
        }
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CODE_ACTIVITY_ADD_WORKSTATION -> {
                    val modelId = AddWorkstationActivity.getWorkstationId(data!!)
                    val workstationModel = mainPresenter.getWorkstationById(modelId)
                    if (workstationModel != null) workstationAdapter.addItem(workstationModel)
                }
            }
        }
    }

    override fun onLockWorkstationResult(
        workstationModel: WorkstationModel,
        responseState: LockWorkstationFragment.ResponseState
    ) {
        val message = when(responseState){
            LockWorkstationFragment.ResponseState.SUCCESS -> "Workstation was blocked successfully"
            else -> "Workstation was not blocked"
        }
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
    }

}
