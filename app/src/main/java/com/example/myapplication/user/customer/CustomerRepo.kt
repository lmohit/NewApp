package com.example.myapplication.user.customer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.NewCorpApplication
import com.example.myapplication.user.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class CustomerRepo : ValueEventListener {
    private val _vendorsList = MutableLiveData<List<User?>>()
    val vendorsList: LiveData<List<User?>>
        get() = _vendorsList

    init {
        getVendorDetails()
    }

    private fun getVendorDetails() {
        Log.d(TAG, "getVendorDetails")
        val dbReference = NewCorpApplication.FIREBASE_DATABASE.getReference(
            NewCorpApplication.FIREBASE_USER_DATABASE
        )
        dbReference.addValueEventListener(this)
    }

    companion object {
        private const val TAG = "CustomerRepo"
    }

    override fun onCancelled(error: DatabaseError) {
        Log.d(TAG, "onCancelled : $error")
    }

    override fun onDataChange(snapshots: DataSnapshot) {
        val vendors = ArrayList<User?>()
        for (dataSnapshot in snapshots.children) {
            vendors.add(dataSnapshot.getValue(User::class.java))
        }
        Log.d(TAG, "onDataChange : ${vendors.toList()}")
        _vendorsList.value = vendors.toList()
    }

}
