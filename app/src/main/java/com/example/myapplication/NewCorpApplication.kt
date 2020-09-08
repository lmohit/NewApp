package com.example.myapplication

import android.app.Application
import com.example.myapplication.login.LoginState
import com.google.firebase.auth.FirebaseAuth

class NewCorpApplication : Application() {

    companion object {
        val FIREBASE_AUTH: FirebaseAuth = FirebaseAuth.getInstance()
        var LOGIN_STATE = LoginState.LOGGED_OUT
    }
}