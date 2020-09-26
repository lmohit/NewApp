package com.example.myapplication.user.customer.addvendor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Constants.Companion.PENDING_REQUESTS_DATABASE
import com.example.myapplication.Constants.Companion.REGISTERED_CUSTOMERS_DATABASE
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_DATABASE
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_USER_DATABASE
import com.example.myapplication.Utils
import com.example.myapplication.user.User

class AddVendorRepo  {
    private val _vendorList = MutableLiveData<List<User?>>()
    val vendorList: LiveData<List<User?>>
        get() = _vendorList

    private val _isVendorAdded = MutableLiveData<Boolean>()
    val isVendorAdded: LiveData<Boolean>
    get() = _isVendorAdded

    fun getPendingVendorsList() {
        Log.d(TAG, "getPendingVendorsList")
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(Utils.getUserEmail())
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val document = it.result
                    document?.let {
                        val user = it.toObject(User::class.java)
                        _vendorList.value = user?.pendingRequests
                    }
                }
            }
    }

    fun addVendor(phoneNumber: String) {
        Log.d(TAG, "addVendor")
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    result?. let { res ->
                        for (document in res) {
                            val user = document.toObject(User::class.java)
                            if (user.phoneNumber == phoneNumber) {
                                addCurrentUserToVendorsPendingRequest(user)
                            }
                        }
                    }
                }
            }
    }

    private fun addCurrentUserToVendorsPendingRequest(vendor: User) {
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(Utils.getUserEmail())
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val document = it.result
                    document?.let {
                        val user = it.toObject(User::class.java)
                        updateVendorDetails(user, vendor)
                    }
                }
            }
    }

    private fun updateVendorDetails(user: User?, vendor: User?) {
        val pendingRequestsList = vendor?.pendingRequests
        pendingRequestsList?.add(user)
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(vendor?.emailId.orEmpty())
            .update(PENDING_REQUESTS_DATABASE, pendingRequestsList)
            .addOnCompleteListener {
                _isVendorAdded.value = it.isSuccessful
            }
    }

    fun acceptRequest(vendor: User?) {
        Log.d(TAG, "acceptRequest : $vendor")
        removeVendorFromPendingVendorsList(vendor, true)
    }

    private fun removeVendorFromPendingVendorsList(vendor: User?, isRequestAccepted: Boolean) {
        val updatedVendorList = _vendorList.value
        (updatedVendorList as ArrayList).remove(vendor)
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(Utils.getUserEmail())
            .update(PENDING_REQUESTS_DATABASE, updatedVendorList)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _vendorList.value = updatedVendorList
                    if (isRequestAccepted) {
                        addCustomerToVendorsRegisteredList(vendor)
                    }
                }
            }
    }

    private fun addCustomerToVendorsRegisteredList(vendor: User?) {
        val registeredCustomers = vendor?.registeredCustomers
        // Getting current customer details
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(vendor?.emailId.orEmpty())
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val document = it.result
                    document?.let {
                        val user = it.toObject(User::class.java)
                        registeredCustomers?.add(user)
                        addCustomerToVendorRegisteredCustomerList(vendor, registeredCustomers)
                    }
                }
            }
    }

    private fun addCustomerToVendorRegisteredCustomerList(vendor: User?, registeredCustomers: ArrayList<User?>?) {
        // Adding current customer to registered customers list
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(vendor?.emailId.orEmpty())
            .update(REGISTERED_CUSTOMERS_DATABASE, registeredCustomers)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "addCustomerToVendorsRegisteredList : success")
                }
            }
    }

    fun rejectRequest(vendor: User?) {
        Log.d(TAG, "rejectRequest : $vendor")
        removeVendorFromPendingVendorsList(vendor, false)
    }

    companion object {
        private const val TAG = "AddVendorRepo"
    }
}