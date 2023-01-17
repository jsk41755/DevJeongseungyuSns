package com.jeongseunggyu.devjeongseungyusns.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongseunggyu.devjeongseungyusns.network.AuthRepository
import com.jeongseunggyu.devjeongseungyusns.network.PostRepository
import com.jeongseunggyu.devjeongseungyusns.network.data.Post
import com.jeongseunggyu.devjeongseungyusns.routes.MainRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel(){

    val isRefreshing = MutableStateFlow<Boolean>(false)

    val isLoadingFlow = MutableStateFlow<Boolean>(false)

    val postFlow = MutableStateFlow<List<Post>>(emptyList())

    val navAction = MutableSharedFlow<MainRoute>()

    val dataUpdatedFlow = MutableSharedFlow<Unit>()

    companion object{
        const val TAG = "홈뷰모델"
    }

    init {
        fetchPost()
    }

    fun refreshData(){
        Log.d(TAG, "refreshData() called")
        viewModelScope.launch{
            isLoadingFlow.emit(true)
            delay(1000)
            callFetchPosts()
        }
    }

    fun fetchPost(){
        viewModelScope.launch { callFetchPosts() }
    }

    private suspend fun callFetchPosts(){
        withContext(Dispatchers.IO){
            kotlin.runCatching {
                isLoadingFlow.emit(true)
                delay(1500)
                PostRepository.fetchAllPosts()
            }.onSuccess {
                postFlow.emit(it)
                dataUpdatedFlow.emit(Unit)
                isLoadingFlow.emit(false)
            }.onFailure {
                Log.d(TAG, "회원가입 실패 - onFailure error: ${it.localizedMessage}")
                isLoadingFlow.emit(false)
            }
        }
    }

}