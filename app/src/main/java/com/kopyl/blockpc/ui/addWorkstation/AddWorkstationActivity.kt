package com.kopyl.blockpc.ui.addWorkstation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewAnimationUtils
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.addListener
import com.kopyl.blockpc.R
import com.kopyl.blockpc.di.App
import com.kopyl.blockpc.models.WorkstationModel
import com.kopyl.blockpc.mvp.contract.AddWorkstationContract
import com.kopyl.blockpc.mvp.contract.BaseContract
import kotlinx.android.synthetic.main.activity_add_workstation.*
import javax.inject.Inject

class AddWorkstationActivity : AppCompatActivity(), AddWorkstationContract.View {

    @Inject
    lateinit var addWorkstationPresenter: AddWorkstationPresenter

    private var workstationCode: String? = null

    override fun closeActivity(model: WorkstationModel) {
        intent = Intent()
        intent.putExtra(EXTRA_WORKSTATION, model)
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_workstation)
        App.appComponent.inject(this)
        addWorkstationPresenter.attach(this)
        workstationCode = intent.getStringExtra(EXTRA_WORKSTATION_CODE)
        tv_workstation_code.text = workstationCode
        initListeners()
    }

    private fun initListeners() {
        btn_add.setOnClickListener {
            when {
                et_workstation_name.text.isNullOrEmpty() -> et_workstation_name.error =
                    "This field can not be empty!"
                else -> {
                    val model =
                        WorkstationModel(et_workstation_name.text.toString(), workstationCode!!)
                    val cx = btn_add.width / 2
                    val cy = btn_add.height / 2
                    val radius: Float = btn_add.width.toFloat()
                    val anim = ViewAnimationUtils
                        .createCircularReveal(btn_add, cx, cy, radius, 0f)
                    anim.addListener(onEnd = {
                        btn_add.visibility = View.INVISIBLE
                        addWorkstationPresenter.addWorkstation(model)
                    })
                    anim.start()
                }

            }

        }
    }

    companion object {

        private const val EXTRA_WORKSTATION =
            "package com.kopyl.blockpc.ui.addWorkstation extra workstation"
        private const val EXTRA_WORKSTATION_CODE =
            "package com.kopyl.blockpc.ui.addWorkstation extra workstation code"

        fun newIntent(context: Context, workstationCode: String): Intent {
            val intent = Intent(context, AddWorkstationActivity::class.java)
            intent.putExtra(EXTRA_WORKSTATION_CODE, workstationCode)
            return intent
        }

        fun getWorkstation(intent: Intent): WorkstationModel? =
            intent.getParcelableExtra(EXTRA_WORKSTATION)
    }

}
