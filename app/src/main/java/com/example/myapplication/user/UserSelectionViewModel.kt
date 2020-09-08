package com.example.myapplication.user

import android.util.Log
import androidx.lifecycle.ViewModel

class UserSelectionViewModel : ViewModel() {

    init {
        Log.d(TAG, "UserSelectionViewModel Created");
    }

    companion object {
        private const val TAG = "UserSelectionViewModel"
    }
}