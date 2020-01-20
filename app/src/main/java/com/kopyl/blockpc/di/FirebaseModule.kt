package com.kopyl.blockpc.di

import android.content.Context
import com.kopyl.blockpc.firebase.IFirebaseClient
import com.kopyl.blockpc.firebase.LockFirebaseClient
import com.kopyl.blockpc.firebase.helpers.LockWorkstationHelper
import com.kopyl.blockpc.firebase.interfaces.ILockWorkstationHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideLockFirebaseClient(context: Context): IFirebaseClient = LockFirebaseClient(context)

    @Provides
    @Singleton
    fun provideILockFirebaseHelper(firebaseClient: IFirebaseClient): ILockWorkstationHelper = LockWorkstationHelper(firebaseClient)
}