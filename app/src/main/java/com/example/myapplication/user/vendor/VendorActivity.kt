package com.example.myapplication.user.vendor

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
import com.example.myapplication.R
import com.example.myapplication.Utils
import com.example.myapplication.databinding.ActivityListCustomersBinding
import com.example.myapplication.user.User
import com.example.myapplication.user.vendor.addcustomer.AddCustomerActivity

class VendorActivity : FragmentActivity(), CustomerListAdapter.CustomerSelectionListener {

    private lateinit var binding: ActivityListCustomersBinding
    private lateinit var vendorViewModel: VendorViewModel
    private lateinit var customerListAdapter: CustomerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_customers)
        vendorViewModel = ViewModelProviders.of(this).get(VendorViewModel::class.java)
        binding.vendorViewModel = vendorViewModel
        binding.lifecycleOwner = this
        setAdapter()
        setListeners()
        fetchCustomersList()
    }

    private fun setAdapter() {
        customerListAdapter = CustomerListAdapter(listOf(), this)
        binding.customersList.layoutManager = LinearLayoutManager(this)
        binding.customersList
            .addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        binding.customersList.adapter = customerListAdapter
    }

    private fun setListeners() {
        binding.addCustomer.setOnClickListener {
            val addCustomerIntent = Intent(this, AddCustomerActivity::class.java)
            startActivity(addCustomerIntent)
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


    private fun fetchCustomersList() {
        val repo = VendorRepo()
        repo.customersList.observe(this, Observer {
            Log.d(TAG, "Vendors List : $it")
            if (it.isEmpty()) {
                binding.customers.text = resources.getString(R.string.no_customers)
            } else {
                binding.customers.text = resources.getString(R.string.vendors_list)
                customerListAdapter.addCustomers(it.requireNoNulls())
            }
        })
    }

    override fun onVendorSelected(user: User) {
        Log.d(TAG, "onVendorSelected")
    }

    companion object {
        private const val TAG = "VendorActivity"
    }
}