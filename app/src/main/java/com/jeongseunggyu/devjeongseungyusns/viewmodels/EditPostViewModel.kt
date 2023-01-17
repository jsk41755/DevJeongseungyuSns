package com.jeongseunggyu.devjeongseungyusns.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel

class EditPostViewModel(val currentPostID: String): ViewModel() {

    companion object{
        const val TAG = "EditPostViewModel"
    }

    init {
        Log.d(TAG, "EditPostViewModel : Init / PostId: $currentPostID")
    }

}