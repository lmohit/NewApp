package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.myapplication.Constants.Companion.LOGGED_IN
import com.example.myapplication.Constants.Companion.PHONE_NUMBER
import com.example.myapplication.Constants.Companion.SHARED_PREFERENCES
import com.example.myapplication.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class Utils {

    companion object {
        var phoneNumber: String = ""

        fun logoutUser(context: Context) {
            val prefs = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean(LOGGED_IN, false)
            editor.apply()
            FirebaseAuth.getInstance().signOut()
            navigateToLoginActivity(context)
        }

        fun getUserEmail(): String {
            return FirebaseAuth.getInstance().currentUser?.email.orEmpty()
        }

        fun navigateToLoginActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }

        fun getUserPhoneNumber(context: Context): String {
            val prefs = context.getSharedPreferences(
                SHARED_PREFERENCES,
                Context.MODE_PRIVATE
            )
            val phoneNumber = prefs.getString(PHONE_NUMBER, "")
            return phoneNumber.orEmpty()
        }

        fun displayToast(context: Context, msg: String) {
            Toast.makeText(
                context,
                msg,
                Toast.LENGTH_SHORT)
                .show()
        }

    }
}