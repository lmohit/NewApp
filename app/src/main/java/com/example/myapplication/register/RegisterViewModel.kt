package com.example.myapplication.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import java.util.regex.Pattern

class RegisterViewModel : ViewModel() {

    val _isUserDetailsValid = MutableLiveData<Boolean>()
    val isUserDetailsValid: LiveData<Boolean>
        get() = _isUserDetailsValid

    val _incorrectDetailsText = MutableLiveData<Int>()
    val incorrectDetailsText: LiveData<Int>
        get() = _incorrectDetailsText

    init {
        Log.d(TAG, "ViewModel Created")
    }

    private fun checkEmptyInformation(
        name: String,
        userName: String,
        password: String,
        phoneNumber: String) {
        if (name.isEmpty() || userName.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
            _isUserDetailsValid.value = false
            _incorrectDetailsText.value = R.string.missing_fields
        }
    }

    private fun checkValidPhoneNumber(phoneNumber: String) {
        if (phoneNumber.length != PHONE_NUMBER_LENGTH) {
            _isUserDetailsValid.value = false
            _incorrectDetailsText.value = R.string.incorrect_phone_number
        }
    }

    private fun checkValidPassword(password: String)  {
        val exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
        val pattern = Pattern.compile(exp)
        val matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            _isUserDetailsValid.value = false
            _incorrectDetailsText.value = R.string.improper_password
        }
    }

    fun validateUserDetails(name: String, userName: String, password: String, phoneNumber: String) {
        _isUserDetailsValid.value = null
        checkEmptyInformation(name, userName, password, phoneNumber)
        checkValidPhoneNumber(phoneNumber)
        checkValidPassword(password)
        if (_isUserDetailsValid.value == null) {
            _isUserDetailsValid.value = true
        }
    }

    companion object {
        private const val PHONE_NUMBER_LENGTH = 10
        private const val TAG = "RegisterViewModel"
    }
}