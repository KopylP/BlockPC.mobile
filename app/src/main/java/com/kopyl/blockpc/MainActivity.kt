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
import com.kopyl.blockpc.utils.circularAnimation
import kotlinx.android.synthetic.main.activity_main.bottom_sheet
import kotlinx.android.synthetic.main.bottom_sheet_main.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_add_workstation.*




class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var mainPresenter: MainPresenter

    lateinit var workstationAdapter: WorkstationAdapter

    private var isBottomSheetOpen: Boolean = false

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun openBottomSheet(workstationModel: WorkstationModel) {
        isBottomSheetOpen = true
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        tv_lock_workstation_name.text = workstationModel.name
        tv_lock_workstation_code.text = workstationModel.code
        darken.visibility = View.VISIBLE
        btn_workstation_lock.setOnClickListener {
            btn_workstation_lock.text = ""
            val anim = circularAnimation(btn_workstation_lock, 24f)
            anim?.doOnEnd {
                pb_lock_workstation.visibility = View.VISIBLE
                btn_workstation_lock.visibility = View.INVISIBLE
                Handler().postDelayed({
                    mainPresenter.lockWorkstation(workstationModel)
                }, 800)
            }
            anim?.start()
        }
        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    closeBottomSheet()
                    bottomSheetBehavior.removeBottomSheetCallback(this)
                }
            }

        })

        darken.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            darken.visibility = View.GONE
        }
    }

    private fun closeBottomSheet(){
        isBottomSheetOpen = false
        darken.visibility = View.GONE
        btn_workstation_lock.visibility = View.VISIBLE
        pb_lock_workstation.visibility = View.GONE
        btn_workstation_lock.text = getString(R.string.btn_lock)
    }

    override fun showSuccessfulBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        closeBottomSheet()
        showSnackbar(getString(R.string.msg_workstation_was_blocked))
    }

    override fun showFailureBotomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        closeBottomSheet()
        showSnackbar(getString(R.string.msg_workstation_was_not_blocked))
    }

    override fun onBackPressed() {
        if(isBottomSheetOpen){
            val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            closeBottomSheet()
        } else {
            super.onBackPressed()
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
}
