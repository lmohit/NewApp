package com.example.myapplication.user.vendor.addcustomer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Constants.Companion.PENDING_REQUESTS_DATABASE
import com.example.myapplication.Constants.Companion.REGISTERED_CUSTOMERS_DATABASE
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_DATABASE
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_USER_DATABASE
import com.example.myapplication.Utils
import com.example.myapplication.user.User

class AddCustomerRepo  {
    private val _customerList = MutableLiveData<List<User?>>()
    val customerList: LiveData<List<User?>>
        get() = _customerList

    private val _isCustomerAdded = MutableLiveData<Boolean>()
    val isCustomerAdded: LiveData<Boolean>
    get() = _isCustomerAdded

    fun getPendingCustomersList() {
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
                        _customerList.value = user?.pendingRequests
                    }
                }
            }
    }

    fun addCustomer(phoneNumber: String) {
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
                                addCurrentUserToCustomersPendingRequest(user)
                            }
                        }
                    }
                }
            }
    }

    private fun addCurrentUserToCustomersPendingRequest(customer: User) {
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(Utils.getUserEmail())
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val document = it.result
                    document?.let {
                        val user = it.toObject(User::class.java)
                        updateCustomerDetails(user, customer)
                    }
                }
            }
    }

    private fun updateCustomerDetails(user: User?, customer: User?) {
        val pendingRequestsList = customer?.pendingRequests
        pendingRequestsList?.add(user)
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(customer?.emailId.orEmpty())
            .update(PENDING_REQUESTS_DATABASE, pendingRequestsList)
            .addOnCompleteListener {
                _isCustomerAdded.value = it.isSuccessful
            }
    }

    fun acceptRequest(customer: User?) {
        Log.d(TAG, "acceptRequest : $customer")
        removeCustomerFromPendingCustomersList(customer, true)
    }

    private fun removeCustomerFromPendingCustomersList(customer: User?, isRequestAccepted: Boolean) {
        val updatedCustomerList = _customerList.value
        (updatedCustomerList as ArrayList).remove(customer)
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(Utils.getUserEmail())
            .update(PENDING_REQUESTS_DATABASE, updatedCustomerList)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _customerList.value = updatedCustomerList
                    if (isRequestAccepted) {
                        addVendorToCustomersRegisteredVendorsList(customer)
                    }
                }
            }
    }

    private fun addVendorToCustomersRegisteredVendorsList(customer: User?) {
        val registeredCustomers = customer?.registeredCustomers
        // Getting current customer details
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(customer?.emailId.orEmpty())
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val document = it.result
                    document?.let {
                        val user = it.toObject(User::class.java)
                        registeredCustomers?.add(user)
                        addVendorToCustomersRegisteredVendorsList(customer, registeredCustomers)
                    }
                }
            }
    }

    private fun addVendorToCustomersRegisteredVendorsList(customer: User?, registeredCustomers: ArrayList<User?>?) {
        // Adding current customer to registered customers list
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(customer?.emailId.orEmpty())
            .update(REGISTERED_CUSTOMERS_DATABASE, registeredCustomers)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "addCustomerToVendorsRegisteredList : success")
                }
            }
    }

    fun rejectRequest(customer: User?) {
        Log.d(TAG, "rejectRequest : $customer")
        removeCustomerFromPendingCustomersList(customer, false)
    }

    companion object {
        private const val TAG = "AddCustomerRepo"
    }
}