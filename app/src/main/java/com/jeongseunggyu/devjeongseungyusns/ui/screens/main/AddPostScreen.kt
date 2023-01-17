package com.jeongseunggyu.devjeongseungyusns.ui.screens.main

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeongseunggyu.devjeongseungyusns.routes.AuthRoute
import com.jeongseunggyu.devjeongseungyusns.routes.AuthRouteAction
import com.jeongseunggyu.devjeongseungyusns.ui.components.BaseButton
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsBackButton
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsPasswordTextField
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsTextField
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AddPostViewModel
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun AddPostScreen(
    addPostViewModel: AddPostViewModel
){

    val titleInput = remember {
        mutableStateOf("")
    }
    val contentInput = remember {
        mutableStateOf("")
    }

    val isAddPostBtnActive =
        titleInput.value.isNotEmpty()

    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

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
                titleInput.value = it
            })

        Spacer(modifier = Modifier.height(15.dp))

        SnsTextField(
            modifier = Modifier.height(300.dp),
            label = "내용",
            value = contentInput.value,
            singleLine = false,
            onValueChanged = {
                contentInput.value = it
            })

        Spacer(modifier = Modifier.height(30.dp))

        BaseButton(
            title = "포스트 올리기",
            enabled = isAddPostBtnActive,
            onClick = {
                Log.d("포스트 추가", "포스트 올리기 버튼 ")
            })

        Spacer(modifier = Modifier.weight(1f))

    }
}