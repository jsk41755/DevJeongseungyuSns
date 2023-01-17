package com.jeongseunggyu.devjeongseungyusns.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.jeongseunggyu.devjeongseungyusns.network.data.Post
import com.jeongseunggyu.devjeongseungyusns.routes.AuthRouteAction
import com.jeongseunggyu.devjeongseungyusns.routes.MainRoute
import com.jeongseunggyu.devjeongseungyusns.routes.MainRouteAction
import com.jeongseunggyu.devjeongseungyusns.ui.components.SimpleDialog
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsAddPostButton
import com.jeongseunggyu.devjeongseungyusns.ui.components.SnsDialogAction
import com.jeongseunggyu.devjeongseungyusns.ui.theme.Dark
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AuthViewModel
import com.jeongseunggyu.devjeongseungyusns.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel, //API 땡기기
    authViewModel: AuthViewModel,
    routeAction: MainRouteAction
){
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()

    val isLoading by homeViewModel.isLoadingFlow.collectAsState()

    val postsListScrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    var selectedPostIdForDelete: String? by remember {
        mutableStateOf(null)
    }

    val isDialogShown = !selectedPostIdForDelete.isNullOrBlank()

    val posts = homeViewModel.postFlow.collectAsState()


    LaunchedEffect(key1 = Unit, block = {
        homeViewModel.dataUpdatedFlow.collectLatest {
            selectedPostIdForDelete = null
            postsListScrollState.animateScrollToItem(posts.value.size)
        }
    })

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { homeViewModel.refreshData() },
        ) {

            Column() {
                Surface(
                    color = Dark,
                    contentColor = Color.White
                ) {
                    Text(text = "총 포스팅  ${posts.value.size}",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    )
                }

                LazyColumn(
                    state = postsListScrollState,
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(20.dp),
                    reverseLayout = true
                ) {
                    items(posts.value) { aPost ->
                        PostItemView(aPost,
                            coroutineScope,
                            homeViewModel,
                            authViewModel,
                            onDeletePostClicked = {
                                selectedPostIdForDelete = aPost.id.toString()
                            })
                    }
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

            if (isLoading){
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .scale(0.7f)
                        .padding(5.dp)
                )
            }

            if(isDialogShown) {
                SimpleDialog(isLoading,
                    onDialogAction = {
                        when(it) {
                            SnsDialogAction.CLOSE -> selectedPostIdForDelete = null
                            SnsDialogAction.ACTION -> {
                                println("아이템 삭제해야함 $selectedPostIdForDelete")
                                selectedPostIdForDelete?.let { postId ->
                                    homeViewModel.deletePostItem(postId)
                                }
                            }
                        }
                    })
            }
        }
    }
}

@Composable
fun PostItemView(
    data : Post,
    coroutineScope : CoroutineScope,
    homeViewModel: HomeViewModel,
    authViewModel: AuthViewModel,
    onDeletePostClicked: () -> Unit){

    val currentUserId =authViewModel.currentUserIdFlow.collectAsState()

    Surface(
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Column() {
            
            Text(text = "userId: ${data.userID}")
            
            Row() {
                Text(
                    text = "${data.id}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )

                if (currentUserId.value == data.userID.toString())
                Row() {
                    TextButton(onClick = onDeletePostClicked) {
                        Text(text = "삭제")
                    }

                    TextButton(onClick = {
                        coroutineScope.launch {
                            homeViewModel
                                .navAction
                                .emit(MainRoute.EditPost(postId = "${data.id}"))
                        }
                    }) {
                        Text(text = "수정")
                    }
                }

            }
            Text(
                text = "${data.title}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "${data.content}",
                maxLines = 5,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            )
        }
    }
}