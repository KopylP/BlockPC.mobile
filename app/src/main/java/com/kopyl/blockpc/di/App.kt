package com.kopyl.blockpc.di

import android.app.Application
import com.google.firebase.FirebaseApp

class App: Application(){

    companion object {
        lateinit var  appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        initDagger()
    }

    private fun initDagger(){
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .mvpModule(MvpModule())
            .firebaseModule(FirebaseModule())
            .build()
    }
}