package com.example.myapplication.user.customer

import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.user.User

class CustomerViewModel : ViewModel() {

    private val _vendorsList = MutableLiveData<List<User?>>()
    val vendorsList: LiveData<List<User?>>
        get() = _vendorsList

    private var customerRepo: CustomerRepo

    init {
        Log.d(TAG, "CustomerViewModel created")
        customerRepo = CustomerRepo()
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