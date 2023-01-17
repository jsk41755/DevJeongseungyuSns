package com.jeongseunggyu.devjeongseungyusns.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jeongseunggyu.devjeongseungyusns.routes.AuthRouteAction
import com.jeongseunggyu.devjeongseungyusns.routes.MainRoute
import com.jeongseunggyu.devjeongseungyusns.routes.MainRouteAction
import com.jeongseunggyu.devjeongseungyusns.ui.components.SimpleDialog
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsAddPostButton
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsDialogAction
import com.jeongseunggyu.devjeongseungyusns.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel, //API 땡기기
    routeAction: MainRouteAction
){
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()

    val isLoading by homeViewModel.isLoading.collectAsState()

    val postsListScrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    var selectedPostIdForDelete: String? by remember {
        mutableStateOf(null)
    }

    val isDialogShown = !selectedPostIdForDelete.isNullOrBlank()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { homeViewModel.refreshData() },
        ) {
            LazyColumn(
                state = postsListScrollState,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp),
                reverseLayout = true
            ) {
                items(30) { index ->
                    PostItemView(index,
                        coroutineScope,
                        homeViewModel,
                        onDeletePostClicked = {
                            selectedPostIdForDelete = index.toString()
                        })
                }
            }

            SnsAddPostButton(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    coroutineScope.launch {
                        homeViewModel.navAction.emit(MainRoute.AddPost)
                    }
                })

            if(isDialogShown) {
                SimpleDialog(isLoading,
                    onDialogAction = {
                        when(it) {
                            SnsDialogAction.CLOSE -> selectedPostIdForDelete = null
                            SnsDialogAction.ACTION -> {
                                println("아이템 삭제해야함 $selectedPostIdForDelete")
                            }
                        }
                    })
            }
        }
    }
}

@Composable
fun PostItemView(
    index : Int,
    coroutineScope : CoroutineScope,
    homeViewModel: HomeViewModel,
    onDeletePostClicked: () -> Unit){
    Surface(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Column() {
            Row() {
                Text(
                    text = "$index",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )

                TextButton(onClick = onDeletePostClicked) {
                    Text(text = "삭제")
                }

                TextButton(onClick = {
                    coroutineScope.launch {
                        homeViewModel
                            .navAction
                            .emit(MainRoute.EditPost(postId = "$index"))
                    }
                }) {
                    Text(text = "수정")

                }
            }
            Text(
                text = "$index - title",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "$index - content",
                maxLines = 5,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            )
        }
    }
}