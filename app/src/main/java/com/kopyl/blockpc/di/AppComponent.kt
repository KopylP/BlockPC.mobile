package com.kopyl.blockpc.di

import com.kopyl.blockpc.MainActivity
import com.kopyl.blockpc.MainPresenter
import com.kopyl.blockpc.ui.addWorkstation.AddWorkstationActivity
import com.kopyl.blockpc.ui.addWorkstation.AddWorkstationPresenter
import com.kopyl.blockpc.ui.lockWorkstation.LockWorkstationFragment
import com.kopyl.blockpc.ui.lockWorkstation.LockWorkstationPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, MvpModule::class, FirebaseModule::class))
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(addWorkstationActivity: AddWorkstationActivity)
    fun inject(addWorkstationPresenter: AddWorkstationPresenter)
    fun inject(lockWorkstationPresenter: LockWorkstationPresenter)
    fun inject(lockWorkstationFragment: LockWorkstationFragment)

}