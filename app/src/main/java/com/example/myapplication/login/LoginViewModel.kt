package com.example.myapplication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
/*
    val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password*/

    val _isCredentialsValid = MutableLiveData<Boolean>()
    val isCredentialsValid: LiveData<Boolean>
        get() = _isCredentialsValid

    init {
/*        _userName.value = ""
        _password.value = ""*/

        _isCredentialsValid.value = false
    }

    fun validateCredentials(userName: String, password: String) {
        if (userName.isEmpty() || password.isEmpty()) {

        }
    }
}