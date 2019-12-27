package com.kopyl.blockpc.di

import com.kopyl.blockpc.MainActivity
import com.kopyl.blockpc.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, MvpModule::class))
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)

}