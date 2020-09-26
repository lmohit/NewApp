package com.example.myapplication.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_AUTH
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.login.LoginActivity

class RegisterActivity : FragmentActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        binding.registerViewModel = registerViewModel
        binding.lifecycleOwner = this
        setRegisterButtonListener()
        observeUserInfoEntry()
    }

    private fun observeUserInfoEntry() {
        registerViewModel.isUserDetailsValid.observe(this, Observer {
            isValid -> isValid?.let {
                if (isValid) {
                    binding.validateCredentials.visibility = View.GONE
                    insertInFirebase()
                } else {
                    binding.validateCredentials.visibility = View.VISIBLE
                }
            }
        })

        registerViewModel.incorrectDetailsText.observe(this, Observer { incorrectId ->
            binding.validateCredentials.text = getString(
                incorrectId ?: R.string.improper_credentials
            )
        })
    }

    private fun insertInFirebase() {
        FIREBASE_AUTH.createUserWithEmailAndPassword(
            binding.email.text.toString(),
            binding.password.text.toString()
        ).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                navigateToLoginActivity()
            } else {
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setRegisterButtonListener() {
        binding.registerButton.setOnClickListener {
            registerViewModel.validateUserDetails(
                binding.email.text.toString(),
                binding.address.toString(),
                binding.phoneNumber.text.toString(),
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}