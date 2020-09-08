package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.myapplication.Constants.Companion.SIGNED_IN
import com.example.myapplication.login.LoginActivity
import com.example.myapplication.login.LoginState
import com.example.myapplication.user.UserSelectionActivity

class AppMainActivity : Activity() {

    private lateinit var loginIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        loginIntent = when (checkUserSignInStatus()) {
            LoginState.LOGGED_OUT -> {
                Intent(applicationContext, LoginActivity::class.java)
            }
            LoginState.LOGGED_IN -> {
                Intent(applicationContext, UserSelectionActivity::class.java)
            }
        }
        startActivity(loginIntent)
    }

    private fun checkUserSignInStatus(): LoginState {
        val sharedPref = getSharedPreferences(getString(R.string.user_cred_pref), Context.MODE_PRIVATE)
        return when (sharedPref.getBoolean(SIGNED_IN, false)) {
            true -> LoginState.LOGGED_IN
            false -> LoginState.LOGGED_OUT
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}