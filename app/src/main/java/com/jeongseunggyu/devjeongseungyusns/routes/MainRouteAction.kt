package com.jeongseunggyu.devjeongseungyusns.routes

import androidx.navigation.NavHostController
import com.jeongseunggyu.devjeongseungyusns.R

sealed class MainRoute(
    open val routeName: String,
    open val title: String,
    open val iconResId: Int? = null
){
    object Home: MainRoute("HOME", "홈", R.drawable.home)
    object MyPage: MainRoute("MY_PAGE", "마이페이지", R.drawable.profile)
    object AddPost: MainRoute("ADD_POST", "포스트 추가")
    class EditPost(val postId: String): MainRoute("Edit_POST", "포스트 수정")
}

// 메인 관련 화면 라우트 액션
class MainRouteAction(navHostController: NavHostController) {

    //특정 라우트로 이동
    val navTo: (MainRoute) -> Unit = { route ->
        navHostController.navigate(route.routeName){
            popUpTo(route.routeName) {  inclusive = true } //화면이동 스택이 쌓지 않게 하려고
        }
    }

    // 뒤로가기 이동(이벤트 터뜨리기)
    val goBack: () -> Unit = {
        navHostController.navigateUp()
    }
}