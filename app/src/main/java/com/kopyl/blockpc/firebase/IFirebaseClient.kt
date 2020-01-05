package com.kopyl.blockpc.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.database.ValueEventListener
import com.kopyl.blockpc.models.LockModel

interface IFirebaseClient {
    fun post(childPath: String, lockModel: LockModel): Task<Void>
    fun readOnce(childPath: String, valueEventListener: ValueEventListener)
    fun readRealTime(childPath: String, valueEventListener: ValueEventListener)
}