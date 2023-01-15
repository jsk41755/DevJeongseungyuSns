package com.jeongseunggyu.devjeongseungyusns.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.jeongseunggyu.devjeongseungyusns.R
import com.jeongseunggyu.devjeongseungyusns.ui.components.BaseButton
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsButtonType
import com.jeongseunggyu.devjeongseungyusns.ui.theme.Dark
import com.jeongseunggyu.devjeongseungyusns.ui.theme.Gray

@Composable
fun WelcomeScreen(){
    Column(
        modifier = Modifier.padding(horizontal = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        lottieAnimationView()

        Text("정승규 SNS 앱", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.weight(1f))

        //로그인 버튼
        BaseButton(title = "로그인", onClick = {
            Log.d("웰컴스크린", "로그인 버튼 클릭")
        })
        
        Spacer(modifier = Modifier.height(15.dp))

        //회원가입 버튼
        BaseButton(
            type = SnsButtonType.OUTLINE,
            title = "회원가입",
            onClick = {
            Log.d("웰컴스크린", "회원가입 버튼 클릭")
        })

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun lottieAnimationView() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .Asset("social_animation.json"))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.height(400.dp)
    )
}