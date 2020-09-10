package com.example.myapplication

import android.content.Context
import android.content.Intent
import com.example.myapplication.Constants.Companion.EMAIL
import com.example.myapplication.Constants.Companion.LOGGED_IN
import com.example.myapplication.Constants.Companion.SHARED_PREFERENCES
import com.example.myapplication.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class Utils {

    companion object {

        fun logoutUser(context: Context) {
            val prefs = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean(LOGGED_IN, false)
            editor.apply()
            FirebaseAuth.getInstance().signOut()
            navigateToLoginActivity(context)
        }

        fun getUserEmail(context: Context): String {
            val prefs = context.getSharedPreferences(
                Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
            val userEmail = prefs.getString(EMAIL, "")
            return userEmail.orEmpty()
        }

        fun navigateToLoginActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}