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
import androidx.compose.runtime.*
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

    val emailInput = authViewModel.emailInputFlow.collectAsState()

    val passwordInput = authViewModel.passwordInputFlow.collectAsState()

    val isLoginBtnActive =
        emailInput.value.isNotEmpty() && passwordInput.value.isNotEmpty()

    val isLoading = authViewModel.isLoadingFlow.collectAsState()

    val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier.padding(horizontal = 22.dp)
        ) {

            SnsBackButton(
                modifier = Modifier.padding(vertical = 20.dp),
                onClick = {
                Log.d("???????????????", "???????????? ?????? ??????")
                routeAction.goBack()
            })

            Text("????????? ??????",
                fontSize = 30.sp,
                modifier = Modifier.padding(30.dp)
            )

        SnsTextField(label = "?????????", value = emailInput.value, onValueChanged = {
            coroutineScope.launch {
                authViewModel.emailInputFlow.emit(it)
            }
        })

        Spacer(modifier = Modifier.height(30.dp))

        SnsPasswordTextField(label = "????????????", value = passwordInput.value, onValueChanged = {
            coroutineScope.launch {
                authViewModel.passwordInputFlow.emit(it)
            }
        })

        Spacer(modifier = Modifier.height(30.dp))

        //????????? ??????
        BaseButton(
            title = "?????????",
            enabled = isLoginBtnActive,
            isLoading = isLoading.value,
            onClick = {
            Log.d("???????????????", "????????? ?????? ??????")
                authViewModel.login()
        })

        Spacer(modifier = Modifier.height(15.dp))
            
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "????????? ????????????????")
            TextButton(onClick = {
                coroutineScope.launch {
                    authViewModel.clearInputs()
                }
                routeAction.navTo(AuthRoute.REGISTER)
            }) {
                Text(text = "???????????? ????????????")
            }
        }

            /*TextButton(onClick = {
                coroutineScope.launch {
                    authViewModel.isLoggedIn.emit(true)
                }
            }) {
                Text(text = "????????? ??????")
            }*/
    }
}
