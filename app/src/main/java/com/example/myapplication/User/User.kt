package com.example.myapplication.User

import com.example.myapplication.Item

/**
 * User Info
 */
data class User(
    var name: String,
    var phoneNumber: String,
    var address: String?,
    var userType: UserType = UserType.NONE,
    var emailId: String?,
    val userCred: UserCred,
    var itemSupplied: Item = Item.NONE,
    var itemRequired: Item = Item.NONE
)