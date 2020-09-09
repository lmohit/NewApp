package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.myapplication.Constants.Companion.LOGGED_IN
import com.example.myapplication.Constants.Companion.SHARED_PREFERENCES
import com.example.myapplication.login.LoginActivity
import com.example.myapplication.user.UserSelectionActivity

class AppMainActivity : Activity() {

    private lateinit var loginIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        loginIntent = if (isUserLoggedIn()) {
            Intent(applicationContext, UserSelectionActivity::class.java)
        } else {
            Intent(applicationContext, LoginActivity::class.java)
        }
        startActivity(loginIntent)
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPref = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(LOGGED_IN, false)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}