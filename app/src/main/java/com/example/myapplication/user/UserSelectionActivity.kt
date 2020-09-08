package com.example.myapplication.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityUserSelectionBinding

class UserSelectionActivity : FragmentActivity() {

    private lateinit var binding: ActivityUserSelectionBinding
    private lateinit var userSelectionViewModel: UserSelectionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_selection)
        userSelectionViewModel = ViewModelProviders.of(this)
            .get(UserSelectionViewModel::class.java)
        binding.userSelectionViewModel = userSelectionViewModel
        binding.lifecycleOwner = this
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.customerButton.setOnClickListener {
            val intent = Intent(applicationContext, CustomerActivity::class.java)
            startActivity(intent)
        }

        binding.vendorButton.setOnClickListener {
            val intent = Intent(applicationContext, VendorActivity::class.java)
            startActivity(intent)
        }
    }
}