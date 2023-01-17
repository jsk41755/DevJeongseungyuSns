package com.jeongseunggyu.devjeongseungyusns.ui.screens.main

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeongseunggyu.devjeongseungyusns.MainActivity
import com.jeongseunggyu.devjeongseungyusns.routes.ActivityCloseAction
import com.jeongseunggyu.devjeongseungyusns.routes.ActivityCloseActionName
import com.jeongseunggyu.devjeongseungyusns.ui.components.BaseButton
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsTextField
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AddPostViewModel
import com.jeongseunggyu.devjeongseungyusns.viewmodels.EditPostViewModel
import com.jeongseunggyu.devjeongseungyusns.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun EditPostScreen(
    editPostViewModel: EditPostViewModel
){

    val titleInput = editPostViewModel.titleInputFlow.collectAsState()
    val contentInput = editPostViewModel.contentInputFlow.collectAsState()

    val isAddPostBtnActive =
        titleInput.value.isNotEmpty()

    val coroutineScope = rememberCoroutineScope()

    val isLoading = editPostViewModel.isLoadingFlow.collectAsState()

    val scrollState = rememberScrollState()

    val isEditPostLoading = editPostViewModel.isEditPostLoadingFlow.collectAsState()

    val snackbarHostState = remember{SnackbarHostState()}

    val activity = (LocalContext.current as? Activity)

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        editPostViewModel.editPostCompleteFlow.collectLatest {
            snackbarHostState
                .showSnackbar(
                    "포스트가 수정 되었습니다.",
                    actionLabel = "홈으로", SnackbarDuration.Short
                ).let {
                    when(it){
                        SnackbarResult.Dismissed -> Log.d(HomeViewModel.TAG, "스낵바 닫힘")
                        SnackbarResult.ActionPerformed -> {
                            val intent = Intent(context, MainActivity::class.java).apply {
                                putExtra(ActivityCloseActionName, ActivityCloseAction.POST_EDITED.actionName)
                            }
                            activity?.setResult(Activity.RESULT_OK, intent)
                            activity?.finish()
                        }
                    }
                }
        }
    })
    
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .verticalScroll(scrollState, enabled = true)
                .padding(horizontal = 22.dp)

        ) {

            Text("포스트 수정 화면",
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            SnsTextField(label = "제목",
                value = titleInput.value,
                onValueChanged = {
                    coroutineScope.launch{
                        editPostViewModel.titleInputFlow.emit(it)
                    }
                })

            Spacer(modifier = Modifier.height(15.dp))

            SnsTextField(
                modifier = Modifier.height(300.dp),
                label = "내용",
                value = contentInput.value,
                singleLine = false,
                onValueChanged = {
                    coroutineScope.launch{
                        editPostViewModel.contentInputFlow.emit(it)
                    }
                })

            Spacer(modifier = Modifier.height(30.dp))

            BaseButton(
                title = "포스트 수정하기",
                enabled = isAddPostBtnActive,
                isLoading = isEditPostLoading.value,
                onClick = {
                    Log.d("포스트 수정", "포스트 수정하기 버튼 ")

                    if(!isEditPostLoading.value){
                        editPostViewModel.editPost()
                    }
                })

            Spacer(modifier = Modifier.weight(1f))

            SnackbarHost(hostState = snackbarHostState)
        }
    }

    if(isLoading.value){
        Surface(
            color = Color.Black,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f)
        ) {
            Box(
                modifier = Modifier.size(40.dp)
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
    


}

