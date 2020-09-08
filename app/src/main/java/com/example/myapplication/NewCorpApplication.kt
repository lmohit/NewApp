package com.example.myapplication

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

class NewCorpApplication : Application() {

    companion object {
        val FIREBASE_AUTH: FirebaseAuth = FirebaseAuth.getInstance()
    }
}