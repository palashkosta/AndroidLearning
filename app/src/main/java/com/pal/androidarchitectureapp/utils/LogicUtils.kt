package com.pal.androidarchitectureapp.utils

import android.util.Log
import javax.inject.Inject

class LogicUtils @Inject constructor() {
    fun showMessage() {
        Log.d("BUGS", "Message")
    }
}