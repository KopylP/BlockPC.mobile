package com.kopyl.blockpc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.integration.android.IntentIntegrator
import com.kopyl.blockpc.adapters.WorkstationAdapter
import com.kopyl.blockpc.di.App
import com.kopyl.blockpc.mvp.contract.main.MainContract
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var mainPresenter: MainPresenter

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
    }

}
