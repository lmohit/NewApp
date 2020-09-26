package com.example.myapplication.user.vendor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Constants.Companion.REGISTERED_CUSTOMERS_DATABASE
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_DATABASE
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_USER_DATABASE
import com.example.myapplication.Utils
import com.example.myapplication.user.User

class VendorRepo {
    private val _customersList = MutableLiveData<List<User?>>()
    val customersList: LiveData<List<User?>>
        get() = _customersList

    init {
        Log.d(TAG, "Vendor Repo")
        getCustomerDetails()
    }

    private fun getCustomerDetails() {
        Log.d(TAG, "getCustomerDetails")
/*        // Testing Purpose
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(Utils.getUserEmail())
            .update(REGISTERED_CUSTOMERS_DATABASE, arrayListOf(User("priyam9501", "1234567", "987876765", "", "sample@gmail.com")))

        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(Utils.getUserEmail())
            .update("pendingRequests", arrayListOf(User("priyam9501", "1234567", "987876765", "", "sample@gmail.com")))
        // Testing Purpose*/
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(Utils.getUserEmail())
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val document = it.result
                    document?.let {
                        val user = it.toObject(User::class.java)
                        _customersList.value = user?.registeredCustomers
                    }
                }
            }
    }

    companion object {
        private const val TAG = "CustomerRepo"
    }

}
