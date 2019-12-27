package com.kopyl.blockpc.di

import com.kopyl.blockpc.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MvpModule{
    @Provides
    @Singleton
    fun provideMainPresenter() = MainPresenter()
}