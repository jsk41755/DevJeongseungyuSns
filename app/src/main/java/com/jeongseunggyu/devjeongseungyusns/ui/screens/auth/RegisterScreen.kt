package com.jeongseunggyu.devjeongseungyusns.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun RegisterScreen(routeAction: AuthRouteAction){

    val emailInput = remember {
        mutableStateOf("")
    }

    val passwordInput = remember {
        mutableStateOf("")
    }

    val passwordConfirmInput = remember {
        mutableStateOf("")
    }

    val isPasswordsNotEmpty = passwordInput.value.isNotEmpty() &&
            passwordConfirmInput.value.isNotEmpty()

    val isPasswordsMatched = passwordInput.value == passwordConfirmInput.value

    val isRegisterBtnActive =
        emailInput.value.isNotEmpty() && isPasswordsNotEmpty && isPasswordsMatched

    val scrollState = rememberScrollState()

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
            emailInput.value = it
        })

        SnsPasswordTextField(label = "비밀번호", value = passwordInput.value, onValueChanged = {
            passwordInput.value = it
        })

        SnsPasswordTextField(label = "비밀번호 확인", value = passwordConfirmInput.value, onValueChanged = {
            passwordConfirmInput.value = it
        })
        
        Spacer(modifier = Modifier.height(15.dp))

        //로그인 버튼
        BaseButton(
            title = "회원가입",
            enabled = isRegisterBtnActive,
            onClick = {
                Log.d("회원가입화면", "회원가입 버튼 클릭")
            })

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "이미 계정이 있으신가요?")
            TextButton(onClick = { routeAction.navTo(AuthRoute.LOGIN) }) {
                Text(text = "로그인 하러가기")
            }
        }
    }
}