package com.example.myapplication

data class Holder(val userName: String, val pwd: String) {
    var age: Int = 10

    fun incrementAge(increment: Int) {
        age+=increment
    }
}