package com.example.myapplication

import android.app.Activity

open class SampleTest(var userName: String, var pwd: String): Activity(), ISample {

    private var index: Int ?= 0
    private val ind:String ?= null
    private var addr: String = ""

    constructor(address: String): this("","") {
        addr = address
    }

    override fun setup(): String {
        index = 0
        return userName + pwd
    }

    open fun createInterfaceObject() {
        val obj = object: ISample {
            override fun setup(): String {
                return ""
            }

        }
        obj.setup()
        var holder = Holder(userName, pwd)
        holder.incrementAge(10)
        holder = holder.copy(userName = "Mohit", pwd="")
    }

    inner class InnerClass {
        fun doNothing() {}
    }

    companion object {
        private const val STRING = ""

        @JvmStatic
        fun doSample() {
            print("Hello")
        }
    }
}

class Inheritance(var name: String, password: String): SampleTest(name, password) {
    fun doNothing() {
        name
    }

    override fun createInterfaceObject() {

    }
}