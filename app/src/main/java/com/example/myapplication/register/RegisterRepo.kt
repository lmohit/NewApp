package com.example.myapplication.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_REFERENCE
import com.example.myapplication.user.User

class RegisterRepo {

    private val _isUserDetailsEntered = MutableLiveData<Boolean>()
    val isUserDetailsEntered : LiveData<Boolean>
        get() = _isUserDetailsEntered

    fun insertUserDetails(userDetails: User) {
        Log.d(TAG, "insertUserDetails")
        FIREBASE_REFERENCE.setValue(userDetails)
    }

    companion object {
        private const val TAG = "RegisterRepo"
    }
}