package com.example.myapplication.user.vendor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.user.User

class VendorViewModel : ViewModel() {

    private val _customersList = MutableLiveData<List<User?>>()
    val customersList: LiveData<List<User?>>
        get() = _customersList

    private var vendorRepo: VendorRepo

    init {
        Log.d(TAG, "VendorViewModel created")
        vendorRepo = VendorRepo()
        //fetchVendorsList()
    }

    // TODO : Implement intermediate live data
/*    fun fetchVendorsList() {
        Log.d(TAG, "fetchVendorsList")
        val liveData = MediatorLiveData<User>()
        liveData.addSource(customerRepo.vendorsList) {
            if (it != null) {
                Log.d(TAG, "vendor list received")
                _vendorsList.postValue(it)
                _vendorsList
            }
        }
    }*/

    companion object {
        private const val TAG = "CustomerViewModel"
    }
}