package com.example.myapplication

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NewCorpApplication : Application() {

    companion object {
        const val FIREBASE_USER_DATABASE = "Users"
        val FIREBASE_DATABASE = FirebaseDatabase.getInstance()
        val FIREBASE_AUTH: FirebaseAuth = FirebaseAuth.getInstance()
    }
}