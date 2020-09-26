package com.example.myapplication.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.myapplication.R
import com.example.myapplication.Utils
import com.example.myapplication.user.customer.CustomerActivity
import com.example.myapplication.user.vendor.VendorActivity


class UserSelectionActivity : AppCompatActivity() {

    private lateinit var customerButton: CardView
    private lateinit var vendorButton: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_user_selection)
        customerButton = findViewById(R.id.customerButton)
        vendorButton = findViewById(R.id.vendorButton)
        setClickListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu")
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.logout == item.itemId) {
            Utils.logoutUser(this)
            return true
        }
        return super.onOptionsItemSelected(item)
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

    companion object {
        private const val TAG = "UserSelectionActivity"
    }
}