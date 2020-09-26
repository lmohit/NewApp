package com.example.myapplication.user

/**
 * User Info
 */
data class User(
    var userName: String = "",
    val password: String = "",
    var phoneNumber: String = "",
    var address: String = "",
    var emailId: String = "",
    var itemSupplied: ArrayList<String> = ArrayList(),
    var pendingRequests: ArrayList<User?> = ArrayList(),
    var registeredVendors: ArrayList<User?> = ArrayList(),
    var registeredCustomers: ArrayList<User?> = ArrayList()
)
