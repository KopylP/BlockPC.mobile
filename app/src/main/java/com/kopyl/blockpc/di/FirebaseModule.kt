package com.kopyl.blockpc.di

import android.content.Context
import com.kopyl.blockpc.firebase.IFirebaseClient
import com.kopyl.blockpc.firebase.LockFirebaseClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideLockFirebaseClient(context: Context): IFirebaseClient = LockFirebaseClient(context)
}