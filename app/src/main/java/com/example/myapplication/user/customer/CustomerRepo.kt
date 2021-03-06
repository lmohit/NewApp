package com.example.myapplication.user.customer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_DATABASE
import com.example.myapplication.NewCorpApplication.Companion.FIREBASE_USER_DATABASE
import com.example.myapplication.Utils
import com.example.myapplication.user.User

class CustomerRepo {
    private val _vendorsList = MutableLiveData<List<User?>>()
    val vendorsList: LiveData<List<User?>>
        get() = _vendorsList

    init {
        getVendorDetails()
    }

    private fun getVendorDetails() {
        Log.d(TAG, "getVendorDetails")
/*        // Testing Purpose
        FIREBASE_DATABASE
            .collection(FIREBASE_USER_DATABASE)
            .document(Utils.getUserEmail())
            .update("registeredVendors", arrayListOf(User("priyam9501", "1234567", "987876765", "", "sample@gmail.com")))

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
                        _vendorsList.value = user?.registeredVendors
                    }
                }
            }
    }

    companion object {
        private const val TAG = "CustomerRepo"
    }

}
