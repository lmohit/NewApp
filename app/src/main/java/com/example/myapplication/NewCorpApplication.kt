package com.example.myapplication

import android.app.Application
import com.example.myapplication.login.LoginState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NewCorpApplication : Application() {

    companion object {
        private const val FIREBASE_USER_DATABASE = "Users"
        private val FIREBASE_DATABASE = FirebaseDatabase.getInstance()
        val FIREBASE_AUTH: FirebaseAuth = FirebaseAuth.getInstance()
        var LOGIN_STATE = LoginState.LOGGED_OUT
        val FIREBASE_REFERENCE = FIREBASE_DATABASE.getReference(FIREBASE_USER_DATABASE)
    }
}