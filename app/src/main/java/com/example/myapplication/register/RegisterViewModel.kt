package com.example.myapplication.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.example.myapplication.user.User
import java.util.regex.Pattern

class RegisterViewModel : ViewModel() {

    private var registerRepo: RegisterRepo
    val _isUserDetailsValid = MutableLiveData<Boolean>()
    val isUserDetailsValid: LiveData<Boolean>
        get() = _isUserDetailsValid

    val _incorrectDetailsText = MutableLiveData<Int>()
    val incorrectDetailsText: LiveData<Int>
        get() = _incorrectDetailsText

    init {
        Log.d(TAG, "ViewModel Created")
        registerRepo = RegisterRepo()
        initObservers()
    }

    private fun initObservers() {
        Transformations.switchMap(registerRepo.isUserDetailsEntered) {
            _isUserDetailsValid.value = it
            _isUserDetailsValid
        }
    }

    private fun checkEmptyInformation(
        name: String,
        userName: String,
        password: String,
        phoneNumber: String
    ) {
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

    private fun checkValidPassword(password: String) {
        val exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
        val pattern = Pattern.compile(exp)
        val matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            _isUserDetailsValid.value = false
            _incorrectDetailsText.value = R.string.improper_password
        }
    }

    fun validateUserDetails(
        email: String,
        address: String,
        phoneNumber: String,
        userName: String,
        password: String
    ) {
        _isUserDetailsValid.value = null
        checkEmptyInformation(email, userName, password, phoneNumber)
        checkValidPhoneNumber(phoneNumber)
        checkValidPassword(password)
        if (_isUserDetailsValid.value == null) {
            registerRepo.insertUserDetails(getUserDetails(email, userName, password, phoneNumber))
            _isUserDetailsValid.value = true
        }
    }

    private fun getUserDetails(
        email: String,
        userName: String,
        password: String,
        phoneNumber: String
    ): User {
        return User(
            userName,
            password,
            phoneNumber,
            "",
            email
        )
    }

    companion object {
        private const val PHONE_NUMBER_LENGTH = 10
        private const val TAG = "RegisterViewModel"
    }
}