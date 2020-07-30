package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.myapplication.Constants.Companion.SIGNED_IN
import com.example.myapplication.Constants.Companion.USERNAME
import com.example.myapplication.User.UserSelectionActivity
import com.example.myapplication.login.LoginActivity
import com.example.myapplication.login.LoginState

class AppMainActivity : Activity() {

    private lateinit var loginIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        loginIntent = when (checkUserSignInStatus()) {
            LoginState.NEW_USER, LoginState.LOGGED_OUT -> {
                Intent(applicationContext, LoginActivity::class.java)
            }
            LoginState.LOGGED_IN -> {
                Intent(applicationContext, UserSelectionActivity::class.java)
            }
        }
        startActivity(loginIntent)
    }

    private fun checkUserSignInStatus(): LoginState {
        val sharedPref =
            getSharedPreferences(getString(R.string.user_cred_pref), Context.MODE_PRIVATE)
        val userName = sharedPref.getString(USERNAME, "")
        val signInState = sharedPref.getBoolean(SIGNED_IN, false)
        userName?.let {
            if (it.isEmpty()) return LoginState.NEW_USER
        }
        return when (signInState) {
            true -> LoginState.LOGGED_IN
            false -> LoginState.LOGGED_OUT
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}