package com.social2023Network.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseManager private constructor() {

    companion object {
        private var INSTANCE: FirebaseManager? = null
        fun getInstance(): FirebaseManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FirebaseManager().also { INSTANCE = it }
            }
        }
    }

    private val storage: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun getDatabaseReference(path: String): DatabaseReference {
        return database.child(path)
    }

    fun getStorageReference(path: String): StorageReference {
        return storage.reference.child(path)
    }

    fun getFirebaseAuthentication(): FirebaseAuth {
        return firebaseAuth
    }
}