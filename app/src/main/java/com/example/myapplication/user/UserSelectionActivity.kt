package com.example.myapplication.user

import android.content.Intent
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R

class UserSelectionActivity : FragmentActivity() {

    private lateinit var userSelectionViewModel: UserSelectionViewModel
    private lateinit var customerButton: CardView
    private lateinit var vendorButton: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_user_selection)
        userSelectionViewModel = ViewModelProviders.of(this)
            .get(UserSelectionViewModel::class.java)
        customerButton = findViewById(R.id.customerButton)
        vendorButton = findViewById(R.id.vendorButton)
        setClickListeners()
    }

    private fun setClickListeners() {
        customerButton.setOnClickListener {
            val intent = Intent(applicationContext, CustomerActivity::class.java)
            startActivity(intent)
        }

        vendorButton.setOnClickListener {
            val intent = Intent(applicationContext, VendorActivity::class.java)
            startActivity(intent)
        }
    }
}