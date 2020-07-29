package com.example.myapplication.login

import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding

class LoginActivity : FragmentActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this
        val sharedPref =
            getSharedPreferences(getString(R.string.user_cred_pref), Context.MODE_PRIVATE)
        sharedPref.getString(USERNAME, "")
        sharedPref.getString(PASSWORD, "")
    }

    companion object {
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
    }
}