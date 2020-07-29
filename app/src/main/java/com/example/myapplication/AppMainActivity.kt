package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.myapplication.Constants.Companion.SIGNED_IN
import com.example.myapplication.login.LoginActivity
import com.example.myapplication.register.RegisterActivity

class AppMainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")
        val signedIn = checkUserSignInStatus()
        if (signedIn) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkUserSignInStatus(): Boolean {
        val sharedPref =
            getSharedPreferences(getString(R.string.user_cred_pref), Context.MODE_PRIVATE)
        return sharedPref.getBoolean(SIGNED_IN, false)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}