package com.example.myapplication.user.customer.addvendor

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
import com.example.myapplication.databinding.ActivityAddVendorBinding
import com.example.myapplication.user.User

class AddVendorActivity : AppCompatActivity(), PendingVendorsAdapter.RequestListener {

    private lateinit var binding: ActivityAddVendorBinding
    private lateinit var addVendorViewModel: AddVendorViewModel
    private lateinit var pendingVendorsAdapter: PendingVendorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_vendor)
        addVendorViewModel = ViewModelProviders.of(this).get(AddVendorViewModel::class.java)
        binding.addVendorViewModel = addVendorViewModel
        binding.lifecycleOwner = this
        setAdapter()
        initObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.registerButton.setOnClickListener {
            addVendorViewModel.addVendor(binding.vendorPhone.text.toString())
            binding.vendorName.text.clear()
            binding.vendorPhone.text.clear()
            binding.vendorItem.text.clear()
        }
    }

    private fun initObservers() {
        addVendorViewModel.vendorList.observe(this, Observer {
            Log.d(TAG, "initObservers : $it")
            if (it.isEmpty()) {
                binding.pendingVendorsList.visibility = View.GONE
                binding.vendorsText.text = resources.getString(R.string.no_vendors_requests_pending)
            } else {
                pendingVendorsAdapter.addPendingVendors(it)
            }
        })

        addVendorViewModel.addVendorRepo.isVendorAdded.observe(this, Observer {
            if (it) {
                Utils.displayToast(this, getString(R.string.vendor_added_successfully))
            } else {
                Utils.displayToast(this, getString(R.string.error))
            }
        })
    }

    private fun setAdapter() {
        pendingVendorsAdapter = PendingVendorsAdapter(listOf(), this)
        binding.pendingVendorsList.layoutManager = LinearLayoutManager(this)
        binding.pendingVendorsList.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
        binding.pendingVendorsList.adapter = pendingVendorsAdapter
    }

    override fun onAcceptedRequest(vendor: User?) {
        addVendorViewModel.acceptRequest(vendor)
    }

    override fun onRejectRequest(vendor: User?) {
        addVendorViewModel.rejectRequest(vendor)
    }

    companion object {
        private const val TAG = "AddVendorActivity"
    }
}