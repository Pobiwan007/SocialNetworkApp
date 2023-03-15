package com.social2023Network.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDB private constructor() {

    companion object {
        private var INSTANCE: FirebaseDB? = null

        fun getInstance(): FirebaseDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FirebaseDB().also { INSTANCE = it }
            }
        }
    }

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }

    fun getDatabaseReference(path: String): DatabaseReference {
        return database.child(path)
    }
}