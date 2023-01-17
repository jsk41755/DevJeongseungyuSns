package com.jeongseunggyu.devjeongseungyusns.ui.screens.auth

import android.util.ArrayMap
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeongseunggyu.devjeongseungyusns.ui.components.BaseButton
import com.jeongseunggyu.devjeongseungyusns.ui.theme.Border
import com.jeongseunggyu.devjeongseungyusns.ui.theme.Gray
import com.jeongseunggyu.devjeongseungyusns.ui.theme.LightGray
import com.jeongseunggyu.devjeongseungyusns.R
import com.jeongseunggyu.devjeongseungyusns.routes.AuthRoute
import com.jeongseunggyu.devjeongseungyusns.routes.AuthRouteAction
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsBackButton
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsPasswordTextField
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsTextField
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    routeAction: AuthRouteAction,
    authViewModel: AuthViewModel
){

    val emailInput = remember {
        mutableStateOf("")
    }

    val passwordInput = remember {
        mutableStateOf("")
    }

    val isLoginBtnActive =
        emailInput.value.isNotEmpty() && passwordInput.value.isNotEmpty()

    val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier.padding(horizontal = 22.dp)
        ) {

            SnsBackButton(
                modifier = Modifier.padding(vertical = 20.dp),
                onClick = {
                Log.d("로그인화면", "뒤로가기 버튼 클릭")
                routeAction.goBack()
            })

            Text("로그인 화면",
                fontSize = 30.sp,
                modifier = Modifier.padding(30.dp)
            )

        SnsTextField(label = "이메일", value = emailInput.value, onValueChanged = {
            emailInput.value = it
        })

        Spacer(modifier = Modifier.height(30.dp))

        SnsPasswordTextField(label = "비밀번호", value = passwordInput.value, onValueChanged = {
            passwordInput.value = it
        })

        Spacer(modifier = Modifier.height(30.dp))

        //로그인 버튼
        BaseButton(
            title = "로그인",
            enabled = isLoginBtnActive,
            onClick = {
            Log.d("웰컴스크린", "로그인 버튼 클릭")
        })

        Spacer(modifier = Modifier.height(15.dp))
            
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "계정이 없으신가요?")
            TextButton(onClick = {
                routeAction.navTo(AuthRoute.REGISTER)
            }) {
                Text(text = "회원가입 하러가기")
            }
        }

            TextButton(onClick = {
                coroutineScope.launch {
                    authViewModel.isLoggedIn.emit(true)
                }
            }) {
                Text(text = "로그인 완료")
            }
    }
}
