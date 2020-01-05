package com.kopyl.blockpc.firebase

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kopyl.blockpc.models.LockModel

open class FirebaseClient(
    private val context: Context,
    private val basePath: String): IFirebaseClient {

    private var databaseReference: DatabaseReference
    init {
        FirebaseApp.initializeApp(context)
        databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference = childReference(basePath)
    }

    private fun childReference(childPath: String):DatabaseReference {
        val filePathTrimmed = childPath.trim('/')
        val childPaths = filePathTrimmed.split('/')
        var databaseRef: DatabaseReference = databaseReference
        for (child in childPaths){
            databaseRef = databaseRef.child(child)
        }
        return databaseRef
    }

    override fun post(childPath: String, lockModel: LockModel): Task<Void> {
        return childReference(childPath).setValue(lockModel)
    }

    override fun readOnce(childPath: String, valueEventListener: ValueEventListener){
        childReference(childPath).addListenerForSingleValueEvent(valueEventListener)
    }

    override fun readRealTime(childPath: String, valueEventListener: ValueEventListener) {
        childReference(childPath).addValueEventListener(valueEventListener)
    }
}