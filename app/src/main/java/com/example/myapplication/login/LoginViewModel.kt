package com.example.myapplication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R

class LoginViewModel : ViewModel() {

    private val _incorrectCredentialsText = MutableLiveData<Int>()
    val incorrectCredentialsText: LiveData<Int>
        get() = _incorrectCredentialsText

    private val _isCredentialsEntered = MutableLiveData<Boolean>()
    val isCredentialsEntered: LiveData<Boolean>
        get() = _isCredentialsEntered

    fun isCredentialEntered(userName: String, password: String) {
        if (userName.isEmpty() || password.isEmpty()) {
            _isCredentialsEntered.value = false
            _incorrectCredentialsText.value = R.string.missing_fields
            return
        }
        _isCredentialsEntered.value = true
    }
}