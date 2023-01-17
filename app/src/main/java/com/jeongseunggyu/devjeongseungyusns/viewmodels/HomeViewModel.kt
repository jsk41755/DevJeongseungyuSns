package com.jeongseunggyu.devjeongseungyusns.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jeongseunggyu.devjeongseungyusns.routes.MainRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel(){

    val isRefreshing = MutableStateFlow<Boolean>(false)

    val isLoading = MutableStateFlow<Boolean>(false)

    val navAction = MutableSharedFlow<MainRoute>()

    companion object{
        const val TAG = "홈뷰모델"
    }

    fun refreshData(){
        Log.d(TAG, "refreshData() called")
    }
}