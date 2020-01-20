package com.kopyl.blockpc.ui.lockWorkstation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kopyl.blockpc.R
import com.kopyl.blockpc.di.App
import com.kopyl.blockpc.models.WorkstationModel
import com.kopyl.blockpc.mvp.contract.LockWorkstationContract
import com.kopyl.blockpc.utils.circularAnimation
import kotlinx.android.synthetic.main.fragment_lock_workstation.*
import javax.inject.Inject

class LockWorkstationFragment : BottomSheetDialogFragment(),
    LockWorkstationContract.LockWorkstationView {

    interface ILockWorkstationResult {
        fun onLockWorkstationResult(workstationModel: WorkstationModel, responseState: ResponseState)
    }

    enum class ResponseState {
        FAILURE,
        SUCCESS,
        RESPONSE_FAILURE
    }

    @Inject
    lateinit var lockWorkstationPresenter: LockWorkstationPresenter

    lateinit var workstationModel: WorkstationModel

    private var lockWorkstationResult: ILockWorkstationResult? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ILockWorkstationResult)
            lockWorkstationResult = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        lockWorkstationPresenter.attach(this)
        workstationModel = arguments?.getParcelable(KEY_WORKSTATION_MODEL)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lock_workstation, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_lock_workstation_name.text = workstationModel.name
        tv_lock_workstation_code.text = workstationModel.code
        initListeners()
    }

    private fun initListeners() {
        onBtnLockClick()
    }

    private fun onBtnLockClick() {
        btn_workstation_lock.setOnClickListener {
            btn_workstation_lock.text = ""
            val anim = circularAnimation(btn_workstation_lock, 24f)
            anim?.doOnEnd {
                pb_lock_workstation.visibility = View.VISIBLE
                btn_workstation_lock.visibility = View.INVISIBLE
                Handler().postDelayed({
                    lockWorkstationPresenter.lockWorkstation(workstationModel)
                }, 800)
            }
            anim?.start()
        }
    }

    override fun showLockWorkstationResult(state: ResponseState) {
        lockWorkstationResult?.onLockWorkstationResult(workstationModel, state)
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        lockWorkstationPresenter.detach()
    }

    companion object {

        val TAG = LockWorkstationFragment::class.simpleName
        private val KEY_WORKSTATION_MODEL = "$TAG key workstation model"


        fun newInstance(workstationModel: WorkstationModel): LockWorkstationFragment {
            val fragment = LockWorkstationFragment()
            val args = Bundle()
            args.putParcelable(KEY_WORKSTATION_MODEL, workstationModel)
            fragment.arguments = args
            return fragment
        }
    }
}