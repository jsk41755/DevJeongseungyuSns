package com.jeongseunggyu.devjeongseungyusns.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongseunggyu.devjeongseungyusns.network.PostRepository
import com.jeongseunggyu.devjeongseungyusns.network.data.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditPostViewModel(val currentPostID: String): ViewModel() {

    companion object{
        const val TAG = "EditPostViewModel"
    }

    var isLoadingFlow = MutableStateFlow<Boolean>(false)

    var isEditPostLoadingFlow = MutableStateFlow<Boolean>(false)

    var editPostCompleteFlow = MutableSharedFlow<Unit>()

    var titleInputFlow = MutableStateFlow<String>("")
    var contentInputFlow = MutableStateFlow<String>("")

    init {
        Log.d(TAG, "EditPostViewModel : Init / PostId: $currentPostID")

        fetchCurrentPostItem()
    }


    fun fetchCurrentPostItem(){
        viewModelScope.launch { callFetchPostItem() }
    }

    fun editPost(){
        viewModelScope.launch { callEditPostItem() }
    }

    private suspend fun callFetchPostItem(){
        withContext(Dispatchers.IO){
            kotlin.runCatching {
                isLoadingFlow.emit(true)
                delay(1500)
                PostRepository.fetchPostItem(currentPostID)
            }.onSuccess {
                titleInputFlow.emit(it.title ?: "")
                contentInputFlow.emit(it.content ?: "")
                isLoadingFlow.emit(false)
            }.onFailure {
                Log.d(TAG, "현재 포스트 가져오기 실패 - onFailure error: ${it.localizedMessage}")
                isLoadingFlow.emit(false)
            }
        }
    }

    private suspend fun callEditPostItem(){
        withContext(Dispatchers.IO){
            kotlin.runCatching {
                isEditPostLoadingFlow.emit(true)
                delay(1500)
                PostRepository.editPostItem(
                    currentPostID,
                    titleInputFlow.value,
                    contentInputFlow.value
                )
            }.onSuccess {
                if(it.status.value == 200){
                    editPostCompleteFlow.emit(Unit)
                }
                isEditPostLoadingFlow.emit(false)
            }.onFailure {
                Log.d(TAG, "현재 포스트 가져오기 실패 - onFailure error: ${it.localizedMessage}")
                isEditPostLoadingFlow.emit(false)
            }
        }
    }

    suspend fun clearInputs(){
        titleInputFlow.emit("")
        contentInputFlow.emit("")
    }

}


