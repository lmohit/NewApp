package com.example.myapplication.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_DATABASE
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_USER_DATABASE
import com.example.myapplication.user.User

class RegisterRepo {

    private val _isUserDetailsEntered = MutableLiveData<Boolean>()
    val isUserDetailsEntered : LiveData<Boolean>
        get() = _isUserDetailsEntered

    fun insertUserDetails(userDetails: User) {
        Log.d(TAG, "insertUserDetails")
        val dbReference = FIREBASE_DATABASE.collection(FIREBASE_USER_DATABASE)
        dbReference.document(userDetails.emailId).set(userDetails)
            .addOnCompleteListener {
                _isUserDetailsEntered.postValue(true)
            }
    }

    companion object {
        private const val TAG = "RegisterRepo"
    }
}