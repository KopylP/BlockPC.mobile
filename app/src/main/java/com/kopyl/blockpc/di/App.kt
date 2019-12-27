package com.kopyl.blockpc.di

import android.app.Application

class App: Application(){

    companion object {
        lateinit var  appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger(){
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .mvpModule(MvpModule())
            .build()
    }
}