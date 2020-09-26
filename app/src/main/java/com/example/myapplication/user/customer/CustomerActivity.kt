package com.example.myapplication.user.customer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Constants
import com.example.myapplication.NewCorpApplication
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_DATABASE
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_USER_DATABASE
import com.example.myapplication.R
import com.example.myapplication.Utils
import com.example.myapplication.databinding.ActivityListVendorsBinding
import com.example.myapplication.user.User
import com.example.myapplication.user.customer.addvendor.AddVendorActivity

class CustomerActivity : FragmentActivity(), VendorListAdapter.VendorSelectionListener {

    private lateinit var binding: ActivityListVendorsBinding
    private lateinit var customerViewModel: CustomerViewModel
    private lateinit var vendorListAdapter: VendorListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_vendors)
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel::class.java)
        binding.customerViewModel = customerViewModel
        binding.lifecycleOwner = this
        setAdapter()
        setListeners()
        fetchVendorsList()
    }

    private fun setAdapter() {
        vendorListAdapter = VendorListAdapter(listOf(), this)
        binding.vendorsList.layoutManager = LinearLayoutManager(this)
        binding.vendorsList
            .addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        binding.vendorsList.adapter = vendorListAdapter
    }

    private fun setListeners() {
        binding.addVendor.setOnClickListener {
            val addVendorIntent = Intent(this, AddVendorActivity::class.java)
            startActivity(addVendorIntent)
        }
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


    private fun fetchVendorsList() {
        val repo = CustomerRepo()
        repo.vendorsList.observe(this, Observer {
            Log.d(TAG, "Vendors List : $it")
            if (it.isEmpty()) {
                binding.vendors.text = resources.getString(R.string.no_vendors)
            } else {
                binding.vendors.text = resources.getString(R.string.vendors_list)
                vendorListAdapter.addVendors(it.requireNoNulls())
            }
        })
    }

    override fun onVendorSelected(user: User) {
        Log.d(TAG, "onVendorSelected")

    }

    companion object {
        private const val TAG = "CustomerActivity"
    }
}