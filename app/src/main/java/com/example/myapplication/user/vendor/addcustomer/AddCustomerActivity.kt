package com.example.myapplication.user.vendor.addcustomer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.Utils
import com.example.myapplication.databinding.ActivityAddCustomerBinding
import com.example.myapplication.user.User

class AddCustomerActivity : AppCompatActivity(), PendingCustomersAdapter.RequestListener {

    private lateinit var binding: ActivityAddCustomerBinding
    private lateinit var addCustomerViewModel: AddCustomerViewModel
    private lateinit var pendingCustomersAdapter: PendingCustomersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_customer)
        addCustomerViewModel = ViewModelProviders.of(this).get(AddCustomerViewModel::class.java)
        binding.addCustomerViewModel = addCustomerViewModel
        binding.lifecycleOwner = this
        setAdapter()
        initObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.registerButton.setOnClickListener {
            addCustomerViewModel.addVendor(binding.customerPhone.text.toString())
            binding.customerName.text.clear()
            binding.customerPhone.text.clear()
            binding.customerItem.text.clear()
        }
    }

    private fun initObservers() {
        addCustomerViewModel.customerList.observe(this, Observer {
            Log.d(TAG, "initObservers : $it")
            if (it.isEmpty()) {
                binding.pendingCustomersList.visibility = View.GONE
                binding.customersText.text = resources.getString(R.string.no_vendors_requests_pending)
            } else {
                pendingCustomersAdapter.addPendingVendors(it)
            }
        })

        addCustomerViewModel.addCustomerRepo.isCustomerAdded.observe(this, Observer {
            if (it) {
                Utils.displayToast(this, getString(R.string.vendor_added_successfully))
            } else {
                Utils.displayToast(this, getString(R.string.error))
            }
        })
    }

    private fun setAdapter() {
        pendingCustomersAdapter = PendingCustomersAdapter(listOf(), this)
        binding.pendingCustomersList.layoutManager = LinearLayoutManager(this)
        binding.pendingCustomersList.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
        binding.pendingCustomersList.adapter = pendingCustomersAdapter
    }

    override fun onAcceptedRequest(vendor: User?) {
        addCustomerViewModel.acceptRequest(vendor)
    }

    override fun onRejectRequest(vendor: User?) {
        addCustomerViewModel.rejectRequest(vendor)
    }

    companion object {
        private const val TAG = "AddVendorActivity"
    }
}