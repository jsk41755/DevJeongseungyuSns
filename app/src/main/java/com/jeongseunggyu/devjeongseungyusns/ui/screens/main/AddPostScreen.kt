package com.jeongseunggyu.devjeongseungyusns.ui.screens.main

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeongseunggyu.devjeongseungyusns.MainActivity
import com.jeongseunggyu.devjeongseungyusns.routes.ActivityCloseAction
import com.jeongseunggyu.devjeongseungyusns.routes.ActivityCloseActionName
import com.jeongseunggyu.devjeongseungyusns.routes.AuthRoute
import com.jeongseunggyu.devjeongseungyusns.routes.AuthRouteAction
import com.jeongseunggyu.devjeongseungyusns.ui.components.BaseButton
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsBackButton
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsPasswordTextField
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsTextField
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AddPostViewModel
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AuthViewModel
import com.jeongseunggyu.devjeongseungyusns.viewmodels.HomeViewModel.Companion.TAG
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddPostScreen(
    addPostViewModel: AddPostViewModel
){

    val titleInput = addPostViewModel.titleInputFlow.collectAsState()

    val contentInput = addPostViewModel.contentInputFlow.collectAsState()

    val isAddPostBtnActive =
        titleInput.value.isNotEmpty()

    val isLoading = addPostViewModel.isLoadingFlow.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    val snackbarHostState = remember{SnackbarHostState()}

    val activity = (LocalContext.current as? Activity)

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        addPostViewModel.addPostCompleteFlow.collectLatest {
            snackbarHostState
                .showSnackbar(
                    "포스트가 등록 되었습니다.",
                    actionLabel = "홈으로", SnackbarDuration.Short
                ).let {
                    when(it){
                        SnackbarResult.Dismissed -> Log.d(TAG, "스낵바 닫힘")
                        SnackbarResult.ActionPerformed -> {
                            val intent = Intent(context, MainActivity::class.java).apply {
                                putExtra(ActivityCloseActionName, ActivityCloseAction.POST_ADDED.actionName)
                            }
                            activity?.setResult(RESULT_OK, intent)
                            activity?.finish()
                        }
                    }
                }
        }
    })

    Column(
        modifier = Modifier
            .verticalScroll(scrollState, enabled = true)
            .padding(horizontal = 22.dp)

    ) {

        Text("포스트 추가 화면",
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 30.dp)
        )

        SnsTextField(label = "제목",
            value = titleInput.value,
            onValueChanged = {
                coroutineScope.launch {
                    addPostViewModel.titleInputFlow.emit(it)
                }
            })

        Spacer(modifier = Modifier.height(15.dp))

        SnsTextField(
            modifier = Modifier.height(300.dp),
            label = "내용",
            value = contentInput.value,
            singleLine = false,
            onValueChanged = {
                coroutineScope.launch {
                    addPostViewModel.contentInputFlow.emit(it)
                }
            })

        Spacer(modifier = Modifier.height(30.dp))

        BaseButton(
            title = "포스트 올리기",
            enabled = isAddPostBtnActive,
            isLoading = isLoading.value,
            onClick = {
                Log.d("포스트 추가", "포스트 올리기 버튼 ")
                if(!isLoading.value){
                    addPostViewModel.addPost()
                }
            })

        Spacer(modifier = Modifier.weight(1f))

        SnackbarHost(hostState = snackbarHostState)

    }
}