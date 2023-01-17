package com.jeongseunggyu.devjeongseungyusns.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jeongseunggyu.devjeongseungyusns.routes.MainRouteAction
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AuthViewModel
import com.jeongseunggyu.devjeongseungyusns.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun MyPageScreen(
    homeViewModel: HomeViewModel, //API 땡기기
    authViewModel: AuthViewModel,
    routeAction: MainRouteAction
){
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()

    val postsListScrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "마이페이지")
        TextButton(onClick = {
            coroutineScope.launch {
                authViewModel.isLoggedIn.emit(false)
            }
        }) {
            Text(text = "로그아웃")
        }
    }
}
