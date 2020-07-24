package com.example.myapplication

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val a: Int
        var b: Int
        val str = "string"
        val arr = arrayOf("str", "str1", 1)

        // Immutable Objects
        val immutableList = listOf(1,2,3,4,5)
        val stringSet = setOf(1,2,3,4,5,6)
        val intMap = mapOf<String, String>()

        // Mutable List
        val list = mutableListOf<String>()
        list.add("mohit")
        qkjdnqkjndwqjkd
        val arrList = ArrayList<String>()
        arrList.add("1")
        val stringMap = HashMap<String, String>()
        stringMap["1"] = "2"

        for (i in list) {
            println(i)
        }
        val sampleTest = SampleTest("lmohit95@gmail.com", "1234")
        SampleTest.doSample()
    }
}