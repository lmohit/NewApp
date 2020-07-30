package com.example.myapplication.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.User.UserSelectionActivity
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.register.RegisterActivity

class LoginActivity : FragmentActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this
        setObservers()
        setButtonClickListeners()
    }

    private fun setButtonClickListeners() {
        binding.loginButton.setOnClickListener {
            loginViewModel.validateCredentials(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setObservers() {
        loginViewModel.incorrectCredentialsText.observe(this, Observer { resId ->
            binding.validateCredentials.text = getString(resId)
        })

        loginViewModel.isCredentialsValid.observe(this, Observer {isValid ->
            if (isValid) {
                binding.validateCredentials.visibility = View.GONE
                navigateToUserSelectionActivity()
            } else {
                binding.validateCredentials.visibility = View.VISIBLE
            }
        })
    }

    private fun navigateToUserSelectionActivity() {
        val intent = Intent(this, UserSelectionActivity::class.java)
        startActivity(intent)
    }
}