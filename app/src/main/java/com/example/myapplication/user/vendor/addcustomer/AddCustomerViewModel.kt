package com.example.myapplication.user.vendor.addcustomer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.user.User

class AddCustomerViewModel: ViewModel() {

    var addCustomerRepo: AddCustomerRepo
    var customerList: LiveData<List<User?>>

    init {
        Log.d(TAG, "init")
        addCustomerRepo = AddCustomerRepo()
        customerList = addCustomerRepo.customerList
        getPendingVendorsList()
    }

    private fun getPendingVendorsList() {
        addCustomerRepo.getPendingCustomersList()
    }

    fun addVendor(phoneNumber: String) {
        addCustomerRepo.addCustomer(phoneNumber)
    }

    fun acceptRequest(vendor: User?) = addCustomerRepo.acceptRequest(vendor)

    fun rejectRequest(vendor: User?) = addCustomerRepo.rejectRequest(vendor)

    companion object {
        private const val TAG = "AddCustomerViewModel"
    }

}