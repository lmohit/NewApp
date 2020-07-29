package com.example.myapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SampleViewModel: ViewModel() {

    val _sampleText = MutableLiveData<String>()
    val sampleText: LiveData<String>
        get() = _sampleText


    init {
        Log.d(TAG, "init block of SampleViewModel")
        _sampleText.value = "Default"
    }

    fun setSomeSampleText() {
        _sampleText.value = "Hello Mohit"
    }

    companion object {
        private const val TAG = "SampleViewModel"
        private const val BASE_URL = "https://mars.udacity.com/"
    }
}