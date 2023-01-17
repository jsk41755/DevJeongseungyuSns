package com.jeongseunggyu.devjeongseungyusns.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    routeAction: AuthRouteAction){

    val emailInput = authViewModel.emailInputFlow.collectAsState()
    val passwordInput = authViewModel.passwordInputFlow.collectAsState()
    val passwordConfirmInput = authViewModel.passwordConfirmInputFlow.collectAsState()

    val isPasswordsNotEmpty = passwordInput.value.isNotEmpty() &&
            passwordConfirmInput.value.isNotEmpty()

    val isPasswordsMatched = passwordInput.value == passwordConfirmInput.value

    val isRegisterBtnActive =
        emailInput.value.isNotEmpty() && isPasswordsNotEmpty && isPasswordsMatched

    val scrollState = rememberScrollState()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    val isLoading = authViewModel.isLoadingFlow.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        // 회원가입 성공 이벤트
        authViewModel.registerCompleteFlow.collectLatest {
            snackBarHostState
                .showSnackbar(
                    "회원가입 완료! 로그인 해주세요!",
                    actionLabel = "확인", SnackbarDuration.Short)
                .let {
                    when(it) {
                        SnackbarResult.Dismissed -> Log.d("TAG", "스낵바 닫힘")
                        SnackbarResult.ActionPerformed -> {
                            routeAction.navTo(AuthRoute.LOGIN)
                        }
                    }
                }
        }
    })

    Column(
        modifier = Modifier
            .verticalScroll(scrollState, true) //키보드 올라가는건 Modifier담당
            .padding(horizontal = 22.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)   //균일하게 공간 나누기
    ) {

        SnsBackButton(
            modifier = Modifier.padding(top = 10.dp),
            onClick = {
                Log.d("회원가입화면", "뒤로가기 버튼 클릭")
                routeAction.goBack()
            })

        Text("회원가입 화면",
            fontSize = 30.sp,
            modifier = Modifier.padding(10.dp)
        )

        SnsTextField(label = "이메일", value = emailInput.value, onValueChanged = {
            coroutineScope.launch {
                authViewModel.emailInputFlow.emit(it)
            }
        })

        SnsPasswordTextField(label = "비밀번호", value = passwordInput.value, onValueChanged = {
            coroutineScope.launch {
                authViewModel.passwordInputFlow.emit(it)
            }
        })

        SnsPasswordTextField(label = "비밀번호 확인", value = passwordConfirmInput.value, onValueChanged = {
            coroutineScope.launch {
                authViewModel.passwordConfirmInputFlow.emit(it)
            }
        })
        
        Spacer(modifier = Modifier.height(15.dp))

        //로그인 버튼
        BaseButton(
            title = "회원가입",
            enabled = isRegisterBtnActive,
            isLoading = isLoading.value,
            onClick = {
                Log.d("회원가입화면", "회원가입 버튼 클릭")
                if(!isLoading.value) {  //로딩중일 때 API 쏘지 않게 하는 방법(중요)
                    authViewModel.register()
                }
            })

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "이미 계정이 있으신가요?")
            TextButton(onClick = {
                coroutineScope.launch { authViewModel.clearInputs() }
                routeAction.navTo(AuthRoute.LOGIN)
            }) {
                Text(text = "로그인 하러가기")
            }
        }

        SnackbarHost(hostState = snackBarHostState)
    }
}