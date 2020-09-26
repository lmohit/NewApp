package com.example.myapplication.user.customer.addvendor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.user.User

class AddVendorViewModel: ViewModel() {

    var addVendorRepo: AddVendorRepo
    var vendorList: LiveData<List<User?>>

    init {
        Log.d(TAG, "init")
        addVendorRepo = AddVendorRepo()
        vendorList = addVendorRepo.vendorList
        getPendingVendorsList()
    }

    private fun getPendingVendorsList() {
        addVendorRepo.getPendingVendorsList()
    }

    fun addVendor(phoneNumber: String) {
        addVendorRepo.addVendor(phoneNumber)
    }

    fun acceptRequest(vendor: User?) = addVendorRepo.acceptRequest(vendor)

    fun rejectRequest(vendor: User?) = addVendorRepo.rejectRequest(vendor)

    companion object {
        private const val TAG = "AddVendorViewModel"
    }

}