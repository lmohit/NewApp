package com.example.myapplication.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.Constants.Companion.EMAIL
import com.example.myapplication.Constants.Companion.LOGGED_IN
import com.example.myapplication.Constants.Companion.SHARED_PREFERENCES
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_AUTH
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.register.RegisterActivity
import com.example.myapplication.user.UserSelectionActivity

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
            loginViewModel.isCredentialEntered(
                binding.email.text.toString(),
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

        loginViewModel.isCredentialsEntered.observe(this, Observer {isEntered ->
            if (isEntered) {
                validateCredentials();
            } else {
                binding.validateCredentials.visibility = View.VISIBLE
            }
        })
    }

    private fun validateCredentials() {
        FIREBASE_AUTH.signInWithEmailAndPassword(
            binding.email.text.toString(),
            binding.password.text.toString()
        ).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Log.d(TAG, "Login Successful")
                insertToSharedPreferences()
                navigateToUserSelectionActivity()
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Incorrect Credentials Entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun insertToSharedPreferences() {
        val prefs = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(LOGGED_IN, true)
        editor.putString(EMAIL, binding.email.text.toString())
        editor.apply()
    }

    private fun navigateToUserSelectionActivity() {
        val intent = Intent(this, UserSelectionActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}