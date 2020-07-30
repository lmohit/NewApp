package com.example.myapplication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R

class LoginViewModel : ViewModel() {

    private val _incorrectCredentialsText = MutableLiveData<Int>()
    val incorrectCredentialsText: LiveData<Int>
        get() = _incorrectCredentialsText

    private val _isCredentialsValid = MutableLiveData<Boolean>()
    val isCredentialsValid: LiveData<Boolean>
        get() = _isCredentialsValid

    fun validateCredentials(userName: String, password: String) {
        if (userName.isEmpty() || password.isEmpty()) {
            _isCredentialsValid.value = false
            _incorrectCredentialsText.value = R.string.missing_fields
            return
        }

        //TODO Checking username and password in server
        _isCredentialsValid.value = true
    }
}