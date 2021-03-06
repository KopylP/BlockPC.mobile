package com.kopyl.blockpc.di

import com.kopyl.blockpc.MainPresenter
import com.kopyl.blockpc.ui.addWorkstation.AddWorkstationPresenter
import com.kopyl.blockpc.ui.lockWorkstation.LockWorkstationPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MvpModule{
    @Provides
    @Singleton
    fun provideMainPresenter() = MainPresenter()

    @Provides
    @Singleton
    fun provideAddWorkstationPresenter() = AddWorkstationPresenter()

    @Provides
    @Singleton
    fun provideLockWorkstationPresenter() = LockWorkstationPresenter()
}