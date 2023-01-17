package com.jeongseunggyu.devjeongseungyusns

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jeongseunggyu.devjeongseungyusns.routes.*
import com.jeongseunggyu.devjeongseungyusns.ui.screens.auth.LoginScreen
import com.jeongseunggyu.devjeongseungyusns.ui.screens.auth.RegisterScreen
import com.jeongseunggyu.devjeongseungyusns.ui.screens.auth.WelcomeScreen
import com.jeongseunggyu.devjeongseungyusns.ui.screens.main.HomeScreen
import com.jeongseunggyu.devjeongseungyusns.ui.screens.main.MyPageScreen
import com.jeongseunggyu.devjeongseungyusns.ui.theme.Dark
import com.jeongseunggyu.devjeongseungyusns.ui.theme.DevJeongseungyuSnsTheme
import com.jeongseunggyu.devjeongseungyusns.ui.theme.Gray
import com.jeongseunggyu.devjeongseungyusns.ui.theme.LightGray
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AuthViewModel
import com.jeongseunggyu.devjeongseungyusns.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    //액티비티가 닫아질 때 이벤트
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK){
            val getActionString = result.data?.getStringExtra("CLOSE_ACTION")
            val closeAction : ActivityCloseAction? = ActivityCloseAction.getActionType(
                ActivityCloseActionName)
            closeAction?.let {
                homeViewModel.refreshData()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(
            WindowManager
                .LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )


        lifecycleScope.launch {
            homeViewModel.navAction.collectLatest {
                when(it) {
                    is MainRoute.AddPost -> {
                        //startActivity(AddPostActivity.newIntent(this@MainActivity))
                        activityResultLauncher.launch(AddPostActivity.newIntent(this@MainActivity))
                    }
                    is MainRoute.EditPost -> {
                        val intent = EditPostActivity.newIntent(this@MainActivity, it.postId)
                        activityResultLauncher.launch(intent)
                    }
                    else -> {}
                }
            }
        }

        setContent {
            DevJeongseungyuSnsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppScreen(authViewModel, homeViewModel)
                }
            }
        }
    }
}

@Composable
fun AppScreen(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
){

    val isLoggedIn = authViewModel.isLoggedIn.collectAsState()

    val mainNavController = rememberNavController()
    val mainRouteAction = remember(mainNavController){
        MainRouteAction(mainNavController)
    }
    
    val authNavController = rememberNavController()
    val authRouteAction = remember(authNavController){
        AuthRouteAction(authNavController)
    }

    //현재 뒤로가기 스택의 마지막을 가져옴
    val authBackStackEntry = authNavController.currentBackStackEntryAsState()

    val mainBackStack = mainNavController.currentBackStackEntryAsState()

    if (!isLoggedIn.value){
        AuthNavHost(
            authNavController = authNavController,
            authViewModel = authViewModel,
            routeAction = authRouteAction
        )
    } else {
        Scaffold(bottomBar = {
            SnsBottomNav(mainRouteAction, mainBackStack.value)
        }) {
            Column(
                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
            ) {
                MainNavHost( mainNavController,
                    authViewModel = authViewModel,
                    homeViewModel = homeViewModel,
                    routeAction = mainRouteAction)
            }
        }


    }

}

@Composable
fun MainNavHost(
    mainNavController: NavHostController,//네비게이션 호스트 컨트롤러 받기
    startRouter: MainRoute = MainRoute.Home, //웰컴에서 시작
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    routeAction: MainRouteAction
) {
    NavHost(
        navController = mainNavController,
        startDestination = startRouter.routeName){
        composable(MainRoute.Home.routeName){
            HomeScreen(homeViewModel, authViewModel, routeAction)
        }
        composable(MainRoute.MyPage.routeName){
            MyPageScreen(homeViewModel, authViewModel, routeAction)
        }
    }
}

//컴포저블에서 네비게이션 처리법
@Composable
fun AuthNavHost(
    authNavController: NavHostController,//네비게이션 호스트 컨트롤러 받기
    startRouter: AuthRoute = AuthRoute.WELCOME, //웰컴에서 시작
    authViewModel: AuthViewModel,
    routeAction: AuthRouteAction
) {
    NavHost(
        navController = authNavController,
        startDestination = startRouter.routeName){
        composable(AuthRoute.WELCOME.routeName){
            WelcomeScreen(routeAction)
        }
        composable(AuthRoute.LOGIN.routeName){
            LoginScreen(routeAction, authViewModel)
        }
        composable(AuthRoute.REGISTER.routeName){
            RegisterScreen(authViewModel, routeAction)
        }
    }
}

@Composable
fun SnsBottomNav(
    mainRouteAction: MainRouteAction,
    mainBackStack: NavBackStackEntry?
){
    val bottomRoutes = listOf<MainRoute>(MainRoute.Home, MainRoute.MyPage)
    BottomNavigation(
        backgroundColor = LightGray,
        modifier = Modifier.fillMaxWidth()
    ) {
        bottomRoutes.forEach {
            BottomNavigationItem(
                label = { Text(text = it.title)},
                icon = {
                       it.iconResId?.let { iconId ->
                           Icon(painter = painterResource(iconId),
                               contentDescription = it.title)
                       }
                },
                selectedContentColor = Dark,
                unselectedContentColor = Gray,
                selected = (mainBackStack?.destination?.route) == it.routeName,
                onClick = { mainRouteAction.navTo(it) })
            }
        }
    }

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DevJeongseungyuSnsTheme {
        Greeting("Android")
    }
}