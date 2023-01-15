package com.jeongseunggyu.devjeongseungyusns.routes

import androidx.navigation.NavHostController

//인증 화면 라우트
enum class AuthRoute(val routeName: String){
    LOGIN("LOGIN"),
    REGISTER("REGISTER"),
    WELCOME("WELCOME")
}

//인증 화면 라우트 액션(화면 이동)
class AuthRouteAction(navHostController: NavHostController) {

    //특정 라우트로 이동
    val navTo: (AuthRoute) -> Unit = { authRoute ->
        navHostController.navigate(authRoute.routeName){
            popUpTo(authRoute.routeName) {  inclusive = true } //화면이동 스택이 쌓지 않게 하려고
        }
    }

    // 뒤로가기 이동(이벤트 터뜨리기)
    val goBack: () -> Unit = {
        navHostController.navigateUp()
    }
}