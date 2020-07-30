package com.example.myapplication.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.Constants.Companion.PASSWORD
import com.example.myapplication.Constants.Companion.SIGNED_IN
import com.example.myapplication.Constants.Companion.USERNAME
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.user.UserSelectionActivity

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
                    insertToSharedPreferences()
                    navigateToUserSelectionActivity()
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

    private fun setRegisterButtonListener() {
        binding.registerButton.setOnClickListener {
            registerViewModel.validateUserDetails(
                binding.name.text.toString(),
                binding.username.text.toString(),
                binding.password.text.toString(),
                binding.phoneNumber.text.toString()
            )
        }

    }

    private fun navigateToUserSelectionActivity() {
        // TODO : Have to use navigation component
        val intent = Intent(this, UserSelectionActivity::class.java)
        startActivity(intent)
    }

    private fun insertToSharedPreferences() {
        Log.d(TAG, "Record Inserted")
        val sharedPref =
            getSharedPreferences(getString(R.string.user_cred_pref), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(USERNAME, binding.username.toString())
        editor.putString(PASSWORD, binding.password.toString())
        editor.putBoolean(SIGNED_IN, true)
        editor.apply()
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}